package com.clw.phaapp.ui.healthqa;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.clw.mysdk.utils.TimeUtils;
import com.clw.phaapp.R;
import com.clw.phaapp.adapter.AskAdapter;
import com.clw.phaapp.base.BaseMvpFragmentV4;
import com.clw.phaapp.common.CommonHintInfo;
import com.clw.phaapp.contract.AskContract;
import com.clw.phaapp.model.entity.AskEntity;
import com.clw.phaapp.presenter.healthqa.GetAskListPresenter;

import java.util.*;

/**
 * 健康问答Fragment，显示健康问答列表
 */
public class HealthQAFragment extends BaseMvpFragmentV4<AskContract.IGetAskListView, GetAskListPresenter>
        implements View.OnClickListener, AskContract.IGetAskListView,
        OnRefreshListener, OnLoadMoreListener {

    private static HealthQAFragment INSTANCE;
    private FloatingActionButton fab_publish_ask;

    /**
     * 记录问答的记录号，避免出现重复
     */
    private Set<Long> mDataSet=new HashSet<>();

    /**
     * 健康问答列表
     */
    private List<AskEntity> mDataList=new ArrayList<>();
    private RecyclerView swipe_target;
    private SwipeToLoadLayout swipeToLoad;
    private AskAdapter mAdapter;
    /**
     * 记录当前页数
     */
    int currentPage=0;

    /**
     * 获取实例对象
     *
     * @return
     */
    public static HealthQAFragment getINSTANCE() {
        if (null == INSTANCE) {
            INSTANCE = new HealthQAFragment();
        }
        return INSTANCE;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mBaseView = inflater.inflate(R.layout.fragment_health_ask, container, false);
        initFragment();
        initView();
        //初始化用户信息
        initUserInfoFromApplication();
        //初始化列表控件，要放到initView()方法之后
        initRecyclerview();
        return mBaseView;
    }

    @Override
    protected void initFragment() {
        //initSearchToolbar();
//        setToolbarNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showShortToast("搜索事件");
//            }
//        });
        initToolbarView();
        setToolbarCenterTitle("健康问答");
    }

    @Override
    protected void initView() {
        fab_publish_ask = (FloatingActionButton) mBaseView.findViewById(R.id.fab_publish_ask);
        fab_publish_ask.setOnClickListener(this);
        swipe_target = (RecyclerView) mBaseView.findViewById(R.id.swipe_target);
        swipeToLoad = (SwipeToLoadLayout) mBaseView.findViewById(R.id.swipeToLoad);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab_publish_ask:
                if (mPhaApplication != null && mPhaApplication.isLogin()) {
                    startActivity(PublishAskActivity.class);
                } else {
                    showShortToast("请登录后再发起问答");
                }
                break;
        }
    }


    /**
     * 初始化Recyclerview
     */
    private void initRecyclerview(){
        //监听下拉刷新
        swipeToLoad.setOnRefreshListener(this);
        //监听上拉加载更多
        swipeToLoad.setOnLoadMoreListener(this);
        //创建默认的线性LayoutManager
        swipe_target.setLayoutManager(new LinearLayoutManager(mContext));
        //设置Item增加、移除动画
        swipe_target.setItemAnimator(new DefaultItemAnimator());
        swipe_target.addItemDecoration(new DividerItemDecoration(mContext,
                DividerItemDecoration.VERTICAL));
        //创建并设置Adapter
        mAdapter = new AskAdapter(mDataList);
        //设置item的点击事件
        mAdapter.setItemClickListener(new AskAdapter.MyItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                AskEntity askEntity=(AskEntity)mDataList.get(position);
                Intent intent=new Intent(mContext,AskDatailActivity.class);
                intent.putExtra("ask",askEntity);
                startActivity(intent);
            }
        });
        swipe_target.setAdapter(mAdapter);
    }

    /**
     * 上拉加载更多
     */
    @Override
    public void onLoadMore() {
        if(!checkNetworkState()){
            showShortToast(CommonHintInfo.NO_NETWORDK);
            return ;
        }
        //添加查询参数
        AskEntity askEntity=new AskEntity();
        //选择已经通过审核的问答
        askEntity.setStatus((byte)1);
        askEntity.setPage(currentPage+1);
        mPresenter.loadMoreData(askEntity);
    }

    /**
     * 加载更多，提供给MainActivity调用
     */
    public void loadingMord(){
        swipeToLoad.setLoadingMore(true);
    }

    /**
     * 下拉刷新
     * 下拉刷新，只更新第一页的数据
     */
    @Override
    public void onRefresh() {
        if(!checkNetworkState()){
            showShortToast(CommonHintInfo.NO_NETWORDK);
            return ;
        }

        //添加查询参数
        AskEntity askEntity=new AskEntity();
        //选择已经通过审核的问答
        askEntity.setStatus((byte)1);
        askEntity.setPage(1);
        mPresenter.refreshData(askEntity);
    }

    /**
     * 下拉刷新，提供给MainActivity调用
     */
    public void refreshing(){
        swipeToLoad.setRefreshing(true);
    }

    /**
     * 实例化Presenter，注意是实现泛型指定的Presenter
     *
     * @return
     */
    @Override
    protected GetAskListPresenter initPresenter() {
        return new GetAskListPresenter();
    }

    /**
     * 获取问答成功
     */
    @Override
    public void getAskListSuccess(AskEntity askEntity) {
        dismissLoading();
        List<AskEntity> list = askEntity.getRows();
        if (list != null && list.size() > 0) {
            currentPage = askEntity.getPage();
            for(AskEntity entity:list){
                if(!mDataSet.contains(entity.getRecno())){
                    mDataList.add(entity);
                    mDataSet.add(entity.getRecno());
                }
            }
            dataSort();
            mAdapter.notifyDataSetChanged();
        } else{
            showShortToast("已无更多数据");
        }
    }


    /**
     * 数据排序，按时间先后排序
     */
    private void dataSort(){
        Collections.sort(mDataList,new Comparator<AskEntity>(){
            @Override
            public int compare(AskEntity arg0, AskEntity arg1) {
                Date date1= TimeUtils.getDate1(String.valueOf(arg0.getOpdate()));
                Date date2=TimeUtils.getDate1(String.valueOf(arg1.getOpdate()));
                int flag = date2.compareTo(date1);
                return flag;
            }

        });
    }


    /**
     * 下拉刷新数据成功回调
     * 下拉刷新，更新第一页
     *
     * @param askEntity
     */
    @Override
    public void refreshDataSuccess(AskEntity askEntity) {
        dismissLoading();

        //把原来的数据清理掉，重新刷新
        mDataList.clear();
        mDataSet.clear();

        List<AskEntity> list=askEntity.getRows();
        if(list!=null && list.size() > 0){
            currentPage = askEntity.getPage();
            //记录原来数据列表的个数
            int size = mDataList.size();
            for(AskEntity entity:list){
                if(!mDataSet.contains(entity.getRecno())){
                    mDataList.add(entity);
                    mDataSet.add(entity.getRecno());
                }
            }
            if(mDataList.size() > size){
                //有最新的数据
                dataSort();
                mAdapter.notifyDataSetChanged();
            }else{
                showShortToast("暂无更新");
            }
        }else{
            showShortToast("已无更多数据");
        }
    }

    /**
     * 上拉加载更多成功回调
     *
     * @param askEntity
     */
    @Override
    public void loadMoreDataSuccess(AskEntity askEntity) {
        dismissLoading();
        List<AskEntity> list=askEntity.getRows();
        if (list != null && list.size() > 0) {
            currentPage = askEntity.getPage();
            for(AskEntity entity:list){
                if(!mDataSet.contains(entity.getRecno())){
                    mDataList.add(entity);
                    mDataSet.add(entity.getRecno());
                }
            }
            dataSort();
            mAdapter.notifyDataSetChanged();
        } else {
            showShortToast("已无更多数据");
        }
    }

    /**
     * 获取问答失败
     */
    @Override
    public void getAskListFail() {
        dismissLoading();
    }

    /**
     * 关闭加载提示
     */
    @Override
    public void dismissLoading() {
        super.dismissLoading();
        //停止上拉加载更多
        swipeToLoad.setLoadingMore(false);
        //停止下拉刷新
        swipeToLoad.setRefreshing(false);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        currentPage = 0;
        mDataList.clear();
        mDataSet.clear();
    }

}

package com.clw.phaapp.ui.healthqa;

import android.os.Bundle;
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
import com.clw.phaapp.adapter.AskPassReviewAdapter;
import com.clw.phaapp.base.BaseMvpFragmentV4;
import com.clw.phaapp.common.CommonHintInfo;
import com.clw.phaapp.contract.AskContract;
import com.clw.phaapp.model.entity.AskEntity;
import com.clw.phaapp.presenter.healthqa.GetAskListPresenter;

import java.util.*;

/**
 * 健康问答   已审核，管理界面
 */
public class AskPassReviewFragment extends BaseMvpFragmentV4<AskContract.IGetAskListView, GetAskListPresenter>
        implements View.OnClickListener, AskContract.IGetAskListView,
        OnRefreshListener, OnLoadMoreListener {

    private static AskPassReviewFragment INSTANCE;

    /**
     * 记录问答的记录号，避免出现重复
     */
    private Set<Long> mDataSet=new HashSet<>();

    /**
     * 健康问答列表
     */
    private List<AskEntity> mDataList = new ArrayList<>();
    private RecyclerView swipe_target;
    private SwipeToLoadLayout swipeToLoad;
    private AskPassReviewAdapter mAdapter;
    /**
     * 记录当前页数
     */
    int currentPage = 0;

    /**
     * 加载数据标识
     * 0 第一次加载数据
     * 1 加载第一次数据完毕
     * 2 其它
     */
    int loadDataStatus=0;

    /**
     * 获取实例对象
     *
     * @return
     */
    public static AskPassReviewFragment getInstance() {
        if (null == INSTANCE) {
            INSTANCE = new AskPassReviewFragment();
        }
        return INSTANCE;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mBaseView = inflater.inflate(R.layout.fragment_ask_pass_review, container, false);
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

    }

    @Override
    protected void initView() {
        swipe_target = mBaseView.findViewById(R.id.swipe_target);
        swipeToLoad = mBaseView.findViewById(R.id.swipeToLoad);
    }

    @Override
    public void onClick(View v) {

    }


    @Override
    public void onResume() {
        super.onResume();
        if (loadDataStatus == 0) {
            initData();
        }
    }


    /**
     * 初始化Recyclerview
     */
    private void initRecyclerview() {
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
        mAdapter = new AskPassReviewAdapter(mDataList,this,mContext);
        //设置item的点击事件
        mAdapter.setItemClickListener(new AskPassReviewAdapter.MyItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

            }
        });
        //设置长按事件
        mAdapter.setOnItemLongClickListener(new AskPassReviewAdapter.MyItemLongClickListener() {
            @Override
            public void onItemLongClick(final View view, final int position) {

            }
        });
        swipe_target.setAdapter(mAdapter);
    }





    /**
     * 第一次初始化数据
     */
    private void initData(){
        if (!checkNetworkState()) {
            showShortToast(CommonHintInfo.NO_NETWORDK);
            return;
        }
        swipeToLoad.setRefreshing(true);
    }


    /**
     * 上拉加载更多
     */
    @Override
    public void onLoadMore() {
        if (!checkNetworkState()) {
            showShortToast(CommonHintInfo.NO_NETWORDK);
            return;
        }

        if(mUserEntity!=null){
            //添加查询参数  0待审核，1审核通过，2待修改
            AskEntity askEntity = new AskEntity();
            //查询当前登录用户的记录
            askEntity.setUserrecno(mUserEntity.getRecno());
            //添加查询参数  0待审核，1审核通过，2待修改
            askEntity.setStatus((byte) 1);
            askEntity.setPage(currentPage + 1);
            mPresenter.loadMoreData(askEntity);
            mPresenter.loadMoreData(askEntity);
        }

    }


    /**
     * 下拉刷新
     * 下拉刷新，只更新第一页的数据
     */
    @Override
    public void onRefresh() {
        if (!checkNetworkState()) {
            showShortToast(CommonHintInfo.NO_NETWORDK);
            return;
        }

        if(mUserEntity!=null){
            //添加查询参数  0待审核，1审核通过，2待修改
            AskEntity askEntity = new AskEntity();
            //查询当前登录用户的记录
            askEntity.setUserrecno(mUserEntity.getRecno());
            askEntity.setStatus((byte) 1);
            askEntity.setPage(1);
            mPresenter.refreshData(askEntity);
        }

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
    private void dataSort() {
        Collections.sort(mDataList, new Comparator<AskEntity>() {
            @Override
            public int compare(AskEntity arg0, AskEntity arg1) {
                Date date1 = TimeUtils.getDate1(String.valueOf(arg0.getOpdate()));
                Date date2 = TimeUtils.getDate1(String.valueOf(arg1.getOpdate()));
                int flag = date2.compareTo(date1);
                return flag;
            }

        });
    }

    /**
     * 下拉刷新数据成功回调
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
            for(AskEntity entity:list){
                if(!mDataSet.contains(entity.getRecno())){
                    mDataList.add(entity);
                    mDataSet.add(entity.getRecno());
                }
            }
            dataSort();
            mAdapter.notifyDataSetChanged();

            if(loadDataStatus == 0){
                loadDataStatus = 1;
            }

        }else{
            if(loadDataStatus != 0){
                showShortToast("暂无更新");
            }else{//第一次刷新数据
                loadDataStatus = 1;
            }
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
        loadDataStatus = 0;
        mDataList.clear();
        mDataSet.clear();
    }

}

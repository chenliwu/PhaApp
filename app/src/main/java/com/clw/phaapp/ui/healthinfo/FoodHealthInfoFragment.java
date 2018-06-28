package com.clw.phaapp.ui.healthinfo;

import android.content.Intent;
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
import com.clw.phaapp.adapter.HealthInfoAdapter;
import com.clw.phaapp.base.BaseMvpFragmentV4;
import com.clw.phaapp.common.CommonHintInfo;
import com.clw.phaapp.contract.HealthInfoContract;
import com.clw.phaapp.model.healthinfo.HealthInfoEntity;
import com.clw.phaapp.presenter.healthinfo.GetHealthInfoListPresenter;

import java.util.*;

/**
 * 易源数据，健康资讯，食品资讯Fragment类
 * tid =3
 *
 * @author chenliwu
 * @create 2018-03-14 21:24
 **/
public class FoodHealthInfoFragment extends BaseMvpFragmentV4<HealthInfoContract.IGetHealthInfoListView, GetHealthInfoListPresenter>
        implements HealthInfoContract.IGetHealthInfoListView,OnRefreshListener, OnLoadMoreListener {

    private final static String TAG = "FoodHealthInfoFragment";

    private static FoodHealthInfoFragment FRAGMENT1;

    /**
     * 记录资讯的id，避免重复
     */
    private Set<String> mDataSet=new HashSet<>();

    /**
     * 健康资讯列表
     */
    private List<HealthInfoEntity> mDataList=new ArrayList<>();
    private RecyclerView swipe_target;
    private SwipeToLoadLayout swipeToLoad;
    private HealthInfoAdapter mAdapter;

    /**
     * 当前页，记录分页数据
     */
    private int currentPage=0;

    /**
     * 加载数据标识
     * 0 第一次加载数据
     * 1 加载第一次数据完毕
     * 2 其它
     */
    int loadDataStatus=0;



    /**
     * 获取对象实例
     *
     * @return
     */
    public static FoodHealthInfoFragment getInstance() {
        if (FRAGMENT1 == null) {
            FRAGMENT1 = new FoodHealthInfoFragment();
        }
        return FRAGMENT1;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        mBaseView = inflater.inflate(R.layout.fragment_food_health_info, container, false);
        initFragment();
        initView();
        initRecyclerview();
        return mBaseView;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(loadDataStatus == 0){
            //下拉刷新
            swipeToLoad.setRefreshing(true);
            loadDataStatus = 1;
        }
    }

    /**
     * 实例化Presenter，注意是实现泛型指定的Presenter
     *
     * @return
     */
    @Override
    protected GetHealthInfoListPresenter initPresenter() {
        return new GetHealthInfoListPresenter();
    }

    @Override
    protected void initFragment() {

    }

    @Override
    protected void initView() {
        swipe_target = (RecyclerView) mBaseView.findViewById(R.id.swipe_target);
        swipeToLoad = (SwipeToLoadLayout) mBaseView.findViewById(R.id.swipeToLoad);
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
        mAdapter = new HealthInfoAdapter(mContext,mDataList);
        //设置item的点击事件
        mAdapter.setItemClickListener(new HealthInfoAdapter.MyItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                //showShortToast("position:"+position);
                Intent intent=new Intent(mContext,HealthInfoDetailActivity.class);
                HealthInfoEntity healthInfoEntity=mDataList.get(position);
                //携带健康资讯的id进入详情界面
                intent.putExtra("id",healthInfoEntity.getId());
                intent.putExtra("tid",healthInfoEntity.getTid());
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
            dismissLoading();
            return ;
        }
        //设置查询参数
        HealthInfoEntity healthInfoEntity=new HealthInfoEntity();
        //tid=1 综合资讯
        healthInfoEntity.setTid("3");
        //添加分页，查询下一页数据
        healthInfoEntity.setCurrentPage(currentPage+1);
        //查询数据
        mPresenter.loadMoreData(healthInfoEntity);
    }


    /**
     * 下拉刷新
     * 只更新第一页，更新最新的数据
     */
    @Override
    public void onRefresh() {
        if(!checkNetworkState()){
            showShortToast(CommonHintInfo.NO_NETWORDK);
            dismissLoading();
            return ;
        }
        //设置查询参数
        HealthInfoEntity healthInfoEntity=new HealthInfoEntity();
        //tid=1 综合资讯
        healthInfoEntity.setTid("3");
        //添加分页，查询下一页数据
        healthInfoEntity.setCurrentPage(1);
        //查询数据
        mPresenter.refreshData(healthInfoEntity);
    }


    /**
     * 成功回调
     *
     * @param healthInfoEntity
     */
    @Override
    public void getHealthInfoListSuccess(HealthInfoEntity healthInfoEntity) {
        dismissLoading();
        List<HealthInfoEntity> rows=healthInfoEntity.getRows();
        if(currentPage > healthInfoEntity.getAllPages()){
            //当前页数超过总页数的时候就没有数据
            showShortToast("已无更多数据");
        }else if(currentPage <= healthInfoEntity.getAllPages()){
            //增加数据
            mDataList.addAll(rows);
            currentPage = healthInfoEntity.getCurrentPage();
            dataSort();
            //更新数据
            mAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 数据排序，按时间先后排序
     */
    private void dataSort(){
        Collections.sort(mDataList,new Comparator<HealthInfoEntity>(){
            @Override
            public int compare(HealthInfoEntity arg0, HealthInfoEntity arg1) {
                Date date1= TimeUtils.getDate3(arg0.getTime());
                Date date2=TimeUtils.getDate3(arg1.getTime());
                int flag = date2.compareTo(date1);
                return flag;
            }

        });
    }


    /**
     * 下拉刷新数据成功回调
     *
     * @param healthInfoEntity
     */
    @Override
    public void refreshDataSuccess(HealthInfoEntity healthInfoEntity) {
        dismissLoading();
        List<HealthInfoEntity> list=healthInfoEntity.getRows();
        if(list !=null && list.size() > 0){
            currentPage = healthInfoEntity.getCurrentPage();
            //记录原来数据列表的个数
            int size = mDataList.size();
            for(HealthInfoEntity entity:list){
                if(!mDataSet.contains(entity.getId())){
                    mDataList.add(entity);
                    mDataSet.add(entity.getId());
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
     * @param healthInfoEntity
     */
    @Override
    public void loadMoreDataSuccess(HealthInfoEntity healthInfoEntity) {
        dismissLoading();
        List<HealthInfoEntity> list=healthInfoEntity.getRows();
        if(list !=null && list.size() > 0){
            currentPage = healthInfoEntity.getCurrentPage();
            for(HealthInfoEntity entity:list){
                if(!mDataSet.contains(entity.getId())){
                    mDataList.add(entity);
                    mDataSet.add(entity.getId());
                }
            }
            dataSort();
            mAdapter.notifyDataSetChanged();
        }else{
            showShortToast("已无更多数据");
        }
    }

    /**
     * 失败回调
     */
    @Override
    public void getHealthInfoListFail() {
        dismissLoading();
    }

    /**
     * 显示加载提示
     *
     * @param msg
     */
    @Override
    public void showLoading(String msg) {
        super.showLoading(msg);

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
        loadDataStatus = 0;
        currentPage = 0;
        mDataList.clear();
        mDataSet.clear();
    }




}
package com.clw.phaapp.ui.user;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.clw.phaapp.R;
import com.clw.phaapp.adapter.HealthInfoAdapter;
import com.clw.phaapp.adapter.HistoryAdapter;
import com.clw.phaapp.base.BaseMvpActivity;
import com.clw.phaapp.contract.LogContract;
import com.clw.phaapp.model.entity.LogEntity;
import com.clw.phaapp.model.healthinfo.HealthInfoEntity;
import com.clw.phaapp.presenter.common.GetVisitHistorylPresenter;
import com.clw.phaapp.ui.healthinfo.HealthInfoDetailActivity;
import com.clw.phaapp.ui.healthqa.AskDatailActivity;
import com.ganxin.library.LoadDataLayout;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class HistoryActivity extends BaseMvpActivity<LogContract.IGetVisitHistoryView, GetVisitHistorylPresenter>
        implements LogContract.IGetVisitHistoryView, View.OnClickListener{
//        OnRefreshListener, OnLoadMoreListener {

    private List<LogEntity> mDataList=new ArrayList<>();
    //记录已经存在的ID
    private Set<String> mDataSet=new HashSet<>();
    private HistoryAdapter mAdapter;

    private RecyclerView swipe_target;
    private SwipeToLoadLayout swipeToLoad;
    private LoadDataLayout loadDataLayout;

    /**
     * 是否已经刷新过数据
     */
    boolean isRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        initActivity();
        initView();
        initRecyclerview();
        initUserInfoFromApplication();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(mDataList.size() == 0 && isRefresh == false){
            //第一次进入界面就刷新数据
            loadData();
            isRefresh = true;
        }
    }

    /**
     * 初始化Recyclerview
     */
    private void initRecyclerview(){
        //监听下拉刷新
        //swipeToLoad.setOnRefreshListener(this);
        //监听上拉加载更多
        //swipeToLoad.setOnLoadMoreListener(this);
        //创建默认的线性LayoutManager
        swipe_target.setLayoutManager(new LinearLayoutManager(mContext));
        //设置Item增加、移除动画
        swipe_target.setItemAnimator(new DefaultItemAnimator());
        swipe_target.addItemDecoration(new DividerItemDecoration(mContext,
                DividerItemDecoration.VERTICAL));
        //创建并设置Adapter
        mAdapter = new HistoryAdapter(mContext,mDataList);
        //设置item的点击事件
        mAdapter.setItemClickListener(new HistoryAdapter.MyItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                LogEntity logEntity=mDataList.get(position);
                int nbyte=0;
                if(logEntity.getType()!=null){
                    nbyte = logEntity.getType();
                }
                switch (nbyte){
                    case 1://1 综合资讯
                        //携带健康资讯的id进入详情界面
                        toHealthInfoDetailActivity(String.valueOf(logEntity.getTargetrecno()),"1");
                        break;
                    case 2://2 疾病资讯
                        //携带健康资讯的id进入详情界面
                        toHealthInfoDetailActivity(String.valueOf(logEntity.getTargetrecno()),"2");
                        break;
                    case 3: //3食品资讯
                        toHealthInfoDetailActivity(String.valueOf(logEntity.getTargetrecno()),"3");
                        break;
                    case 20:   //20访问问答
                        toAskDetailActivity(Long.parseLong(logEntity.getTargetrecno()));
                        break;
                }

            }
        });
        swipe_target.setAdapter(mAdapter);
    }

    /**
     * 进入健康资讯详情界面
     * @param id 资讯ID
     * @param tid 资讯分类ID
     */
    private void toHealthInfoDetailActivity(String id,String tid){
        Intent intent=new Intent(mContext,HealthInfoDetailActivity.class);
        //携带健康资讯的id进入详情界面
        intent.putExtra("id",id);
        intent.putExtra("tid",tid);
        startActivity(intent);
    }

    /**
     * 进入健康问答详情界面
     * @param recno 问答记录号
     */
    private void toAskDetailActivity(Long recno){
        Intent intent=new Intent(mContext,AskDatailActivity.class);
        intent.putExtra("recno",recno);
        startActivity(intent);
    }




    /**
     * 实例化Presenter，注意是实现泛型指定的Presenter
     *
     * @return
     */
    @Override
    protected GetVisitHistorylPresenter initPresenter() {
        return new GetVisitHistorylPresenter();
    }



    @Override
    protected void initActivity() {
        initBackToolbar();
        setToolbarCenterTitle("浏览历史");
    }

    @Override
    protected void initView() {
        swipe_target = (RecyclerView) findViewById(R.id.swipe_target);
        swipeToLoad = (SwipeToLoadLayout) findViewById(R.id.swipeToLoad);
        loadDataLayout = (LoadDataLayout) findViewById(R.id.loadDataLayout);
        loadDataLayout.setOnClickListener(this);

        loadDataLayout.setOnReloadListener(new LoadDataLayout.OnReloadListener() {
            @Override
            public void onReload(View v, int status) {
                loadDataLayout.setStatus(LoadDataLayout.LOADING);//加载中
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loadData();
                        //loadDataLayout.setStatus(LoadDataLayout.LOADING); //加载中
                        //loadDataLayout.setStatus(LoadDataLayout.EMPTY); //无数据
                        //loadDataLayout.setStatus(LoadDataLayout.ERROR); //出错
                        //loadDataLayout.setStatus(LoadDataLayout.NO_NETWORK); //无网络
                        //loadDataLayout.setStatus(LoadDataLayout.SUCCESS); //加载成功

                    }
                }, 2000);
            }
        });

    }


    private void loadData(){
        if (!checkNetworkState()) {
            loadDataLayout.setStatus(LoadDataLayout.NO_NETWORK); //无网络
            return;
        }
        loadDataLayout.setStatus(LoadDataLayout.LOADING); //加载中
        if(mUserEntity!=null){
            LogEntity logEntity=new LogEntity();
            logEntity.setUserrecno(mUserEntity.getRecno());
            mPresenter.getVisitHistory(logEntity);
        }else{
            loadDataLayout.setStatus(LoadDataLayout.EMPTY); //无数据
        }
    }

    /**
     * 成功回调接口
     *
     * @param logEntity
     */
    @Override
    public void getVisitHistorySuccess(LogEntity logEntity) {
        if(logEntity.getTotal() > 0){
            loadDataLayout.setStatus(LoadDataLayout.SUCCESS); //加载成功
            List<LogEntity> list=logEntity.getRows();
            for(LogEntity entity:list){
                String targetRecno=entity.getTargetrecno();
                if(!mDataSet.contains(targetRecno)){
                    //剔除重复记录
                    mDataList.add(entity);
                    mDataSet.add(targetRecno);
                }
            }
            mAdapter.notifyDataSetChanged();
        }else{
            loadDataLayout.setStatus(LoadDataLayout.EMPTY); //无数据
        }
    }

    /**
     * 失败回调接口
     */
    @Override
    public void getVisitHistoryFail() {
        loadDataLayout.setStatus(LoadDataLayout.ERROR); //出错
    }


//    @Override
//    public void onLoadMore() {
//
//    }
//
//    @Override
//    public void onRefresh() {
//
//    }


    @Override
    public void dismissLoading() {
        super.dismissLoading();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDataSet.clear();
        mDataList.clear();
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {

        }
    }
}

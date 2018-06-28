package com.clw.phaapp.ui.healthqa;

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
import com.clw.phaapp.adapter.AcceptAskAnswerAdapter;
import com.clw.phaapp.base.BaseMvpActivity;
import com.clw.phaapp.common.CommonHintInfo;
import com.clw.phaapp.contract.AskAnswerContract;
import com.clw.phaapp.model.entity.AskAnswerEntity;
import com.clw.phaapp.model.entity.AskEntity;
import com.clw.phaapp.presenter.healthqa.GetAskAnswerListPresenter;
import com.ganxin.library.LoadDataLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * 采纳回答界面
 */
public class AcceptAskAnswerActivity extends BaseMvpActivity<AskAnswerContract.IGetAskAnswerListView, GetAskAnswerListPresenter>
        implements AskAnswerContract.IGetAskAnswerListView, View.OnClickListener,
        OnRefreshListener, OnLoadMoreListener {

    private List<AskAnswerEntity> mDataList = new ArrayList<>();

    /**
     * 是否已经加载过回答列表
     */
    private boolean isLoadData;


    /**
     * 记录当前页数
     */
    int currentPage = 0;


    /**
     * 记录健康问答信息
     */
    private AskEntity mAskEntity;

    private RecyclerView swipe_target;
    private AcceptAskAnswerAdapter mAdapter;


    private LoadDataLayout loadDataLayout;
    private SwipeToLoadLayout swipeToLoad;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accept_ask_answer);
        initActivity();
        initView();
        initUserInfoFromApplication();
        initData();
        initRecyclerview();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!isLoadData) {
            loadData();
            isLoadData = true;
        }
    }

    @Override
    protected void initActivity() {
        initBackToolbar();
        setToolbarCenterTitle("健康问答管理");
    }

    @Override
    protected void initView() {
        swipeToLoad = (SwipeToLoadLayout) findViewById(R.id.swipeToLoad);
        swipe_target = (RecyclerView) findViewById(R.id.swipe_target);

        loadDataLayout = (LoadDataLayout) findViewById(R.id.loadDataLayout);

        //设置重新加载操作
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

    /**
     * 初始化Recyclerview，要在initData方法后
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
        //添加一个空值的对象，因为position=0时要显示问答的明细
        mDataList.add(new AskAnswerEntity());
        //创建并设置Adapter
        mAdapter = new AcceptAskAnswerAdapter(this, mAskEntity, mDataList, mUserEntity);
        //设置item的点击事件
        mAdapter.setItemClickListener(new AcceptAskAnswerAdapter.MyItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                //showShortToast("position:"+position);

            }
        });
        swipe_target.setAdapter(mAdapter);
    }


    /**
     * 初始化数据
     */
    private void initData() {
        if (getIntent().getSerializableExtra("ask") != null) {
            mAskEntity = (AskEntity) getIntent().getSerializableExtra("ask");
        }
    }


    private void loadData() {
        if (!checkNetworkState()) {
            loadDataLayout.setStatus(LoadDataLayout.NO_NETWORK); //无网络
            return;
        }
        loadDataLayout.setStatus(LoadDataLayout.LOADING); //加载中
        //加载回答列表
        if (mAskEntity != null) {
            AskAnswerEntity askAnswerEntity = new AskAnswerEntity();
            //添加查询记录的用户记录号，用于判断其是否对回答点赞
            askAnswerEntity.setSelectuserreno(mUserEntity.getRecno());
            askAnswerEntity.setAskrecno(mAskEntity.getRecno());
            askAnswerEntity.setPage(currentPage + 1);
            //只查询未被采纳的回答---状态：0未采纳，1已采纳，2被删除
            //askAnswerEntity.setStatus((byte)0);
            mPresenter.getAskAnswerList(askAnswerEntity);
        }

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

        }
    }


    /**
     * 实例化Presenter，注意是实现泛型指定的Presenter
     *
     * @return
     */
    @Override
    protected GetAskAnswerListPresenter initPresenter() {
        return new GetAskAnswerListPresenter();
    }

    @Override
    public void onRefresh() {
        if (!checkNetworkState()) {
            showShortToast(CommonHintInfo.NO_NETWORDK);
            return;
        }
        //加载回答列表
        if (mAskEntity != null) {
            AskAnswerEntity askAnswerEntity = new AskAnswerEntity();
            //添加查询记录的用户记录号，用于判断其是否对回答点赞
            askAnswerEntity.setSelectuserreno(mUserEntity.getRecno());
            askAnswerEntity.setAskrecno(mAskEntity.getRecno());
            askAnswerEntity.setPage(currentPage + 1);
            //只查询未被采纳的回答---状态：0未采纳，1已采纳
            //askAnswerEntity.setStatus((byte)0);
            mPresenter.refreshData(askAnswerEntity);
        }
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
        //加载回答列表
        if (mAskEntity != null) {
            AskAnswerEntity askAnswerEntity = new AskAnswerEntity();
            //添加查询记录的用户记录号，用于判断其是否对回答点赞
            askAnswerEntity.setSelectuserreno(mUserEntity.getRecno());
            askAnswerEntity.setAskrecno(mAskEntity.getRecno());
            askAnswerEntity.setPage(currentPage + 1);
            //只查询未被采纳的回答---状态：0未采纳，1已采纳
            //askAnswerEntity.setStatus((byte)0);
            mPresenter.loadMoreData(askAnswerEntity);
        }
    }


    /**
     * 成功
     *
     * @param askAnswerEntity
     */
    @Override
    public void getAskAnswerListSuccess(AskAnswerEntity askAnswerEntity) {
        loadDataLayout.setStatus(LoadDataLayout.SUCCESS); //加载成功
        if (askAnswerEntity.getTotal() > 0) {
            currentPage = askAnswerEntity.getPage();
            List<AskAnswerEntity> list = askAnswerEntity.getRows();
            mDataList.addAll(list);
            mAdapter.notifyDataSetChanged();
        } else {
            showShortToast("暂无回答的数据");
        }
    }


    /**
     * 下拉刷新数据成功回调
     *
     * @param askAnswerEntity
     */
    @Override
    public void refreshDataSuccess(AskAnswerEntity askAnswerEntity) {
        dismissLoading();
        List<AskAnswerEntity> list = askAnswerEntity.getRows();
        if (list != null && list.size() > 0) {
            currentPage = askAnswerEntity.getPage();
            mDataList.addAll(list);
            mAdapter.notifyDataSetChanged();
        } else {
            showShortToast("已无更多数据");
        }
    }



    /**
     * 上拉加载更多成功回调
     *
     * @param askAnswerEntity
     */
    @Override
    public void loadMoreDataSuccess(AskAnswerEntity askAnswerEntity) {
        dismissLoading();
        List<AskAnswerEntity> list = askAnswerEntity.getRows();
        if (list != null && list.size() > 0) {
            currentPage = askAnswerEntity.getPage();
            mDataList.addAll(list);
            mAdapter.notifyDataSetChanged();
        } else {
            showShortToast("已无更多数据");
        }
    }

    /**
     * 失败
     */
    @Override
    public void getAskAnswerListFail() {
        loadDataLayout.setStatus(LoadDataLayout.ERROR); //出错
        dismissLoading();
    }

    //loadDataLayout.setStatus(LoadDataLayout.LOADING); //加载中
    //loadDataLayout.setStatus(LoadDataLayout.EMPTY); //无数据
    //loadDataLayout.setStatus(LoadDataLayout.ERROR); //出错
    //loadDataLayout.setStatus(LoadDataLayout.NO_NETWORK); //无网络
    //loadDataLayout.setStatus(LoadDataLayout.SUCCESS); //加载成功

    @Override
    public void dismissLoading() {
        super.dismissLoading();
        //停止上拉加载更多
        swipeToLoad.setLoadingMore(false);
        //停止下拉刷新
        swipeToLoad.setRefreshing(false);
    }
}

package com.clw.phaapp.ui.healthqa;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.clw.mysdk.utils.NetworkUtils;
import com.clw.phaapp.R;
import com.clw.phaapp.adapter.AskAnswerAdapter;
import com.clw.phaapp.base.BaseMvpActivity;
import com.clw.phaapp.common.CommonHintInfo;
import com.clw.phaapp.contract.AskAnswerContract;
import com.clw.phaapp.contract.UserCollectionContract;
import com.clw.phaapp.model.entity.AskAnswerEntity;
import com.clw.phaapp.model.entity.AskEntity;
import com.clw.phaapp.model.entity.LogEntity;
import com.clw.phaapp.model.entity.UserCollectionEntity;
import com.clw.phaapp.presenter.common.LogPresenter;
import com.clw.phaapp.presenter.healthqa.CollectAskPresenter;
import com.clw.phaapp.presenter.healthqa.GetAskAnswerListPresenter;
import com.clw.phaapp.presenter.healthqa.SetAskVisitCountPresenter;
import com.ganxin.library.LoadDataLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * 健康问答明细界面
 */
public class AskDatailActivity extends BaseMvpActivity<AskAnswerContract.IGetAskAnswerListView, GetAskAnswerListPresenter>
        implements AskAnswerContract.IGetAskAnswerListView, View.OnClickListener,
        OnRefreshListener, OnLoadMoreListener {


    private CollectAskPresenter mCollectAskPresenter=new CollectAskPresenter();


    private LinearLayout ll_publish_answer;
    private LinearLayout ll_collection_ask;
    private LinearLayout ll_report_ask;

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
    private AskAnswerAdapter mAdapter;


    private LoadDataLayout loadDataLayout;
    private SwipeToLoadLayout swipeToLoad;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ask_detail);
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
        setToolbarCenterTitle("健康问答");
    }

    @Override
    protected void initView() {
        ll_publish_answer = (LinearLayout) findViewById(R.id.ll_publish_answer);
        ll_publish_answer.setOnClickListener(this);
        ll_collection_ask = (LinearLayout) findViewById(R.id.ll_collection_ask);
        ll_collection_ask.setOnClickListener(this);
        ll_report_ask = (LinearLayout) findViewById(R.id.ll_report_ask);
        ll_report_ask.setOnClickListener(this);

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
        mAdapter = new AskAnswerAdapter(this, mAskEntity, mDataList, mUserEntity);
        //设置item的点击事件
        mAdapter.setItemClickListener(new AskAnswerAdapter.MyItemClickListener() {
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
            //设置问答访问次数加1
            if (NetworkUtils.isAvailable(mContext)) {
                SetAskVisitCountPresenter setAskVisitCountPresenter = new SetAskVisitCountPresenter();
                setAskVisitCountPresenter.setAskVisitCount(mAskEntity);
                if (mUserEntity != null && mAskEntity != null) {
                    //添加日志
                    LogEntity logEntity = new LogEntity();
                    //添加用户记录号
                    logEntity.setUserrecno(mUserEntity.getRecno());
                    //设置访问类型
                    logEntity.setType((byte) 20);
                    //设置目标记录号
                    logEntity.setTargetrecno(String.valueOf(mAskEntity.getRecno()));
                    LogPresenter.getInstance().insertLogRecord(logEntity);
                }
            }
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
            if(mUserEntity!=null){
                //添加查询记录的用户记录号，用于判断其是否对回答点赞
                askAnswerEntity.setSelectuserreno(mUserEntity.getRecno());
            }
            askAnswerEntity.setAskrecno(mAskEntity.getRecno());
            askAnswerEntity.setPage(currentPage + 1);
            //只查询已被采纳的回答---状态：0未采纳，1已采纳，2被删除
            askAnswerEntity.setStatus((byte)1);
            mPresenter.getAskAnswerList(askAnswerEntity);
        }

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_publish_answer:    //回答问题
                if(isLogin){
                    Intent intent = new Intent(mContext, PublishAskAnswerActivity.class);
                    intent.putExtra("ask", mAskEntity);
                    startActivity(intent);
                }else{
                    showShortToast("请先登录");
                }

                break;
            case R.id.ll_collection_ask:    //收藏问题
                if(isLogin){
                    collectAsk();
                }else{
                    showShortToast("请先登录");
                }

                break;
            case R.id.ll_report_ask:    //举报问题
                if(isLogin){
                    Intent intent=new Intent(mContext,ReportAskActivity.class);
                    intent.putExtra("ask",mAskEntity);
                    startActivity(intent);
                }else{
                    showShortToast("请先登录");
                }

                break;
        }
    }

    /**
     * 收藏问答
     * 要提供的字段：收藏目标记录号，用户记录号，收藏类型
     */
    void collectAsk(){
        UserCollectionEntity userCollectionEntity=new UserCollectionEntity();
        if(mAskEntity == null || mUserEntity == null){
            return ;
        }
        userCollectionEntity.setTargetrecno(mAskEntity.getRecno().toString());
        userCollectionEntity.setUserrecno(mUserEntity.getRecno());
        userCollectionEntity.setType((byte)20);
        mCollectAskPresenter.collectAsk(userCollectionEntity, new UserCollectionContract.ICollectAskView() {
            @Override
            public void collectAskSuccess(String msg) {
                showShortToast(msg);
            }

            @Override
            public void collectAskFail(String msg) {
                showShortToast(msg);
            }

            @Override
            public boolean isActive() {
                return isActive;
            }

            @Override
            public void showLoading(String msg) {

            }

            @Override
            public void dismissLoading() {

            }

            @Override
            public void showMessage(String title, String msg) {
                showShortToast(msg);
            }

            @Override
            public void showSuccessMessage(String title, String msg) {
                showShortToast(msg);
            }

            @Override
            public void showErrorMessage(String title, String msg) {
                showShortToast(msg);
            }

            @Override
            public void showProgress(String msg) {

            }

            @Override
            public void dismissProgress() {

            }

            @Override
            public void closeView() {

            }

            @Override
            public boolean checkNetworkState() {
                return NetworkUtils.isAvailable(mContext);
            }
        });
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
            //只查询已被采纳的回答---状态：0未采纳
            askAnswerEntity.setStatus((byte)1);
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
            //只查询已被采纳的回答---状态：0未采纳，1已采纳
            askAnswerEntity.setStatus((byte)1);
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

        mDataList.clear();
        mDataList.add(new AskAnswerEntity());

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


    @Override
    protected void onDestroy() {
        super.onDestroy();
        isLoadData = false;
        mDataList.clear();
    }
}

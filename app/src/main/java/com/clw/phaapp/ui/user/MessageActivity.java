package com.clw.phaapp.ui.user;

import android.os.Bundle;
import android.os.Message;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.clw.mysdk.utils.NetworkUtils;
import com.clw.phaapp.R;
import com.clw.phaapp.adapter.MessageAdapter;
import com.clw.phaapp.base.BaseMvpActivity;
import com.clw.phaapp.common.CommonHintInfo;
import com.clw.phaapp.common.recyclerview.CustomLoadMoreFooterView;
import com.clw.phaapp.contract.MessageContract;
import com.clw.phaapp.model.entity.MessageEntity;
import com.clw.phaapp.presenter.user.GetMessageListPresenter;
import com.clw.phaapp.presenter.user.UpdateMessageStatusPresenter;
import com.clw.phaapp.ui.MainActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * 消息通知Activity
 */
public class MessageActivity extends BaseMvpActivity<MessageContract.IGetMessageListView, GetMessageListPresenter>
        implements MessageContract.IGetMessageListView, View.OnClickListener,
        OnRefreshListener, OnLoadMoreListener {

    private RecyclerView swipe_target;
    private CustomLoadMoreFooterView swipe_load_more_footer;
    private SwipeToLoadLayout swipeToLoad;

    /**
     * 修改消息状态Presenter
     */
    private UpdateMessageStatusPresenter mUpdateMessageStatusPresenter=new UpdateMessageStatusPresenter();


    /**
     * 数据列表
     */
    private List<MessageEntity> mDataList=new ArrayList<>();

    private MessageAdapter mAdapter;


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



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        initActivity();
        initView();
        initUserInfoFromApplication();
        initRecyclerview();
    }

    @Override
    protected void initActivity() {
        initBackToolbar();
        setToolbarCenterTitle("消息通知");
    }

    @Override
    protected void initView() {
        swipe_target = (RecyclerView) findViewById(R.id.swipe_target);
        swipe_target.setOnClickListener(this);
        swipe_load_more_footer = (CustomLoadMoreFooterView) findViewById(R.id.swipe_load_more_footer);
        swipe_load_more_footer.setOnClickListener(this);
        swipeToLoad = (SwipeToLoadLayout) findViewById(R.id.swipeToLoad);
        swipeToLoad.setOnClickListener(this);
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
        //创建并设置Adapter
        mAdapter = new MessageAdapter(mDataList, this, mUserEntity);
        //设置item的点击事件
        mAdapter.setItemClickListener(new MessageAdapter.MyItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

            }
        });
        //设置长按事件
        mAdapter.setOnItemLongClickListener(new MessageAdapter.MyItemLongClickListener() {
            @Override
            public void onItemLongClick(final View view, final int position) {


            }
        });
        swipe_target.setAdapter(mAdapter);
    }




    /**
     * 实例化Presenter，注意是实现泛型指定的Presenter
     *
     * @return
     */
    @Override
    protected GetMessageListPresenter initPresenter() {
        return new GetMessageListPresenter();
    }


    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {

    }


    @Override
    public void onResume() {
        super.onResume();
        if (loadDataStatus == 0) {
            swipeToLoad.setRefreshing(true);
        }
    }


    @Override
    public void onLoadMore() {
        if (!checkNetworkState()) {
            showShortToast(CommonHintInfo.NO_NETWORDK);
            return;
        }
        if(mUserEntity!=null){
            MessageEntity messageEntity=new MessageEntity();
            messageEntity.setReceiver(mUserEntity.getRecno());
            messageEntity.setPage(currentPage+1);
            mPresenter.loadMoreData(messageEntity);
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
            MessageEntity messageEntity=new MessageEntity();
            messageEntity.setReceiver(mUserEntity.getRecno());
            messageEntity.setPage(1);
            mPresenter.refreshData(messageEntity);
        }
    }

    /**
     * 失败
     *
     * @param msg
     */
    @Override
    public void getMessageListFail(String msg) {
        dismissLoading();
        showShortToast(msg);
    }

    /**
     * 下拉刷新数据成功回调
     *
     * @param messageEntity
     */
    @Override
    public void refreshDataSuccess(MessageEntity messageEntity) {
        dismissLoading();
        //把原来的数据清理掉，重新刷新
        mDataList.clear();
        List<MessageEntity> list=messageEntity.getRows();
        if(list!=null && list.size() > 0){
            currentPage = messageEntity.getPage();
            mDataList.addAll(list);
            mAdapter.notifyDataSetChanged();
            if(loadDataStatus == 0){
                loadDataStatus = 1;
            }
            //把消息变成已读状态
            updateMessageStatus();
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
     * @param messageEntity
     */
    @Override
    public void loadMoreDataSuccess(MessageEntity messageEntity) {
        dismissLoading();
        List<MessageEntity> list=messageEntity.getRows();
        if (list != null && list.size() > 0) {
            currentPage = messageEntity.getPage();
            mDataList.addAll(list);
            mAdapter.notifyDataSetChanged();
            //把消息变成已读状态
            updateMessageStatus();
        } else {
            showShortToast("已无更多数据");
        }
    }


    /**
     * 修改消息状态，把消息变成已读状态
     */
    private void updateMessageStatus(){
        StringBuilder updateStatusMessageRecnolist=new StringBuilder();
        for(MessageEntity messageEntity:mDataList){
            if(messageEntity.getStatus() == 0){
                updateStatusMessageRecnolist.append(messageEntity.getRecno()+",");
            }
        }
        if(updateStatusMessageRecnolist.length() > 0){
            MessageEntity messageEntity=new MessageEntity();
            messageEntity.setUpdateStatusMessageRecnolist(updateStatusMessageRecnolist.toString());
            mUpdateMessageStatusPresenter.updateMessageStatus(messageEntity, new MessageContract.IUpdateMessageStatusView() {
                @Override
                public void updateMessageStatusSuccess(String msg) {
                    for(int i=0,size=mDataList.size();i<size;i++){
                        mDataList.get(i).setStatus((byte)1);
                    }
                    if(mPhaApplication!=null){
                        mPhaApplication.setMessageNumber(0);
                    }

//                    //发送修改消息指示器消息到主线程，修改提醒消息的数量
//                    Message message=new Message();
//                    message.what=MainActivity.UPDATE_MESSAGE_INDICATOR;
//                    message.obj = 0;
//                    MainActivity.getHandler().sendMessage(message);
                }

                @Override
                public void updateMessageStatusFail(String msg) {

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

                }

                @Override
                public void showSuccessMessage(String title, String msg) {

                }

                @Override
                public void showErrorMessage(String title, String msg) {

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
    }


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
        loadDataStatus = 0;
        mDataList.clear();
        currentPage = 0;
    }


}

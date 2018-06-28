package com.clw.phaapp.contract;

import com.clw.phaapp.base.IBaseView;
import com.clw.phaapp.model.entity.MessageEntity;

public interface MessageContract {

    /////////////////  获取消息 //////////////////////

    interface IGetMessageListView extends IBaseView {

        /**
         * 失败
         */
        void getMessageListFail(String msg);


        /**
         * 下拉刷新数据成功回调
         * @param messageEntity
         */
        void refreshDataSuccess(MessageEntity messageEntity);

        /**
         * 上拉加载更多成功回调
         * @param messageEntity
         */
        void loadMoreDataSuccess(MessageEntity messageEntity);
    }

    interface IGetMessageListPresenter{
        /**
         * 下拉刷新数据
         * @param messageEntity
         */
        void refreshData(MessageEntity messageEntity);

        /**
         * 上拉加载更多
         * @param messageEntity
         */
        void loadMoreData(MessageEntity messageEntity);
    }

    //////////////////////////////////////////////////////



    /////////////////  修改消息状态，将消息修改成已读  //////////////////////

    interface IUpdateMessageStatusView extends IBaseView{

        /**
         * 成功回调
         */
        void updateMessageStatusSuccess(String msg);

        /**
         * 失败回调
         */
        void updateMessageStatusFail(String msg);
    }

    interface IUpdateMessagePresenter{
        /**
         * 修改消息状态，将消息修改成已读
         * @param messageEntity
         */
        void updateMessageStatus(MessageEntity messageEntity,IUpdateMessageStatusView view);
    }

    //////////////////////////////////////////////////////


    /////////////////  获取消息数量  //////////////////////

    interface IGetMessageNumberView extends IBaseView{

        /**
         * 成功回调
         */
        void getMessageNumberSuccess(int number);

        /**
         * 失败回调
         */
        void getMessageNumberFail(String msg);
    }

    interface IGetMessageNumberPresenter{
        /**
         * 获取消息记录数
         * @param messageEntity
         */
        void getMessageNumber(MessageEntity messageEntity,IGetMessageNumberView view);
    }

    //////////////////////////////////////////////////////




}

package com.clw.phaapp.contract;

import com.clw.phaapp.base.IBaseView;
import com.clw.phaapp.model.entity.AskAnswerEntity;
import com.clw.phaapp.model.entity.AskEntity;
import com.clw.phaapp.model.entity.LikeEntity;

public interface AskAnswerContract {

    /////////////////  回答问题  //////////////////////

    interface IPublishAskAnswerView extends IBaseView {
        /**
         * 回答的内容
         * @return
         */
        String getAskAnswerContent();

        /**
         * 成功回调
         */
        void publishAskAnswerSuccess();

        /**
         * 失败回调
         */
        void publishAskAnswerFail();
    }

    interface IPublishAskAnswerPresenter{
        /**
         * 回答问题
         * @param askAnswerEntity
         */
        void publishAskAnswer(AskAnswerEntity askAnswerEntity);
    }

    //////////////////////////////////////////////////////




    /////////////////  获取回答列表列表  //////////////////////

    interface IGetAskAnswerListView extends IBaseView{

        /**
         * 成功
         */
        void getAskAnswerListSuccess(AskAnswerEntity askAnswerEntity);

        /**
         * 失败
         */
        void getAskAnswerListFail();


        /**
         * 下拉刷新数据成功回调
         * @param askAnswerEntity
         */
        void refreshDataSuccess(AskAnswerEntity askAnswerEntity);

        /**
         * 上拉加载更多成功回调
         * @param askAnswerEntity
         */
        void loadMoreDataSuccess(AskAnswerEntity askAnswerEntity);


    }

    interface IGetAskAnswerListPresenter{
        /**
         * 获取回答列表
         * @param askAnswerEntity
         */
        void getAskAnswerList(AskAnswerEntity askAnswerEntity);


        /**
         * 下拉刷新数据
         * @param askAnswerEntity
         */
        void refreshData(AskAnswerEntity askAnswerEntity);

        /**
         * 上拉加载更多
         * @param askAnswerEntity
         */
        void loadMoreData(AskAnswerEntity askAnswerEntity);

    }

    //////////////////////////////////////////////////////



    /////////////////  点赞答案  //////////////////////

    interface ILikeAskAnswerView{

        /**
         * 成功回调
         */
        void likeAskAnswerSuccess(String msg);

        /**
         * 失败回调
         */
        void likeAskAnswerFail(String msg);
    }

    interface ILikeAskAnswerPresenter{
        /**
         * 点赞
         * @param likeEntity
         */
        void likeAskAnswer(LikeEntity likeEntity,ILikeAskAnswerView view);
    }

    //////////////////////////////////////////////////////


    /////////////////  采纳回答  //////////////////////

    interface IAcceptAskAnswerView extends IBaseView{

        /**
         * 成功回调
         */
        void acceptAskAnswerSuccess(String msg);

        /**
         * 失败回调
         */
        void acceptAskAnswerFail(String msg);
    }

    interface IAcceptAskAnswerPresenter{
        /**
         * 采纳
         * @param askAnswerEntity
         */
        void acceptAskAnswer(AskAnswerEntity askAnswerEntity,IAcceptAskAnswerView view);
    }

    //////////////////////////////////////////////////////



    /////////////////  删除回答  //////////////////////

    interface IDeleteAskAnswerView extends IBaseView{

        /**
         * 成功回调
         */
        void deleteAskAnswerSuccess(String msg);

        /**
         * 失败回调
         */
        void deleteAskAnswerFail(String msg);
    }

    interface IDeleteAskAnswerPresenter{
        /**
         * 删除回答
         * @param askAnswerEntity
         */
        void deleteAskAnswer(AskAnswerEntity askAnswerEntity,IDeleteAskAnswerView view);
    }

    //////////////////////////////////////////////////////




}

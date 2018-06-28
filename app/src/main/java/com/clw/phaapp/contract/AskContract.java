package com.clw.phaapp.contract;

import com.clw.phaapp.base.IBaseView;
import com.clw.phaapp.common.entity.ResultEntity;
import com.clw.phaapp.model.entity.AskEntity;

import java.util.List;

/**
 * 健康问答业务契约接口
 */
public interface AskContract {

    /////////////////  发起健康问答  //////////////////////

    interface IPublishView extends IBaseView{
        /**
         * 获取标题
         * @return
         */
        String getAskTitle();

        /**
         * 获取问题内容
         * @return
         */
        String getAskContent();

        /**
         * 发起问答成功
         */
        void publishSuccess();

        /**
         * 发起问答失败
         */
        void publishFail();
    }

    interface IPublishPresenter{
        /**
         * 发起健康问答
         * @param askEntity
         */
        void publishAsk(AskEntity askEntity);
    }

    //////////////////////////////////////////////////////



    /////////////////  获取健康问答列表  //////////////////////

    interface IGetAskListView extends IBaseView{

        /**
         * 获取问答成功
         */
        void getAskListSuccess(AskEntity askEntity);

        /**
         * 获取问答失败
         */
        void getAskListFail();

        /**
         * 下拉刷新数据成功回调
         * @param askEntity
         */
        void refreshDataSuccess(AskEntity askEntity);

        /**
         * 上拉加载更多成功回调
         * @param askEntity
         */
        void loadMoreDataSuccess(AskEntity askEntity);
    }

    interface IGetAskListPresenter{
        /**
         * 获取健康问答列表
         * @param askEntity
         */
        void getAskList(AskEntity askEntity);

        /**
         * 下拉刷新数据
         * @param askEntity
         */
        void refreshData(AskEntity askEntity);

        /**
         * 上拉加载更多
         * @param askEntity
         */
        void loadMoreData(AskEntity askEntity);

    }

    //////////////////////////////////////////////////////


    /////////////////  获取单条健康问答明细  //////////////////////

    interface IGetOneAskDetailView extends IBaseView{

        /**
         * 获取问答成功
         */
        void getOneAskDetailSuccess(AskEntity askEntity);

        /**
         * 获取问答失败
         */
        void getOneAskDetailFail();
    }

    interface IGetOneAskDetailPresenter{
        /**
         * 获取单条健康问答明细
         * @param askEntity
         */
        void getOneAskDetail(AskEntity askEntity);
    }

    //////////////////////////////////////////////////////


    /////////////////  设置问答访问次数加1  //////////////////////

    interface ISetAskVisitCountView extends IBaseView{

        /**
         * 成功
         */
        void setAskVisitCountSuccess();

        /**
         * 失败
         */
        void setAskVisitCountFail();
    }

    interface ISetAskVisitCountPresenter{
        /**
         * 设置问答访问次数加1
         * @param askEntity
         */
        void setAskVisitCount(AskEntity askEntity);
    }

    //////////////////////////////////////////////////////


    /////////////////  删除问题 //////////////////////

    interface IDeleteAskView extends IBaseView{

        /**
         * 成功
         */
        void deleteAskSuccess(String msg);

        /**
         * 失败
         */
        void deleteAskFail(String msg);
    }

    interface IDeleteAskPresenter{
        /**
         * 删除问题
         * @param askEntity
         * @param view
         */
        void deleteAsk(AskEntity askEntity,IDeleteAskView view);
    }

    //////////////////////////////////////////////////////




    /////////////////  修改问题 //////////////////////

    interface IReviseAskView extends IBaseView{

        /**
         * 成功
         */
        void reviseAskSuccess(String msg);

        /**
         * 失败
         */
        void reviseAskFail(String msg);
    }

    interface IReviseAskPresenter{
        /**
         * 修改问题
         * @param askEntity
         */
        void reviseAsk(AskEntity askEntity);
    }

    //////////////////////////////////////////////////////





}

package com.clw.phaapp.contract;

import com.clw.phaapp.base.IBaseView;
import com.clw.phaapp.model.healthinfo.HealthInfoEntity;

/**
 * 健康资讯Contract接口
 */
public interface HealthInfoContract {

    /////////////////  获取单条健康资讯明细  //////////////////////

    interface IGetOneHealthInfoView extends IBaseView {

        /**
         * 成功回调
         */
        void getOneHealthInfoSuccess(HealthInfoEntity healthInfoEntity);

        /**
         * 失败回调
         */
        void getOneHealthInfoFail();
    }

    interface IGetOneHealthInfoPresenter{
        /**
         * 获取单条资讯明细
         * @param healthInfoEntity
         */
        void getOneHealthInfo(HealthInfoEntity healthInfoEntity);


        /**
         * 获取单条资讯明细
         * @param healthInfoEntity
         * @param view
         */
        void getOneHealthInfo(HealthInfoEntity healthInfoEntity,IGetOneHealthInfoView view);

    }

    //////////////////////////////////////////////////////



    /////////////////  获取健康资讯列表  //////////////////////

    interface IGetHealthInfoListView extends IBaseView {

        /**
         * 成功回调
         */
        void getHealthInfoListSuccess(HealthInfoEntity healthInfoEntity);

        /**
         * 失败回调
         */
        void getHealthInfoListFail();

        /**
         * 下拉刷新数据成功回调
         * @param healthInfoEntity
         */
        void refreshDataSuccess(HealthInfoEntity healthInfoEntity);

        /**
         * 上拉加载更多成功回调
         * @param healthInfoEntity
         */
        void loadMoreDataSuccess(HealthInfoEntity healthInfoEntity);

    }

    interface IGetHealthInfoListPresenter{
        /**
         * 获取健康资讯列表
         * @param healthInfoEntity
         */
        void getHealthInfoList(HealthInfoEntity healthInfoEntity);


        /**
         * 下拉刷新数据
         * @param healthInfoEntity
         */
        void refreshData(HealthInfoEntity healthInfoEntity);

        /**
         * 上拉加载更多
         * @param healthInfoEntity
         */
        void loadMoreData(HealthInfoEntity healthInfoEntity);


    }

    //////////////////////////////////////////////////////



}

package com.clw.phaapp.contract;

import com.clw.phaapp.base.IBaseView;
import com.clw.phaapp.model.entity.UserCollectionEntity;

/**
 * 用户收藏Contract
 */
public interface UserCollectionContract {

    /////////////////  收藏资讯  //////////////////////

    interface ICollectHealthInfoView extends IBaseView {

        /**
         * 成功回调
         */
        void collectHealthInfoSuccess(String msg);

        /**
         * 失败回调
         */
        void collectHealthInfoFail(String msg);
    }

    interface ICollectHealthInfoPresenter{
        /**
         * 收藏资讯
         * @param userCollectionEntity
         */
        void collectHealthInfo(UserCollectionEntity userCollectionEntity, ICollectHealthInfoView view);
    }

    //////////////////////////////////////////////////////


    /////////////////  收藏问答  //////////////////////

    interface ICollectAskView extends IBaseView {

        /**
         * 成功回调
         */
        void collectAskSuccess(String msg);

        /**
         * 失败回调
         */
        void collectAskFail(String msg);
    }

    interface ICollectAskPresenter{
        /**
         * 收藏问答
         * @param userCollectionEntity
         */
        void collectAsk(UserCollectionEntity userCollectionEntity, ICollectAskView view);
    }

    //////////////////////////////////////////////////////



    /////////////////  获取健康资讯收藏列表  //////////////////////

    interface IGetHealthInfoCollectionView extends IBaseView {


        /**
         * 失败回调
         */
        void getHealthInfoCollectionFail(String msg);

        /**
         * 下拉刷新数据成功回调
         * @param userCollectionEntity
         */
        void refreshDataSuccess(UserCollectionEntity userCollectionEntity);

        /**
         * 上拉加载更多成功回调
         * @param userCollectionEntity
         */
        void loadMoreDataSuccess(UserCollectionEntity userCollectionEntity);




    }

    interface IGetHealthInfoCollectionPresenter{

        /**
         * 下拉刷新数据
         * @param userCollectionEntity
         */
        void refreshData(UserCollectionEntity userCollectionEntity);

        /**
         * 上拉加载更多
         * @param userCollectionEntity
         */
        void loadMoreData(UserCollectionEntity userCollectionEntity);

    }

    //////////////////////////////////////////////////////




    /////////////////  获取健康问答收藏列表  //////////////////////

    interface IGetAskCollectionView extends IBaseView {

        /**
         * 下拉刷新数据成功回调
         * @param userCollectionEntity
         */
        void refreshDataSuccess(UserCollectionEntity userCollectionEntity);

        /**
         * 上拉加载更多成功回调
         * @param userCollectionEntity
         */
        void loadMoreDataSuccess(UserCollectionEntity userCollectionEntity);

        /**
         * 失败回调
         */
        void getAskCollectionCollectionFail(String msg);
    }

    interface IGetAskCollectionPresenter{

        /**
         * 下拉刷新数据
         * @param userCollectionEntity
         */
        void refreshData(UserCollectionEntity userCollectionEntity);

        /**
         * 上拉加载更多
         * @param userCollectionEntity
         */
        void loadMoreData(UserCollectionEntity userCollectionEntity);

    }

    //////////////////////////////////////////////////////


    /////////////////  删除收藏 //////////////////////

    interface IDeleteUserCollectionView extends IBaseView{

        /**
         * 成功
         */
        void deleteUserCollectionSuccess(String msg);

        /**
         * 失败
         */
        void deleteUserCollectionFail(String msg);
    }

    interface IDeleteUserCollectionPresenter{
        /**
         * 删除收藏
         * @param userCollectionEntity
         * @param view
         */
        void deleteUserCollection(UserCollectionEntity userCollectionEntity,IDeleteUserCollectionView view);
    }

    //////////////////////////////////////////////////////






}

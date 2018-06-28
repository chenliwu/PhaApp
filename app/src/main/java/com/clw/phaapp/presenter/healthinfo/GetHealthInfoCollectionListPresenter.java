package com.clw.phaapp.presenter.healthinfo;

import android.os.Handler;
import com.clw.mysdk.utils.GsonUtils;
import com.clw.phaapp.common.entity.ResultEntity;
import com.clw.phaapp.common.utils.NetErrorUtils;
import com.clw.phaapp.contract.UserCollectionContract;
import com.clw.phaapp.model.entity.UserCollectionEntity;
import com.clw.phaapp.presenter.base.BaseUserCollectionPresenter;
import io.reactivex.functions.Consumer;

import java.util.List;


/**
 * 获取收藏资讯Presenter
 */
public class GetHealthInfoCollectionListPresenter extends BaseUserCollectionPresenter<UserCollectionContract.IGetHealthInfoCollectionView>
        implements UserCollectionContract.IGetAskCollectionPresenter{

    private final static String TAG="GetHealthInfoCollectionListPresenter";


    /**
     * 下拉刷新数据
     *
     * @param userCollectionEntity
     */
    @Override
    public void refreshData(final UserCollectionEntity userCollectionEntity) {
        if(mView == null || !mView.checkNetworkState() || !mView.isActive()){
            return ;
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mRxManager.register(mIUserCollectionModel.selectHealthInfoCollectionListByPage(userCollectionEntity).subscribe(new Consumer<ResultEntity>() {
                    //接受数据成功，选择视图显示数据
                    @Override
                    public void accept(ResultEntity resultEntity) throws Exception {
                        if(mView == null || !mView.isActive()){
                            return ;
                        }
                        if (resultEntity.getState() == 200) {
                            UserCollectionEntity data= GsonUtils.parseJsonToObject(resultEntity.getData().toString(),UserCollectionEntity.class);
                            List collectionList=GsonUtils.parseJsonToArrayList(data.getRows().toString(),UserCollectionEntity.class);
                            data.setRows(collectionList);
                            mView.refreshDataSuccess(data);
                        } else {
                            mView.getHealthInfoCollectionFail(resultEntity.getMessage());
                        }
                    }
                }, new Consumer<Throwable>() {
                    //接受数据失败，显示错误信息
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        if(mView == null || !mView.isActive()){
                            return ;
                        }
                        mView.getHealthInfoCollectionFail(NetErrorUtils.getErrorMessage(throwable));
                    }
                }));
            }
        },1000);

    }

    /**
     * 上拉加载更多
     *
     * @param userCollectionEntity
     */
    @Override
    public void loadMoreData(final UserCollectionEntity userCollectionEntity) {
        if(mView == null || !mView.checkNetworkState() || !mView.isActive()){
            return ;
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mRxManager.register(mIUserCollectionModel.selectHealthInfoCollectionListByPage(userCollectionEntity).subscribe(new Consumer<ResultEntity>() {
                    //接受数据成功，选择视图显示数据
                    @Override
                    public void accept(ResultEntity resultEntity) throws Exception {
                        if(mView == null || !mView.isActive()){
                            return ;
                        }
                        if (resultEntity.getState() == 200) {
                            UserCollectionEntity data= GsonUtils.parseJsonToObject(resultEntity.getData().toString(),UserCollectionEntity.class);
                            mView.loadMoreDataSuccess(data);
                        } else {
                            mView.getHealthInfoCollectionFail(resultEntity.getMessage());
                        }
                    }
                }, new Consumer<Throwable>() {
                    //接受数据失败，显示错误信息
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        if(mView == null || !mView.isActive()){
                            return ;
                        }
                        mView.getHealthInfoCollectionFail(NetErrorUtils.getErrorMessage(throwable));
                    }
                }));
            }
        },1000);

    }
}

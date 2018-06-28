package com.clw.phaapp.presenter.healthqa;

import android.util.Log;
import com.clw.mysdk.utils.GsonUtils;
import com.clw.phaapp.common.entity.ResultEntity;
import com.clw.phaapp.common.utils.NetErrorUtils;
import com.clw.phaapp.contract.UserCollectionContract;
import com.clw.phaapp.model.entity.AskEntity;
import com.clw.phaapp.model.entity.UserCollectionEntity;
import com.clw.phaapp.presenter.base.BaseUserCollectionPresenter;
import io.reactivex.functions.Consumer;

import java.util.List;


/**
 * 获取收藏问答Presenter
 */
public class GetAskCollectionListPresenter extends BaseUserCollectionPresenter<UserCollectionContract.IGetAskCollectionView>
        implements UserCollectionContract.IGetAskCollectionPresenter{

    private final static String TAG="GetAskCollectionListPresenter";


    /**
     * 下拉刷新数据
     *
     * @param userCollectionEntity
     */
    @Override
    public void refreshData(UserCollectionEntity userCollectionEntity) {
        if(mView == null || !mView.checkNetworkState() || !mView.isActive()){
            return ;
        }
        mRxManager.register(mIUserCollectionModel.selectAskCollectionListByPage(userCollectionEntity).subscribe(new Consumer<ResultEntity>() {
            //接受数据成功，选择视图显示数据
            @Override
            public void accept(ResultEntity resultEntity) throws Exception {
                if(mView == null || !mView.isActive()){
                    return ;
                }
                if (resultEntity.getState() == 200) {
                    UserCollectionEntity data= GsonUtils.parseJsonToObject(resultEntity.getData().toString(),UserCollectionEntity.class);
                    if(data.getTotal() > 0){
                        List asklist=GsonUtils.parseJsonToArrayList(data.getAsklist().toString(), AskEntity.class);
                        data.setAsklist(asklist);

                        List collectionList=GsonUtils.parseJsonToArrayList(data.getRows().toString(),UserCollectionEntity.class);
                        data.setRows(collectionList);

                        for(int i=0,size=asklist.size();i<size;i++){
                            Log.d(TAG,asklist.get(i).toString());
                        }
                    }
                    mView.refreshDataSuccess(data);
                } else {
                    mView.getAskCollectionCollectionFail(resultEntity.getMessage());
                }
            }
        }, new Consumer<Throwable>() {
            //接受数据失败，显示错误信息
            @Override
            public void accept(Throwable throwable) throws Exception {
                if(mView == null || !mView.isActive()){
                    return ;
                }
                mView.getAskCollectionCollectionFail(NetErrorUtils.getErrorMessage(throwable));
            }
        }));
    }

    /**
     * 上拉加载更多
     *
     * @param userCollectionEntity
     */
    @Override
    public void loadMoreData(UserCollectionEntity userCollectionEntity) {
        if(mView == null || !mView.checkNetworkState() || !mView.isActive()){
            return ;
        }
        mRxManager.register(mIUserCollectionModel.selectAskCollectionListByPage(userCollectionEntity).subscribe(new Consumer<ResultEntity>() {
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
                    mView.getAskCollectionCollectionFail(resultEntity.getMessage());
                }
            }
        }, new Consumer<Throwable>() {
            //接受数据失败，显示错误信息
            @Override
            public void accept(Throwable throwable) throws Exception {
                if(mView == null || !mView.isActive()){
                    return ;
                }
                mView.getAskCollectionCollectionFail(NetErrorUtils.getErrorMessage(throwable));
            }
        }));
    }
}

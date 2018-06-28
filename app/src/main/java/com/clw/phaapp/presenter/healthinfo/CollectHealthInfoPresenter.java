package com.clw.phaapp.presenter.healthinfo;

import com.clw.phaapp.common.entity.ResultEntity;
import com.clw.phaapp.common.utils.NetErrorUtils;
import com.clw.phaapp.contract.UserCollectionContract;
import com.clw.phaapp.model.entity.UserCollectionEntity;
import com.clw.phaapp.presenter.base.BaseUserCollectionPresenter;
import io.reactivex.functions.Consumer;


/**
 * 收藏资讯Presenter
 */
public class CollectHealthInfoPresenter extends BaseUserCollectionPresenter<UserCollectionContract.ICollectHealthInfoView>
        implements UserCollectionContract.ICollectHealthInfoPresenter{

    private final static String TAG="CollectHealthInfoPresenter";


    /**
     * 收藏资讯
     *
     * @param userCollectionEntity
     * @param view
     */
    @Override
    public void collectHealthInfo(UserCollectionEntity userCollectionEntity,final UserCollectionContract.ICollectHealthInfoView view) {
        if(view == null || !view.checkNetworkState() || !view.isActive()){
            return ;
        }
        mRxManager.register(mIUserCollectionModel.insertRecord(userCollectionEntity).subscribe(new Consumer<ResultEntity>() {
            //接受数据成功，选择视图显示数据
            @Override
            public void accept(ResultEntity resultEntity) throws Exception {
                if(view == null || !view.isActive()){
                    return ;
                }
                if (resultEntity.getState() == 200) {
                    view.collectHealthInfoSuccess(resultEntity.getMessage());
                } else {
                    view.collectHealthInfoFail(resultEntity.getMessage());
                }
            }
        }, new Consumer<Throwable>() {
            //接受数据失败，显示错误信息
            @Override
            public void accept(Throwable throwable) throws Exception {
                if(view == null || !view.isActive()){
                    return ;
                }
                view.collectHealthInfoFail(NetErrorUtils.getErrorMessage(throwable));
            }
        }));
    }
}

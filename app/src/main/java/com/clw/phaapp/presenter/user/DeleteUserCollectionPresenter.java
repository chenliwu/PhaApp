package com.clw.phaapp.presenter.user;

import com.clw.phaapp.common.entity.ResultEntity;
import com.clw.phaapp.common.utils.NetErrorUtils;
import com.clw.phaapp.contract.UserCollectionContract;
import com.clw.phaapp.model.entity.UserCollectionEntity;
import com.clw.phaapp.presenter.base.BaseUserCollectionPresenter;
import io.reactivex.functions.Consumer;


/**
 * 删除收藏Presenter
 */
public class DeleteUserCollectionPresenter extends BaseUserCollectionPresenter<UserCollectionContract.IDeleteUserCollectionView>
        implements UserCollectionContract.IDeleteUserCollectionPresenter{

    private final static String TAG="DeleteUserCollectionPresenter";


    /**
     * 删除收藏
     *
     * @param userCollectionEntity
     * @param view
     */
    @Override
    public void deleteUserCollection(final UserCollectionEntity userCollectionEntity,final UserCollectionContract.IDeleteUserCollectionView view) {
        if(view == null || !view.checkNetworkState() || !view.isActive()){
            return ;
        }
        mRxManager.register(mIUserCollectionModel.deleteOneRecord(userCollectionEntity).subscribe(new Consumer<ResultEntity>() {
            //接受数据成功，选择视图显示数据
            @Override
            public void accept(ResultEntity resultEntity) throws Exception {
                if(view == null || !view.isActive()){
                    return ;
                }
                if (resultEntity.getState() == 200) {
                    view.deleteUserCollectionSuccess(resultEntity.getMessage());
                } else {
                    view.deleteUserCollectionFail(resultEntity.getMessage());
                }
            }
        }, new Consumer<Throwable>() {
            //接受数据失败，显示错误信息
            @Override
            public void accept(Throwable throwable) throws Exception {
                if(view == null || !view.isActive()){
                    return ;
                }
                view.deleteUserCollectionFail(NetErrorUtils.getErrorMessage(throwable));
            }
        }));
    }


}

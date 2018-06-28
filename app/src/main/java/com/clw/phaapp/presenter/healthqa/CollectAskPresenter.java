package com.clw.phaapp.presenter.healthqa;

import com.clw.phaapp.common.entity.ResultEntity;
import com.clw.phaapp.common.utils.NetErrorUtils;
import com.clw.phaapp.contract.UserCollectionContract;
import com.clw.phaapp.model.entity.UserCollectionEntity;
import com.clw.phaapp.presenter.base.BaseUserCollectionPresenter;
import io.reactivex.functions.Consumer;


/**
 * 收藏回答Presenter
 */
public class CollectAskPresenter extends BaseUserCollectionPresenter<UserCollectionContract.ICollectAskView>
        implements UserCollectionContract.ICollectAskPresenter{

    private final static String TAG="DeleteAskAnswerPresenter";


    /**
     * 收藏问答
     *
     * @param userCollectionEntity
     * @param view
     */
    @Override
    public void collectAsk(UserCollectionEntity userCollectionEntity,final UserCollectionContract.ICollectAskView view) {
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
                    view.collectAskSuccess(resultEntity.getMessage());
                } else {
                    view.collectAskFail(resultEntity.getMessage());
                }
            }
        }, new Consumer<Throwable>() {
            //接受数据失败，显示错误信息
            @Override
            public void accept(Throwable throwable) throws Exception {
                if(view == null || !view.isActive()){
                    return ;
                }
                view.collectAskFail(NetErrorUtils.getErrorMessage(throwable));
            }
        }));
    }
}

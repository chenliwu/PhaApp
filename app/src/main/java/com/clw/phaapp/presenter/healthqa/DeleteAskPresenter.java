package com.clw.phaapp.presenter.healthqa;

import com.clw.phaapp.common.entity.ResultEntity;
import com.clw.phaapp.common.utils.NetErrorUtils;
import com.clw.phaapp.contract.AskContract;
import com.clw.phaapp.model.entity.AskEntity;
import com.clw.phaapp.presenter.base.BaseAskPresenter;
import io.reactivex.functions.Consumer;


/**
 * 删除问题Presenter
 */
public class DeleteAskPresenter extends BaseAskPresenter<AskContract.IDeleteAskView>
        implements AskContract.IDeleteAskPresenter{

    private final static String TAG="DeleteAskPresenter";


    /**
     * 删除问题
     *
     * @param askEntity
     * @param view
     */
    @Override
    public void deleteAsk(final AskEntity askEntity,final AskContract.IDeleteAskView view) {
        if(view == null || !view.isActive() || !view.checkNetworkState()){
            return ;
        }
        mRxManager.register(mAskModel.deleteAsk(askEntity).subscribe(new Consumer<ResultEntity>() {
            //接受数据成功，选择视图显示数据
            @Override
            public void accept(ResultEntity resultEntity) throws Exception {
                if(view == null || !view.isActive()){
                    return ;
                }
                if (resultEntity.getState() == 200) {
                    view.deleteAskSuccess(resultEntity.getMessage());
                } else {
                    view.deleteAskFail(resultEntity.getMessage());
                }
            }
        }, new Consumer<Throwable>() {
            //接受数据失败，显示错误信息
            @Override
            public void accept(Throwable throwable) throws Exception {
                if(view == null || !view.isActive()){
                    return ;
                }
                view.deleteAskFail(NetErrorUtils.getErrorMessage(throwable));
                mView.showErrorMessage("操作失败", NetErrorUtils.getErrorMessage(throwable));
            }
        }));
    }
}

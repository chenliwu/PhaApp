package com.clw.phaapp.presenter.healthqa;

import com.clw.phaapp.common.entity.ResultEntity;
import com.clw.phaapp.common.utils.NetErrorUtils;
import com.clw.phaapp.contract.AskContract;
import com.clw.phaapp.model.entity.AskEntity;
import com.clw.phaapp.presenter.base.BaseAskPresenter;
import io.reactivex.functions.Consumer;


/**
 * 修改问题Presenter
 */
public class ReviseAskPresenter extends BaseAskPresenter<AskContract.IReviseAskView>
        implements AskContract.IReviseAskPresenter{

    private final static String TAG="ReviseAskPresenter";

    /**
     * 修改问题
     *
     * @param askEntity
     */
    @Override
    public void reviseAsk(final AskEntity askEntity) {
        if(mView == null || !mView.isActive() || !mView.checkNetworkState()){
            return ;
        }
        mRxManager.register(mAskModel.reviseAsk(askEntity).subscribe(new Consumer<ResultEntity>() {
            //接受数据成功，选择视图显示数据
            @Override
            public void accept(ResultEntity resultEntity) throws Exception {
                if(mView == null || !mView.isActive()){
                    return ;
                }
                if (resultEntity.getState() == 200) {
                    mView.reviseAskSuccess(resultEntity.getMessage());
                } else {
                    mView.reviseAskFail(resultEntity.getMessage());
                }
            }
        }, new Consumer<Throwable>() {
            //接受数据失败，显示错误信息
            @Override
            public void accept(Throwable throwable) throws Exception {
                if(mView == null || !mView.isActive()){
                    return ;
                }
                mView.reviseAskFail(NetErrorUtils.getErrorMessage(throwable));
                mView.showErrorMessage("操作失败", NetErrorUtils.getErrorMessage(throwable));
            }
        }));
    }
}

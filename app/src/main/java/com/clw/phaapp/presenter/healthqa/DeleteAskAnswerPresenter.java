package com.clw.phaapp.presenter.healthqa;

import com.clw.phaapp.common.entity.ResultEntity;
import com.clw.phaapp.common.utils.NetErrorUtils;
import com.clw.phaapp.contract.AskAnswerContract;
import com.clw.phaapp.model.entity.AskAnswerEntity;
import com.clw.phaapp.presenter.base.BaseAskAnswerPresenter;
import io.reactivex.functions.Consumer;


/**
 * 删除回答Presenter
 */
public class DeleteAskAnswerPresenter extends BaseAskAnswerPresenter<AskAnswerContract.IDeleteAskAnswerView>
        implements AskAnswerContract.IDeleteAskAnswerPresenter{

    private final static String TAG="DeleteAskAnswerPresenter";

    /**
     * 删除回答
     *
     * @param askAnswerEntity
     * @param view
     */
    @Override
    public void deleteAskAnswer(final AskAnswerEntity askAnswerEntity,final AskAnswerContract.IDeleteAskAnswerView view) {
        if(view == null || !view.checkNetworkState() || !view.isActive()){
            return ;
        }
        mRxManager.register(mIAskAnswerModel.deleteAskAnswer(askAnswerEntity).subscribe(new Consumer<ResultEntity>() {
            //接受数据成功，选择视图显示数据
            @Override
            public void accept(ResultEntity resultEntity) throws Exception {
                if(view == null || !view.isActive()){
                    return ;
                }
                if (resultEntity.getState() == 200) {
                    view.deleteAskAnswerSuccess(resultEntity.getMessage());
                } else {
                    view.deleteAskAnswerFail(resultEntity.getMessage());
                }
            }
        }, new Consumer<Throwable>() {
            //接受数据失败，显示错误信息
            @Override
            public void accept(Throwable throwable) throws Exception {
                if(view == null || !view.isActive()){
                    return ;
                }
                view.deleteAskAnswerFail(NetErrorUtils.getErrorMessage(throwable));
            }
        }));
    }
}

package com.clw.phaapp.presenter.healthqa;

import com.clw.phaapp.common.entity.ResultEntity;
import com.clw.phaapp.common.utils.NetErrorUtils;
import com.clw.phaapp.contract.AskAnswerContract;
import com.clw.phaapp.model.entity.AskAnswerEntity;
import com.clw.phaapp.presenter.base.BaseAskAnswerPresenter;
import io.reactivex.functions.Consumer;


/**
 * 采纳回答Presenter
 */
public class AcceptAskAnswerPresenter extends BaseAskAnswerPresenter<AskAnswerContract.IAcceptAskAnswerView>
        implements AskAnswerContract.IAcceptAskAnswerPresenter{

    private final static String TAG="AcceptAskAnswerPresenter";


    /**
     * 采纳回答
     *
     * @param askAnswerEntity
     * @param view
     */
    @Override
    public void acceptAskAnswer(AskAnswerEntity askAnswerEntity,final AskAnswerContract.IAcceptAskAnswerView view) {
        if(view == null || !view.checkNetworkState()){
            return ;
        }
        mRxManager.register(mIAskAnswerModel.acceptAskAnswer(askAnswerEntity).subscribe(new Consumer<ResultEntity>() {
            //接受数据成功，选择视图显示数据
            @Override
            public void accept(ResultEntity resultEntity) throws Exception {
                if(view == null || !view.isActive()){
                    return ;
                }
                if (resultEntity.getState() == 200) {
                    view.acceptAskAnswerSuccess(resultEntity.getMessage());
                } else {
                    view.acceptAskAnswerFail(resultEntity.getMessage());
                }
            }
        }, new Consumer<Throwable>() {
            //接受数据失败，显示错误信息
            @Override
            public void accept(Throwable throwable) throws Exception {
                if(view == null || !view.isActive()){
                    return ;
                }
                view.acceptAskAnswerFail(NetErrorUtils.getErrorMessage(throwable));
            }
        }));
    }
}

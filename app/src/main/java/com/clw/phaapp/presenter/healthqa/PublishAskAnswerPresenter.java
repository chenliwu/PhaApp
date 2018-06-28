package com.clw.phaapp.presenter.healthqa;

import com.clw.phaapp.base.BasePresenter;
import com.clw.phaapp.common.CommonHintInfo;
import com.clw.phaapp.common.entity.ResultEntity;
import com.clw.phaapp.common.utils.NetErrorUtils;
import com.clw.phaapp.contract.AskAnswerContract;
import com.clw.phaapp.contract.AskContract;
import com.clw.phaapp.model.IAskAnswerModel;
import com.clw.phaapp.model.entity.AskAnswerEntity;
import com.clw.phaapp.model.entity.AskEntity;
import com.clw.phaapp.model.impl.AskAnswerModelImpl;
import com.clw.phaapp.ui.healthqa.PublishAskAnswerActivity;
import io.reactivex.functions.Consumer;

/**
 * 回答问题Presenter
 */
public class PublishAskAnswerPresenter extends BasePresenter<AskAnswerContract.IPublishAskAnswerView>
        implements AskAnswerContract.IPublishAskAnswerPresenter{

    private final static String TAG="PublishAskPresenter";

    private IAskAnswerModel mIAskAnswerModel;

    public PublishAskAnswerPresenter(){
        mIAskAnswerModel=new AskAnswerModelImpl();
    }

    /**
     * 发起健康问答
     *
     * @param askAnswerEntity
     */
    @Override
    public void publishAskAnswer(final AskAnswerEntity askAnswerEntity) {
        if(mView == null || !mView.isActive()|| !mView.checkNetworkState()){
            mView.showMessage("", CommonHintInfo.NO_NETWORDK);
            mView.dismissLoading();
            return ;
        }
        mView.showProgress("正在提交");
        new android.os.Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mRxManager.register(mIAskAnswerModel.publishAskAnswer(askAnswerEntity).subscribe(new Consumer<ResultEntity>() {
                    //接受数据成功，选择视图显示数据
                    @Override
                    public void accept(ResultEntity resultEntity) throws Exception {
                        if(mView == null || !mView.isActive()){  //如果视图已经不在活动状态，停止UI操作
                            return ;
                        }
                        mView.dismissProgress();
                        if(resultEntity.getState() == 200){

                            mView.showSuccessMessage("操作成功",resultEntity.getMessage());
                            mView.publishAskAnswerSuccess();
                        }else{
                            mView.publishAskAnswerFail();
                            mView.showErrorMessage("操作失败",resultEntity.getMessage());
                        }
                    }
                }, new Consumer<Throwable>() {
                    //接受数据失败，显示错误信息
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        if(mView == null || !mView.isActive()){  //如果视图已经不在活动状态，停止UI操作
                            return ;
                        }
                        mView.dismissProgress();
                        mView.showErrorMessage("操作失败",NetErrorUtils.getErrorMessage(throwable));
                    }
                }));
            }
        },1000);
    }


}

package com.clw.phaapp.presenter.user;

import com.clw.phaapp.common.CommonHintInfo;
import com.clw.phaapp.common.entity.ResultEntity;
import com.clw.phaapp.common.utils.NetErrorUtils;
import com.clw.phaapp.contract.UserContract;
import com.clw.phaapp.model.entity.UserFeedbackEntity;
import com.clw.phaapp.presenter.base.BaseUserPresenter;
import io.reactivex.functions.Consumer;

/**
 * 用户反馈Presenter
 */
public class FeedbackUserPresenter extends BaseUserPresenter<UserContract.IFeedbackView>
        implements UserContract.IFeedbackPresenter{

    private final static String TAG="LoginUserPresenter";

    @Override
    public void feedback(final UserFeedbackEntity userFeedbackEntity) {
        if(!mView.checkNetworkState()){
            mView.showMessage("", CommonHintInfo.NO_NETWORDK);
            return ;
        }
        mView.showProgress("正在提交");
        new android.os.Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mRxManager.register(mUserModel.feedback(userFeedbackEntity).subscribe(new Consumer<ResultEntity>() {
                    //接受数据成功，选择视图显示数据
                    @Override
                    public void accept(ResultEntity resultEntity) throws Exception {
                        if(!mView.isActive()){  //如果视图已经不在活动状态，停止UI操作
                            return ;
                        }
                        mView.dismissProgress();
                        if(resultEntity.getState() == 200){
                            mView.showSuccessMessage("反馈成功",resultEntity.getMessage());
                            mView.feedbackSuccess();
                        }else{
                            mView.showErrorMessage("反馈失败",resultEntity.getMessage());
                        }
                    }
                }, new Consumer<Throwable>() {
                    //接受数据失败，显示错误信息
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        if(!mView.isActive()){  //如果视图已经不在活动状态，停止UI操作
                            return ;
                        }
                        mView.dismissProgress();
                        mView.showErrorMessage("反馈失败",NetErrorUtils.getErrorMessage(throwable));
                    }
                }));
            }
        },1000);

    }
}

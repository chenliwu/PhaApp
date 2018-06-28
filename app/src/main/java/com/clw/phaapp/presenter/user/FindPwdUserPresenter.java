package com.clw.phaapp.presenter.user;

import com.clw.phaapp.common.CommonHintInfo;
import com.clw.phaapp.common.entity.ResultEntity;
import com.clw.phaapp.common.utils.NetErrorUtils;
import com.clw.phaapp.contract.UserContract;
import com.clw.phaapp.model.entity.UserEntity;
import com.clw.phaapp.presenter.base.BaseUserPresenter;
import io.reactivex.functions.Consumer;

/**
 * @author clw
 * @create 2018-03-04 20:41
 **/
public class FindPwdUserPresenter extends BaseUserPresenter<UserContract.IFindPwdView>
        implements  UserContract.IFindPwdPresenter{

    /**
     * 找回密码
     *
     * @param userEntity
     */
    @Override
    public void findPwd(final UserEntity userEntity) {
        if(!mView.checkNetworkState()){
            mView.showMessage("", CommonHintInfo.NO_NETWORDK);
            return ;
        }
        mView.showProgress("正在处理");
        new android.os.Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mRxManager.register(mUserModel.findPwd(userEntity).subscribe(new Consumer<ResultEntity>() {
                    //接受数据成功，选择视图显示数据
                    @Override
                    public void accept(ResultEntity resultEntity) throws Exception {
                        if(!mView.isActive()){  //如果视图已经不在活动状态，停止UI操作
                            return ;
                        }
                        mView.dismissProgress();
                        if(resultEntity.getState() == 200){
                            mView.showSuccessMessage("操作成功",resultEntity.getMessage());
                            mView.findPwdSuccess();
                        }else{
                            mView.showErrorMessage("操作失败",resultEntity.getMessage());
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
                        mView.showErrorMessage("操作失败", NetErrorUtils.getErrorMessage(throwable));
                    }
                }));
            }
        },1000);
    }
}

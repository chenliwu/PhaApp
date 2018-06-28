package com.clw.phaapp.presenter.user;

import com.clw.mysdk.utils.GsonUtils;
import com.clw.phaapp.common.CommonHintInfo;
import com.clw.phaapp.common.entity.ResultEntity;
import com.clw.phaapp.common.utils.NetErrorUtils;
import com.clw.phaapp.contract.UserContract;
import com.clw.phaapp.model.entity.UserEntity;
import com.clw.phaapp.presenter.base.BaseUserPresenter;
import io.reactivex.functions.Consumer;
/**
 * MVP中的Presenter层：负责处理业务逻辑代码，处理Model数据，然后分发给View层的抽象接口。<p></p>
 * Presenter是用作Model和View之间交互的桥梁。
 * 应用程序主要的程序逻辑在Presenter内实现，Presenter将Model和View完全分离、所有的交互都发生在Presenter内部
 * 其实也是主要看该功能有什么操作，比如本例，两个操作:login和clear。<p></p>
 *
 * 在Presenter实现类中，要通过泛型说明视图的类型。
 */
public class LoginUserPresenter extends BaseUserPresenter<UserContract.ILoginView>
        implements UserContract.ILoginPresenter{

    private final static String TAG="LoginUserPresenter";

    @Override
    public void login(final UserEntity userEntity) {
        if(!mView.checkNetworkState()){
            mView.showMessage("", CommonHintInfo.NO_NETWORDK);
            return ;
        }
        mView.showProgress("正在登录中");
        new android.os.Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mRxManager.register(mUserModel.login(userEntity).subscribe(new Consumer<ResultEntity>() {
                    //接受数据成功，选择视图显示数据
                    @Override
                    public void accept(ResultEntity resultEntity) throws Exception {
                        if(!mView.isActive()){  //如果视图已经不在活动状态，停止UI操作
                            return ;
                        }
                        mView.dismissProgress();
                        if(resultEntity.getState() == 200){
                            //mView.showMessage("登录成功","登录成功");
                            mView.showSuccessMessage("登录成功",resultEntity.getMessage());
                            mView.loginSuccess(GsonUtils.parseJsonToObject(resultEntity.getData().toString(),UserEntity.class));
                        }else{
                            mView.showErrorMessage("登录失败",resultEntity.getMessage());
                            //mView.showMessage("登录失败",resultEntity.getMessage());
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
                        mView.showErrorMessage("登录失败",NetErrorUtils.getErrorMessage(throwable));
                        //mView.showMessage("登录失败", NetErrorUtils.getErrorMessage(throwable));
                    }
                }));
            }
        },1000);

    }
}

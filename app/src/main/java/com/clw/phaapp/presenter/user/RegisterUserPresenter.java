package com.clw.phaapp.presenter.user;

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
public class RegisterUserPresenter extends BaseUserPresenter<UserContract.IRegisterView> implements UserContract.IRegisterPresenter{

    private final static String TAG="RegisterUserPresenter";


    /**
     * 注册账号
     *
     * @param userEntity
     */
    @Override
    public void register(final UserEntity userEntity) {
        if(!mView.checkNetworkState()){
            mView.showMessage("", CommonHintInfo.NO_NETWORDK);
            return ;
        }
        mView.showProgress("正在处理中");
        new android.os.Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mRxManager.register(mUserModel.register(userEntity).subscribe(new Consumer<ResultEntity>() {
                    @Override
                    public void accept(ResultEntity resultEntity) throws Exception {
                        if(!mView.isActive()){
                            return ;
                        }
                        mView.dismissProgress();
                        mView.showMessage("提示信息",resultEntity.getMessage());
                        if(resultEntity.getState() == 200){
                            mView.registerSuccess();
                            mView.closeView();
                        }else if(resultEntity.getState() == 500){   //账号已被注册
                            mView.showMessage("注册失败",resultEntity.getMessage());
                        }else{
                            mView.showMessage("注册失败",resultEntity.getMessage());
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        if(!mView.isActive()){
                            return ;
                        }
                        mView.showErrorMessage("提示信息", NetErrorUtils.getErrorMessage(throwable));
                    }
                }));
            }
        },1000);
    }
}

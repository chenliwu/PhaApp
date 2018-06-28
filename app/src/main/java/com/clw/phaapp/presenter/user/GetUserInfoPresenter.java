package com.clw.phaapp.presenter.user;

import android.util.Log;
import com.clw.mysdk.utils.GsonUtils;
import com.clw.phaapp.base.BasePresenter;
import com.clw.phaapp.common.CommonHintInfo;
import com.clw.phaapp.common.entity.ResultEntity;
import com.clw.phaapp.common.utils.NetErrorUtils;
import com.clw.phaapp.contract.UserContract;
import com.clw.phaapp.model.IUserModel;
import com.clw.phaapp.model.entity.UserEntity;
import com.clw.phaapp.model.impl.UserModelImpl;
import io.reactivex.functions.Consumer;
import org.json.JSONObject;

/**
 * 获取用户状态Presenter
 */
public class GetUserInfoPresenter extends BasePresenter<UserContract.IGetUserInfoView>
        implements UserContract.IGetUserInfoPresenter{

    private final static String TAG="GetUserInfoPresenter";

    /**
     * 用户业务接口，提供用户的业务方法调用
     */
    protected IUserModel mUserModel;

    public GetUserInfoPresenter(){
        mUserModel=new UserModelImpl();
    }

    @Override
    public void getUserInfo(final UserEntity userEntity,final UserContract.IGetUserInfoView iGetUserStatusView) {
        if(!iGetUserStatusView.checkNetworkState()){
            iGetUserStatusView.showMessage("", CommonHintInfo.NO_NETWORDK);
            return ;
        }
        mRxManager.register(mUserModel.getUserInfo(userEntity).subscribe(new Consumer<ResultEntity>() {
            //接受数据成功，选择视图显示数据
            @Override
            public void accept(ResultEntity resultEntity) throws Exception {
                //服务器返回的data数据，数字类型会变成0.0
                if(resultEntity.getState() == 200){
                    iGetUserStatusView.getUserInfoSuccess(GsonUtils.parseJsonToObject(resultEntity.getData().toString().trim().replace(" ",""),UserEntity.class));
                }else{
                    iGetUserStatusView.showErrorMessage("失败",resultEntity.getMessage());
                    iGetUserStatusView.getUserInfoFail();
                }
            }
        }, new Consumer<Throwable>() {
            //接受数据失败，显示错误信息
            @Override
            public void accept(Throwable throwable) throws Exception {
                Log.e(TAG,throwable.toString());
                iGetUserStatusView.getUserInfoFail();
                iGetUserStatusView.showErrorMessage("失败",NetErrorUtils.getErrorMessage(throwable));
            }
        }));
    }
}

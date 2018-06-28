package com.clw.phaapp.presenter.user;

import com.clw.phaapp.common.entity.ResultEntity;
import com.clw.phaapp.common.utils.NetErrorUtils;
import com.clw.phaapp.contract.UserContract;
import com.clw.phaapp.presenter.base.BaseUserPresenter;
import io.reactivex.functions.Consumer;

import java.io.File;

/**
 * 上传头像Presenter
 */
public class UploadImgPresenter extends BaseUserPresenter<UserContract.IUploadImgView>
        implements UserContract.IUploadImgPresenter {

    private final static String TAG = "UploadImgPresenter";

    /**
     * 上传头像
     *
     * @param file
     * @param view
     */
    @Override
    public void uploadImg(final File file,final UserContract.IUploadImgView view) {
        if(view == null || !view.checkNetworkState() || !view.isActive()){
            return ;
        }
        mRxManager.register(mUserModel.uploadImg(file).subscribe(new Consumer<ResultEntity>() {
            //接受数据成功，选择视图显示数据
            @Override
            public void accept(ResultEntity resultEntity) throws Exception {
                if(view == null || !view.isActive()){
                    return ;
                }

                if (resultEntity.getState() == 200) {
                    view.uploadImgSuccess(resultEntity.getData().toString());

                } else {
                    view.uploadImgFail(resultEntity.getMessage());
                }
            }
        }, new Consumer<Throwable>() {
            //接受数据失败，显示错误信息
            @Override
            public void accept(Throwable throwable) throws Exception {
                if(view == null || !view.isActive()){
                    return ;
                }
                view.uploadImgFail(NetErrorUtils.getErrorMessage(throwable));
            }
        }));
    }



}

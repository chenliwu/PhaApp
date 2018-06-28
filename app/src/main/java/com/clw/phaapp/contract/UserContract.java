package com.clw.phaapp.contract;


import com.clw.phaapp.base.IBaseView;
import com.clw.phaapp.model.entity.UserEntity;
import com.clw.phaapp.model.entity.UserFeedbackEntity;

import java.io.File;

/**
 * 用户业务契约接口
 * 说明View层要做什么、Presenter层要做什么
 * <p>Contract其实就是一个包涵了Presenter和View的接口，Presenter实现的逻辑层方法，
 * View实现的UI层的方法都能在Contract接口中一目了然的看明白，
 * 具体的Presenter和View的实现类都是通过实现Contract接口来完成。这种方式既方便了管理和维护，也给开发点了一个导航灯。</p>
 */
public interface UserContract {

    ///////////////////////////////////////////
    /**
     * 获取用户信息View层接口
     */
    interface IGetUserInfoView extends IBaseView{

        /**
         * 成功回调方法
         */
        void getUserInfoSuccess(UserEntity userEntity);
        /**
         * 失败回调方法
         */
        void getUserInfoFail();
    }
    /**
     * 注册账号Presenter层接口
     */
    interface  IGetUserInfoPresenter{
        /**
         * 获取用户信息
         * @param userEntity
         * @param iGetUserStatusView 回调接口
         */
        void getUserInfo(UserEntity userEntity,IGetUserInfoView iGetUserStatusView);
    }
    ///////////////////////////////////////////







    ///////////////////////////////////////////
    /**
     * 注册账号View层接口
     */
    interface IRegisterView extends IBaseView{

        String getUsercode();
        String getPwd();
        String getSecurity();
        String getAnswer();

        /**
         * 注册成功回调方法
         */
        void registerSuccess();
    }
    /**
     * 注册账号Presenter层接口
     */
    interface  IRegisterPresenter{

        /**
         * 注册账号
         * @param userEntity
         */
        void register(UserEntity userEntity);
    }
    ///////////////////////////////////////////


    /**
     * 登录View层接口
     */
    interface ILoginView extends IBaseView {
        String getUsercode();
        String getPwd();
        void clearUsercode();
        void clearPwd();
        void loginSuccess(UserEntity userEntity);

    }
    /**
     * 登录Presenter层接口
     */
    interface ILoginPresenter {
        /**
         * 用户登录
         * @param userEntity
         */
        void login(UserEntity userEntity);
    }


    /////////////////////////////////////////////
    /**
     * 修改密码View层接口
     */
    interface IRevisePwdView extends IBaseView{
        String getOldPwd();
        String getNewPwd();
        String getConfPwd();
        /**
         * 修改密码成功回调
         */
        void revisePwdSuccess();
    }
    /**
     *  修改密码Presenter层接口
     */
    interface IRevisePwdPresenter{
        /**
         *  修改密码
         * @param userEntity
         */
        void revisePwd(UserEntity userEntity);
    }


    /////////////////////////////////////////////
    /**
     * 找回密码View层接口
     */
    interface IFindPwdView extends IBaseView{

        String getNewPwd();
        String getConfPwd();

        /**
         * 找回密码成功回调
         */
        void findPwdSuccess();
    }
    /**
     *  找回密码Presenter层接口
     */
    interface IFindPwdPresenter{
        /**
         *  找回密码
         * @param userEntity
         */
        void findPwd(UserEntity userEntity);
    }


    //////////////////////////////////////////
    /**
     * 维护个人信息View层接口
     */
    interface IUpdateView extends IBaseView{


        /**
         * 成功回调接口
         */
        void updateSuccess();

        /**
         * 失败回调接口
         */
        void updateFail();
    }

    /**
     *  维护个人信息Presenter层接口
     */
    interface IUpdatePresenter{
        /**
         *  维护个人信息
         * @param userEntity
         */
        void update(UserEntity userEntity);
    }
    /////////////////////////////////////////////////



    //////////////////  用户反馈   ////////////////////////
    /**
     * 用户反馈View层接口
     */
    interface IFeedbackView extends IBaseView {


        /**
         * 成功回调接口
         */
        void feedbackSuccess();

        /**
         * 失败回调接口
         */
        void feedbackFail();
    }

    /**
     *  用户反馈Presenter层接口
     */
    interface IFeedbackPresenter{
        /**
         *  用户反馈
         * @param userFeedbackEntity
         */
        void feedback(UserFeedbackEntity userFeedbackEntity);
    }
    /////////////////////////////////////////////////


    //////////////////  上传头像   ////////////////////////
    /**
     * 上传文件View层接口
     */
    interface IUploadImgView extends IBaseView {


        /**
         * 成功回调接口
         */
        void uploadImgSuccess(String path);

        /**
         * 失败回调接口
         */
        void uploadImgFail(String msg);
    }

    /**
     * 上传文件Presenter层接口
     */
    interface IUploadImgPresenter{
        /**
         *  上传头像
         * @param file
         */
        void uploadImg(File file,IUploadImgView view);
    }
    /////////////////////////////////////////////////


}

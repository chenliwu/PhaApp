package com.clw.phaapp.model.impl;

import com.clw.mysdk.retrofit.RetrofitCreateHelper;
import com.clw.mysdk.rxjava.RxHelper;
import com.clw.phaapp.api.IUserModelApi;
import com.clw.phaapp.app.ServerInformation;
import com.clw.phaapp.common.entity.ResultEntity;
import com.clw.phaapp.model.IUserModel;
import com.clw.phaapp.model.entity.UserEntity;
import com.clw.phaapp.model.entity.UserFeedbackEntity;
import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * @author clw
 * @create 2018-01-28 16:34
 **/
public class UserModelImpl implements IUserModel {

    /**
     * API接口对象
     */
    private IUserModelApi mIUserModelApi;

    public UserModelImpl(){
        mIUserModelApi=(RetrofitCreateHelper
                .createApi(IUserModelApi.class, ServerInformation.BASE_URL));
    }

    /**
     * 获取用户状态，返回status：0正常，1锁定
     *
     * @param userEntity
     * @return
     */
    @Override
    public Observable<ResultEntity> getUserStatus(UserEntity userEntity) {
        Map<String, RequestBody> bodyMap = new HashMap<>();
        bodyMap.put("recno", toRequestBody(String.valueOf(userEntity.getRecno())));
        return mIUserModelApi.getUserStatus(bodyMap)
                .compose(RxHelper.<ResultEntity>rxSchedulerHelper());
    }

    /**
     * 注册账号
     *
     * @param userEntity
     * @return
     */
    @Override
    public Observable<ResultEntity> register(UserEntity userEntity) {
        Map<String, RequestBody> bodyMap = new HashMap<>();
        bodyMap.put("usercode", toRequestBody(userEntity.getUsercode()));
        bodyMap.put("pwd", toRequestBody(userEntity.getPwd()));
        bodyMap.put("nickname", toRequestBody(userEntity.getNickname()));
        bodyMap.put("security", toRequestBody(userEntity.getSecurity()));
        bodyMap.put("answer", toRequestBody(userEntity.getAnswer()));
        return mIUserModelApi.register(bodyMap)
                .compose(RxHelper.<ResultEntity>rxSchedulerHelper());
    }

    /**
     * 解决Retrofit以Mutipart上传参数时，String参数会多一对双引号
     * @param value
     * @return
     */
    public static RequestBody toRequestBody(String value) {
        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), value);
        return requestBody;
    }



    /**
     * 用户登录
     *
     * @param userEntity
     * @return
     */
    @Override
    public Observable<ResultEntity> login(UserEntity userEntity) {
        return mIUserModelApi.login(userEntity.getUsercode(),userEntity.getPwd())
                .compose(RxHelper.<ResultEntity>rxSchedulerHelper());
    }

    /**
     * 修改密码
     *
     * @param userEntity
     * @return
     */
    @Override
    public Observable<ResultEntity> revisePwd(UserEntity userEntity) {
        Map<String, RequestBody> bodyMap = new HashMap<>();
        bodyMap.put("recno",toRequestBody(String.valueOf(userEntity.getRecno().toString())));
        bodyMap.put("pwd",toRequestBody(userEntity.getPwd()));
        return mIUserModelApi.revisePwd(bodyMap)
                .compose(RxHelper.<ResultEntity>rxSchedulerHelper());
    }

    /**
     * 找回密码
     *
     * @param userEntity
     * @return
     */
    @Override
    public Observable<ResultEntity> findPwd(UserEntity userEntity) {
        Map<String, RequestBody> bodyMap = new HashMap<>();
        bodyMap.put("recno",toRequestBody(String.valueOf(userEntity.getRecno().toString())));
        bodyMap.put("pwd",toRequestBody(userEntity.getPwd()));
        return mIUserModelApi.findPwd(bodyMap)
                .compose(RxHelper.<ResultEntity>rxSchedulerHelper());
    }

    /**
     * 获取用户信息
     *
     * @param userEntity
     * @return
     */
    @Override
    public Observable<ResultEntity> getUserInfo(UserEntity userEntity) {
        return mIUserModelApi.getUserInfo(userEntity.getUsercode())
                .compose(RxHelper.<ResultEntity>rxSchedulerHelper());
    }

    /**
     * 维护个人信息
     *
     * @param userEntity
     * @return
     */
    @Override
    public Observable<ResultEntity> update(UserEntity userEntity) {
        Map<String, RequestBody> bodyMap = new HashMap<>();
        bodyMap.put("recno",toRequestBody(String.valueOf(userEntity.getRecno())));
        if(userEntity.getNickname()!=null){
            bodyMap.put("nickname", toRequestBody(userEntity.getNickname()));
        }
        if(userEntity.getName()!=null){
            bodyMap.put("name", toRequestBody(userEntity.getName()));
        }
        if(userEntity.getSex()!=null){
            bodyMap.put("sex", toRequestBody(userEntity.getSex()));
        }
        if(userEntity.getBirthday()!=null){
            bodyMap.put("birthday", toRequestBody(String.valueOf(userEntity.getBirthday())));
        }
        return mIUserModelApi.update(bodyMap)
                .compose(RxHelper.<ResultEntity>rxSchedulerHelper());
    }

    /**
     * 用户反馈
     *
     * @param userFeedbackEntity
     * @return
     */
    @Override
    public Observable<ResultEntity> feedback(UserFeedbackEntity userFeedbackEntity) {
        Map<String, RequestBody> bodyMap = new HashMap<>();
        if(userFeedbackEntity.getUserrecno()!=null){
            bodyMap.put("userrecno", toRequestBody(userFeedbackEntity.getUserrecno()));
        }
        if(userFeedbackEntity.getType()!=null){
            bodyMap.put("type", toRequestBody(String.valueOf(userFeedbackEntity.getType())));
        }
        if(userFeedbackEntity.getContact()!=null){
            bodyMap.put("contact", toRequestBody(userFeedbackEntity.getContact()));
        }
        if(userFeedbackEntity.getContent()!=null){
            bodyMap.put("content", toRequestBody(userFeedbackEntity.getContent()));
        }
        if(userFeedbackEntity.getOpdate()!=null){
            bodyMap.put("opdate", toRequestBody(String.valueOf(userFeedbackEntity.getOpdate())));
        }
        return mIUserModelApi.feekback(bodyMap)
                .compose(RxHelper.<ResultEntity>rxSchedulerHelper());
    }


    /**
     * 上传头像
     *
     * @param file
     * @return
     */
    @Override
    public Observable<ResultEntity> uploadImg(File file) {
        //构建body
        RequestBody requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("imgFile", file.getName(), RequestBody.create(MediaType.parse("image/*"), file))
                .build();
        return mIUserModelApi.uploadImg(requestBody)
                .compose(RxHelper.<ResultEntity>rxSchedulerHelper());
    }
}

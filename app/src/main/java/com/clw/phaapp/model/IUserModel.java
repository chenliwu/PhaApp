package com.clw.phaapp.model;

import com.clw.phaapp.common.entity.ResultEntity;
import com.clw.phaapp.model.entity.UserEntity;
import com.clw.phaapp.model.entity.UserFeedbackEntity;
import io.reactivex.Observable;

import java.io.File;

/**
 * 与用户相关的业务接口
 * @author clw
 * @create 2018-01-28 16:31
 **/
public interface IUserModel {

    /**
     * 获取用户状态，返回status：0正常，1锁定
     * @param userEntity
     * @return
     */
    Observable<ResultEntity> getUserStatus(UserEntity userEntity);

    /**
     * 注册账号
     * @param userEntity
     * @return
     */
    Observable<ResultEntity> register(UserEntity userEntity);

    /**
     * 用户登录
     * @param userEntity
     * @return
     */
    Observable<ResultEntity> login(UserEntity userEntity);

    /**
     * 修改密码
     * @param userEntity
     * @return
     */
    Observable<ResultEntity> revisePwd(UserEntity userEntity);

    /**
     * 找回密码
     * @param userEntity
     * @return
     */
    Observable<ResultEntity> findPwd(UserEntity userEntity);

    /**
     * 获取用户信息
     * @param userEntity
     * @return
     */
    Observable<ResultEntity> getUserInfo(UserEntity userEntity);

    /**
     * 维护个人信息
     * @param userEntity
     * @return
     */
    Observable<ResultEntity> update(UserEntity userEntity);


    /**
     * 用户反馈
     * @param userFeedbackEntity
     * @return
     */
    Observable<ResultEntity> feedback(UserFeedbackEntity userFeedbackEntity);


    /**
     * 上传头像
     * @param file
     * @return
     */
    Observable<ResultEntity> uploadImg(File file);

}

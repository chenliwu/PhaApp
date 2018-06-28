package com.clw.phaapp.base;

import android.os.Bundle;
import com.clw.mysdk.base.activity.BaseSlidingAppComatActivity;
import com.clw.phaapp.common.SharePreferencesKey;
import com.clw.phaapp.model.entity.UserEntity;

/**
 * 业务公用Activity类，将一些业务逻辑可能要用到的类都抽象到这里，降低代码冗余
 * BaseAppComatActivity ---> BaseSlidingAppComatActivity ---> BaseModelAppComatActivity
 * @author clw
 * @create 2018-03-04 13:04
 **/
public abstract class BaseModelAppComatActivity extends BaseSlidingAppComatActivity {

    /**
     * 记录用户信息
     */
    protected UserEntity mUserEntity;

    /**
     * 用户登录状态---true为登录，false为未登录
     */
    protected boolean isLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * 从SharedPreferences中读取用户信息，并初始化
     */
    protected void initUserInfoFromSharedPreferences(){
        //获取登录状态
        isLogin = getBooleanFromSharedPreferences(SharePreferencesKey
                .IS_LOGIN, false);
        if(!isLogin){
            return ;
        }
        //读取用户json字符串并解析
        String json = getStringFromSharedPreferences(SharePreferencesKey
                .LOGIN_USER_INFO, "");
        mUserEntity = new UserEntity();
        mUserEntity = (UserEntity) mUserEntity.fromJson(json);
    }
}

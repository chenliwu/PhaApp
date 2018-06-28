package com.clw.phaapp.api;

import com.clw.phaapp.common.entity.ResultEntity;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.*;

import java.util.Map;

/**
 * 用户业务API接口
 */
public interface IUserModelApi {

    /**
     * 获取用户状态，返回status：0正常，1锁定
     * @param params
     * @return
     */
    @Multipart
    @POST("user/getUserStatus")
    Observable<ResultEntity> getUserStatus(@PartMap Map<String, RequestBody> params);

    /**
     * 注册账号
     * @param params
     * @return
     */
    @Multipart
    @POST("user/register")
    Observable<ResultEntity> register(@PartMap Map<String, RequestBody> params);

    /**
     * 用户登录
     * @param usercode
     * @param pwd
     * @return
     */
    @POST("user/login")
    Observable<ResultEntity> login(@Query("usercode") String usercode,@Query("pwd")String pwd);

    /**
     * 修改密码
     * @param params
     * @return
     */
    @Multipart
    @POST("user/revisePwd")
    Observable<ResultEntity> revisePwd(@PartMap Map<String, RequestBody> params);

    /**
     * 找回密码
     * @param params
     * @return
     */
    @Multipart
    @POST("user/findPwd")
    Observable<ResultEntity> findPwd(@PartMap Map<String, RequestBody> params);

    /**
     * 获取用户信息 query字段会被默认使用urlencode编码，encoded 设置为false 时 传输的值不会被urlencode（默认）
     * @param usercode
     * @return
     */
    @GET("user/getUserInfo")
    Observable<ResultEntity> getUserInfo(@Query("usercode") String usercode);

    /**
     * 找回密码
     * @param params
     * @return
     */
    @Multipart
    @POST("user/update")
    Observable<ResultEntity> update(@PartMap Map<String, RequestBody> params);


    /**
     * 用户反馈
     * @param params
     * @return
     */
    @Multipart
    @POST("userFeedback/insertRecord")
    Observable<ResultEntity> feekback(@PartMap Map<String, RequestBody> params);


    /**
     * 上传头像
     * @param body
     * @return
     */
    @Multipart
    @POST("upload/img")
    Observable<ResultEntity> uploadImg(@Body RequestBody body);

}

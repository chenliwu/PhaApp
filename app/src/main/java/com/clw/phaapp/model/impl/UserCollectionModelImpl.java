package com.clw.phaapp.model.impl;

import com.clw.mysdk.retrofit.RetrofitCreateHelper;
import com.clw.mysdk.rxjava.RxHelper;
import com.clw.phaapp.api.IUserCollectionApi;
import com.clw.phaapp.app.ServerInformation;
import com.clw.phaapp.common.entity.ResultEntity;
import com.clw.phaapp.model.IUserCollectionModel;
import com.clw.phaapp.model.entity.UserCollectionEntity;
import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.RequestBody;

import java.util.HashMap;
import java.util.Map;

/**
 * 用户收藏Model实现类
 * @author chenliwu
 * @create 2018-04-06 12:55
 **/
public class UserCollectionModelImpl implements IUserCollectionModel{



    private IUserCollectionApi mIUserCollectionApi;

    public UserCollectionModelImpl(){
        mIUserCollectionApi=(RetrofitCreateHelper
                .createApi(IUserCollectionApi.class, ServerInformation.BASE_URL));
    }



    /**
     * 解决Retrofit以Mutipart上传参数时，String参数会多一对双引号
     * @param value
     * @return
     */
    public RequestBody toRequestBody(String value) {
        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), value);
        return requestBody;
    }

    /**
     * 添加收藏记录
     * 添加收藏记录需要的字段：收藏模板记录号，用户记录号，收藏类型
     *
     * @param userCollectionEntity
     * @return
     */
    @Override
    public Observable<ResultEntity> insertRecord(UserCollectionEntity userCollectionEntity) {
        Map<String, RequestBody> bodyMap = new HashMap<>();
        if(userCollectionEntity.getTargetrecno() != null){
            bodyMap.put("targetrecno",toRequestBody(String.valueOf(userCollectionEntity.getTargetrecno())));
        }
        if(userCollectionEntity.getUserrecno() != null){
            bodyMap.put("userrecno",toRequestBody(String.valueOf(userCollectionEntity.getUserrecno())));
        }
        if(userCollectionEntity.getType() != null){
            bodyMap.put("type",toRequestBody(String.valueOf(userCollectionEntity.getType())));
        }
        return mIUserCollectionApi.insertRecord(bodyMap)
                .compose(RxHelper.<ResultEntity>rxSchedulerHelper());
    }

    /**
     * 分页查询，收藏记录
     *
     * @param userCollectionEntity
     * @return
     */
    @Override
    public Observable<ResultEntity> selectRecordsByPage(UserCollectionEntity userCollectionEntity) {
        Map<String, RequestBody> bodyMap = new HashMap<>();
        if(userCollectionEntity.getTargetrecno() != null){
            bodyMap.put("targetrecno",toRequestBody(String.valueOf(userCollectionEntity.getTargetrecno())));
        }
        if(userCollectionEntity.getUserrecno() != null){
            bodyMap.put("userrecno",toRequestBody(String.valueOf(userCollectionEntity.getUserrecno())));
        }
        if(userCollectionEntity.getType() != null){
            bodyMap.put("type",toRequestBody(String.valueOf(userCollectionEntity.getType())));
        }
        bodyMap.put("page",toRequestBody(String.valueOf(userCollectionEntity.getPage())));
        return mIUserCollectionApi.selectRecordsByPage(bodyMap)
                .compose(RxHelper.<ResultEntity>rxSchedulerHelper());
    }


    /**
     * 分页查询，健康问答收藏记录
     *
     * @param userCollectionEntity
     * @return
     */
    @Override
    public Observable<ResultEntity> selectAskCollectionListByPage(UserCollectionEntity userCollectionEntity) {
        Map<String, RequestBody> bodyMap = new HashMap<>();
        if(userCollectionEntity.getTargetrecno() != null){
            bodyMap.put("targetrecno",toRequestBody(String.valueOf(userCollectionEntity.getTargetrecno())));
        }
        if(userCollectionEntity.getUserrecno() != null){
            bodyMap.put("userrecno",toRequestBody(String.valueOf(userCollectionEntity.getUserrecno())));
        }
        if(userCollectionEntity.getType() != null){
            bodyMap.put("type",toRequestBody(String.valueOf(userCollectionEntity.getType())));
        }
        return mIUserCollectionApi.selectAskCollectionListByPage(bodyMap)
                .compose(RxHelper.<ResultEntity>rxSchedulerHelper());
    }

    /**
     * 分页查询，健康资讯收藏记录
     *
     * @param userCollectionEntity
     * @return
     */
    @Override
    public Observable<ResultEntity> selectHealthInfoCollectionListByPage(UserCollectionEntity userCollectionEntity) {
        Map<String, RequestBody> bodyMap = new HashMap<>();
        if(userCollectionEntity.getTargetrecno() != null){
            bodyMap.put("targetrecno",toRequestBody(String.valueOf(userCollectionEntity.getTargetrecno())));
        }
        if(userCollectionEntity.getUserrecno() != null){
            bodyMap.put("userrecno",toRequestBody(String.valueOf(userCollectionEntity.getUserrecno())));
        }
        if(userCollectionEntity.getType() != null){
            bodyMap.put("type",toRequestBody(String.valueOf(userCollectionEntity.getType())));
        }
        return mIUserCollectionApi.selectHealthInfoCollectionListByPage(bodyMap)
                .compose(RxHelper.<ResultEntity>rxSchedulerHelper());
    }

    /**
     * 删除单条收藏记录
     *
     * @param userCollectionEntity
     * @return
     */
    @Override
    public Observable<ResultEntity> deleteOneRecord(UserCollectionEntity userCollectionEntity) {
        Map<String, RequestBody> bodyMap = new HashMap<>();
        if(userCollectionEntity.getRecno() != null){
            bodyMap.put("recno",toRequestBody(String.valueOf(userCollectionEntity.getRecno())));
        }
        return mIUserCollectionApi.deleteOneRecord(bodyMap)
                .compose(RxHelper.<ResultEntity>rxSchedulerHelper());
    }
}

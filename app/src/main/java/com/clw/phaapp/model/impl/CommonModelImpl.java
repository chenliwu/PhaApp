package com.clw.phaapp.model.impl;

import com.clw.mysdk.retrofit.RetrofitCreateHelper;
import com.clw.mysdk.rxjava.RxHelper;
import com.clw.phaapp.api.ICommonAPI;
import com.clw.phaapp.app.ServerInformation;
import com.clw.phaapp.common.entity.ResultEntity;
import com.clw.phaapp.model.ICommonModel;
import com.clw.phaapp.model.entity.LogEntity;
import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.RequestBody;

import java.util.HashMap;
import java.util.Map;

/**
 * 公共业务类
 *
 * @author chenliwu
 * @create 2018-03-20 23:47
 **/
public class CommonModelImpl implements ICommonModel {

    private ICommonAPI mICommonAPI;

    public CommonModelImpl(){
        mICommonAPI=(RetrofitCreateHelper
                .createApi(ICommonAPI.class, ServerInformation.BASE_URL));
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
     * 插入访问日志
     *
     * @param logEntity
     * @return
     */
    @Override
    public Observable<ResultEntity> insertLogRecord(LogEntity logEntity) {
        Map<String, RequestBody> bodyMap = new HashMap<>();
        if(logEntity.getType()!=null){
            bodyMap.put("type",toRequestBody(String.valueOf(logEntity.getType())));
        }
        if(logEntity.getTargetrecno()!=null){
            bodyMap.put("targetrecno",toRequestBody(logEntity.getTargetrecno()));
        }
        if(logEntity.getUserrecno()!=null){
            bodyMap.put("userrecno",toRequestBody(String.valueOf(logEntity.getUserrecno())));
        }
        if(logEntity.getMark()!=null){
            bodyMap.put("mark",toRequestBody(logEntity.getMark()));
        }
        return mICommonAPI.insertLogRecord(bodyMap)
                .compose(RxHelper.<ResultEntity>rxSchedulerHelper());
    }


    /**
     * 查询访问日志，获取浏览历史
     *
     * @param logEntity
     * @return
     */
    @Override
    public Observable<ResultEntity> selectVisitHistoryRecordsByPage(LogEntity logEntity) {
        Map<String, RequestBody> bodyMap = new HashMap<>();
        if(logEntity.getUserrecno()!=null){
            bodyMap.put("userrecno",toRequestBody(String.valueOf(logEntity.getUserrecno())));
        }
        return mICommonAPI.selectVisitHistoryRecordsByPage(bodyMap)
                .compose(RxHelper.<ResultEntity>rxSchedulerHelper());
    }
}

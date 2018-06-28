package com.clw.phaapp.model.impl;

import com.clw.mysdk.retrofit.RetrofitCreateHelper;
import com.clw.mysdk.rxjava.RxHelper;
import com.clw.phaapp.api.IAskModelApi;
import com.clw.phaapp.app.ServerInformation;
import com.clw.phaapp.common.entity.ResultEntity;
import com.clw.phaapp.model.IAskModel;
import com.clw.phaapp.model.entity.AskEntity;
import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.RequestBody;

import java.util.HashMap;
import java.util.Map;

/**
 * 健康问答业务实现类
 *
 * @author chenliwu
 * @create 2018-03-08 22:12
 **/
public class AskModelImpl implements IAskModel {

    /**
     * 健康问答API接口
     */
    private IAskModelApi mIAskModelApi;

    public AskModelImpl(){
        mIAskModelApi=(RetrofitCreateHelper
                .createApi(IAskModelApi.class, ServerInformation.BASE_URL));
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
     * 设置问答访问次数加1
     *
     * @param askEntity
     * @return
     */
    @Override
    public Observable<ResultEntity> setAskVisitCount(AskEntity askEntity) {
        Map<String, RequestBody> bodyMap = new HashMap<>();
        bodyMap.put("recno",toRequestBody(String.valueOf(askEntity.getRecno())));
        return mIAskModelApi.setAskVisitCount(bodyMap)
                .compose(RxHelper.<ResultEntity>rxSchedulerHelper());
    }





    /**
     * 发起健康问答
     * 客户端需要提交三个字段到服务器：标题，内容，用户记录号
     * @param askEntity
     * @return
     */
    @Override
    public Observable<ResultEntity> publishAsk(AskEntity askEntity) {
        Map<String, RequestBody> bodyMap = new HashMap<>();
        bodyMap.put("title",toRequestBody(askEntity.getTitle()));
        bodyMap.put("content",toRequestBody(askEntity.getContent()));
        bodyMap.put("userrecno",toRequestBody(String.valueOf(askEntity.getUserrecno())));
        return mIAskModelApi.publishAsk(bodyMap)
                .compose(RxHelper.<ResultEntity>rxSchedulerHelper());
    }

    /**
     * 删除健康问答
     *
     * @param askEntity
     * @return
     */
    @Override
    public Observable<ResultEntity> deleteAsk(AskEntity askEntity) {
        Map<String, RequestBody> bodyMap = new HashMap<>();
        bodyMap.put("recno",toRequestBody(String.valueOf(askEntity.getRecno())));
        return mIAskModelApi.deleteAsk(bodyMap)
                .compose(RxHelper.<ResultEntity>rxSchedulerHelper());
    }

    /**
     * 修改健康问答
     *
     * @param askEntity
     * @return
     */
    @Override
    public Observable<ResultEntity> reviseAsk(AskEntity askEntity) {
        Map<String, RequestBody> bodyMap = new HashMap<>();
        bodyMap.put("recno",toRequestBody(String.valueOf(askEntity.getRecno())));
        bodyMap.put("title",toRequestBody(askEntity.getTitle()));
        bodyMap.put("content",toRequestBody(askEntity.getContent()));
        bodyMap.put("userrecno",toRequestBody(String.valueOf(askEntity.getUserrecno())));
        if(askEntity.getStatus()!=null){
            bodyMap.put("status",toRequestBody(String.valueOf(askEntity.getStatus())));
        }
        return mIAskModelApi.reviseAsk(bodyMap)
                .compose(RxHelper.<ResultEntity>rxSchedulerHelper());
    }

    /**
     * 获取健康问答列表，分页
     *
     * @param askEntity
     * @return
     */
    @Override
    public Observable<ResultEntity> selectAskRecordListByPage(AskEntity askEntity) {
        Map<String, RequestBody> bodyMap = new HashMap<>();
        if(askEntity.getUserrecno()!=null){
            bodyMap.put("userrecno",toRequestBody(String.valueOf(askEntity.getUserrecno())));
        }
        if(askEntity.getStatus()!=null){
            bodyMap.put("status",toRequestBody(String.valueOf(askEntity.getStatus())));
        }
        bodyMap.put("page",toRequestBody(String.valueOf(askEntity.getPage())));
        return mIAskModelApi.selectAskRecordListByPage(bodyMap)
                .compose(RxHelper.<ResultEntity>rxSchedulerHelper());
    }

    /**
     * 获取单条健康问答明细
     *
     * @param askEntity
     * @return
     */
    @Override
    public Observable<ResultEntity> selectOneAskRecord(AskEntity askEntity) {
        Map<String, RequestBody> bodyMap = new HashMap<>();
        bodyMap.put("recno",toRequestBody(String.valueOf(askEntity.getRecno())));
        return mIAskModelApi.selectOneAskRecord(bodyMap)
                .compose(RxHelper.<ResultEntity>rxSchedulerHelper());
    }
}

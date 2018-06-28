package com.clw.phaapp.model.impl;

import com.clw.mysdk.retrofit.RetrofitCreateHelper;
import com.clw.mysdk.rxjava.RxHelper;
import com.clw.phaapp.api.IAskAnswerApi;
import com.clw.phaapp.app.ServerInformation;
import com.clw.phaapp.common.entity.ResultEntity;
import com.clw.phaapp.model.IAskAnswerModel;
import com.clw.phaapp.model.entity.AskAnswerEntity;
import com.clw.phaapp.model.entity.LikeEntity;
import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.RequestBody;

import java.util.HashMap;
import java.util.Map;

/**
 * @author chenliwu
 * @create 2018-03-23 22:00
 **/
public class AskAnswerModelImpl implements IAskAnswerModel {


    private IAskAnswerApi mIAskAnswerApi;

    public AskAnswerModelImpl(){
        mIAskAnswerApi=(RetrofitCreateHelper
                .createApi(IAskAnswerApi.class, ServerInformation.BASE_URL));
    }

    public static RequestBody toRequestBody(String value) {
        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), value);
        return requestBody;
    }

    /**
     * @param askAnswerEntity
     * @return
     */
    @Override
    public Observable<ResultEntity> publishAskAnswer(AskAnswerEntity askAnswerEntity) {
        Map<String, RequestBody> bodyMap = new HashMap<>();
        bodyMap.put("askrecno",toRequestBody(askAnswerEntity.getAskrecno().toString()));
        bodyMap.put("content",toRequestBody(askAnswerEntity.getContent()));
        bodyMap.put("userrecno",toRequestBody(String.valueOf(askAnswerEntity.getUserrecno())));
        return mIAskAnswerApi.publishAskAnswer(bodyMap)
                .compose(RxHelper.<ResultEntity>rxSchedulerHelper());
    }

    /**
     * 获取回答列表
     * @param askAnswerEntity
     * @return
     */
    @Override
    public Observable<ResultEntity> getAskAnswerList(AskAnswerEntity askAnswerEntity) {
        Map<String, RequestBody> bodyMap = new HashMap<>();
        bodyMap.put("selectuserreno",toRequestBody(askAnswerEntity.getSelectuserreno().toString()));
        bodyMap.put("askrecno",toRequestBody(askAnswerEntity.getAskrecno().toString()));
        return mIAskAnswerApi.getAskAnswerList(bodyMap)
                .compose(RxHelper.<ResultEntity>rxSchedulerHelper());
    }

    /**
     * 查询回答列表，分页
     *
     * @param askAnswerEntity
     * @return
     */
    @Override
    public Observable<ResultEntity> selectAskAnswerByPage(AskAnswerEntity askAnswerEntity) {
        Map<String, RequestBody> bodyMap = new HashMap<>();
        if(askAnswerEntity.getSelectuserreno()!=null){
            bodyMap.put("selectuserreno",toRequestBody(askAnswerEntity.getSelectuserreno().toString()));
        }
        if(askAnswerEntity.getAskrecno()!=null){
            bodyMap.put("askrecno",toRequestBody(askAnswerEntity.getAskrecno().toString()));
        }
        if(askAnswerEntity.getStatus()!=null){
            bodyMap.put("status",toRequestBody(String.valueOf(askAnswerEntity.getStatus())));
        }
        bodyMap.put("page",toRequestBody(String.valueOf(askAnswerEntity.getPage())));

        return mIAskAnswerApi.selectAskAnswerByPage(bodyMap)
                .compose(RxHelper.<ResultEntity>rxSchedulerHelper());
    }

    /**
     * 采纳回答
     *
     * @param askAnswerEntity
     * @return
     */
    @Override
    public Observable<ResultEntity> acceptAskAnswer(AskAnswerEntity askAnswerEntity) {
        Map<String, RequestBody> bodyMap = new HashMap<>();
        if(askAnswerEntity.getRecno()!=null){
            bodyMap.put("recno",toRequestBody(askAnswerEntity.getRecno().toString()));
        }
        if(askAnswerEntity.getUserrecno()!=null){
            bodyMap.put("userrecno",toRequestBody(askAnswerEntity.getUserrecno().toString()));
        }
        if(askAnswerEntity.getAskrecno()!=null){
            bodyMap.put("askrecno",toRequestBody(askAnswerEntity.getAskrecno().toString()));
        }
        return mIAskAnswerApi.acceptAskAnswer(bodyMap)
                .compose(RxHelper.<ResultEntity>rxSchedulerHelper());
    }

    /**
     * 删除回答
     *
     * @param askAnswerEntity
     * @return
     */
    @Override
    public Observable<ResultEntity> deleteAskAnswer(AskAnswerEntity askAnswerEntity) {
        Map<String, RequestBody> bodyMap = new HashMap<>();
        if(askAnswerEntity.getRecno()!=null){
            bodyMap.put("recno",toRequestBody(askAnswerEntity.getRecno().toString()));
        }
        return mIAskAnswerApi.deleteAskAnswer(bodyMap)
                .compose(RxHelper.<ResultEntity>rxSchedulerHelper());
    }

    /**
     * 点赞
     * 点赞需要的字段：回答记录号，点赞者记录号
     * @param likeEntity
     * @return
     */
    @Override
    public Observable<ResultEntity> like(LikeEntity likeEntity) {
        Map<String, RequestBody> bodyMap = new HashMap<>();
        bodyMap.put("targetrecno",toRequestBody(likeEntity.getTargetrecno()));
        bodyMap.put("userrecno",toRequestBody(String.valueOf(likeEntity.getUserrecno())));
        return mIAskAnswerApi.like(bodyMap)
                .compose(RxHelper.<ResultEntity>rxSchedulerHelper());
    }
}

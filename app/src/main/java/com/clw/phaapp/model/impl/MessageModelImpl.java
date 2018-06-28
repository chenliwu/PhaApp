package com.clw.phaapp.model.impl;

import com.clw.mysdk.retrofit.RetrofitCreateHelper;
import com.clw.mysdk.rxjava.RxHelper;
import com.clw.phaapp.api.IMessageApi;
import com.clw.phaapp.app.ServerInformation;
import com.clw.phaapp.common.entity.ResultEntity;
import com.clw.phaapp.model.IMessageModel;
import com.clw.phaapp.model.entity.MessageEntity;
import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.RequestBody;

import java.util.HashMap;
import java.util.Map;

/**
 * @author chenliwu
 * @create 2018-04-10 11:22
 **/
public class MessageModelImpl implements IMessageModel {

    private IMessageApi mIMessageApi;

    public MessageModelImpl(){
        mIMessageApi=(RetrofitCreateHelper
                .createApi(IMessageApi.class, ServerInformation.BASE_URL));
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
     * 分页查询消息记录
     *
     * @param messageEntity
     * @return
     */
    @Override
    public Observable<ResultEntity> selectRecordsListByPage(MessageEntity messageEntity) {
        Map<String, RequestBody> bodyMap = new HashMap<>();
        if(messageEntity.getReceiver()!=null){
            bodyMap.put("receiver",toRequestBody(String.valueOf(messageEntity.getReceiver())));
        }
        bodyMap.put("page",toRequestBody(String.valueOf(messageEntity.getPage())));
        return mIMessageApi.selectRecordsListByPage(bodyMap)
                .compose(RxHelper.<ResultEntity>rxSchedulerHelper());
    }

    /**
     * 修改消息状态，将消息修改成已读
     *
     * @param messageEntity
     * @return
     */
    @Override
    public Observable<ResultEntity> updateRecord(MessageEntity messageEntity) {
        Map<String, RequestBody> bodyMap = new HashMap<>();
        bodyMap.put("receiver",toRequestBody(String.valueOf(messageEntity.getReceiver())));
        return mIMessageApi.updateRecord(bodyMap)
                .compose(RxHelper.<ResultEntity>rxSchedulerHelper());
    }


    /**
     * 将消息列表标为已读
     *
     * @param messageEntity
     * @return
     */
    @Override
    public Observable<ResultEntity> updateMessageStatusByParam(MessageEntity messageEntity) {
        Map<String, RequestBody> bodyMap = new HashMap<>();
        bodyMap.put("updateStatusMessageRecnolist",toRequestBody(messageEntity.getUpdateStatusMessageRecnolist()));
        return mIMessageApi.updateMessageStatusByParam(bodyMap)
                .compose(RxHelper.<ResultEntity>rxSchedulerHelper());
    }

    /**
     * 根据参数获取消息记录数
     *
     * @param messageEntity
     * @return
     */
    @Override
    public Observable<ResultEntity> selectMessageTotalByParam(MessageEntity messageEntity) {
        Map<String, RequestBody> bodyMap = new HashMap<>();
        bodyMap.put("receiver",toRequestBody(String.valueOf(messageEntity.getReceiver())));
        return mIMessageApi.selectMessageTotalByParam(bodyMap)
                .compose(RxHelper.<ResultEntity>rxSchedulerHelper());
    }
}

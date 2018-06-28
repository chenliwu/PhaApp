package com.clw.phaapp.model.impl;

import com.clw.mysdk.retrofit.RetrofitCreateHelper;
import com.clw.mysdk.rxjava.RxHelper;
import com.clw.phaapp.api.IReportApi;
import com.clw.phaapp.app.ServerInformation;
import com.clw.phaapp.common.entity.ResultEntity;
import com.clw.phaapp.model.IReportModel;
import com.clw.phaapp.model.entity.ReportEntity;
import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.RequestBody;

import java.util.HashMap;
import java.util.Map;

/**
 * @author chenliwu
 * @create 2018-04-08 0:54
 **/
public class ReportModelImpl implements IReportModel {


    private IReportApi mIReportApi;


    public ReportModelImpl(){
        mIReportApi=(RetrofitCreateHelper
                .createApi(IReportApi.class, ServerInformation.BASE_URL));
    }


    public static RequestBody toRequestBody(String value) {
        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), value);
        return requestBody;
    }


    /**
     * 添加举报记录
     * 需要的字段：举报类型，举报内容类型，举报目标记录号，用户记录号，举报描述
     *
     * @param reportEntity
     * @return
     */
    @Override
    public Observable<ResultEntity> insertRecord(ReportEntity reportEntity) {
        Map<String, RequestBody> bodyMap = new HashMap<>();
        if(reportEntity.getType()!=null){
            bodyMap.put("type",toRequestBody(reportEntity.getType().toString()));
        }
        if(reportEntity.getContenttype()!=null){
            bodyMap.put("contenttype",toRequestBody(reportEntity.getContenttype().toString()));
        }
        if(reportEntity.getTargetrecno()!=null){
            bodyMap.put("targetrecno",toRequestBody(reportEntity.getTargetrecno()));
        }
        if(reportEntity.getUserrecno()!=null){
            bodyMap.put("userrecno",toRequestBody(reportEntity.getUserrecno().toString()));
        }
        if(reportEntity.getDetail()!=null){
            bodyMap.put("detail",toRequestBody(reportEntity.getDetail()));
        }
        return mIReportApi.insertRecord(bodyMap)
                .compose(RxHelper.<ResultEntity>rxSchedulerHelper());
    }
}

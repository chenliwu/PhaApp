package com.clw.phaapp.api;

import com.clw.phaapp.common.entity.ResultEntity;
import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;

import java.util.Map;

/**
 * 健康问答API接口
 */
public interface IAskModelApi {

    /**
     * 设置问答访问次数加1
     * @param params
     * @return
     */
    @Multipart
    @POST("ask/setAskVisitCount")
    Observable<ResultEntity> setAskVisitCount(@PartMap Map<String, RequestBody> params);

    /**
     * 发表健康问答
     * @param params
     * @return
     */
    @Multipart
    @POST("ask/publishAsk")
    Observable<ResultEntity> publishAsk(@PartMap Map<String, RequestBody> params);

    /**
     * 删除健康问答
     * @param params
     * @return
     */
    @Multipart
    @POST("ask/deleteAsk")
    Observable<ResultEntity> deleteAsk(@PartMap Map<String, RequestBody> params);

    /**
     * 修改健康问答
     * @param params
     * @return
     */
    @Multipart
    @POST("ask/reviseAsk")
    Observable<ResultEntity> reviseAsk(@PartMap Map<String, RequestBody> params);

    /**
     * 获取健康问答列表，分页
     * @param params
     * @return
     */
    @Multipart
    @POST("ask/selectAskRecordListByPage")
    Observable<ResultEntity> selectAskRecordListByPage(@PartMap Map<String, RequestBody> params);

    /**
     * 获取单条健康问答明细
     * @param params
     * @return
     */
    @Multipart
    @POST("ask/selectOneAskRecord")
    Observable<ResultEntity> selectOneAskRecord(@PartMap Map<String, RequestBody> params);

}

package com.clw.phaapp.api;

import com.clw.phaapp.common.entity.ResultEntity;
import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;

import java.util.Map;

public interface IAskAnswerApi {

    /**
     * 回答问题
     * @param params
     * @return
     */
    @Multipart
    @POST("askAnswer/publishAskAnswer")
    Observable<ResultEntity> publishAskAnswer(@PartMap Map<String, RequestBody> params);


    /**
     * 查询回答列表
     * @param params
     * @return
     */
    @Multipart
    @POST("askAnswer/getAskAnswerList")
    Observable<ResultEntity> getAskAnswerList(@PartMap Map<String, RequestBody> params);


    /**
     * 查询回答列表，分页
     * @param params
     * @return
     */
    @Multipart
    @POST("askAnswer/selectAskAnswerByPage")
    Observable<ResultEntity> selectAskAnswerByPage(@PartMap Map<String, RequestBody> params);


    /**
     * 采纳回答
     * @param params
     * @return
     */
    @Multipart
    @POST("askAnswer/acceptAskAnswer")
    Observable<ResultEntity> acceptAskAnswer(@PartMap Map<String, RequestBody> params);

    /**
     * 删除回答
     * @param params
     * @return
     */
    @Multipart
    @POST("askAnswer/deleteAskAnswer")
    Observable<ResultEntity> deleteAskAnswer(@PartMap Map<String, RequestBody> params);

    /**
     * 点赞
     * @param params
     * @return
     */
    @Multipart
    @POST("askAnswer/like")
    Observable<ResultEntity> like(@PartMap Map<String, RequestBody> params);

}

package com.clw.phaapp.api;

import com.clw.phaapp.common.entity.ResultEntity;
import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;

import java.util.Map;

/**
 * 公共API接口
 */
public interface ICommonAPI {

    /**
     * 发表健康问答
     * @param params
     * @return
     */
    @Multipart
    @POST("log/insertLogRecord")
    Observable<ResultEntity> insertLogRecord(@PartMap Map<String, RequestBody> params);

    /**
     * 查询访问日志，获取浏览历史
     * @param params
     * @return
     */
    @Multipart
    @POST("log/selectVisitHistoryRecordsByPage")
    Observable<ResultEntity> selectVisitHistoryRecordsByPage(@PartMap Map<String, RequestBody> params);

}

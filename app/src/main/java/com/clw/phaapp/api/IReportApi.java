package com.clw.phaapp.api;

import com.clw.phaapp.common.entity.ResultEntity;
import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;

import java.util.Map;

/**
 * 举报API
 */
public interface IReportApi {


    /**
     * 添加举报记录
     * @param params
     * @return
     */
    @Multipart
    @POST("report/insertRecord")
    Observable<ResultEntity> insertRecord(@PartMap Map<String, RequestBody> params);

}

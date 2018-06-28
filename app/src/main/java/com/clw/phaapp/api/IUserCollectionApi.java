package com.clw.phaapp.api;

import com.clw.phaapp.common.entity.ResultEntity;
import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;

import java.util.Map;

/**
 * 收藏列表API
 */
public interface IUserCollectionApi {


    /**
     * 添加收藏记录
     * @param params
     * @return
     */
    @Multipart
    @POST("userCollection/insertRecord")
    Observable<ResultEntity> insertRecord(@PartMap Map<String, RequestBody> params);


    /**
     * 分页查询，收藏记录
     * @param params
     * @return
     */
    @Multipart
    @POST("userCollection/selectRecordsByPage")
    Observable<ResultEntity> selectRecordsByPage(@PartMap Map<String, RequestBody> params);


    /**
     * 分页查询，健康问答收藏记录
     * @param params
     * @return
     */
    @Multipart
    @POST("userCollection/selectAskCollectionListByPage")
    Observable<ResultEntity> selectAskCollectionListByPage(@PartMap Map<String, RequestBody> params);


    /**
     * 分页查询，健康资讯收藏记录
     * @param params
     * @return
     */
    @Multipart
    @POST("userCollection/selectHealthInfoCollectionListByPage")
    Observable<ResultEntity> selectHealthInfoCollectionListByPage(@PartMap Map<String, RequestBody> params);



    /**
     * 删除单条收藏记录
     * @param params
     * @return
     */
    @Multipart
    @POST("userCollection/deleteOneRecord")
    Observable<ResultEntity> deleteOneRecord(@PartMap Map<String, RequestBody> params);


}

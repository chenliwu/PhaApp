package com.clw.phaapp.api;

import com.clw.phaapp.common.entity.ResultEntity;
import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;

import java.util.Map;

public interface IMessageApi {


    /**
     * 分页查询消息记录
     * @param params
     * @return
     */
    @Multipart
    @POST("message/selectRecordsListByPage")
    Observable<ResultEntity> selectRecordsListByPage(@PartMap Map<String, RequestBody> params);


    /**
     * 修改消息状态，将消息修改成已读
     * @param params
     * @return
     */
    @Multipart
    @POST("message/updateRecord")
    Observable<ResultEntity> updateRecord(@PartMap Map<String, RequestBody> params);


    /**
     *  将消息列表标为已读
     * @param params
     * @return
     */
    @Multipart
    @POST("message/updateMessageStatusByParam")
    Observable<ResultEntity> updateMessageStatusByParam(@PartMap Map<String, RequestBody> params);



    /**
     * 根据参数获取消息记录数
     * 传递用户记录号
     * @param params
     * @return
     */
    @Multipart
    @POST("message/selectMessageTotalByParam")
    Observable<ResultEntity> selectMessageTotalByParam(@PartMap Map<String, RequestBody> params);


}

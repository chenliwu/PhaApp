package com.clw.phaapp.model;

import com.clw.phaapp.common.entity.ResultEntity;
import com.clw.phaapp.model.entity.UserCollectionEntity;
import io.reactivex.Observable;

/**
 * 用户收藏Model
 */
public interface IUserCollectionModel {


    /**
     * 添加收藏记录
     * @param userCollectionEntity
     * @return
     */
    Observable<ResultEntity> insertRecord(UserCollectionEntity userCollectionEntity);


    /**
     * 分页查询，收藏记录
     * @param userCollectionEntity
     * @return
     */
    Observable<ResultEntity> selectRecordsByPage(UserCollectionEntity userCollectionEntity);


    /**
     * 分页查询，健康问答收藏记录
     * @param userCollectionEntity
     * @return
     */
    Observable<ResultEntity> selectAskCollectionListByPage(UserCollectionEntity userCollectionEntity);


    /**
     * 分页查询，健康资讯收藏记录
     * @param userCollectionEntity
     * @return
     */
    Observable<ResultEntity> selectHealthInfoCollectionListByPage(UserCollectionEntity userCollectionEntity);



    /**
     * 删除单条收藏记录
     * @param userCollectionEntity
     * @return
     */
    Observable<ResultEntity> deleteOneRecord(UserCollectionEntity userCollectionEntity);


}

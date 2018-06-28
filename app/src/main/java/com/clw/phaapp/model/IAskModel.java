package com.clw.phaapp.model;

import com.clw.phaapp.common.entity.ResultEntity;
import com.clw.phaapp.model.entity.AskEntity;
import io.reactivex.Observable;

/**
 * 健康问答业务接口
 */
public interface IAskModel {

    /**
     * 设置问答访问次数加1
     * @param askEntity
     * @return
     */
    Observable<ResultEntity> setAskVisitCount(AskEntity askEntity);

    /**
     * 发起健康问答
     * @param askEntity
     * @return
     */
    Observable<ResultEntity> publishAsk(AskEntity askEntity);


    /**
     * 删除健康问答
     * @param askEntity
     * @return
     */
    Observable<ResultEntity> deleteAsk(AskEntity askEntity);


    /**
     * 修改健康问答
     * @param askEntity
     * @return
     */
    Observable<ResultEntity> reviseAsk(AskEntity askEntity);

    /**
     * 获取健康问答列表，分页
     * @param askEntity
     * @return
     */
    Observable<ResultEntity> selectAskRecordListByPage(AskEntity askEntity);

    /**
     * 获取单条健康问答明细
     * @param askEntity
     * @return
     */
    Observable<ResultEntity> selectOneAskRecord(AskEntity askEntity);

}

package com.clw.phaapp.model;

import com.clw.phaapp.common.entity.ResultEntity;
import com.clw.phaapp.model.entity.LogEntity;
import io.reactivex.Observable;

public interface ICommonModel {

    /**
     * 插入访问日志
     * @param logEntity
     * @return
     */
    Observable<ResultEntity> insertLogRecord(LogEntity logEntity);

    /**
     * 查询访问日志，获取浏览历史
     * @param logEntity
     * @return
     */
    Observable<ResultEntity> selectVisitHistoryRecordsByPage(LogEntity logEntity);

}

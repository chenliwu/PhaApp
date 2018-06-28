package com.clw.phaapp.model;

import com.clw.phaapp.common.entity.ResultEntity;
import com.clw.phaapp.model.entity.ReportEntity;
import io.reactivex.Observable;

/**
 * 举报Model
 */
public interface IReportModel {


    /**
     * 添加举报记录
     * @param reportEntity
     * @return
     */
    Observable<ResultEntity> insertRecord(ReportEntity reportEntity);

}

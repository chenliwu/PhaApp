package com.clw.phaapp.model;

import com.clw.phaapp.common.entity.ResultEntity;
import com.clw.phaapp.model.entity.AskAnswerEntity;
import com.clw.phaapp.model.entity.LikeEntity;
import io.reactivex.Observable;

public interface IAskAnswerModel {

    /**
     *
     * @param askAnswerEntity
     * @return
     */
    Observable<ResultEntity> publishAskAnswer(AskAnswerEntity askAnswerEntity);

    /**
     *
     * @param askAnswerEntity
     * @return
     */
    Observable<ResultEntity> getAskAnswerList(AskAnswerEntity askAnswerEntity);


    /**
     * 查询回答列表，分页
     * @param askAnswerEntity
     * @return
     */
    Observable<ResultEntity> selectAskAnswerByPage(AskAnswerEntity askAnswerEntity);

    /**
     * 采纳回答
     * @param askAnswerEntity
     * @return
     */
    Observable<ResultEntity> acceptAskAnswer(AskAnswerEntity askAnswerEntity);


    /**
     * 删除回答
     * @param askAnswerEntity
     * @return
     */
    Observable<ResultEntity> deleteAskAnswer(AskAnswerEntity askAnswerEntity);


    /**
     * 点赞
     * @param likeEntity
     * @return
     */
    Observable<ResultEntity> like(LikeEntity likeEntity);

}

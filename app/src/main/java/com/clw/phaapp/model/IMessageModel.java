package com.clw.phaapp.model;

import com.clw.phaapp.common.entity.ResultEntity;
import com.clw.phaapp.model.entity.MessageEntity;
import io.reactivex.Observable;

/**
 * 消息model
 */
public interface IMessageModel {

    /**
     * 分页查询消息记录
     * @param messageEntity
     * @return
     */
    Observable<ResultEntity> selectRecordsListByPage(MessageEntity messageEntity);


    /**
     * 修改消息状态，将消息修改成已读
     * @param messageEntity
     * @return
     */
    Observable<ResultEntity> updateRecord(MessageEntity messageEntity);


    /**
     * 将消息列表标为已读
     * @param messageEntity
     * @return
     */
    Observable<ResultEntity> updateMessageStatusByParam(MessageEntity messageEntity);


    /**
     *  根据参数获取消息记录数
     * @param messageEntity
     * @return
     */
    Observable<ResultEntity> selectMessageTotalByParam(MessageEntity messageEntity);

}

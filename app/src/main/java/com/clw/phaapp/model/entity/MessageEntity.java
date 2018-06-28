package com.clw.phaapp.model.entity;

import com.clw.phaapp.common.entity.DatagridPageEntity;

public class MessageEntity extends DatagridPageEntity {

    /**
     * 记录要修改状态已读的消息列表记录号，用,号分割
     */
    private String updateStatusMessageRecnolist;

    public String getUpdateStatusMessageRecnolist() {
        return updateStatusMessageRecnolist;
    }

    public void setUpdateStatusMessageRecnolist(String updateStatusMessageRecnolist) {
        this.updateStatusMessageRecnolist = updateStatusMessageRecnolist;
    }

    /**
     * 记录号
     */
    private Long recno;

    /**
     * 类型：0系统通知，1点赞消息，2评论，3回复
     */
    private Byte type;

    /**
     * 消息接收者
     */
    private Long receiver;

    /**
     * 消息发送者
     */
    private Long sender;

    /**
     * 发送时间
     */
    private Long opdate;

    /**
     * 消息内容
     */
    private String content;

    /**
     * 状态：0未读，1已读
     */
    private Byte status;

    /**
     * 备注
     */
    private String mark;


    /**
     * 发送方昵称
     */
    private String sendernickname;


    public String getSendernickname() {
        return sendernickname;
    }

    public void setSendernickname(String sendernickname) {
        this.sendernickname = sendernickname;
    }

    /**
     * 记录号
     * @return recno 记录号
     */
    public Long getRecno() {
        return recno;
    }

    /**
     * 记录号
     * @param recno 记录号
     */
    public void setRecno(Long recno) {
        this.recno = recno;
    }

    /**
     * 类型：0系统通知，1点赞消息，2评论，3回复
     * @return type 类型：0系统通知，1点赞消息，2评论，3回复
     */
    public Byte getType() {
        return type;
    }

    /**
     * 类型：0系统通知，1点赞消息，2评论，3回复
     * @param type 类型：0系统通知，1点赞消息，2评论，3回复
     */
    public void setType(Byte type) {
        this.type = type;
    }

    /**
     * 消息接收者
     * @return receiver 消息接收者
     */
    public Long getReceiver() {
        return receiver;
    }

    /**
     * 消息接收者
     * @param receiver 消息接收者
     */
    public void setReceiver(Long receiver) {
        this.receiver = receiver;
    }

    /**
     * 消息发送者
     * @return sender 消息发送者
     */
    public Long getSender() {
        return sender;
    }

    /**
     * 消息发送者
     * @param sender 消息发送者
     */
    public void setSender(Long sender) {
        this.sender = sender;
    }

    /**
     * 发送时间
     * @return opdate 发送时间
     */
    public Long getOpdate() {
        return opdate;
    }

    /**
     * 发送时间
     * @param opdate 发送时间
     */
    public void setOpdate(Long opdate) {
        this.opdate = opdate;
    }

    /**
     * 消息内容
     * @return content 消息内容
     */
    public String getContent() {
        return content;
    }

    /**
     * 消息内容
     * @param content 消息内容
     */
    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    /**
     * 状态：0未读，1已读
     * @return status 状态：0未读，1已读
     */
    public Byte getStatus() {
        return status;
    }

    /**
     * 状态：0未读，1已读
     * @param status 状态：0未读，1已读
     */
    public void setStatus(Byte status) {
        this.status = status;
    }

    /**
     * 备注
     * @return mark 备注
     */
    public String getMark() {
        return mark;
    }

    /**
     * 备注
     * @param mark 备注
     */
    public void setMark(String mark) {
        this.mark = mark == null ? null : mark.trim();
    }

    @Override
    public String toString() {
        return "MessageEntity{" +
                "recno=" + recno +
                ", type=" + type +
                ", receiver=" + receiver +
                ", sender=" + sender +
                ", opdate=" + opdate +
                ", content='" + content + '\'' +
                ", status=" + status +
                ", mark='" + mark + '\'' +
                ", sendernickname='" + sendernickname + '\'' +
                '}';
    }
}
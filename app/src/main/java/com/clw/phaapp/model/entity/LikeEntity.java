package com.clw.phaapp.model.entity;

public class LikeEntity {
    /**
     * 记录号
     */
    private Long recno;

    /**
     * 点赞类型：0回答
     */
    private Byte type;

    /**
     * 收藏对象记录号或id
     */
    private String targetrecno;

    /**
     * 用户记录号
     */
    private Long userrecno;

    /**
     * 点赞时间
     */
    private Long opdate;

    /**
     * 状态：0未读，1已读
     */
    private Byte status;

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
     * 收藏类型：0-19资讯，20-39问答
     * @return type 收藏类型：0-19资讯，20-39问答
     */
    public Byte getType() {
        return type;
    }

    /**
     * 收藏类型：0-19资讯，20-39问答
     * @param type 收藏类型：0-19资讯，20-39问答
     */
    public void setType(Byte type) {
        this.type = type;
    }

    /**
     * 收藏对象记录号或id
     * @return targetrecno 收藏对象记录号或id
     */
    public String getTargetrecno() {
        return targetrecno;
    }

    /**
     * 收藏对象记录号或id
     * @param targetrecno 收藏对象记录号或id
     */
    public void setTargetrecno(String targetrecno) {
        this.targetrecno = targetrecno == null ? null : targetrecno.trim();
    }

    /**
     * 用户记录号
     * @return userrecno 用户记录号
     */
    public Long getUserrecno() {
        return userrecno;
    }

    /**
     * 用户记录号
     * @param userrecno 用户记录号
     */
    public void setUserrecno(Long userrecno) {
        this.userrecno = userrecno;
    }

    /**
     * 点赞时间
     * @return opdate 点赞时间
     */
    public Long getOpdate() {
        return opdate;
    }

    /**
     * 点赞时间
     * @param opdate 点赞时间
     */
    public void setOpdate(Long opdate) {
        this.opdate = opdate;
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
}
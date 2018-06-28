package com.clw.phaapp.model.entity;

/**
 * 用户反馈实体类
 */
public class UserFeedbackEntity {
    /**
     * 记录号
     */
    private Long recno;

    /**
     * 用户记录号，可为空
     */
    private String userrecno;

    /**
     * 反馈类型--0：bug问题，1建议意见
     */
    private Byte type;

    /**
     * 联系方式
     */
    private String contact;

    /**
     * 反馈内容
     */
    private String content;

    /**
     * 反馈时间
     */
    private Long opdate;

    /**
     * 状态---0未处理，1已处理
     */
    private Byte status;

    /**
     * ip地址
     */
    private String ip;

    /**
     * 备注
     */
    private String mark;

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
     * 用户记录号，可为空
     * @return userrecno 用户记录号，可为空
     */
    public String getUserrecno() {
        return userrecno;
    }

    /**
     * 用户记录号，可为空
     * @param userrecno 用户记录号，可为空
     */
    public void setUserrecno(String userrecno) {
        this.userrecno = userrecno == null ? null : userrecno.trim();
    }

    /**
     * 反馈类型--0：bug问题，1建议意见
     * @return type 反馈类型--0：bug问题，1建议意见
     */
    public Byte getType() {
        return type;
    }

    /**
     * 反馈类型--0：bug问题，1建议意见
     * @param type 反馈类型--0：bug问题，1建议意见
     */
    public void setType(Byte type) {
        this.type = type;
    }

    /**
     * 联系方式
     * @return contact 联系方式
     */
    public String getContact() {
        return contact;
    }

    /**
     * 联系方式
     * @param contact 联系方式
     */
    public void setContact(String contact) {
        this.contact = contact == null ? null : contact.trim();
    }

    /**
     * 反馈内容
     * @return content 反馈内容
     */
    public String getContent() {
        return content;
    }

    /**
     * 反馈内容
     * @param content 反馈内容
     */
    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    /**
     * 反馈时间
     * @return opdate 反馈时间
     */
    public Long getOpdate() {
        return opdate;
    }

    /**
     * 反馈时间
     * @param opdate 反馈时间
     */
    public void setOpdate(Long opdate) {
        this.opdate = opdate;
    }

    /**
     * 状态---0未处理，1已处理
     * @return status 状态---0未处理，1已处理
     */
    public Byte getStatus() {
        return status;
    }

    /**
     * 状态---0未处理，1已处理
     * @param status 状态---0未处理，1已处理
     */
    public void setStatus(Byte status) {
        this.status = status;
    }

    /**
     * ip地址
     * @return ip ip地址
     */
    public String getIp() {
        return ip;
    }

    /**
     * ip地址
     * @param ip ip地址
     */
    public void setIp(String ip) {
        this.ip = ip == null ? null : ip.trim();
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
}
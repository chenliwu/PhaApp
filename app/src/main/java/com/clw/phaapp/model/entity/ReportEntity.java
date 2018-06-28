package com.clw.phaapp.model.entity;

import com.clw.phaapp.common.entity.DatagridPageEntity;

public class ReportEntity extends DatagridPageEntity {
    /**
     * 记录号
     */
    private Long recno;

    /**
     * 举报时间
     */
    private Long opdate;

    /**
     * 举报类型：0内容不实，1内容违规
     */
    private Byte type;

    /**
     * 举报内容类型：0问答
     */
    private Byte contenttype;

    /**
     * 举报目标记录号
     */
    private String targetrecno;

    /**
     * 用户记录号
     */
    private Long userrecno;

    /**
     * 举报描述
     */
    private String detail;

    /**
     * 状态：0待处理，1已处理
     */
    private Byte status;

    /**
     * 处理结果：0属实，1虚假
     */
    private Byte result;

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
     * 举报时间
     * @return opdate 举报时间
     */
    public Long getOpdate() {
        return opdate;
    }

    /**
     * 举报时间
     * @param opdate 举报时间
     */
    public void setOpdate(Long opdate) {
        this.opdate = opdate;
    }

    /**
     * 举报类型：0内容不实，1内容违规
     * @return type 举报类型：0内容不实，1内容违规
     */
    public Byte getType() {
        return type;
    }

    /**
     * 举报类型：0内容不实，1内容违规
     * @param type 举报类型：0内容不实，1内容违规
     */
    public void setType(Byte type) {
        this.type = type;
    }

    /**
     * 举报内容类型：0问答
     * @return contenttype 举报内容类型：0问答
     */
    public Byte getContenttype() {
        return contenttype;
    }

    /**
     * 举报内容类型：0问答
     * @param contenttype 举报内容类型：0问答
     */
    public void setContenttype(Byte contenttype) {
        this.contenttype = contenttype;
    }

    /**
     * 举报目标记录号
     * @return targetrecno 举报目标记录号
     */
    public String getTargetrecno() {
        return targetrecno;
    }

    /**
     * 举报目标记录号
     * @param targetrecno 举报目标记录号
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
     * 举报描述
     * @return detail 举报描述
     */
    public String getDetail() {
        return detail;
    }

    /**
     * 举报描述
     * @param detail 举报描述
     */
    public void setDetail(String detail) {
        this.detail = detail == null ? null : detail.trim();
    }

    /**
     * 状态：0待处理，1已处理
     * @return status 状态：0待处理，1已处理
     */
    public Byte getStatus() {
        return status;
    }

    /**
     * 状态：0待处理，1已处理
     * @param status 状态：0待处理，1已处理
     */
    public void setStatus(Byte status) {
        this.status = status;
    }

    /**
     * 处理结果：0属实，1虚假
     * @return result 处理结果：0属实，1虚假
     */
    public Byte getResult() {
        return result;
    }

    /**
     * 处理结果：0属实，1虚假
     * @param result 处理结果：0属实，1虚假
     */
    public void setResult(Byte result) {
        this.result = result;
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
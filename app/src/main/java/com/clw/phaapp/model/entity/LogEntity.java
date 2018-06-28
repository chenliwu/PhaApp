package com.clw.phaapp.model.entity;

import com.clw.phaapp.common.entity.DatagridPageEntity;

public class LogEntity extends DatagridPageEntity{
    /**
     * 记录号
     */
    private Long recno;

    /**
     * 访问时间
     */
    private Long opdate;

    /**
     * 访问类型：0-19资讯，20-39问答，40-59工具
     * 1 综合资讯，2 疾病资讯，3食品资讯
     * 20访问问答
     * 40使用查询常见疾病，41使用计算BML体重指数
     */
    private Byte type;

    /**
     * 访问对象记录号
     */
    private String targetrecno;

    /**
     * 用户记录号
     */
    private Long userrecno;

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
     * 访问时间
     * @return opdate 访问时间
     */
    public Long getOpdate() {
        return opdate;
    }

    /**
     * 访问时间
     * @param opdate 访问时间
     */
    public void setOpdate(Long opdate) {
        this.opdate = opdate;
    }

    /**
     * 访问类型：0-19资讯，20-39问答，40-59工具
     * @return type 访问类型：0-19资讯，20-39问答，40-59工具
     */
    public Byte getType() {
        return type;
    }

    /**
     * 访问类型：0-19资讯，20-39问答，40-59工具
     * @param type 访问类型：0-19资讯，20-39问答，40-59工具
     */
    public void setType(Byte type) {
        this.type = type;
    }

    /**
     * 访问对象记录号
     * @return targetrecno 访问对象记录号
     */
    public String getTargetrecno() {
        return targetrecno;
    }

    /**
     * 访问对象记录号
     * @param targetrecno 访问对象记录号
     */
    public void setTargetrecno(String targetrecno) {
        this.targetrecno = targetrecno;
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
        return "LogEntity{" +
                "recno=" + recno +
                ", opdate=" + opdate +
                ", type=" + type +
                ", targetrecno=" + targetrecno +
                ", userrecno=" + userrecno +
                ", mark='" + mark + '\'' +
                '}';
    }
}
package com.clw.phaapp.model.entity;

import com.clw.phaapp.common.entity.DatagridPageEntity;

public class AskEntity extends DatagridPageEntity {


    /**
     * 回答总数
     */
    private Integer allanswercount;

    /**
     * 已采纳回答总数
     */
    private Integer acceptanswercount;



    /**
     * 记录号
     */
    private Long recno;

    /**
     * 标题
     */
    private String title;

    /**
     * 内容
     */
    private String content;

    /**
     * 发表者记录号
     */
    private Long userrecno;

    /**
     * 发表时间
     */
    private Long opdate;

    /**
     * 0待审核，1审核通过，2待修改
     */
    private Byte status;

    /**
     * 被浏览次数
     */
    private Integer visitcount;

    /**
     * 备注
     */
    private String mark;


    public Integer getAllanswercount() {
        return allanswercount;
    }

    public void setAllanswercount(Integer allanswercount) {
        this.allanswercount = allanswercount;
    }

    public Integer getAcceptanswercount() {
        return acceptanswercount;
    }

    public void setAcceptanswercount(Integer acceptanswercount) {
        this.acceptanswercount = acceptanswercount;
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
     * 标题
     * @return title 标题
     */
    public String getTitle() {
        return title;
    }

    /**
     * 标题
     * @param title 标题
     */
    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    /**
     * 内容
     * @return content 内容
     */
    public String getContent() {
        return content;
    }

    /**
     * 内容
     * @param content 内容
     */
    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    /**
     * 发表者记录号
     * @return userrecno 发表者记录号
     */
    public Long getUserrecno() {
        return userrecno;
    }

    /**
     * 发表者记录号
     * @param userrecno 发表者记录号
     */
    public void setUserrecno(Long userrecno) {
        this.userrecno = userrecno;
    }

    /**
     * 发表时间
     * @return opdate 发表时间
     */
    public Long getOpdate() {
        return opdate;
    }

    /**
     * 发表时间
     * @param opdate 发表时间
     */
    public void setOpdate(Long opdate) {
        this.opdate = opdate;
    }

    /**
     * 0待审核，1审核通过，2待修改
     * @return status 0待审核，1审核通过，2待修改
     */
    public Byte getStatus() {
        return status;
    }

    /**
     * 0待审核，1审核通过，2待修改
     * @param status 0待审核，1审核通过，2待修改
     */
    public void setStatus(Byte status) {
        this.status = status;
    }

    /**
     * 被浏览次数
     * @return visitcount 被浏览次数
     */
    public Integer getVisitcount() {
        return visitcount;
    }

    /**
     * 被浏览次数
     * @param visitcount 被浏览次数
     */
    public void setVisitcount(Integer visitcount) {
        this.visitcount = visitcount;
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
        return "AskEntity{" +
                "recno=" + recno +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", userrecno=" + userrecno +
                ", opdate=" + opdate +
                ", status=" + status +
                ", visitcount=" + visitcount +
                ", mark='" + mark + '\'' +
                '}';
    }
}
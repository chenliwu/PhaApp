package com.clw.phaapp.model.entity;

import com.clw.phaapp.common.entity.DatagridPageEntity;

public class AskAnswerEntity extends DatagridPageEntity {

    /**
     * 问题回答者姓名
     */
    private String showusernickname;

    /**
     * 查询数据的用户，用于获取用户是否点赞了回答
     */
    private Long selectuserreno;


    /**
     * 是否点赞了
     */
    private boolean like;





    public boolean isLike() {
        return like;
    }

    public void setLike(boolean like) {
        this.like = like;
    }


    public Long getSelectuserreno() {
        return selectuserreno;
    }

    public void setSelectuserreno(Long selectuserreno) {
        this.selectuserreno = selectuserreno;
    }


    /**
     * 记录号
     */
    private Long recno;

    /**
     * 问题记录号
     */
    private Long askrecno;

    /**
     * 内容
     */
    private String content;

    /**
     * 用户记录号
     */
    private Long userrecno;

    /**
     * 发表时间
     */
    private Long optime;

    /**
     * 状态：0未采纳，1已采纳
     */
    private Byte status;

    /**
     * 被浏览次数
     */
    private Integer visitcount;

    /**
     * 被点赞次数
     */
    private Integer likecount;

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
     * 问题记录号
     * @return askrecno 问题记录号
     */
    public Long getAskrecno() {
        return askrecno;
    }

    /**
     * 问题记录号
     * @param askrecno 问题记录号
     */
    public void setAskrecno(Long askrecno) {
        this.askrecno = askrecno;
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
     * 发表时间
     * @return optime 发表时间
     */
    public Long getOptime() {
        return optime;
    }

    /**
     * 发表时间
     * @param optime 发表时间
     */
    public void setOptime(Long optime) {
        this.optime = optime;
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
     * 被点赞次数
     * @return likecount 被点赞次数
     */
    public Integer getLikecount() {
        return likecount;
    }

    /**
     * 被点赞次数
     * @param likecount 被点赞次数
     */
    public void setLikecount(Integer likecount) {
        this.likecount = likecount;
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

    public String getShowusernickname() {
        return showusernickname;
    }

    public void setShowusernickname(String showusernickname) {
        this.showusernickname = showusernickname;
    }

    @Override
    public String toString() {
        return "AskAnswerEntity{" +
                "recno=" + recno +
                ", askrecno=" + askrecno +
                ", content='" + content + '\'' +
                ", userrecno=" + userrecno +
                ", optime=" + optime +
                ", status=" + status +
                ", visitcount=" + visitcount +
                ", likecount=" + likecount +
                ", mark='" + mark + '\'' +
                '}';
    }
}
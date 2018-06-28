package com.clw.phaapp.model.entity;

import com.clw.phaapp.common.entity.DatagridPageEntity;

import java.util.List;

public class UserCollectionEntity extends DatagridPageEntity {
    /**
     * 记录号
     */
    private Long recno;

    /**
     * 收藏类型：0-19资讯，20-39问答
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
     * 收藏时间
     */
    private Long opdate;


    /**
     * 记录问答列表
     */
    private List asklist;


    public List getAsklist() {
        return asklist;
    }

    public void setAsklist(List asklist) {
        this.asklist = asklist;
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
     * 收藏时间
     * @return opdate 收藏时间
     */
    public Long getOpdate() {
        return opdate;
    }

    /**
     * 收藏时间
     * @param opdate 收藏时间
     */
    public void setOpdate(Long opdate) {
        this.opdate = opdate;
    }
}
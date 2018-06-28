package com.clw.phaapp.model.entity;

import com.clw.mysdk.base.BaseBean;

import java.util.List;

/**
 * 分页实体类
 *
 * @author chenliwu
 * @create 2018-03-16 23:21
 **/
public class PageDataBean extends BaseBean{

    /**
     * 全部数据记录数量
     */
    protected int allNum;

    /**
     * 分页总数
     */
    protected int allPages;

    /**
     * 当前页数
     */
    protected int currentPage = 1;

    /**
     * 最大记录数
     */
    protected int maxResult;

    /**
     * 分页数据
     */
    public List rows;


    public int getAllNum() {
        return allNum;
    }

    public void setAllNum(int allNum) {
        this.allNum = allNum;
    }

    public int getAllPages() {
        return allPages;
    }

    public void setAllPages(int allPages) {
        this.allPages = allPages;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getMaxResult() {
        return maxResult;
    }

    public void setMaxResult(int maxResult) {
        this.maxResult = maxResult;
    }

    public List getRows() {
        return rows;
    }

    public void setRows(List rows) {
        this.rows = rows;
    }
}

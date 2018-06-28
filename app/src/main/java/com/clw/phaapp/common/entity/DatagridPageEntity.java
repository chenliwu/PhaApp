package com.clw.phaapp.common.entity;

import java.io.Serializable;
import java.util.List;

public class DatagridPageEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	private boolean whetherPaging=true;
	
	private int total;// 总条数

	private boolean doAount = true; // 是否查询总数 默认是true，首先查询总数。

	private List rows; // 返回页面的数据

	private int endIndex = 11;

	private int beginIndex = 0;

	private int page = 1;

	private int pageSize = 10;
	
	
	//
//	private Integer begyear;
//    private Integer endyear;
//    private Integer begloannum;
//    private Integer endloannum;
//    private Integer begage;
//    private Integer endage;
//    private String counttype;
//    private String sqllist;
//    private String sqlfield;
//    private String sqlgroupby;
//    private List<String> fintype;
//    private List<String> cirtype;
//    private List<String> localtype;
//    private List<String> operator;
//    private List<String> sumtype;
//    private Integer selectbegdate;
//    private Integer selectenddate;
//    private String  showdate;

//	public List<String> getOperator() {
//		return operator;
//	}
//
//	public void setOperator(List<String> operator) {
//		this.operator = operator;
//	}
//	public List<String> getFintype() {
//		return fintype;
//	}
//
//	public void setFintype(List<String> fintype) {
//		this.fintype = fintype;
//	}
//
//	public List<String> getCirtype() {
//		return cirtype;
//	}
//
//	public void setCirtype(List<String> cirtype) {
//		this.cirtype = cirtype;
//	}
//	public List<String> getSumtype() {
//		return sumtype;
//	}
//
//	public void setSumtype(List<String> sumtype) {
//		this.sumtype = sumtype;
//	}
//    public Integer getBegyear() {
//		return begyear;
//	}
//
//	public void setBegyear(Integer begyear) {
//		this.begyear = begyear;
//	}
//
//	public Integer getEndyear() {
//		return endyear;
//	}
//
//	public void setEndyear(Integer endyear) {
//		this.endyear = endyear;
//	}
//
//	public Integer getBegloannum() {
//		return begloannum;
//	}
//
//	public void setBegloannum(Integer begloannum) {
//		this.begloannum = begloannum;
//	}
//
//	public Integer getEndloannum() {
//		return endloannum;
//	}
//
//	public void setEndloannum(Integer endloannum) {
//		this.endloannum = endloannum;
//	}
//
//	public Integer getBegage() {
//		return begage;
//	}
//
//	public void setBegage(Integer begage) {
//		this.begage = begage;
//	}
//
//	public Integer getEndage() {
//		return endage;
//	}
//
//	public void setEndage(Integer endage) {
//		this.endage = endage;
//	}
//
//	public String getCounttype() {
//		return counttype;
//	}
//
//	public void setCounttype(String counttype) {
//		this.counttype = counttype;
//	}
//
//	public String getSqllist() {
//		return sqllist;
//	}
//
//	public void setSqllist(String sqllist) {
//		this.sqllist = sqllist;
//	}


	//////////////////////////////////
	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
		this.countEndAndIndex();
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
		this.countEndAndIndex();
	}

	public boolean isDoAount() {
		return doAount;
	}

	public void setDoAount(boolean doAount) {
		this.doAount = doAount;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public List getRows() {
		return rows;
	}

	public void setRows(List lists) {
		if (lists != null && lists.size() > 0) {
			Object row = lists.get(0);
			if (row != null && row instanceof String) {
				setPageSize((Integer.valueOf((String) row)));
			}
		}
		this.rows = lists;
	}

	public int getBeginIndex() {
		return beginIndex;
	}

	public void setBeginIndex(int beginIndex) {
		this.beginIndex = beginIndex;
	}
	
	private void countEndAndIndex(){
			 this.beginIndex = (this.page-1) * this.pageSize;
			 if(this.page>1){  //stl -update
				 this.endIndex = (beginIndex)+11;
				 //this.beginIndex = beginIndex+1;
			 }
				 
				 
	}

	public boolean isWhetherPaging() {
		return whetherPaging;
	}

	public void setWhetherPaging(boolean whetherPaging) {
		this.whetherPaging = whetherPaging;
	}

	public int getEndIndex() {
		return endIndex;
	}

	public void setEndIndex(int endIndex) {
		this.endIndex = endIndex;
	}

//	public List<String> getLocaltype() {
//		return localtype;
//	}
//
//	public void setLocaltype(List<String> localtype) {
//		this.localtype = localtype;
//	}
//
//	public String getSqlfield() {
//		return sqlfield;
//	}
//
//	public void setSqlfield(String sqlfield) {
//		this.sqlfield = sqlfield;
//	}
//
//	public String getSqlgroupby() {
//		return sqlgroupby;
//	}
//
//	public void setSqlgroupby(String sqlgroupby) {
//		this.sqlgroupby = sqlgroupby;
//	}
//
//
//
//	public String getShowdate() {
//		return showdate;
//	}
//
//	public void setShowdate(String showdate) {
//		this.showdate = showdate;
//	}
//
//	public Integer getSelectbegdate() {
//		return selectbegdate;
//	}
//
//	public void setSelectbegdate(Integer selectbegdate) {
//		this.selectbegdate = selectbegdate;
//	}
//
//	public Integer getSelectenddate() {
//		return selectenddate;
//	}
//
//	public void setSelectenddate(Integer selectenddate) {
//		this.selectenddate = selectenddate;
//	}

	
}

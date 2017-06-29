package com.sys.bean;

import java.util.List;

/**
 * 
 * @author lwyx
 *
 */
public class JqgridBean {
	/**
	 * 总记录数
	 */
	private String total;
	/**
	 * 当前页
	 */
	private String page;
	/**
	 * 分页每页显示量
	 */
	private String records;
	
	/**
	 * 数据行
	 */
	private List rows;
	
	public List getRows() {
		return rows;
	}
	public void setRows(List rows) {
		this.rows = rows;
	}
	
	public String getTotal() {
		return total;
	}
	public void setTotal(String total) {
		this.total = total;
	}
	public String getPage() {
		return page;
	}
	public void setPage(String page) {
		this.page = page;
	}
	public String getRecords() {
		return records;
	}
	public void setRecords(String records) {
		this.records = records;
	}
}

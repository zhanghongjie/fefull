package com.sys.bean;

import java.util.List;

/**
 * 返回bean
 * @author lwyx
 * @param <T>
 *
 */
public class ReBean<T> {
	
	private String flag;
	/**
	 *信息 
	 */
	private String msg;
	/**
	 *标题
	 */
	private String title;
	
	//分页参数
	/**
	 * 总页面
	 */
	private String totalPage;
	/**
	 * 当前页数
	 */
	private String nowPage;
	/**
	 * 一页数据量
	 */
	private String pageSize;
	/**
	 * 总记录数
	 */
	private String records;
	/**
	 *返回数据 
	 */
	private List<T> reList;
	/**
	 *返回数据 
	 */
	private T obj;
	
	public String getRecords() {
		return records;
	}
	public void setRecords(String records) {
		this.records = records;
	}
	public String getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(String totalPage) {
		this.totalPage = totalPage;
	}
	public String getNowPage() {
		return nowPage;
	}
	public void setNowPage(String nowPage) {
		this.nowPage = nowPage;
	}
	public String getPageSize() {
		return pageSize;
	}
	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * 用户唯一标识
	 */
	private String userId;
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public List<T> getReList() {
		return reList;
	}
	public void setReList(List<T> reList) {
		this.reList = reList;
	}
	public T getObj() {
		return obj;
	}
	public void setObj(T obj) {
		this.obj = obj;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	
}

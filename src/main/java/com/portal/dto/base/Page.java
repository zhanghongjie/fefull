package com.portal.dto.base;

import com.jfinal.plugin.activerecord.Record;
import com.sys.bean.ReBean;

public class Page {
	
	private String totalPage;
	
	private String nowPage;
	
	private String pageSize;
	
	public void setPage(ReBean<Record> reBean){
		this.setTotalPage(reBean.getTotalPage());
		this.setNowPage(reBean.getNowPage());
		this.setPageSize(reBean.getPageSize());
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
	
}

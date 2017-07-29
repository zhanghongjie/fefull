package com.portal.dto.request;

public class ArticleListRequest {
	
	private String categoryId;
	
	private String tagcloudId;
	
	private String keyword;
	
	private String sort;
	
	private String nowPage;
	
	private String pageSize;
	
	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getKeyword() {
		return keyword;
	}
	
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	
	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public String getTagcloudId() {
		return tagcloudId;
	}

	public void setTagcloudId(String tagcloudId) {
		this.tagcloudId = tagcloudId;
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

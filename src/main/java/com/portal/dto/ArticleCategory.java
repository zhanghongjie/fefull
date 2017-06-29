package com.portal.dto;

import com.biz.model.BizArticleCategory;
import com.portal.dto.base.BaseDto;

public class ArticleCategory implements BaseDto<BizArticleCategory, ArticleCategory>{
	
	private Integer categoryId;
	
	private String categoryName;
	
	public ArticleCategory(){}
	
	public ArticleCategory(Integer categoryId, String categoryName) {
		this.categoryId = categoryId;
		this.categoryName = categoryName;
	}

	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	@Override
	public ArticleCategory wrapper(BizArticleCategory d) {
		this.setCategoryId(d.getPkArticleCategory());
		this.setCategoryName(d.getCategoryName());
		return this;
	}
	
	
}

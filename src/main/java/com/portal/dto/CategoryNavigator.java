package com.portal.dto;

import java.util.List;

public class CategoryNavigator {
	
	private Integer categoryId;
	
	private String categoryName;
	
	private List<Navigator> navigators;

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

	public List<Navigator> getNavigators() {
		return navigators;
	}

	public void setNavigators(List<Navigator> navigators) {
		this.navigators = navigators;
	}
	
}

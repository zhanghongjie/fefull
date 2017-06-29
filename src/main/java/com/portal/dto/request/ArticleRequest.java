package com.portal.dto.request;

import com.portal.dto.Category;
import com.portal.dto.Origin;

public class ArticleRequest {
	
	private Integer articleId;
	
	private String title;
	
	private Origin origin;
	
	private String maintxt;
	
	private String cover;
	
	private Category category;
	
	private Integer[] tagclouds;
	
	private String reprint;
	
	public Integer getArticleId() {
		return articleId;
	}

	public void setArticleId(Integer articleId) {
		this.articleId = articleId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Origin getOrigin() {
		return origin;
	}

	public void setOrigin(Origin origin) {
		this.origin = origin;
	}

	public String getMaintxt() {
		return maintxt;
	}

	public void setMaintxt(String maintxt) {
		this.maintxt = maintxt;
	}

	public String getCover() {
		return cover;
	}

	public void setCover(String cover) {
		this.cover = cover;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Integer[] getTagclouds() {
		return tagclouds;
	}

	public void setTagclouds(Integer[] tagclouds) {
		this.tagclouds = tagclouds;
	}

	public String getReprint() {
		return reprint;
	}

	public void setReprint(String reprint) {
		this.reprint = reprint;
	}
}

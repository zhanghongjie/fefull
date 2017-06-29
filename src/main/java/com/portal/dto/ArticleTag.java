package com.portal.dto;

import com.biz.model.BizArticleTag;
import com.portal.dto.base.BaseDto;

public class ArticleTag implements BaseDto<BizArticleTag, ArticleTag>{
	
	private Integer tagcloudId;
	
	private String tagcloudName;
		
	public Integer getTagcloudId() {
		return tagcloudId;
	}

	public void setTagcloudId(Integer tagcloudId) {
		this.tagcloudId = tagcloudId;
	}

	public String getTagcloudName() {
		return tagcloudName;
	}

	public void setTagcloudName(String tagcloudName) {
		this.tagcloudName = tagcloudName;
	}

	@Override
	public ArticleTag wrapper(BizArticleTag d) {
		this.setTagcloudId(d.getPkArticleTag());
		this.setTagcloudName(d.getTagName());
		return this;
	}
	
}

package com.biz.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings("serial")
public abstract class BaseBizArticleTag<M extends BaseBizArticleTag<M>> extends Model<M> implements IBean {

	public void setPkArticleTag(java.lang.Integer pkArticleTag) {
		set("PK_ARTICLE_TAG", pkArticleTag);
	}

	public java.lang.Integer getPkArticleTag() {
		return get("PK_ARTICLE_TAG");
	}

	public void setTagName(java.lang.String tagName) {
		set("TAG_NAME", tagName);
	}

	public java.lang.String getTagName() {
		return get("TAG_NAME");
	}

	public void setMEMO(java.lang.String MEMO) {
		set("MEMO", MEMO);
	}

	public java.lang.String getMEMO() {
		return get("MEMO");
	}

	public void setCreateById(java.lang.Integer createById) {
		set("CREATE_BY_ID", createById);
	}

	public java.lang.Integer getCreateById() {
		return get("CREATE_BY_ID");
	}

	public void setCreateByTime(java.util.Date createByTime) {
		set("CREATE_BY_TIME", createByTime);
	}

	public java.util.Date getCreateByTime() {
		return get("CREATE_BY_TIME");
	}

	public void setUpdateById(java.lang.Integer updateById) {
		set("UPDATE_BY_ID", updateById);
	}

	public java.lang.Integer getUpdateById() {
		return get("UPDATE_BY_ID");
	}

	public void setUpdateByTime(java.util.Date updateByTime) {
		set("UPDATE_BY_TIME", updateByTime);
	}

	public java.util.Date getUpdateByTime() {
		return get("UPDATE_BY_TIME");
	}

	public void setIsValid(java.lang.Integer isValid) {
		set("IS_VALID", isValid);
	}

	public java.lang.Integer getIsValid() {
		return get("IS_VALID");
	}

}
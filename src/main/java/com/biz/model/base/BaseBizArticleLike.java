package com.biz.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings("serial")
public abstract class BaseBizArticleLike<M extends BaseBizArticleLike<M>> extends Model<M> implements IBean {

	public void setPkArticleLike(java.lang.Integer pkArticleLike) {
		set("PK_ARTICLE_LIKE", pkArticleLike);
	}

	public java.lang.Integer getPkArticleLike() {
		return get("PK_ARTICLE_LIKE");
	}

	public void setFkArticle(java.lang.Integer fkArticle) {
		set("FK_ARTICLE", fkArticle);
	}

	public java.lang.Integer getFkArticle() {
		return get("FK_ARTICLE");
	}

	public void setFkMember(java.lang.Integer fkMember) {
		set("FK_MEMBER", fkMember);
	}

	public java.lang.Integer getFkMember() {
		return get("FK_MEMBER");
	}

	public void setMemberCreateById(java.lang.Integer memberCreateById) {
		set("MEMBER_CREATE_BY_ID", memberCreateById);
	}

	public java.lang.Integer getMemberCreateById() {
		return get("MEMBER_CREATE_BY_ID");
	}

	public void setMemberCreateByTime(java.util.Date memberCreateByTime) {
		set("MEMBER_CREATE_BY_TIME", memberCreateByTime);
	}

	public java.util.Date getMemberCreateByTime() {
		return get("MEMBER_CREATE_BY_TIME");
	}

	public void setMemberUpdateById(java.lang.Integer memberUpdateById) {
		set("MEMBER_UPDATE_BY_ID", memberUpdateById);
	}

	public java.lang.Integer getMemberUpdateById() {
		return get("MEMBER_UPDATE_BY_ID");
	}

	public void setMemberUpdateByTime(java.util.Date memberUpdateByTime) {
		set("MEMBER_UPDATE_BY_TIME", memberUpdateByTime);
	}

	public java.util.Date getMemberUpdateByTime() {
		return get("MEMBER_UPDATE_BY_TIME");
	}

	public void setIsValid(java.lang.Integer isValid) {
		set("IS_VALID", isValid);
	}

	public java.lang.Integer getIsValid() {
		return get("IS_VALID");
	}

}
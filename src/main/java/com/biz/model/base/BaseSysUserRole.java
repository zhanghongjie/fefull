package com.biz.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings("serial")
public abstract class BaseSysUserRole<M extends BaseSysUserRole<M>> extends Model<M> implements IBean {

	public void setPkUserRole(java.lang.Integer pkUserRole) {
		set("PK_USER_ROLE", pkUserRole);
	}

	public java.lang.Integer getPkUserRole() {
		return get("PK_USER_ROLE");
	}

	public void setFkUser(java.lang.Integer fkUser) {
		set("FK_USER", fkUser);
	}

	public java.lang.Integer getFkUser() {
		return get("FK_USER");
	}

	public void setFkRole(java.lang.Integer fkRole) {
		set("FK_ROLE", fkRole);
	}

	public java.lang.Integer getFkRole() {
		return get("FK_ROLE");
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
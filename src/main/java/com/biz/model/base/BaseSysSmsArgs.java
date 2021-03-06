package com.biz.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings("serial")
public abstract class BaseSysSmsArgs<M extends BaseSysSmsArgs<M>> extends Model<M> implements IBean {

	public void setPkSysSmsArgs(java.lang.Integer pkSysSmsArgs) {
		set("pk_sys_sms_args", pkSysSmsArgs);
	}

	public java.lang.Integer getPkSysSmsArgs() {
		return get("pk_sys_sms_args");
	}

	public void setArgName(java.lang.String argName) {
		set("arg_name", argName);
	}

	public java.lang.String getArgName() {
		return get("arg_name");
	}

	public void setArgCode(java.lang.String argCode) {
		set("arg_code", argCode);
	}

	public java.lang.String getArgCode() {
		return get("arg_code");
	}

	public void setArgValue(java.lang.String argValue) {
		set("arg_value", argValue);
	}

	public java.lang.String getArgValue() {
		return get("arg_value");
	}

	public void setFkSysSmsProduct(java.lang.Integer fkSysSmsProduct) {
		set("fk_sys_sms_product", fkSysSmsProduct);
	}

	public java.lang.Integer getFkSysSmsProduct() {
		return get("fk_sys_sms_product");
	}

	public void setFkSysSmsChannel(java.lang.Integer fkSysSmsChannel) {
		set("fk_sys_sms_channel", fkSysSmsChannel);
	}

	public java.lang.Integer getFkSysSmsChannel() {
		return get("fk_sys_sms_channel");
	}

}

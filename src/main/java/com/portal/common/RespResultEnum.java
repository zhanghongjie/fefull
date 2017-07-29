package com.portal.common;

/**
 * 返回码
 */
public enum RespResultEnum {
	
	/**
	 * 系统返回码
	 */
	SUCCESS("0000", "请求成功"), 
	NOT_LOGIN("1001", "未登录"),
	PARAMETER_ERR("1002", "请求参数错误"), 
	SYSTEM_ERR("1003", "网络异常"),
	
	/**
	 * 业务返回码
	 */
	LOGIN_ERR("2001", "帐号或者密码错误"),
	DATA_EXIST("2002", "记录已存在"),
	DATA_NOEXIST("2003", "记录不存在");
	
	String respCode;
	
	String respDesc;
	
	private RespResultEnum(String respCode, String respDesc) {
		this.respCode = respCode;
		this.respDesc = respDesc;
	}
	
	public String getRespDesc() {
		return respDesc;
	}

	public void setRespDesc(String respDesc) {
		this.respDesc = respDesc;
	}

	public String getRespCode() {
		return respCode;
	}

	public void setRespCode(String respCode) {
		this.respCode = respCode;
	}

	
}

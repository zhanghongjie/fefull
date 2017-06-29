package com.biz.commom.sms;

/**   
* @Title: SmsReBaseBean.java 
* @Package com.biz.bean 
* @Description: 片云网发送短信回复内容类 
* @author sos
* @date 2016年8月25日 下午10:03:47 
* @version V1.0   
*/
public class SmsReBaseBean {
	//返回状态
	private int code;
	//返回内容
	private String msg;
	//返回结果
	private SmsReResultBean srrb;
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public SmsReResultBean getSrrb() {
		return srrb;
	}
	public void setSrrb(SmsReResultBean srrb) {
		this.srrb = srrb;
	}
	
}

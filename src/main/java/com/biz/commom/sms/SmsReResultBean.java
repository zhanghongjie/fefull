package com.biz.commom.sms;

/**   
* @Title: SmsReResultBean.java 
* @Package com.biz.bean 
* @Description: 片云网发送短信回复内容类 
* @author sos
* @date 2016年8月25日 下午10:03:47 
* @version V1.0   
*/
public class SmsReResultBean {
	//成功发送的短信个数
	private int count;
	//扣费条数，70个字一条，超出70个字时按每67字一条计
	private Integer fee;
	//短信id，多个号码时以该id+各手机号尾号后8位作为短信id。64位整型， 对应Java和C#的Long，不可用int解析
	private Long sid;
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public Integer getFee() {
		return fee;
	}
	public void setFee(Integer fee) {
		this.fee = fee;
	}
	public Long getSid() {
		return sid;
	}
	public void setSid(Long sid) {
		this.sid = sid;
	}
	
}

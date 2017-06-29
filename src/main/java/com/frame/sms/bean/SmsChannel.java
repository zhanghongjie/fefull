package com.frame.sms.bean;

import com.biz.model.SysSms;

/**   
* @Title: SmsChannel.java 
* @Package org.whale.system.bean 
* @Description: 短信通道接口 
* @author lwyx
* @date 2015年11月13日 上午9:53:35 
* @version V1.0   
*/
public interface SmsChannel {
	/**
	 * 实时发送验证码
	 * @param sms
	 * @return
	 */
	public boolean sendRealTime4Vcode(SysSms sms);
	/**
	 * 实时发送通知信息
	 * @param sms
	 * @return
	 */
	public boolean sendRealTime4Notice(SysSms sms);
	
	/**
	 * 实时发送营销信息
	 * @param sms
	 * @return
	 */
	public boolean sendRealTime4Market(SysSms sms);
	
	/**
	 * 非实时发送验证码
	 * @param sms
	 * @return
	 */
	public void sendNoRealTime4Vcode(SysSms sms);
	/**
	 * 非实时发送通知信息
	 * @param sms
	 * @return
	 */
	public void sendNoRealTime4Notice(SysSms sms);
	/**
	 * 非实时发送营销信息
	 * @param sms
	 * @return
	 */
	public void sendNoRealTime4Market(SysSms sms);
}

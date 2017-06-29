package com.frame.sms.service.inter;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.biz.model.SysSms;
import com.frame.sms.common.exception.SmsException;
import com.frame.sms.constant.SysSmsConstant;
import com.frame.sms.service.SmsService;
import com.frame.sms.util.SendUtil;
import com.jfinal.plugin.activerecord.Record;

/**   
* @Title: AbstractSysSmsSend.java 
* @Package com.frame.sms.service.inter 
* @Description: 短信发送抽象类
* @author sos
* @date 2016年5月27日 下午2:33:14 
* @version V1.0   
*/
public abstract class AbstractSysSmsSend {
	public static final Logger logger = LoggerFactory.getLogger(AbstractSysSmsSend.class);

	public abstract void  init(List<Record> confList) throws SmsException;
	
	/**
	 * 实时发送短信
	 * @param sms
	 * @return
	 * @throws SmsException 
	 */
	public boolean sendRealTimeSms(SysSms sms) {
		if(sms==null){
			throw new SmsException("ERROR-->AbstractSysSmsSend-->sendRealTimeSms:参数sms不能为空");
		}
		Map<String, String> reMap = SendUtil.reReqArg(sms, this.getConfMap(),SysSmsConstant.SMS_ENCODE,0);
		try{
			logger.info("短信发送 接口调用开始： URL="+reMap.get("url"));
			sms.setCurRetryTime((sms.getCurRetryTime() == null ? 0 : sms.getCurRetryTime())+1);
			
			String rs = java.net.URLDecoder.decode(SendUtil.simpleHttpPost4Proxy(reMap.get("url"), SmsService.urlArgs2Map(reMap.get("args"))), SysSmsConstant.SMS_ENCODE);
			logger.info("短信发送 接口调用完成：rs="+rs+" URL="+reMap.get("url"));
			
			sms.setRecTime(new Date());
			sms.setResMsg(rs);
			if(null != rs){
				SendUtil.reResStatus(rs, sms);
			}else{
				sms.setResStatus(SysSmsConstant.STATUS_SEND_FAIL.toString());
			}
		} catch(Exception e){
			sms.setBisExpInfo("短信发送失败：短信接口调用异常 url="+reMap.get("url")+" 异常信息："+e.getMessage());
			if(sms.getCurRetryTime() >= sms.getRetryTime()){
				sms.setStatus(SysSmsConstant.STATUS_SEND_FAIL);
				logger.error("短信发送失败：短信接口调用异常 url="+reMap.get("url"), e);
			}
		}
		sms.save();
		return true;
	}
	/**
	 * 非实时发送短信
	 * @param sms
	 * @return
	 * @throws SmsException 
	 */
	public boolean sendNoRealTimeSms(SysSms sms,int isOnlyAm) throws SmsException {
		if(sms==null){
			throw new SmsException("ERROR-->AbstractSysSmsSend-->sendNoRealTimeSms:参数sms不能为空");
		}
		SendUtil.reReqArg(sms, this.getConfMap(),SysSmsConstant.SMS_ENCODE,isOnlyAm);
		sms.save();
		return true;
	}
	public abstract Map<String, String>  getConfMap();
}

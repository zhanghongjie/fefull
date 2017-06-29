package com.frame.sms.service;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.frame.sms.common.exception.SmsException;
import com.frame.sms.constant.SysSmsConstant;
import com.frame.sms.service.inter.AbstractSysSmsSend;
import com.frame.sms.util.SendUtil;
import com.jfinal.plugin.activerecord.Record;


/**   
* @Title: SysSmsSend4VoiceImpl.java 
* @Package org.whale.system.service 
* @Description: 通知类短信发送 
* @author lwyx
* @date 2015年11月20日 下午2:39:22 
* @version V1.0   
*/
public class SysSmsSend4NoticeImpl extends AbstractSysSmsSend{
	public static final Logger logger = LoggerFactory.getLogger(SysSmsSend4NoticeImpl.class);
	private List<Record> confList;
	private Map<String, String> confMap;
	
	private SysSmsSend4NoticeImpl() {}

	public static SysSmsSend4NoticeImpl getInstance(List<Record> confList) {
		SysSmsSend4NoticeImpl tmp = SysSmsSend4NoticeImplHolder.instance;
		if(tmp.confMap==null){
			tmp.init(confList);
		}
		return tmp;
	}

	private static class SysSmsSend4NoticeImplHolder {
		private static SysSmsSend4NoticeImpl instance = new SysSmsSend4NoticeImpl();
	}
	
	@Override
	public void init(List<Record> mConfList) {
		if(mConfList!=null){
			confList = mConfList;
			confMap = SendUtil.boxArg(confList,SysSmsConstant.SMS_NOTICE_TYPE);
		}else{
			throw new SmsException("ERROR-->SysSmsSend4NoticeImpl-->init:输入的参数mConfList不能为空");
		}
	}

	@Override
	public Map<String, String> getConfMap() {
		return this.confMap;
	}
}

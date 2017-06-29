package com.frame.sms.service;

import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.biz.model.SysSms;
import com.frame.boot.Bootable;
import com.frame.sms.constant.SysSmsConstant;
import com.frame.sms.mgr.SysSmsMgr;
import com.frame.sms.util.SendUtil;
import com.frame.utils.ArgsTool;
import com.frame.utils.Fun;
import com.jfinal.plugin.activerecord.Record;


/**   
* @Title: SmsService.java 
* @Package com.frame.sms.service 
* @Description: 短信发送方法
* @author sos
* @date 2016年5月27日 下午2:45:12 
* @version V1.0   
*/
public class SmsService implements Bootable{
	private static final Logger logger = LoggerFactory.getLogger(SmsService.class);
	
	@Override
	public void init() {
		logger.info("短信:初始化短信配置...");
		this.initConf();
		Thread thread = new SendSmsThread();
		thread.setName("短信扫描发送线程");
		thread.setDaemon(true);
		thread.start();
		logger.info("短信:启动短信发送线程...");
		/*SysSms sms = new SysSms();
		sms.setToPhones("971528126806");
		sms.setContent("sms test");
		SmsService.sendRealTime4Vcode(sms);*/
	}

	@Override
	public int initOrder() {
		return 4;
	}
	
	/**
	 * 短信配置信息
	 */
	private static List<List<Record>> confList;
	/**
	 * 当前生效短信通道
	 */
	private static List<Record> conf;
	
	/**
	 * 初始化短信配置
	 */
	public void initConf(){
		//取数据库配置
		try {
			if(null==confList){
				confList = SysSmsMgr.getInstance().getSmsConf();
				//按优先级排序，最前面的等级最高，最先使用
				conf = confList.get(0);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 实时发送验证码
	 * @param sms
	 * @return
	 */
	public static boolean sendRealTime4Vcode(SysSms sms){
		return SysSmsSend4VoiceImpl.getInstance(conf).sendRealTimeSms(sms);
	}
	/**
	 * 实时发送通知信息
	 * @param sms
	 * @return
	 */
	public static boolean sendRealTime4Notice(SysSms sms){
		return SysSmsSend4NoticeImpl.getInstance(conf).sendRealTimeSms(sms);
	}
	/**
	 * 实时发送营销信息
	 * @param sms
	 * @return
	 */
	public static boolean sendRealTime4Market(SysSms sms){
		return SysSmsSend4MarketImpl.getInstance(conf).sendRealTimeSms(sms);
	}
	
	/**
	 * 非实时发送验证码
	 * @param sms
	 * @return
	 */
	public static boolean sendNoRealTime4Vcode(SysSms sms){
		return SysSmsSend4VoiceImpl.getInstance(conf).sendNoRealTimeSms(sms,0);
	}
	/**
	 * 非实时发送通知信息
	 * @param sms
	 * @return
	 */
	public static boolean sendNoRealTime4Notice(SysSms sms){
		return SysSmsSend4NoticeImpl.getInstance(conf).sendNoRealTimeSms(sms,0);
	}
	/**
	 * 非实时发送营销信息
	 * @param sms
	 * @return
	 */
	public static boolean sendNoRealTime4Market(SysSms sms){
		return SysSmsSend4MarketImpl.getInstance(conf).sendNoRealTimeSms(sms,0);
	}
	/**
	 *只能早上发送通知信息
	 * @param sms
	 * @return
	 */
	public static boolean sendOnlyAm4Notice(SysSms sms){
		return SysSmsSend4NoticeImpl.getInstance(conf).sendNoRealTimeSms(sms,1);
	}
	/**
	 * 只能早上发送营销信息
	 * @param sms
	 * @return
	 */
	public static boolean sendOnlyAm4Market(SysSms sms){
		return SysSmsSend4MarketImpl.getInstance(conf).sendNoRealTimeSms(sms,1);
	}
	
	
	
	/**
	 * 短信发送扫描线程
	 */
	class SendSmsThread extends Thread {
		@Override
		public void run() {
			Calendar rightNow = null;
			while(true){
				rightNow = Calendar.getInstance();
				List<SysSms> smss = null;
				//*100 为支持配置到分
				int hour = rightNow.get(Calendar.HOUR_OF_DAY)*100;
				Map<String,String> mQryMap = new HashMap<String, String>();
				mQryMap.put("status", SysSmsConstant.STATUS_SEND_WAITING+"");
				if(hour>= SysSmsConstant.SEND_START_TIME && hour<= SysSmsConstant.SEND_END_TIME){
					String sql = "select * from sys_sms t where status = @status and isOnlyAm = 0 ORDER BY t.sendRealTime desc, t.createTime desc LIMIT 0,@size";
					sql = sql.replace("@size", SysSmsConstant.SMS_SCAN_LIMIT+"");
					smss = ArgsTool.getList(SysSms.dao, sql, mQryMap);
				}else{
					String sql = "select * from sys_sms t where status = @status ORDER BY t.sendRealTime desc, t.createTime desc LIMIT 0,@size";
					sql = sql.replace("@size", SysSmsConstant.SMS_SCAN_LIMIT+"");
					smss = ArgsTool.getList(SysSms.dao, sql, mQryMap);
				}
				if(smss != null){
					for(SysSms sms : smss){
						try {
							logger.info("异步短信发送 接口调用开始： URL="+sms.getChannelUrl());
							sms.setCurRetryTime((sms.getCurRetryTime() == null ? 0 : sms.getCurRetryTime())+1);
							String rs = java.net.URLDecoder.decode(SendUtil.simpleHttpPost4Proxy(sms.getChannelUrl(), SmsService.urlArgs2Map(sms.getChannelArgs())), SysSmsConstant.SMS_ENCODE);
							logger.info("异步短信发送 接口调用完成：rs="+rs+" URL="+sms.getChannelUrl());
							
							sms.setRecTime(new Date());
							sms.setResMsg(rs);
							sms.setStatus(SysSmsConstant.STATUS_SEND_SUCCESS);
							if(null != rs){
								SendUtil.reResStatus(rs, sms);
							}
							sms.update();
						} catch (UnsupportedEncodingException e) {
							sms.setBisExpInfo("异步短信发送失败：短信接口调用异常 url="+sms.getChannelUrl()+" 异常信息："+e.getMessage());
							if(sms.getCurRetryTime() >= sms.getRetryTime()){
								sms.setStatus(SysSmsConstant.STATUS_SEND_FAIL);
								logger.error("异步短信发送失败：短信接口调用异常 url="+sms.getChannelUrl(), e);
							}
						}
					}
				}else{
					logger.debug("异步短信发送: 没有待发送的短信");
				}
				try {
					int interval =SysSmsConstant.SMS_SCAN_INTERVAL;
					if(interval < 3000){
						interval = 3000;
					}
					Thread.sleep(new Long(interval));
				} catch (InterruptedException e) {
				}
			}
		}
		
	}
	
	
	/**
	 * url参数转成map
	 * @param args
	 * @return
	 */
	public static Map<String,String> urlArgs2Map(String args){
		Map<String,String> map = null;
		if(!Fun.eqNull(args)){
			String[] argsList =  args.split("&");
			if(!Fun.eqNull(argsList) && argsList.length>0){
				map = new HashMap<String, String>();
				String[] tmp = null;
				for (String str : argsList) {
					tmp = str.split("=");
					if(!Fun.eqNull(tmp) && tmp.length==2){
						map.put(tmp[0], tmp[1]);
					}
				}
			}
		}
		return map;
	}
	public static void main(String[] args) {
		Calendar rightNow = Calendar.getInstance();
		int hour = rightNow.get(Calendar.HOUR_OF_DAY);
		System.out.println("hour:"+hour);
	}

	public static List<List<Record>> getConfList() {
		return confList;
	}

	public static void setConfList(List<List<Record>> confList) {
		SmsService.confList = confList;
	}
	
}
package com.frame.sms.constant;

public class SysSmsConstant {
	/**
	 * 短信内容编码
	 */
	public static String SMS_ENCODE = "UTF-8";
	//---------------短信类型与sys_sms_product表中sms_type字段的值一一对应--------------
	/**
	 * 短信类型-验证码
	 */
	public static String SMS_VERIFY_TYPE = "SMS_VERIFY_TYPE";
	/**
	 * 短信类型-通知
	 */
	public static String SMS_NOTICE_TYPE = "SMS_NOTICE_TYPE";
	/**
	 * 短信类型-营销
	 */
	public static String SMS_MARKET_TYPE = "SMS_MARKET_TYPE";
	/**
	 * 短信类型-通用
	 */
	public static String SMS_ALL_TYPE = "SMS_ALL_TYPE";
	
	/**
	 *只能早上发送的短信，时间段 早上8点到晚上8点 
	 *830 = 早上8:30
	 *2000 = 晚上  20:20
	 */
	public static int SEND_START_TIME = 830;
	public static int SEND_END_TIME = 2000;
	/**
	 * 异步短信，一次发送条数
	 */
	public static int SMS_SCAN_LIMIT = 20;
	/**
	 * 异步短信，间隔发送时间
	 */
	public static int SMS_SCAN_INTERVAL = 6000;
	
	/**
	 * 通道参数标识
	 */
	public static String CHANNEL_ARGS = "channelArgs";
	/**
	 * 通道url标识
	 */
	public static String CHANNEL_URL = "channelUrl";
	/**
	 * 通道签名标识
	 */
	public static String CHANNEL_SING = "channelSign";
	

	
	/**待发送 */
	public static final Integer STATUS_SEND_WAITING = 1;
	/**发送成功*/
	public static final Integer STATUS_SEND_SUCCESS = 2;
	/**发送失败 */
	public static final Integer STATUS_SEND_FAIL = -1;
	/**忽略发送 */
	public static final Integer STATUS_SEND_IGNORE = 4;
	
	/**短信内容最大长度*/
	public static final int MAX_CONTENT_LENGTH = 70;
	
}

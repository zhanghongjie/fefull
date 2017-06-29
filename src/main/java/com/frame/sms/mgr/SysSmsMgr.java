package com.frame.sms.mgr;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.frame.utils.ArgsTool;
import com.jfinal.plugin.activerecord.Record;
/**   
* @Title: SysSmsMgr.java 
* @Package com.frame.sms.mgr 
* @Description: 取短信配置 
* @author sos
* @date 2016年5月27日 下午3:31:58 
* @version V1.0   
*/
public class SysSmsMgr {
	public static final Logger logger = LoggerFactory.getLogger(SysSmsMgr.class);
	private SysSmsMgr() {

	}

	public static SysSmsMgr getInstance() {
		return SysSmsMgrHolder.instance;
	}

	private static class SysSmsMgrHolder {
		private static SysSmsMgr instance = new SysSmsMgr();
	}
	/**
	 * 取短信配置信息
	 */
	public  List<List<Record>> getSmsConf() throws Exception{
		//1.查询配置 UNION ALL的原因是为了把公用的属性也查询出来
		StringBuilder sql = new StringBuilder("SELECT a. channel_code ,a. pk_sys_sms_channel ,a. channel_name ,a. channel_url ,a. channel_sign ,b. product_name ,b. sms_type ,c. fk_sys_sms_product ,c. arg_name ,c. arg_code ,c. arg_value ");
		sql.append(" FROM sys_sms_channel a")
		.append(" LEFT JOIN  sys_sms_product  b ON a. pk_sys_sms_channel  = b. fk_sys_sms_channel ")
		.append(" LEFT JOIN  sys_sms_args  c ON b. pk_sys_sms_product  = c. fk_sys_sms_product ")
		.append(" WHERE a. channel_status =1")
		.append(" UNION ALL ")
		.append(" SELECT a. channel_code ,a. pk_sys_sms_channel ,a. channel_name ,a. channel_url ,a. channel_sign ,b. product_name ,b. sms_type ,c. fk_sys_sms_product ,c. arg_name ,c. arg_code ,c. arg_value  ")
		.append("  FROM sys_sms_channel a")
		.append(" LEFT JOIN  sys_sms_product  b ON a. pk_sys_sms_channel  = b. fk_sys_sms_channel ")
		.append(" LEFT JOIN  sys_sms_args  c ON a. pk_sys_sms_channel  = c. fk_sys_sms_channel ")
		.append(" WHERE a. channel_status =1 AND c. fk_sys_sms_product =0 ");
		List<Record> list = ArgsTool.getList(sql.toString());
		if(list!=null && list.size()<=0){
			throw new Exception("ERROR-->SysSmsProductDao-->getSmsConf:短信插件查询配置失败，请查看相关表结构是否已创建并配置完成！");
		}
		
		//2. 将配置信息转成list[list] 结构，外层list划分不同的短信通道，内层list划分同一通道中不同的产品
		String channelCodeTmp = null;
		List<Record> list2 = null;
		List<List<Record>> list1 = new ArrayList<List<Record>>();
		for (Record item : list) {
			if(!item.get("channel_code").equals(channelCodeTmp)){
				list2 = new ArrayList<Record>();
				channelCodeTmp = item.get("channel_code").toString();
				list1.add(list2);
			}
			list2.add(item);
		}
		
		return list1;
	}
}

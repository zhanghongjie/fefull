package com.sys.util;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.biz.model.SysLanguageCfg;
import com.frame.constants.SysEnum;
import com.frame.utils.Fun;
import com.jfinal.plugin.activerecord.Record;
import com.sys.bean.UserInfoBean;
import com.sys.cache.SysInfoCache;


/**   
* @Title: LanCfg.java 
* @Package com.sys.util 
* @Description: 根据配置取显示的语言 
* @author sos
* @date 2016年5月13日 下午2:28:05 
* @version V1.0   
*/
public class LanCfg {
	private LanCfg() {

	}

	public static LanCfg getInstance() {
		return LanCfgHolder.instance;
	}

	private static class LanCfgHolder {
		private static LanCfg instance = new LanCfg();
	}
	
	public String get(String str,HttpServletRequest req){
		String reStr = null;
		SysLanguageCfg sysLanguageCfg = SysInfoCache.getInstance().getLanMap().get(str);
		if(Fun.eqNull(sysLanguageCfg)){
			return str;
		}else{
			//UserInfoBean userInfoBean = SysInfoCache.getInstance().getUserInfoBean(req);
			
			//TODO 未登录默认中文 后续需从cookies中取
			Integer lanType = SysEnum.LAN_CFG.CN.getValue();
			/*if(!Fun.eqNull(userInfoBean) && !Fun.eqNull(userInfoBean.getSysUser().getLanType())){
				lanType = userInfoBean.getSysUser().getLanType();
			}*/
			
			if(lanType == SysEnum.LAN_CFG.EN.getValue()){
				reStr = sysLanguageCfg.getEN();
			}else{//默认中文
				reStr = sysLanguageCfg.getCN();
			}
		}
		return reStr;
	}
	/**
	 * 转化返回列表中对应的中文
	 * @param list
	 * @param recordName 需要替换的字段名
	 */
	public List<Record> repLan(List<Record> list,String recordName,HttpServletRequest req){
		if(!Fun.eqListNull(list) && !Fun.eqNull(recordName)){
			for (Record record : list) {
				record.set(recordName, this.get(record.getStr(recordName),req));
			}
		}
		return list;
	}
}

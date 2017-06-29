package com.sys.boot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.frame.boot.Bootable;
import com.frame.constants.SysEnum;
import com.sys.mgr.LanguageCfgMgr;
/**   
* @Title: InitLanCfg.java 
* @Package com.sys.boot 
* @Description: 初始化语言配置 
* @author sos
* @date 2016年5月12日 下午6:17:18 
* @version V1.0   
*/
public class InitLanCfg implements Bootable{
	private static Logger log=LoggerFactory.getLogger(InitLanCfg.class);
	

	@Override
	public void init(){
		log.info("初始化Beetl共享变量！！-->InitLanCfg-->init-->初始化语言配置");
		LanguageCfgMgr.getInstance().initLanCfg();
	}
	
	@Override
	public int initOrder() {
		return SysEnum.BOOT_TYPE.LAN_CFG.getValue();
	}
}

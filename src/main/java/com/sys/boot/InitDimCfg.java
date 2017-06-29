package com.sys.boot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.frame.boot.Bootable;
import com.frame.constants.SysEnum;
import com.sys.mgr.SysDictMgr;
/**   
* @Title: InitDimCfg.java 
* @Package com.sys.boot 
* @Description: 维表初始化 
* @author sos
* @date 2016年5月13日 上午11:19:04 
* @version V1.0   
*/
public class InitDimCfg implements Bootable{
	private static Logger log=LoggerFactory.getLogger(InitDimCfg.class);
	

	@Override
	public void init(){
		log.info("InitDimCfg-->init-->维表初始化 ");
		SysDictMgr.getInstance().initDimTable();
	}
	@Override
	public int initOrder() {
		return SysEnum.BOOT_TYPE.DIM_TABEL.getValue();
	}
}

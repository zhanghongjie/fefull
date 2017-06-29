package com.sys.mgr;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.biz.model.SysLanguageCfg;
import com.frame.utils.ArgsTool;
import com.frame.utils.Fun;
import com.jfinal.plugin.activerecord.Record;
import com.sys.bean.ReBean;
import com.sys.cache.SysInfoCache;
/**   
* @Title: LanguageCfgMgr.java 
* @Package com.sys.mgr 
* @Description: 语言配置管理
* @author sos
* @date 2016年5月12日 下午4:30:28 
* @version V1.0   
*/
public class LanguageCfgMgr {
	public static final Logger logger = LoggerFactory.getLogger(LanguageCfgMgr.class);
	private LanguageCfgMgr() {

	}

	public static LanguageCfgMgr getInstance() {
		return LanguageCfgMgrHolder.instance;
	}

	private static class LanguageCfgMgrHolder {
		private static LanguageCfgMgr instance = new LanguageCfgMgr();
	}
	
	/**
	 * 分页查询
	 * @return
	 */
	public ReBean<Record> getPage(Map<String, String> mQryMap){
		ReBean<Record> reBean = null;
		StringBuilder sb = new StringBuilder("select * from sys_language_cfg order by PK_LANGUAGE_CFG  desc");
		if (!Fun.eqNull(mQryMap)) {
			reBean = ArgsTool.getPageObj(this.reWhereSql(mQryMap,sb), mQryMap);
		}
		return reBean;
	}
	/**
	 * @param mQryMap
	 */
	private String reWhereSql(Map<String, String> mQryMap,StringBuilder sb){
		StringBuilder msb = new StringBuilder();
		if(!Fun.eqNull(mQryMap.get("lanName"))){
			msb.append(" and CN like @lanName or  EN like @lanName or AR_SA like @lanName");
		}
		msb.append(" and IS_VALID=1 ");
		return ArgsTool.reSql(sb, msb);
	}
	
	/**
	 * 判断添加的语言配置是否已经存在
	 * @param qryMap
	 * @return
	 */
	public boolean isRepeat(Map<String, String> qryMap){
		//TODO 代码暂缺
		return false;
	}
	
	/**
	 * 初始化语言配置
	 */
	public void initLanCfg(){
		if(!Fun.eqNull(SysInfoCache.getInstance().getLanMap())){
			SysInfoCache.getInstance().getLanMap().clear();
		}
		List<SysLanguageCfg> list = ArgsTool.getList(SysLanguageCfg.dao, "select PK_LANGUAGE_CFG,CN,EN from sys_language_cfg where IS_VALID=1", null);
		Map<String, SysLanguageCfg> lanMap = new HashMap<String, SysLanguageCfg>();
		for (SysLanguageCfg item : list) {
			lanMap.put(item.getCN(), item);
			logger.info(item.toJson());
		}
		SysInfoCache.getInstance().setLanMap(lanMap);
	}
}

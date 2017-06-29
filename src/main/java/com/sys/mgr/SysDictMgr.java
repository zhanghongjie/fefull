package com.sys.mgr;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.biz.model.SysDict;
import com.frame.utils.ArgsTool;
import com.frame.utils.Fun;
import com.jfinal.plugin.activerecord.Record;
import com.sys.bean.ReBean;
import com.sys.cache.SysInfoCache;

public class SysDictMgr {
	
	public static final Logger logger = LoggerFactory.getLogger(SysDictMgr.class);
	
	private SysDictMgr() {

	}

	public static SysDictMgr getInstance() {
		return SysDictMgrHolder.instance;
	}

	private static class SysDictMgrHolder {
		private static SysDictMgr instance = new SysDictMgr();
	}
	
	/**
	 * 分页查询
	 * @return
	 */
	public ReBean<Record> getPage(Map<String, String> mQryMap){
		ReBean<Record> reBean = null;
		StringBuilder sb = new StringBuilder("select * from sys_dict order by PK_DICT desc");
		if (!Fun.eqNull(mQryMap)) {
			reBean = ArgsTool.getPageObj(this.reWhereSql(mQryMap,sb), mQryMap);
		}
		return reBean;
	}
	
	/**
	 * 条件拼接
	 * @param mQryMap
	 */
	private String reWhereSql(Map<String, String> mQryMap,StringBuilder sb){
		StringBuilder msb = new StringBuilder();
		msb.append(" and IS_VALID=1 ");
		return ArgsTool.reSql(sb, msb);
	}
	
	/**
	 * 判断添加的语言配置是否已经存在
	 * @param qryMap
	 * @return
	 */
	public boolean isRepeat(Map<String, String> qryMap){
		StringBuilder sb = new StringBuilder("select 1 from sys_dict where DICT_KEY=@DICT_KEY  and DICT_TYPE=@DICT_TYPE");
		if(!Fun.eqNull(qryMap.get("PK_DICT"))){
			sb.append(" and PK_DICT!=@PK_DICT");
		}
		Record record = ArgsTool.getObj(sb.toString(), qryMap);
		if(!Fun.eqNull(record)){
			return true;
		}
		return false;
	}
	
	/**
	 * 初始化维表配置
	 */
	public void initDimTable(){
		if(!Fun.eqNull(SysInfoCache.getInstance().getDimMap())){
			SysInfoCache.getInstance().getDimMap().clear();
		}
		List<SysDict> list = ArgsTool.getList(SysDict.dao, "SELECT * FROM   `sys_dict` t ORDER BY t.`PK_DICT` ,t.`SORT_ID` DESC", null);
		Map<String, SysDict> dimMap = new HashMap<String, SysDict>();
		for (SysDict item : list) {
			dimMap.put(item.getDictKey(), item);
			logger.info(item.toJson());
		}
		SysInfoCache.getInstance().setDimMap(dimMap);
	}
	
	public SysDict getObj4Code(Map<String,String> mQryMap){
		return ArgsTool.getObj(SysDict.dao, "select * from sys_dict", mQryMap);
	}
	public String getVal4Code(String code){
		String re = null;
		if(!Fun.eqNull(code)){
			SysDict st = this.getObj4Code(code);
			if(!Fun.eqNull(st)){
				re =  st.getDictValue();
			}
		}
		return re;
	}
	public SysDict getObj4Code(String code){
		SysDict re = null;
		if(!Fun.eqNull(code)){
			re = SysDict.dao.findFirst("select * from sys_dict where DICT_KEY = ?",code);
		}
		return re;
	}
}

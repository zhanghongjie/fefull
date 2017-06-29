package com.sys.mgr;

import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.frame.utils.ArgsTool;
import com.frame.utils.Fun;
import com.jfinal.plugin.activerecord.Record;
import com.sys.bean.ReBean;

public class SysDimTableMgr {
	
	public static final Logger logger = LoggerFactory.getLogger(SysDimTableMgr.class);
	
	private SysDimTableMgr() {

	}

	public static SysDimTableMgr getInstance() {
		return SysDimTableMgrHolder.instance;
	}

	private static class SysDimTableMgrHolder {
		private static SysDimTableMgr instance = new SysDimTableMgr();
	}
	
	/**
	 * 分页查询
	 * @return
	 */
	public ReBean<Record> getPage(Map<String, String> mQryMap){
		ReBean<Record> reBean = null;
		StringBuilder sb = new StringBuilder("select * from sys_dim_table order by PK_DIM_TABLE desc");
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
		return ArgsTool.reSql(sb, msb);
	}
	
	/**
	 * 判断添加的记录是否已经存在
	 * @param qryMap
	 * @return
	 */
	public boolean isRepeat(Map<String, String> qryMap){
		
		return false;
	}
}

package com.sys.mgr;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.biz.model.SysArea;
import com.frame.utils.ArgsTool;
import com.frame.utils.Fun;
import com.jfinal.plugin.activerecord.Record;
import com.sys.bean.ReBean;

public class SysAreaMgr {

	public static final Logger logger = LoggerFactory
			.getLogger(SysAreaMgr.class);

	private SysAreaMgr() {

	}

	public static SysAreaMgr getInstance() {
		return SysAreaMgrHolder.instance;
	}

	private static class SysAreaMgrHolder {
		private static SysAreaMgr instance = new SysAreaMgr();
	}

	/**
	 * 分页查询
	 * 
	 * @return
	 */
	public ReBean<Record> getPage(Map<String, String> mQryMap) {
		ReBean<Record> reBean = null;
		StringBuilder sb = new StringBuilder(
				"select * from sys_area order by PK_AREA desc");
		if (!Fun.eqNull(mQryMap)) {
			reBean = ArgsTool.getPageObj(this.reWhereSql(mQryMap, sb), mQryMap);
		}
		return reBean;
	}

	/**
	 * 条件拼接
	 * 
	 * @param mQryMap
	 */
	private String reWhereSql(Map<String, String> mQryMap, StringBuilder sb) {
		StringBuilder msb = new StringBuilder();
		msb.append(" and IS_VALID=1 ");
		return ArgsTool.reSql(sb, msb);
	}

	/**
	 * 判断添加的记录是否已经存在
	 * 
	 * @param qryMap
	 * @return
	 */
	public boolean isRepeat(Map<String, String> qryMap) {
		String sql = "select 1 from sys_area where 1=1";
		if (!Fun.eqNull(qryMap.get("SPONSOR_NAME"))) {
			sql += " and SPONSOR_NAME=@SPONSOR_NAME";
		}
		if (!Fun.eqNull(qryMap.get("PK_AREA"))) {
			sql += " and PK_AREA!=@PK_AREA";
		}
		Record record = ArgsTool.getObj(sql, qryMap);
		if (!Fun.eqNull(record)) {
			return true;
		}
		return false;
	}

	/**
	 * 根据叶子节点取地区信息
	 * @param id
	 * @return
	 */
	public String getArea4Leaf(int id) {
		String ids = this.getAreaIds4Leaf(id, new StringBuilder());
		if (ids.length() > 1) {
			ids = ids.substring(0, ids.length() - 1);
		}
		if(Fun.eqNull(ids)){
			return "";
		}
		List<SysArea> list = SysArea.dao
				.find("select * from sys_area where PK_AREA in (" + ids	+ ") order by ID asc");
		StringBuilder sb = new StringBuilder();
		for (SysArea sysArea : list) {
			sb.append(sysArea.getNAME());
		}
		return sb.toString();
	}

	public String getAreaIds4Leaf(int id, StringBuilder msb) {
		SysArea sysArea = SysArea.dao.findFirst("select * from sys_area where ID =?",id);
		if (Fun.eqNull(sysArea) || id==0) {
			return "";
		}
		msb.append(this.getAreaIds4Leaf(sysArea.getPID(), msb));
		msb.append(sysArea.getPkArea()).append(",");
		return msb.toString();
	}
}

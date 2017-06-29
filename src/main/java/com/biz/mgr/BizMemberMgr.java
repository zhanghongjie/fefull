package com.biz.mgr;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.biz.model.BizMember;
import com.frame.utils.ArgsTool;
import com.frame.utils.Fun;
import com.jfinal.plugin.activerecord.Record;
import com.portal.dto.request.LoginRequest;
import com.sys.bean.ReBean;

public class BizMemberMgr {
	
	public static final Logger logger = LoggerFactory.getLogger(BizMemberMgr.class);
	
	private BizMemberMgr() {

	}

	public static BizMemberMgr getInstance() {
		return BizMemberMgrHolder.instance;
	}

	private static class BizMemberMgrHolder {
		private static BizMemberMgr instance = new BizMemberMgr();
	}
	
	/**
	 * 分页查询
	 * @return
	 */
	public ReBean<Record> getPage(Map<String, String> mQryMap){
		ReBean<Record> reBean = null;
		StringBuilder sb = new StringBuilder("select * from biz_member order by PK_MEMBER desc");
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
	 * 判断添加的记录是否已经存在
	 * @param qryMap
	 * @return
	 */
	public boolean isRepeat(Map<String, String> qryMap){
		StringBuilder msb = new StringBuilder("select 1 from biz_member where 1=1");
		Boolean bool = true;
		if(!Fun.eqNull(qryMap.get("PK_MEMBER"))){
			msb.append(" and PK_MEMBER!=@PK_MEMBER");
			bool = false;
		}
		if(bool){
			msb.append(" and 1!=1");
		}
		Record record = ArgsTool.getObj(msb.toString(), qryMap);
		if(!Fun.eqNull(record)){
			return true;
		}
		return false;
	}

	public BizMember getBizMemberInfo(LoginRequest loginRequest) {
		return BizMember.dao.findFirst("select * from biz_member where login_name = ? and login_pwd = ?", 
				loginRequest.getUserName(), loginRequest.getPassword());
	}
}

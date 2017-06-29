package com.sys.mgr;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.biz.model.SysRole;
import com.biz.model.SysUser;
import com.frame.constants.Constants;
import com.frame.utils.ArgsTool;
import com.frame.utils.Fun;
import com.frame.utils.MD5;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.sys.bean.ReBean;
import com.sys.bean.UserInfoBean;


public class UserMgr {
	private UserMgr() {

	}
	public static UserMgr getInstance() {
		return KstAdminUserMgrHolder.instance;
	}

	private static class KstAdminUserMgrHolder {
		private static UserMgr instance = new UserMgr();
	}
	/**
	 * 取得用户信息-分页查询
	 * 
	 * @param qryMap
	 * @return
	 */
	public ReBean<Record> getPage(Map<String, String> mQryMap) {
		ReBean<Record> reBean = null;
		StringBuilder sb = new StringBuilder("SELECT *  FROM sys_user and order by PK_USER desc");
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
		if(!Fun.eqNull(mQryMap.get("userName"))){
			msb.append(" and USER_NAME like @userName ");
		}
		if(!Fun.eqNull(mQryMap.get("realName"))){
			msb.append(" and REAL_NAME like @realName ");
		}
		if(!Fun.eqNull(mQryMap.get("roleStatus"))){
			msb.append(" and ROLE_STATUS=@roleStatus");
		}
		msb.append(" and IS_VALID=1 and STATUS=1");
		return ArgsTool.reSql(sb, msb);
	}
	
	/**
	 * 是否存在重名与重复手机号
	 * @param qryMap
	 * @return
	 */
	public String isRepeat(Map<String, String> mQryMap) {
		StringBuilder msb = new StringBuilder();
		StringBuilder sb = new StringBuilder("select * from sys_user");
		
		String re = " and (&USER_NAME or &PHONE)";
		
		if(!Fun.eqNull(mQryMap.get("USER_NAME"))){
			re = re.replace("&USER_NAME", " USER_NAME = @USER_NAME ");
		}else{
			re = re.replace("&USER_NAME", " ");
		}
		if(!Fun.eqNull(mQryMap.get("PHONE"))){
			re = re.replace("&PHONE", " PHONE = @PHONE ");
		}else{
			re = re.replace("&PHONE", " ");
		}
		if(Fun.eqNull(mQryMap.get("USER_NAME")) || Fun.eqNull(mQryMap.get("PHONE"))){
			re = re.replace("or", " ");
		}
		if(!Fun.eqNull(mQryMap.get("USER_NAME")) || !Fun.eqNull(mQryMap.get("userName")) || !Fun.eqNull(mQryMap.get("PHONE"))){
			msb.append(re);
		}
		if(!Fun.eqNull(mQryMap.get("PK_USER"))){
			msb.append(" and PK_USER != @PK_USER ");
		}
		msb.append(" and IS_VALID = 1");
		String sql = ArgsTool.reSql(sb, msb);
		
		SysUser user =  ArgsTool.getObj(SysUser.dao, sql, mQryMap);
		return(user==null? Constants.SUCCESS : Constants.REPEAT);
	}
	/**
	 * 更新用户密码
	 * @param qryMap
	 * @return
	 */
	public int upUserById(Map<String, String> qryMap) {
		if (!Fun.eqNull(qryMap)){
			Db.update("update sys_user set PASSWORD = '"+MD5.encrypt(qryMap.get("userPass"))+"'where PK_USER="+qryMap.get("pkUser"));
		}
		return 1;
	}
	/**
	 * 取得用户信息
	 * @param mQryMap
	 * @return
	 */
	public UserInfoBean getUserInfo(Map<String, String> mQryMap) {
		UserInfoBean userInfoBean = null;
		if (!Fun.eqNull(mQryMap)) {
			if(!Fun.eqNull(mQryMap.get("userPass"))){
				mQryMap.put("userPass", MD5.encrypt(mQryMap.get("userPass")));
			}
			SysUser sysUser = ArgsTool.getObj(SysUser.dao, "select * from sys_user where IS_VALID=1 and STATUS=1 and USER_NAME=@userName and PASSWORD=@userPass", mQryMap);
			if(!Fun.eqNull(sysUser)){
				userInfoBean = new UserInfoBean();
				userInfoBean.setSysUser(sysUser);
				Map<String, String> qryMap = new HashMap<String, String>();
				qryMap.put("fkUser", userInfoBean.getSysUser().getPkUser()+"");
				SysRole sysRole = ArgsTool.getObj(SysRole.dao, "SELECT a.* FROM `sys_role` a LEFT JOIN sys_user_role b ON a.`PK_ROLE` = b.`FK_ROLE` WHERE b.`FK_USER` = @fkUser ", qryMap);
				userInfoBean.setSysRole(sysRole);
				//更新最后登录时间
				sysUser.setLastLoginTime(new Date());
				sysUser.update();
			}
			
		}
		return userInfoBean;
	}
	/**
	 * 设置用户信息
	 * @param mQryMap
	 * @return
	 */
	public int setUserInfo(Map<String, String> mQryMap) {
		/*if (!Fun.eqNull(mQryMap)) {
			TmUserModel.dao.findById(mQryMap.get("userId"))
				.set("user_name",mQryMap.get("user_name"))
				.set("true_name",mQryMap.get("true_name"))
				.set("job_id",mQryMap.get("job_id"))
				.set("user_card_id",mQryMap.get("user_card_id"))
				.set("user_age",mQryMap.get("user_age"))
				.set("user_email",mQryMap.get("user_email"))
				.set("user_phone",mQryMap.get("user_phone"))
				.set("user_sex",mQryMap.get("user_sex"))
				.set("remind_type",mQryMap.get("remind_type"))
				.update();
			return 0;
		}*/
		return 1;
	}
}

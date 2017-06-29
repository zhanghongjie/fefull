package com.sys.controller;

import com.biz.model.SysUser;
import com.frame.constants.Constants;
import com.frame.utils.MD5;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.sys.bean.ReBean;
import com.sys.cache.SysInfoCache;
import com.sys.interceptor.AuthorityCharacterITR;

/**
 * IndexController
 */
@Before(AuthorityCharacterITR.class)
public class IndexController extends Controller {
	//private Map<String,String> qryMap;
	public void index() {
		render("/manage/index.html");
	}
	public void top() {
		render("/manage/top.html");
	}
	public void right() {
		render("/manage/right.html");
	}
	public void left() {
		setAttr("menuList",SysInfoCache.getInstance().getUserInfoBean(getRequest()).getMenuList());
		render("/manage/left.html");
	}
	public void center() {
		render("/manage/center.html");
	}
	
	public void modifyPwdPage(){
		render("/manage/sys/modify_pwd.html");
	}
	
	public void doModifyPwd(){
		ReBean<SysUser> reBean = new ReBean<SysUser>();
		String oldPwd = getPara("oldPwd");
		String newPwd = getPara("newPwd");
		String confirmNewPwd = getPara("confirmNewPwd");
		
		SysUser sysUser = SysInfoCache.getInstance().getUserInfoBean(getRequest()).getSysUser(); 
		
		if(!newPwd.equals(confirmNewPwd)){
			reBean.setFlag(Constants.REPEAT);
		}
		if(!(MD5.encrypt(oldPwd)).equals(sysUser.getPASSWORD())){
			reBean.setFlag(Constants.ERROR);
		}else{
			int result = Db.update("update sys_user set PASSWORD = ? where PK_USER= ?", MD5.encrypt(newPwd), sysUser.getPkUser());
			if(result == 1){
				reBean.setFlag(Constants.SUCCESS);
			}
		}
		
		reBean.setMsg("");
		renderJson(reBean);
	}
}
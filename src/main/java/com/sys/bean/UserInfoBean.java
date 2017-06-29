package com.sys.bean;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.biz.model.SysMenu;
import com.biz.model.SysRole;
import com.biz.model.SysUser;
import com.sys.mgr.MenuMgr;

public class UserInfoBean{
	private SysUser sysUser;
	private SysRole sysRole;
	/**
	 * 用户拥有的菜单（用于权限判断）
	 */
	private Map<String,String> menu;
	/**
	 * 用户拥有的菜单（用于展示）
	 */
	private List<List<SysMenu>> menuList;
	/**
	 * 登录权限标识
	 */
	private String vcode;
	
	
	/**
	 * 初始化用户菜单
	 * @param pkUser
	 */
	public void initMenu(UserInfoBean userInfoBean,HttpServletRequest req){
		MenuMgr.getInstance().getUserMenu4Class(userInfoBean,req);
	}
	
	public SysUser getSysUser() {
		return sysUser;
	}
	public void setSysUser(SysUser sysUser) {
		this.sysUser = sysUser;
	}
	public SysRole getSysRole() {
		return sysRole;
	}
	public void setSysRole(SysRole sysRole) {
		this.sysRole = sysRole;
	}
	public Map<String, String> getMenu() {
		return menu;
	}
	public void setMenu(Map<String, String> menu) {
		this.menu = menu;
	}

	public List<List<SysMenu>> getMenuList() {
		return menuList;
	}

	public void setMenuList(List<List<SysMenu>> menuList) {
		this.menuList = menuList;
	}

	public String getVcode() {
		return vcode;
	}

	public void setVcode(String vcode) {
		this.vcode = vcode;
	}
	
}

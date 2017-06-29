package com.sys.cache;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.beetl.core.GroupTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.biz.model.BizMember;
import com.biz.model.SysDict;
import com.biz.model.SysLanguageCfg;
import com.biz.model.SysMenu;
import com.sys.bean.UserInfoBean;
/**
 * 业务信息缓存
 * @author sos
 *
 */
public class SysInfoCache {
	
	private static final Logger logger = LoggerFactory.getLogger(SysInfoCache.class);
	
	/**
	 * 系统使用模板变量
	 */
	private GroupTemplate groupTemplate;
	
	/**
	 * 中文字符串做为key
	 */
	private Map<String,SysLanguageCfg> lanMap;
	
	/**
	 * 维表code做key
	 */
	private Map<String,SysDict> dimMap;
	/**
	 * 所有菜单
	 */
	private List<List<SysMenu>> allMenuList;
	
	private HttpSession session;
	
	private SysInfoCache() {

	}

	public static SysInfoCache getInstance() {
		return AppInfoHolder.instance;
	}

	private static class AppInfoHolder {
		private static SysInfoCache instance = new SysInfoCache();
	}

	public HttpSession getSession(HttpServletRequest req) {
		return req.getSession();
	}

	public GroupTemplate getGroupTemplate() {
		return groupTemplate;
	}

	public void setGroupTemplate(GroupTemplate groupTemplate) {
		this.groupTemplate = groupTemplate;
	}

	public UserInfoBean getUserInfoBean(HttpServletRequest req) {
		return (UserInfoBean)req.getSession().getAttribute("userInfoBean");
	}
	
	public void setUserInfoBean(UserInfoBean userInfoBean,HttpServletRequest req) {
		logger.info("------------设置用户信息------------");
		req.getSession().setAttribute("userInfoBean", userInfoBean);
	}
	
	public Map<String, SysLanguageCfg> getLanMap() {
		return lanMap;
	}

	public void setLanMap(Map<String, SysLanguageCfg> lanMap) {
		this.lanMap = lanMap;
	}

	public Map<String, SysDict> getDimMap() {
		return dimMap;
	}

	public void setDimMap(Map<String, SysDict> dimMap) {
		this.dimMap = dimMap;
	}

	public List<List<SysMenu>> getAllMenuList() {
		return allMenuList;
	}

	public void setAllMenuList(List<List<SysMenu>> allMenuList) {
		this.allMenuList = allMenuList;
	}

	public HttpSession getSession() {
		return session;
	}

	public void setSession(HttpSession session) {
		this.session = session;
	}

	public BizMember getBizMember(HttpServletRequest req) {
		return (BizMember)req.getSession().getAttribute("bizMember");
	}

	public void setBizMember(BizMember bizMember,HttpServletRequest req) {
		req.getSession().setAttribute("bizMember", bizMember);
	}
	
}
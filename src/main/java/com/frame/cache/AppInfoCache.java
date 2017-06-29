package com.frame.cache;

import org.beetl.core.GroupTemplate;
/**
 * 系统全局信息
 * @author Administrator
 *
 */
public class AppInfoCache {
	/**
	 * 系统使用模板变量
	 */
	private GroupTemplate groupTemplate;
	
	private AppInfoCache() {

	}

	public static AppInfoCache getInstance() {
		return AppInfoHolder.instance;
	}

	private static class AppInfoHolder {
		private static AppInfoCache instance = new AppInfoCache();
	}

	public GroupTemplate getGroupTemplate() {
		return groupTemplate;
	}

	public void setGroupTemplate(GroupTemplate groupTemplate) {
		this.groupTemplate = groupTemplate;
	}
}
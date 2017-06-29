package com.frame.beetl.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.beetl.core.GroupTemplate;
import org.beetl.ext.web.SimpleCrossFilter;

import com.frame.cache.AppInfoCache;

/**
 * @Title: HTMLFilter.java
 * @Package org.whale.beetl.filter
 * @Description: 用于直接Web中运行Beetl模板
 * @author sos
 * @date 2015�?0�?6�?下午3:50:09
 * @version V1.0
 */
public class HTMLFilter extends SimpleCrossFilter {

	@Override
	public void init(FilterConfig arg0) throws ServletException {
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		//HttpServletRequest req = (HttpServletRequest)request;
		response.setContentType("text/html;charset=utf-8");
		super.doFilter(request, response, chain);
	}

	@Override
	protected GroupTemplate getGroupTemplate() {
		return AppInfoCache.getInstance().getGroupTemplate();
	}

}

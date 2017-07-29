package com.portal.controller;

import java.util.List;
import java.util.Map;

import com.biz.mgr.BizNavigatorMgr;
import com.biz.model.BizMember;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.portal.common.Response;
import com.portal.common.ThreadContext;
import com.portal.controller.annotation.PermessionLimit;
import com.portal.controller.interceptor.PortalInterceptor;
import com.portal.dto.CategoryNavigator;

@Before(PortalInterceptor.class)
public class NavigatorController extends Controller{
	
	/**
	 * 获取系统的导航数据
	 * @return
	 */
	public Response system(){
		List<CategoryNavigator> list = BizNavigatorMgr.getInstance().getSystemNavigator();
		return new Response().success(list);
	}
	
	/**
	 * 获取用户个人的导航数据
	 * @return
	 */
	@PermessionLimit
	public Response member(){
		BizMember bizMember = (BizMember)ThreadContext.getContext().get(ThreadContext.KEY_USER_CONTEXT);
		Map<String, List<CategoryNavigator>> retMap = BizNavigatorMgr.getInstance().getMemberNavigator(bizMember.getPkMember());
		return new Response().success(retMap);
	}

}

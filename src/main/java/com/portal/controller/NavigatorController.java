package com.portal.controller;

import java.util.List;

import com.biz.mgr.BizNavigatorMgr;
import com.biz.model.BizMember;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.portal.common.Response;
import com.portal.common.ThreadContext;
import com.portal.controller.interceptor.PortalInterceptor;
import com.portal.dto.CategoryNavigator;

@Before(PortalInterceptor.class)
public class NavigatorController extends Controller{
	
	/**
	 * 返回系统分类
	 * 判断当前用户是否登录来获取收藏字段
	 * @return
	 */
	public Response sys(){
		BizMember bizMember = (BizMember)ThreadContext.getContext().get(ThreadContext.KEY_USER_CONTEXT);
		List<CategoryNavigator> list = BizNavigatorMgr.getInstance().getSysNavigator(bizMember == null ? null : bizMember.getPkMember());
		
		return new Response().success(list);
	}

}

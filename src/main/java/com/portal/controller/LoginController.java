package com.portal.controller;

import com.biz.mgr.BizMemberMgr;
import com.biz.model.BizMember;
import com.frame.utils.Fun;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.portal.common.Constant;
import com.portal.common.RespResultEnum;
import com.portal.common.Response;
import com.portal.controller.annotation.PermessionLimit;
import com.portal.controller.annotation.RequestJsonClass;
import com.portal.controller.interceptor.PortalInterceptor;
import com.portal.dto.Member;
import com.portal.dto.request.LoginRequest;
import com.portal.security.TokenService;

/**
 * 前端用户登录
 */
@Before(PortalInterceptor.class)
public class LoginController extends Controller{
	
	/**
	 * 登录验证
	 * @return
	 */
	@PermessionLimit(limit=false)
	@RequestJsonClass(cls=LoginRequest.class)
	public Response verify(){
		LoginRequest loginRequest = getAttr(Constant.REQUEST_PARAM);
		if(Fun.eqNull(loginRequest) || Fun.eqNull(loginRequest.getUserName()) || Fun.eqNull(loginRequest.getPassword())){
			return new Response().failure(RespResultEnum.PARAMETER_ERR);
		}
		BizMember bizMember = BizMemberMgr.getInstance().getBizMemberInfo(loginRequest);
		if(null == bizMember){
			return new Response().failure(RespResultEnum.LOGIN_ERR);
		}
		Member member = new Member().wrapper(bizMember);
		TokenService.getInstance().put(member.getAccessToken(), bizMember);
		return new Response().success(member);
	}
	
}

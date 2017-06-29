package com.sys.interceptor;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.biz.model.SysUser;
import com.frame.utils.Fun;
import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.sys.cache.SysInfoCache;

public class AuthorityCharacterITR implements Interceptor {

	public static final Logger log = LoggerFactory.getLogger(AuthorityCharacterITR.class);

	@Override
	public void intercept(Invocation ai) {
		log.debug("------------登录权限判断------------");
		HttpServletRequest request = ai.getController().getRequest();
		HttpServletResponse response = ai.getController().getResponse();
		if (Fun.eqNull(AppWebFilter.doFilter(request))) {
			try {
				response.sendRedirect(request.getContextPath()+"/login");
			} catch (IOException e) {
				e.printStackTrace();
			} 
		}else{
			//1.设置菜单名
			String menuId = request.getParameter("menuId");
			if(!Fun.eqNull(menuId) && !Fun.eqNull(SysInfoCache.getInstance().getUserInfoBean(request))){
				request.setAttribute("_menuName", SysInfoCache.getInstance().getUserInfoBean(request).getMenu().get(menuId));
			}
			//2.设置公用属性
			this.setComm(request);
		}
		ai.invoke();
	}
	
	
	/**
	 * 设置公用属性
	 * @param request
	 */
	private void setComm(HttpServletRequest request){
		SysUser sysUser = SysInfoCache.getInstance().getUserInfoBean(request).getSysUser();
		request.setAttribute("userId", sysUser.getPkUser());
		request.setAttribute("reUserObj", SysInfoCache.getInstance().getUserInfoBean(request));
		/*//默认英文
		int lanType = SysEnum.LAN_CFG.EN.getValue();
		if(!Fun.eqNull(sysUser.getLanType())){
			lanType = sysUser.getLanType();
		}
		if(lanType == SysEnum.LAN_CFG.EN.getValue()){
			request.setAttribute("_my97lanCfg", "lang:'en'");
		}else if(lanType == SysEnum.LAN_CFG.AR_SA.getValue()){
		}else if(lanType == SysEnum.LAN_CFG.CN.getValue()){
			request.setAttribute("_my97lanCfg", "lang:'zh-cn'");
		}*/
		request.setAttribute("_my97lanCfg", "lang:'zh-cn'");
	}
}

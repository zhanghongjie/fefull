package com.sys.interceptor;

import javax.servlet.http.HttpServletRequest;

import com.frame.constants.Constants;
import com.frame.utils.Fun;
import com.sys.bean.UserInfoBean;
import com.sys.cache.SysInfoCache;

public class AppWebFilter {
    /**
     * @param request
     * @return
     */
    public static UserInfoBean doFilter(HttpServletRequest request) {
    	if(AppWebFilter.filterWhitelist(request.getRequestURL().toString())){
    		//qryMap = ArgsTool.getParameterMap(request.getParameterMap());
    		SysInfoCache.getInstance().setSession(request.getSession());
    		return AppWebFilter.doFilter2(request);
    	}
    	return null;
    }
    
    public static UserInfoBean doFilter2(HttpServletRequest request) {
    	UserInfoBean userInfoBean = SysInfoCache.getInstance().getUserInfoBean(request);
    	
    	if(!Fun.eqNull(userInfoBean) && userInfoBean.getVcode().equals(request.getParameter("vcode"))){
    		return userInfoBean;
    	}
    	return null;
    }
    
    private static boolean filterWhitelist(String aimUrl){
    	if(!Fun.eqNull(aimUrl)){
    		aimUrl =aimUrl.substring(aimUrl.lastIndexOf("/"), aimUrl.length());
    		if(Fun.eqNull(aimUrl)){
    			return true;
    		}
    	}
    	for(String url : Constants.WHITE_LIST){
    		if(aimUrl.indexOf(url) >= 0){
    			return false;
    		}
    	}
    	return true;
    }
}

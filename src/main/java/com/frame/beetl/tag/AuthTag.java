package com.frame.beetl.tag;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.beetl.core.GeneralVarTagBinding;

/**   
* @Title: AuthTag.java 
* @Package org.whale.beetl.tag 
* @Description: 权限控件
* @version V1.0   
*/
public class AuthTag extends GeneralVarTagBinding  {
	
	@Override
	public void render() {
		Object authCodeObj = this.getAttributeValue("authCode");
		if(authCodeObj != null) {
			String authCode = authCodeObj.toString();
			HttpServletRequest request = (HttpServletRequest)this.ctx.getGlobal("request");
			Object authCodesObj = request.getSession().getAttribute("USER_AUTH_CODES");
			if(authCodesObj != null) {
				Map<String, Object> authCodesMap = (Map<String, Object>)authCodesObj;
				Object obj = authCodesMap.get(authCode);
				if(obj != null) {
					doBodyRender();
				}
			}
		}
	}
}

package com.portal.controller.interceptor;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.frame.exception.NotLoginException;
import com.frame.utils.Fun;
import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.portal.common.Constant;
import com.portal.common.RespResultEnum;
import com.portal.common.Response;
import com.portal.common.ThreadContext;
import com.portal.controller.annotation.PermessionLimit;
import com.portal.controller.annotation.RequestJsonClass;
import com.portal.security.TokenService;

/**
 * 作用： 1、前端用户登录（根据url来过滤判断token） 2、日志/异常处理 3、回报构造
 */
public class PortalInterceptor implements Interceptor {

	public static final Logger log = LoggerFactory.getLogger(PortalInterceptor.class);

	public static final String AUTHORIZATION = "Authorization";
	
	@Override
	public void intercept(Invocation ai) {
		HttpServletRequest httpRequest = ai.getController().getRequest();
		HttpServletResponse httpResponse = ai.getController().getResponse();
//		String uri = httpRequest.getRequestURI().substring(httpRequest.getContextPath().length());
		
		ThreadContext.getContext().put(ThreadContext.KEY_REQUEST, httpRequest);
		ThreadContext.getContext().put(ThreadContext.KEY_RESPONSE, httpResponse);
		ThreadContext.getContext().put(ThreadContext.KEY_FROM_WEB, true);
		
		Response response = null;
		try {
			PermessionLimit permession = ai.getMethod().getAnnotation(PermessionLimit.class);
			if (null != permession && permession.limit()) {
				String token = httpRequest.getHeader(AUTHORIZATION);
				if (Fun.eqNull(token) || !TokenService.getInstance().hasToken(token)) 
					throw new NotLoginException("用户未登录");
				// 绑定用户到线程上下文
				ThreadContext.getContext().put(ThreadContext.KEY_USER_CONTEXT, TokenService.getInstance().get(token));
			}
			// 设置请求Json串，并传入方法
			String reqPayload = getRequestPayload(ai.getController().getRequest());
			if(!Fun.eqNull(reqPayload)){
				RequestJsonClass jsonClass = ai.getMethod().getAnnotation(RequestJsonClass.class);
				if(null != jsonClass){
					ai.getController().getRequest().setAttribute(Constant.REQUEST_PARAM, JSON.parseObject(reqPayload, jsonClass.cls()));
				} else{
					ai.getController().getRequest().setAttribute(Constant.REQUEST_PARAM, reqPayload);
				}
			}
			ai.invoke();
			// 获取接口返回值，Response
			response = ai.getReturnValue(); 
		} catch (NotLoginException e) {
			e.printStackTrace();
			response = new Response().failure(RespResultEnum.NOT_LOGIN);
		} catch (Exception e) {
			e.printStackTrace();
			response = new Response().failure(RespResultEnum.SYSTEM_ERR);
		} finally {
			ThreadContext.removeContext();
			ai.getController().renderJson(response);
		}
	}
	
	/**
	 * 原始方式读取请求流
	 * @param req
	 * @return
	 */
	private String getRequestPayload(HttpServletRequest req) {
		StringBuilder sb = new StringBuilder();
		try (BufferedReader reader = req.getReader();) {
			char[] buff = new char[1024];
			int len;
			while ((len = reader.read(buff)) != -1) {
				sb.append(buff, 0, len);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}
}

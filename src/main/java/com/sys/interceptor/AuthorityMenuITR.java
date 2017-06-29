package com.sys.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;

public class AuthorityMenuITR implements Interceptor {
	public static final Logger log = LoggerFactory.getLogger(AuthorityMenuITR.class);
	private String menuName;
	
	
	
	@Override
	public void intercept(Invocation ai) {
		ai.invoke();
	}
}

package com.frame.exception;

import com.frame.exception.BusinessException;

/**
 * 用户未登录异常
 */
public class NotLoginException extends BusinessException {

	private static final long serialVersionUID = 1L;
	
	public NotLoginException(String message) {
		super(message);
	}

}

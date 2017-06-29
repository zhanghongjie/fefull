package com.frame.exception;


/**
 * 调用服务接口异常
 * 
 */
public class CallServiceException extends BusinessException {

	private static final long serialVersionUID = 1L;
	
	public CallServiceException(String message) {
		super(message);
	}

	
	public CallServiceException(String message, Exception e) {
		super(message, e);
	}
}

package com.frame.exception;

/**
 * 基础异常
 *
 */
public class BaseException extends RuntimeException {

	private static final long serialVersionUID = 23231L;
	
	/**
	 * 系统异常
	 */
	public BaseException(String message, Exception cause) {
		super(message, cause);
	}

	/**
	 * 系统异常
	 */
	public BaseException(String message) {
		super(message);
	}
	
	
}

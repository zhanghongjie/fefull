package com.frame.exception;

/**
 * 业务异常
 *
 */
public class BusinessException extends BaseException {

	private static final long serialVersionUID = 23231L;
	
	/**
	 * 业务异常
	 */
	public BusinessException(String message, Exception cause) {
		super(message, cause);
	}

	/**
	 * 业务异常
	 */
	public BusinessException(String message) {
		super(message);
	}
	
	/**
	 * 优化性能
	 * http://www.blogjava.net/stone2083/archive/2010/07/09/325649.html
	 */
	@Override
	public Throwable fillInStackTrace() {
		return this;
	}
}

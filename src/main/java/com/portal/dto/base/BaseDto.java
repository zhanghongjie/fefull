package com.portal.dto.base;

/**
 * 业务对象转化Dto
 *
 * @param <D>
 * @param <B>
 */
public interface BaseDto<B, D> {

	public D wrapper(B d);
	
}

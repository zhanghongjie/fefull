package com.portal.controller.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RequestJsonClass {
	
	/**
	 * 请求request payload对应的实体Bean
	 */
	Class<?> cls() default String.class;

}
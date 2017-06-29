package com.frame.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Title: ValidTools.java
 * @Package com.frame.utils
 * @Description: 校验工具类
 * @author sos
 * @date 2016年9月4日 下午12:13:34
 * @version V1.0
 */
public class ValidTools {
	/**
	 * 手机号校验
	 * @param mobiles
	 * @return boolean
	 */
	public static boolean isMobileNO(String mobiles){  
		Pattern p = Pattern.compile("^((17[0-9])|(13[0-9])|(15[^4,\\D])|(18[0,1,2,3,4,5-9]))\\d{8}$");  
		Matcher m = p.matcher(mobiles);  
		System.out.println(m.matches()+"---");  
		return m.matches();  
	}
	/**
	 * 邮箱校验
	 * @param email
	 * @return boolean
	 */
	public static boolean isEmail(String email){
		Pattern pattern = Pattern.compile("^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$");
		Matcher matcher = pattern.matcher(email);
		System.out.println(matcher.matches());
		return matcher.matches();  
	}
	public static void main(String[] args) {
		System.out.println(ValidTools.isMobileNO("11189194680"));
	}
}

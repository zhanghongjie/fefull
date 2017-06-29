package com.frame.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

/**
 * 返回浏览器类
 * @author lwyx
 *
 */
public class BrowseUtil {

	private final static String IE = "MSIE";
	private final static String MAXTHON = "Maxthon";
	private final static String QQ = "QQBrowser";
	private final static String GREEN = "GreenBrowser";
	private final static String SE360 = "360SE";
	private final static String FIREFOX = "Firefox";
	private final static String OPERA = "Opera";
	private final static String CHROME = "Chrome";
	private final static String SAFARI = "Safari";
	private final static String OTHER = "其它";

	public static String getBrowseType(HttpServletRequest request) {
		return getBrowseType(request.getHeader("user-agent"));
	}
	public static String getBrowseType(String userAgent) {
		if(Fun.eqNull(userAgent)){
			return OTHER;
		}
		if (regex(OPERA, userAgent))
			return OPERA;
		if (regex(CHROME, userAgent))
			return CHROME;
		if (regex(FIREFOX, userAgent))
			return FIREFOX;
		if (regex(SAFARI, userAgent))
			return SAFARI;
		if (regex(SE360, userAgent))
			return SE360;
		if (regex(GREEN, userAgent))
			return GREEN;
		if (regex(QQ, userAgent))
			return QQ;
		if (regex(MAXTHON, userAgent))
			return MAXTHON;
		if (regex(IE, userAgent))
			return IE;
		return OTHER;
	}

	public static boolean regex(String regex, String str) {
		Pattern p = Pattern.compile(regex, Pattern.MULTILINE);
		Matcher m = p.matcher(str);
		return m.find();
	}
}

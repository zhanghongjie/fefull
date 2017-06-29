package com.portal.common;

import java.util.HashMap;
import java.util.Map;

/**
 * 线程上下文
 */
public class ThreadContext {
	
	/**用户上下文 */
	public static final String KEY_USER_CONTEXT = "userContext";
	/**请求对象 */
	public static final String KEY_REQUEST = "request";
	/**响应对象 */
	public static final String KEY_RESPONSE = "response";
	/**是否从页面进入 */
	public static final String KEY_FROM_WEB = "from_web";

	private static final ThreadLocal<ThreadContext> LOCAL = new ThreadLocal<ThreadContext>() {
		@Override
		protected ThreadContext initialValue() {
			return new ThreadContext();
		}
	};
	
	public static ThreadContext getContext() {
	    return LOCAL.get();
	}
	
	public static void removeContext() {
	    LOCAL.remove();
	}
	
	private Map<String, Object> map = new HashMap<String, Object>();
	
	public Map<String, Object> put(String key, Object val){
		map.put(key, val);
		return map;
	}
	
	public Object get(String key){
		return map.get(key);
	}
	
	public String getStr(String key){
		return map.get(key) == null ? null : map.get(key).toString();
	}
	
	public Integer getInteger(String key){
		return map.get(key) == null ? null : Integer.parseInt(map.get(key).toString());
	}
	
	public Long getLong(String key){
		return map.get(key) == null ? null : Long.parseLong(map.get(key).toString());
	}
	
	public Boolean getBoolean(String key){
		return map.get(key) == null ? null : (Boolean)map.get(key);
	}
	
	public void remove(String key){
		map.remove(key);
	}
}

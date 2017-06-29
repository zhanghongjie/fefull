package com.portal.security;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.biz.model.BizMember;

public class TokenService {

	private static final Logger logger = LoggerFactory.getLogger(TokenService.class);
	/** 登录会话数据扫描间隔：5s */
	private static final Long CACHE_SCAN_INTERVAL = Long.valueOf(5 * 1000L);
	/** 默认的会话失效时间：30min*/
	private static final Integer DEFAULT_EXP_TIME = Integer.valueOf(30 * 60);
	
	/**
	 * Token --> MemberMemberCacheEntry 
	 */
	private Map<String, MemberCacheEntry> cache;
	
	private static TokenService tokenService = new TokenService();
	
	public static TokenService getInstance() {
		return tokenService;
	}

	private TokenService() {
		cache = new ConcurrentHashMap<String, MemberCacheEntry>();
		
		Thread thread = new MemberCacheExpScanThread();
		thread.setName("Portal Member Cache扫描线程");
		thread.setDaemon(true);
		thread.start();
		logger.info(">>>>>启动前端用户缓存扫描线程...");
	}

	public void put(String token, BizMember value) {
		logger.info(">>>>>>用户加入缓存 token:[" + token + "]; BizMember[" + value.toString() + "]");
		put(token, value, DEFAULT_EXP_TIME);
	}

	public void put(String token, BizMember value, Integer expTime) {
		this.cache.put(token, new MemberCacheEntry(value, expTime));
	}
	
	public boolean hasToken(String token){
		return this.cache.containsKey(token);
	}
	
	public BizMember get(String token) {
		MemberCacheEntry MemberCacheEntry = (MemberCacheEntry) this.cache.get(token);
		if (MemberCacheEntry == null)
			return null;
		return MemberCacheEntry.getValue();
	}

	public void evict(String token) {
		this.cache.remove(token);
	}

	public Object getNativeCache() {
		return this.cache;
	}
	
	class MemberCacheExpScanThread extends Thread {
		
		MemberCacheExpScanThread() {}

		public void run() {
			List<String> removeKeys = new ArrayList<String>();
			while (true)
				try {
					for (Map.Entry<String, MemberCacheEntry> entry : TokenService.this.cache.entrySet()) {
						if (((MemberCacheEntry) entry.getValue()).isOutOfDate()) {
							removeKeys.add((String) entry.getKey());
						}
					}
					if (removeKeys.size() > 0) {
						for (String key : removeKeys) {
							if (TokenService.logger.isDebugEnabled()) {
								TokenService.logger.debug("PORTAL MEMBER CACHE: 清除过期会话 key="
												+ key
												+ " value = "
												+ TokenService.this.cache.get(key));
							}
							TokenService.this.cache.remove(key);
						}
						removeKeys.clear();
					}
					
					Thread.sleep(TokenService.CACHE_SCAN_INTERVAL.longValue());
				} catch (Exception e) {
					TokenService.logger.error("PORTAL MEMBER CACHE: 清除过期会话出现异常：", e);
				}
		}
	}
}

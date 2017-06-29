package com.portal.security;

import com.biz.model.BizMember;

/**
 * 前端用户登录存放实体
 * 
 * 可以考虑使用Soft Reference，只有当内存不够的时候，才进行回收这类内存，
 * 这样能保证最大限度的使用内存而不引起OutOfMemory
 */
public class MemberCacheEntry {
	
	private BizMember value;
	/**不会过期 */
	private boolean forever = true;
	/**有效时间，单位秒 */
	private int expTime;
	/**最近活动时间戳*/
	private long lastActiveTime;
	
	public MemberCacheEntry(BizMember value){
		this.setVaule(value);
	}
	
	public MemberCacheEntry(BizMember value, Integer expTime){
		if(expTime != null && expTime > 0){
			this.lastActiveTime = System.currentTimeMillis();
			this.expTime = expTime;
			this.forever = false;
		}else{
			this.forever = true;
		}
		this.setVaule(value);
	}
	
	/**
	 * 登录是否过期
	 * @return
	 */
	public boolean isOutOfDate() {
        return !this.forever && (System.currentTimeMillis()-lastActiveTime) >= this.expTime*1000L;
    }

	public BizMember getValue() {
		if(!this.forever){
			lastActiveTime = System.currentTimeMillis();
		}
		if(value == null)
			return null;
		return this.value;
	}
	
	public void setVaule(BizMember value){
		this.value = value;
	}

	public Boolean getForever() {
		return forever;
	}

	public Integer getExpTime() {
		return expTime;
	}

	public long getLastActiveTime() {
		return lastActiveTime;
	}

	@Override
	public String toString() {
		return "MemberCacheEntry [value=" + value 
				+ ", forever=" + forever + ", expTime=" + expTime
				+ ", lastActiveTime=" + lastActiveTime + "]";
	}
}

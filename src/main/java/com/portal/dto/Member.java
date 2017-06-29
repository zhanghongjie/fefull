package com.portal.dto;

import com.biz.model.BizMember;
import com.frame.utils.UUID;
import com.portal.dto.base.BaseDto;

public class Member implements BaseDto<BizMember, Member>{
	
	private Integer userId;
	
	private String userName;
	
	private String userHead;
	
	private String accessToken;
	
	public Member(){}
	
	public Member(Integer userId, String userName, String userHead) {
		this.userId = userId;
		this.userName = userName;
		this.userHead = userHead;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserHead() {
		return userHead;
	}

	public void setUserHead(String userHead) {
		this.userHead = userHead;
	}

	@Override
	public Member wrapper(BizMember d) {
		this.setUserId(d.getPkMember());
		this.setUserName(d.getLoginName());
		this.setUserHead(d.getHeadPortrait());
		this.setAccessToken(UUID.getUUID());
		return this;
	}
	
}

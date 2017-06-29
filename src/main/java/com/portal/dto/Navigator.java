package com.portal.dto;

public class Navigator {

	private Integer navigatorId;
    private String navigatorName;
    private String navigatorUrl;
    private String description;
    private Long isFavor;
    
	public Integer getNavigatorId() {
		return navigatorId;
	}
	public void setNavigatorId(Integer navigatorId) {
		this.navigatorId = navigatorId;
	}
	public String getNavigatorName() {
		return navigatorName;
	}
	public void setNavigatorName(String navigatorName) {
		this.navigatorName = navigatorName;
	}
	public String getNavigatorUrl() {
		return navigatorUrl;
	}
	public void setNavigatorUrl(String navigatorUrl) {
		this.navigatorUrl = navigatorUrl;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Long getIsFavor() {
		return isFavor;
	}
	public void setIsFavor(Long isFavor) {
		this.isFavor = isFavor;
	}
    
}

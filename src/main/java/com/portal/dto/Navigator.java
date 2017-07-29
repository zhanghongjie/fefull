package com.portal.dto;

public class Navigator {

	private Integer navigatorId;
    private String navigatorName;
    private String navigatorUrl;
    private String description;
    private String isFavor;
    private Integer isSystem;
    
	public Integer getIsSystem() {
		return isSystem;
	}
	public void setIsSystem(Integer isSystem) {
		this.isSystem = isSystem;
	}
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
	public String getIsFavor() {
		return isFavor;
	}
	public void setIsFavor(String isFavor) {
		this.isFavor = isFavor;
	}
    
}

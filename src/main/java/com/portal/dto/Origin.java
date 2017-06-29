package com.portal.dto;

public class Origin {
	
	private Integer originId;
	
    private String originName;
    
    public Origin(){
    	
    }
    
    public Origin(Integer originId){
    	this.originId = originId;
    	this.originName = (originId == 0) ? "转载" : "原创";
    }
    
	public Integer getOriginId() {
		return originId;
	}

	public void setOriginId(Integer originId) {
		this.originId = originId;
	}

	public String getOriginName() {
		return originName;
	}

	public void setOriginName(String originName) {
		this.originName = originName;
	}

	@Override
	public String toString() {
		return "Origin [originId=" + originId + ", originName=" + originName
				+ "]";
	}
	
}

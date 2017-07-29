package com.portal.common;

public class FefullEnum {
	
	 public enum IsSystem{
    	YES(1), 
    	NO(0);
    	
    	private int value;
    	
    	IsSystem(int val){
    		this.value=val;
    	}
    	public int getValue() {
    		return value;
    	}  
    }
	
}

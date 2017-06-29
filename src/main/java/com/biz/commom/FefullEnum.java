package com.biz.commom;


/**
 *
 * 业务侧常量定义
 */
public class FefullEnum {
    // 用户状态
    public enum UserStatus {
        // 正常
        NORMAL(1),
        // 异常
        EXCEPTION(0);
        UserStatus(int value) {
            this.value = value;
        }

        private final int value;

        public int getValue() {
            return value;
        }
    }

    public enum MemberStatus{
    	// 1正常，2禁用，3删除
    	NORMAL(1),
    	DISABLED(2),
    	DELETED(3);
    	MemberStatus(int value) {
           this.value = value;
       }

       private final int value;

       public int getValue() {
           return value;
       }
   }
    /**   
     * 短信发送状态
     */
    public enum SmsReType{
    	// 发送成功
    	OK(0),
    	ERROR(-1);
    	SmsReType(int value) {
    		this.value = value;
    	}
    	private final int value;
    	public int getValue() {
    		return value;
    	}
    }
    
    /**
     * 排序方式
     */
    public enum SortOrder{
    	NEWEST(1),
    	HOT(2);
    	SortOrder(int value) {
    		this.value = value;
    	}
    	private final int value;
    	public int getValue() {
    		return value;
    	}
    }
    
}

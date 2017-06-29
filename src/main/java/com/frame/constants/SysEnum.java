package com.frame.constants;

/**
 * 
 * 公用常量
 */
public class SysEnum {
	/**
	 *系统初始化对象类
	 */
	public enum BOOT_TYPE{
		//注解初始化
		ANNOTATION(0),
		//权限系统初始化扫描
		AUTH(1),
		//beetl共享变量初始化
		BEETL_SHARED(2),
		//维表配置
		DIM_TABEL(3),
		//语言配置
		LAN_CFG(4);
		BOOT_TYPE(int value) {
            this.value = value;
        }
		private final int value;
		public int getValue() {
            return value;
        }
    }
	/**
	 *系统提示信息
	 */
	public enum SYS_MSG{
		
		LOGIN_SUCCESS("登录成功！"),
		LOGIN_FAIL("输入的用户名或密码错误！"),
		LOGIN_INVALID("该用户已失效！"),
		LOGIN_TIMEOUT("登入超时，请重新登录！"),
		VERITY_ERROR("验证码输入有误！");
		
		SYS_MSG(String value) {
			this.value = value;
		}
		private final String value;
		public String getValue() {
			return value;
		}
	}
	
	/** 
	 * 系统语言配置  
	*/
	public enum LAN_CFG{
		CN(1),
		EN(2),
		AR_SA(3);
		LAN_CFG(int value) {
			this.value = value;
		}
		private final int value;
		public int getValue() {
			return value;
		}
	}
}

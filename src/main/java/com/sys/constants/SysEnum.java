package com.sys.constants;

/**
 * 系统级枚举类
 */
public final class SysEnum {
	/** 
	 * 数据是否有效
	 */
	public enum IS_VALID{
		YES(1),
		NO(0);
		IS_VALID(int value) {
			this.value = value;
		}
		private final int value;
		public int getValue() {
			return value;
		}
	}
	/**   
	* 用户类型 
	*/
	public enum UserTypeEnum {
		ADMINISTRATOR(0),
		SPONSOR(1);
		
		Integer value;

		private UserTypeEnum(Integer value) {
			this.value = value;
		}

		public Integer getValue() {
			return this.value;
		}

		public void setValue(Integer value) {
			this.value = value;
		}

		public static UserTypeEnum parse(Integer value) {
			for (UserTypeEnum o : values()) {
				if (o.getValue().equals(value)) {
					return o;
				}
			}
			throw new IllegalArgumentException();
		}
	}
}

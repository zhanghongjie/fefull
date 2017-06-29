package com.frame.constants;

public class Constants {
	/**
	 * 为空的整型接口定义
	 */
	public static final String NULL_VALUE = "";
	public static final String[] WHITE_LIST = new String[]{"login"};
	public static final String LOGIN_ERROR = "输入的用户名或密码错误！";
	public static final String LOGIN_INVALID = "该用户已失效！";
	public static final String LOGIN_TIMEOUT = "登入超时，请重新登入!";
	public static final String VERITY_ERROR = "验证码输入有误!";

	public static final String USER_INVALID = "0";
	public static final String USER_VALID = "1";

	public static final String SUCCESS = "1";
	public static final String REPEAT = "-1";
	public static final String WARN = "-2";
	public static final String ERROR = "0";
	public static final String OVER = "-3";

	public static final String NOT_DATA = "3";


	public static final int SELECT = 1;
	public static final int DELETE = 2;
	public static final int UPDATE = 3;
	public static final int INSERT = 4;

	//是否做假删除
	public static final int DATA_NOT_DEL = 0;
	public static final int DATA_DEL = 1;

	public static final String STORE_IMG = ".png";

	public static final String FS = System.getProperty("file.separator");

	public static final String APP_PATH = System.getProperty("user.dir");
	public static final String IMG_PATH = APP_PATH+FS+"tempImg";
	/**
	 * 分页每页显示数量
	 */
	public static final int PAGE_SIZE = 10;
	/**
	 * 校验码
	 */
	public static final String VERITY_CODE_KEY = "VERITY_CODE";
	/**
	 * 文件保存路径
	 */
	public static final String FILE_UPLOAD_PATH = "upload";
	//默认语言配置
	public static final String DEF_LAN_CFG = "lanCfg";
	
	//添加操作（表单提交方式）
	public static final String OPERATE_ADD = "OPERATE_ADD";
	//修改操作（表单提交方式）
	public static final String OPERATE_EDIT = "OPERATE_EDIT";

}

package com.sys.controller;

import java.io.File;

import com.frame.constants.Constants;
import com.frame.utils.Fun;
import com.frame.utils.PropertiesUtil;
import com.jfinal.core.Controller;
import com.jfinal.kit.PathKit;
/**   
* @Title: LoadImgController.java 
* @Package com.sys.controller 
* @Description: 文件下载 
* @author sos
* @date 2016年7月9日 下午5:09:31 
* @version V1.0   
*/
public class LoadImgController extends Controller {
	
	/**
	 * 文件上传路径
	 */
	private static String upPath;
	private static final String DB_CONFIG = PathKit.getRootClassPath()+Constants.FS+ "upConfig.txt";
	public LoadImgController() {
		if(Fun.eqNull(LoadImgController.upPath)){
			upPath = PropertiesUtil.getProperty(DB_CONFIG, "upPath").replace("//", Constants.FS);
		}
	}
	public void index(){
		renderFile(new File((upPath+getPara("filePath")).toString().replace("//","/")));
	}
}

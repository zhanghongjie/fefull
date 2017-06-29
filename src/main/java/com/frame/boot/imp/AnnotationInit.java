package com.frame.boot.imp;

import com.frame.boot.Bootable;
import com.frame.constants.SysEnum;


/**   
* @Title: AnnotationInit.java 
* @Package com.frame.common.boot.impl 
* @Description: 注解初始化
* @author sos
* @date 2016年4月8日 下午11:21:20 
* @version V1.0   
*/
public class AnnotationInit implements Bootable{

	@Override
	public void init() {
		/*// 找中系统中所有的注解类
		//1.找到系统对应于服务器的绝对路径
		String path = SysConstant.APP_PATH;
		//2.遍历出所有java文件
		ArrayList<String> list  = FileUtils.getFiles(path+"\\src\\main\\java\\".replace("\\", SysConstant.FS), ".java");
		for (String string : list) {
			System.out.println(string);
		}*/
	}

	@Override
	public int initOrder() {
		return SysEnum.BOOT_TYPE.ANNOTATION.getValue();
	}
}

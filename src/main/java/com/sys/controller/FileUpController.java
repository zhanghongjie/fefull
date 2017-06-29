package com.sys.controller;

import java.util.ArrayList;
import java.util.List;

import com.frame.constants.Constants;
import com.frame.utils.CompressPic;
import com.frame.utils.DataUtils;
import com.frame.utils.DateUtil;
import com.frame.utils.Fun;
import com.frame.utils.PropertiesUtil;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.kit.PathKit;
import com.jfinal.upload.UploadFile;
import com.sys.bean.ReBean;
import com.sys.interceptor.AuthorityCharacterITR;
/**   
* @Title: FileUpController.java 
* @Package com.sys.controller 
* @Description: 文件上传 
* @author sos
* @date 2016年5月18日 下午3:14:04 
* @version V1.0   
*/
@Before({AuthorityCharacterITR.class})
public class FileUpController extends Controller {
	private ReBean<String>  reBean;
	
	/**
	 * 文件上传路径
	 */
	private static String upPath;
	private static final String DB_CONFIG = PathKit.getRootClassPath()+Constants.FS+ "upConfig.txt";
	
	public void index(){
	}
	
	public FileUpController() {
		if(Fun.eqNull(FileUpController.upPath)){
			upPath = PropertiesUtil.getProperty(DB_CONFIG, "upPath").replace("//", Constants.FS);
		}
	}
	/**
	 * 图片上传
	 */
	public void doUpImg(){
		reBean = new ReBean<String>();
		reBean.setFlag(Constants.ERROR);
		//1.上传本地文件
		UploadFile file = getFile();
		//2.判断文件是否存在
		if(Fun.eqNull(file)){
			reBean.setMsg("上传图片失败！");
			reBean.setFlag(Constants.ERROR);
			renderJson(reBean);
			return;
		}
		String fileName = "upImg"+System.currentTimeMillis()+"."+Fun.getSubString(file.getFileName(), "\\.", false);
		//3.判断文件类型
		if(!Fun.isFileType(file.getFileName(), "png") && !Fun.isFileType(file.getFileName(), "gif") && !Fun.isFileType(file.getFileName(), "jpg")){
			reBean.setMsg("请上传相关格式的图片！");
			reBean.setFlag(Constants.ERROR);
			renderJson(reBean);
			return;
		}
		//4.判断文件是否上传成功
		String webPath = Constants.FS+DateUtil.getCurrDate("yyyyMM")+Constants.FS+DateUtil.getCurrDate("yyyyMMdd");
		/*String newFilePath = file.getFile().getAbsolutePath().replace(file.getFileName(), "")
				+webPath;*/
		String newFilePath = FileUpController.upPath+Constants.FS+webPath;
		boolean bool = DataUtils.move(file.getFile().getAbsolutePath(),newFilePath,fileName);
		if(!bool){
			reBean.setMsg("上传图片失败！");
			reBean.setFlag(Constants.ERROR);
			renderJson(reBean);
			return;
		}
		//5.压缩图片
		CompressPic compressPic =  new CompressPic();
		String miniImgPath = compressPic.compressPic(newFilePath+Constants.FS+fileName);
		//6.返回图片地址
		reBean.setFlag(Constants.SUCCESS);
		List<String> reList = new ArrayList<String>();
		reList.add(("//loadImg?filePath="+webPath+Constants.FS+fileName).replace("//", Constants.FS));
		reList.add(("//loadImg?filePath="+webPath+Constants.FS+miniImgPath).replace("//", Constants.FS));
		reBean.setReList(reList);
		renderJson(reBean);
	}
}

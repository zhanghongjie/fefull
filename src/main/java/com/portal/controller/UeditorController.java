package com.portal.controller;

import java.io.FileInputStream;

import com.frame.constants.Constants;
import com.frame.ueditor.MyActionEnter;
import com.frame.utils.FileUtils;
import com.frame.utils.Fun;
import com.jfinal.core.Controller;
import com.jfinal.kit.PathKit;

public class UeditorController extends Controller{
	
    public void doExec() throws Exception{
    	String configFullPath = PathKit.getRootClassPath()+Constants.FS+ "config.json";
        if ("config".equals(getPara("action"))) {
        	if(!Fun.eqNull(configFullPath)){
        		renderJson(FileUtils.readTextFile(configFullPath));
        	}
            return;
        }
        
        String rs = new MyActionEnter(getRequest(), new FileInputStream(configFullPath)).exec();
        renderJson(rs);
    }
    
}

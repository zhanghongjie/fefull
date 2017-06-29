package com.sys.controller;

import com.frame.constants.Constants;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.sys.bean.ReBean;
import com.sys.interceptor.AuthorityCharacterITR;
import com.sys.mgr.SysDictMgr;
import com.sys.mgr.LanguageCfgMgr;
/**   
* @Title: CacheController.java 
* @Package com.sys.controller 
* @Description: 缓存更新 
* @author sos
* @date 2016年5月13日 下午4:53:55 
* @version V1.0   
*/
@Before({AuthorityCharacterITR.class})
public class CacheController extends Controller {
	private ReBean<String>  reBean;
	public void index(){
		render("/manage/sys/cache.html");
	}
	/**
	 * 更新数据
	 */
	public void update() {
		reBean = new ReBean<String>();
		LanguageCfgMgr.getInstance().initLanCfg();
		SysDictMgr.getInstance().initDimTable();
		reBean.setFlag(Constants.SUCCESS);
		renderJson(reBean);
	}
}

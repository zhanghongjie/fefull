package com.sys.controller;

import java.util.Map;

import com.biz.model.SysLanguageCfg;
import com.frame.constants.Constants;
import com.frame.utils.ArgsTool;
import com.frame.utils.Fun;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.sys.bean.ReBean;
import com.sys.interceptor.AuthorityCharacterITR;
import com.sys.mgr.LanguageCfgMgr;
import com.sys.util.LanCfg;

/**   
* @Title: LanguageCfgController.java 
* @Package com.sys.controller 
* @Description: 语言配置
* @author sos
* @date 2016年5月12日 下午4:29:51 
* @version V1.0   
*/
@Before({AuthorityCharacterITR.class})
public class LanguageCfgController extends Controller {
	private ReBean<SysLanguageCfg>  reBean;
	public void index(){
		render("/manage/sys/languageCfg.html");
	}
	/**
	 * 分页查询lanCfg
	 */
	public void list() {
		renderJson(LanguageCfgMgr.getInstance().getPage(Fun.getPara(getParaMap())));
	}
	
	
	/**
	 * 添加数据
	 */
	public void add() {
		Map<String, String> qryMap = ArgsTool.getParameterMap(getParaMap());
		if(LanguageCfgMgr.getInstance().isRepeat(qryMap)){
			reBean.setFlag(Constants.REPEAT);
			reBean.setMsg(LanCfg.getInstance().get("添加的配置已经存在",getRequest())+"？");
			renderJson(reBean);
		}else{
			reBean = ArgsTool.addObj(new SysLanguageCfg(), qryMap,getRequest());
			reBean.setMsg("");
			reBean.setFlag(Constants.SUCCESS);
			renderJson(reBean);
		}
	}
	/**
	 * 修改数据
	 * 
	 */
	public void edit() {
		reBean = new ReBean<SysLanguageCfg>();
		Map<String, String> qryMap = ArgsTool.getParameterMap(getParaMap());
		//PK_LANGUAGE_CFG 前台页面上传的主键
		String flag = ArgsTool.upObj(SysLanguageCfg.dao, qryMap, "PK_LANGUAGE_CFG",getRequest());
		reBean.setMsg("");
		reBean.setFlag(flag);
		renderJson(reBean);
	}
	/**
	 * 删除数据
	 */
	public void del() {
		reBean = new ReBean<SysLanguageCfg>();
		Map<String, String> qryMap = ArgsTool.getParameterMap(getParaMap());
		if(!Fun.eqNull(qryMap) && qryMap.containsKey("PK_LANGUAGE_CFG")){
			SysLanguageCfg.dao.deleteById(qryMap.get("PK_LANGUAGE_CFG"));
		}
		reBean.setFlag(Constants.SUCCESS);
		renderJson(reBean);
	}
}

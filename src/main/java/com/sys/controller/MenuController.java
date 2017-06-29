package com.sys.controller;

import java.util.Map;

import com.biz.model.SysMenu;
import com.frame.constants.Constants;
import com.frame.utils.ArgsTool;
import com.frame.utils.Fun;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.sys.bean.ReBean;
import com.sys.interceptor.AuthorityCharacterITR;
import com.sys.mgr.MenuMgr;

/**   
* @Title: MenuController.java 
* @Package com.sys.controller 
* @Description: 菜单管理
* @author sos
* @date 2016年5月11日 下午5:24:03 
* @version V1.0   
*/
@Before({AuthorityCharacterITR.class})
public class MenuController extends Controller {
	private ReBean<SysMenu>  reBean;
	public void index(){
		render("/manage/sys/menu.html");
	}
	/**
	 * 分页查询
	 */
	public void list() {
		renderJson(MenuMgr.getInstance().getPage(Fun.getPara(getParaMap())));
	}
	
	/**
	 * 添加数据
	 */
	public void add() {
		Map<String, String> qryMap = ArgsTool.getParameterMap(getParaMap());
		if(MenuMgr.getInstance().isRepeat(qryMap)){
			reBean.setFlag(Constants.REPEAT);
			reBean.setMsg("添加的菜单已经存在!");
			renderJson(reBean);
		}else{
			reBean = ArgsTool.addObj(new SysMenu(), qryMap,getRequest());
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
		reBean = new ReBean<SysMenu>();
		Map<String, String> qryMap = ArgsTool.getParameterMap(getParaMap());
		String flag = ArgsTool.upObj(SysMenu.dao, qryMap, "PK_MENU",getRequest());
		reBean.setMsg("");
		reBean.setFlag(flag);
		renderJson(reBean);
	}
	/**
	 * 删除数据
	 */
	public void del() {
		reBean = new ReBean<SysMenu>();
		Map<String, String> qryMap = ArgsTool.getParameterMap(getParaMap());
		if(!Fun.eqNull(qryMap) && qryMap.containsKey("PK_MENU")){
			SysMenu.dao.deleteById(qryMap.get("PK_MENU"));
		}
		reBean.setFlag(Constants.SUCCESS);
		renderJson(reBean);
	}
}

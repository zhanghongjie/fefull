package com.sys.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.biz.model.SysRole;
import com.frame.constants.Constants;
import com.frame.utils.ArgsTool;
import com.frame.utils.Fun;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.sys.bean.ReBean;
import com.sys.interceptor.AuthorityCharacterITR;
import com.sys.mgr.MenuMgr;
import com.sys.mgr.RoleMgr;

/**   
* @Title: RoleController.java 
* @Package com.sys.controller 
* @Description: 角色管理 
* @author sos
* @date 2016年5月17日 下午2:00:39 
* @version V1.0   
*/
@Before({AuthorityCharacterITR.class})
public class RoleController extends Controller {
	private ReBean<SysRole>  reBean;
	public void index(){
		setAttr("roleMenuList", MenuMgr.getInstance().getAllMenu4Role());
		render("/manage/sys/role.html");
	}
	public void roleAdd(){
		setAttr("menuList", MenuMgr.getInstance().getAllMenu4Class());
		render("/manage/sys/roleAdd.html");
	}
	@Before(Tx.class)
	public void roleAddAjax(){
		Map<String,Object> qryMap = new HashMap<String, Object>();
		qryMap.put("roleName", getPara("roleName"));
		List<SysRole>  tmp = SysRole.dao.find("select 1 from sys_role where role_name =?",getPara("roleName"));
		if(!Fun.eqListNull(tmp)){
			qryMap.put("flag", Constants.REPEAT);
			renderJson(qryMap);
			return;
		}
		//RoleMgr.getInstance().addRoleMenu(qryMap);
		renderJson(RoleMgr.getInstance().addRoleMenu(ArgsTool.getParameterMap(getParaMap()),getRequest()));
	}
	public void roleEdit(){
		Map<String, String> qryMap = ArgsTool.getParameterMap(getParaMap());
		SysRole sysRole = SysRole.dao.findById(getPara("roleId"));
		if(!Fun.eqNull(sysRole)){
			setAttr("roleName", sysRole.getStr("ROLE_NAME"));
			setAttr("roleId", sysRole.get("PK_ROLE"));
			setAttr("roleMenuList", RoleMgr.getInstance().getRoleMenu4Edit(qryMap));
		}
		render("/manage/sys/roleEdit.html");
	}
	@Before(Tx.class)
	public void roleEditAjax(){
		Map<String,String> qryMap = Fun.getPara(getParaMap());
		//qryMap.put("menuList", ArgsTool.reRoleMenuList(getPara("menuList")));
		renderJson(RoleMgr.getInstance().editRoleMenu(qryMap,getRequest()));
	}
	/**
	 * 
	 */
	public void list() {
		/*Map<String, String> qryMap = ArgsTool.getParameterMap(getParaMap());
		ReBean reBean = new ReBean<List<List<VmRoleMenuModel>>>();
		reBean.setFlag(Constants.SUCCESS_FLAG);
		reBean.setReList(RoleMgr.getInstance().getRoleMenu4Class(qryMap));
		renderJson(reBean);*/
	}
	/**
	 * 删锟斤拷锟斤拷锟�
	 */
	public void roleDel() {
		//boolean bool = true;
		Map<String, String> qryMap = ArgsTool.getParameterMap(getParaMap());
		if(!Fun.eqNull(qryMap) && qryMap.containsKey("roleId")){
			RoleMgr.getInstance().delRoleById(qryMap);
		}
		/*reBean = new ReBean<TmDepartModel>();
		if(bool){
			reBean.setMsg("true");
			reBean.setFlag(Constants.SUCCESS_FLAG);
		}else{
			reBean.setMsg("false");
			reBean.setFlag(Constants.ERROR_FLAG);
		}*/
		setAttr("menuList", MenuMgr.getInstance().getAllMenu4Class());
		render("/manage/sys/role.html");
	}
}

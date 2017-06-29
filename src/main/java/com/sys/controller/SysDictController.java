package com.sys.controller;

import java.util.Date;
import java.util.Map;

import com.biz.model.SysDict;
import com.frame.constants.Constants;
import com.frame.utils.ArgsTool;
import com.frame.utils.Fun;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.sys.bean.ReBean;
import com.sys.cache.SysInfoCache;
import com.sys.interceptor.AuthorityCharacterITR;
import com.sys.mgr.SysDictMgr;

@Before({AuthorityCharacterITR.class})
public class SysDictController extends Controller {
	
	private ReBean<SysDict> reBean;
	
	public void index(){
		render("/manage/sys/dict.html");
	}
	
	/**
	 * 新增编辑页面
	 */
	public void editIndex(){
		String sql = "select * from sys_dict where FK_USER=?";
		SysDict sysDict = SysDict.dao.findFirst(sql,SysInfoCache.getInstance().getUserInfoBean(getRequest()).getSysUser().getPkUser());
		setAttr("sysDict", Fun.nvl(sysDict,new SysDict()));
		render("/manage/sys/dict_edit.html");
	}
	
	/**
	 * 分页查询
	 */
	public void list() {
		renderJson(SysDictMgr.getInstance().getPage(Fun.getPara(getParaMap())));
	}
	
	/**
	 * 表单数据修改
	 */
	@Before(Tx.class)
	public void edit4Form() {
		reBean = new ReBean<SysDict>();
		SysDict model = getModel(SysDict.class);
		Date date = new Date();
		try {
			if(Fun.eqNull(model.getPkDict())){
				model.setCreateById(SysInfoCache.getInstance().getUserInfoBean(getRequest()).getSysUser().getPkUser());
				model.setCreateByTime(date);
				model.save();
			}else{
				model.setUpdateById(SysInfoCache.getInstance().getUserInfoBean(getRequest()).getSysUser().getPkUser());
				model.setUpdateByTime(date);
				model.update();
			}
			reBean.setMsg("");
			reBean.setFlag(Constants.SUCCESS);
		} catch (Exception e) {
			reBean.setMsg("操作失败");
			reBean.setFlag(Constants.ERROR);
		}
		renderJson(reBean);
	}
	
	/**
	 * 添加数据
	 */
	public void add() {
		Map<String, String> qryMap = ArgsTool.getParameterMap(getParaMap());
		if(SysDictMgr.getInstance().isRepeat(qryMap)){
			reBean.setFlag(Constants.REPEAT);
			reBean.setMsg("添加的记录已经存在!");
			renderJson(reBean);
		}else{
			reBean = ArgsTool.addObj(new SysDict(), qryMap,getRequest());
			reBean.setMsg("");
			reBean.setFlag(Constants.SUCCESS);
			renderJson(reBean);
		}
	}
	
	/**
	 * 修改数据
	 */
	public void edit() {
		reBean = new ReBean<SysDict>();
		Map<String, String> qryMap = ArgsTool.getParameterMap(getParaMap());
		String flag = ArgsTool.upObj(SysDict.dao, qryMap, "PK_DICT",getRequest());
		reBean.setMsg("");
		reBean.setFlag(flag);
		renderJson(reBean);
	}
	
	/**
	 * 删除数据
	 */
	public void del() {
		reBean = new ReBean<SysDict>();
		Map<String, String> qryMap = ArgsTool.getParameterMap(getParaMap());
		if(!Fun.eqNull(qryMap) && qryMap.containsKey("PK_DICT")){
			SysDict.dao.deleteById(qryMap.get("PK_DICT"));
		}
		reBean.setFlag(Constants.SUCCESS);
		renderJson(reBean);
	}
	
}

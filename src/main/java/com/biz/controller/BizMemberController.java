package com.biz.controller;

import java.util.Date;
import java.util.Map;

import com.frame.constants.Constants;
import com.frame.utils.ArgsTool;
import com.frame.utils.Fun;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.sys.bean.ReBean;
import com.sys.cache.SysInfoCache;
import com.sys.interceptor.AuthorityCharacterITR;
import com.biz.mgr.BizMemberMgr;
import com.biz.model.BizMember;

@Before({AuthorityCharacterITR.class})
public class BizMemberController extends Controller {
	
	private ReBean<BizMember> reBean;
	
	public void index(){
		render("/manage/biz/member.html");
	}
	
	/**
	 * 新增编辑页面
	 */
	public void editIndex(){
		String sql = "select * from biz_member where PK_MEMBER=?";
		BizMember bizMember = BizMember.dao.findFirst(sql,getPara("PK_MEMBER"));
		setAttr("bizMember", Fun.nvl(bizMember,new BizMember()));
		render("/manage/biz/member_edit.html");
	}
	
	/**
	 * 分页查询
	 */
	public void list() {
		renderJson(BizMemberMgr.getInstance().getPage(Fun.getPara(getParaMap())));
	}
	
	/**
	 * 表单数据修改
	 */
	@Before(Tx.class)
	public void edit4Form() {
		reBean = new ReBean<BizMember>();
		BizMember model = getModel(BizMember.class);
		Date date = new Date();
		try {
			if(Fun.eqNull(model.getPkMember())){
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
			e.printStackTrace();
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
		reBean = new ReBean<BizMember>();
		if(BizMemberMgr.getInstance().isRepeat(qryMap)){
			reBean.setFlag(Constants.REPEAT);
			reBean.setMsg("添加的记录已经存在!");
			renderJson(reBean);
		}else{
			reBean = ArgsTool.addObj(new BizMember(), qryMap,getRequest());
			reBean.setMsg("");
			reBean.setFlag(Constants.SUCCESS);
			renderJson(reBean);
		}
	}
	
	/**
	 * 修改数据
	 */
	public void edit() {
		reBean = new ReBean<BizMember>();
		Map<String, String> qryMap = ArgsTool.getParameterMap(getParaMap());
		String flag = ArgsTool.upObj(BizMember.dao, qryMap, "PK_MEMBER",getRequest());
		reBean.setMsg("");
		reBean.setFlag(flag);
		renderJson(reBean);
	}
	
	/**
	 * 删除数据
	 */
	public void del() {
		reBean = new ReBean<BizMember>();
		Map<String, String> qryMap = ArgsTool.getParameterMap(getParaMap());
		if(!Fun.eqNull(qryMap) && qryMap.containsKey("PK_MEMBER")){
			BizMember.dao.deleteById(qryMap.get("PK_MEMBER"));
		}
		reBean.setFlag(Constants.SUCCESS);
		renderJson(reBean);
	}
	
}

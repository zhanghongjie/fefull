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
import com.biz.mgr.BizArticleTagRefMgr;
import com.biz.model.BizArticleTagRef;

@Before({AuthorityCharacterITR.class})
public class BizArticleTagRefController extends Controller {
	
	private ReBean<BizArticleTagRef> reBean;
	
	public void index(){
		render("/manage/biz/article_tag_ref.html");
	}
	
	/**
	 * 新增编辑页面
	 */
	public void editIndex(){
		String sql = "select * from biz_article_tag_ref where PK_ARTICLE_TAG_REF=?";
		BizArticleTagRef bizArticleTagRef = BizArticleTagRef.dao.findFirst(sql,getPara("PK_ARTICLE_TAG_REF"));
		setAttr("bizArticleTagRef", Fun.nvl(bizArticleTagRef,new BizArticleTagRef()));
		render("/manage/biz/article_tag_ref_edit.html");
	}
	
	/**
	 * 分页查询
	 */
	public void list() {
		renderJson(BizArticleTagRefMgr.getInstance().getPage(Fun.getPara(getParaMap())));
	}
	
	/**
	 * 表单数据修改
	 */
	@Before(Tx.class)
	public void edit4Form() {
		reBean = new ReBean<BizArticleTagRef>();
		BizArticleTagRef model = getModel(BizArticleTagRef.class);
		Date date = new Date();
		try {
			if(Fun.eqNull(model.getPkArticleTagRef())){
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
		reBean = new ReBean<BizArticleTagRef>();
		if(BizArticleTagRefMgr.getInstance().isRepeat(qryMap)){
			reBean.setFlag(Constants.REPEAT);
			reBean.setMsg("添加的记录已经存在!");
			renderJson(reBean);
		}else{
			reBean = ArgsTool.addObj(new BizArticleTagRef(), qryMap,getRequest());
			reBean.setMsg("");
			reBean.setFlag(Constants.SUCCESS);
			renderJson(reBean);
		}
	}
	
	/**
	 * 修改数据
	 */
	public void edit() {
		reBean = new ReBean<BizArticleTagRef>();
		Map<String, String> qryMap = ArgsTool.getParameterMap(getParaMap());
		String flag = ArgsTool.upObj(BizArticleTagRef.dao, qryMap, "PK_ARTICLE_TAG_REF",getRequest());
		reBean.setMsg("");
		reBean.setFlag(flag);
		renderJson(reBean);
	}
	
	/**
	 * 删除数据
	 */
	public void del() {
		reBean = new ReBean<BizArticleTagRef>();
		Map<String, String> qryMap = ArgsTool.getParameterMap(getParaMap());
		if(!Fun.eqNull(qryMap) && qryMap.containsKey("PK_ARTICLE_TAG_REF")){
			BizArticleTagRef.dao.deleteById(qryMap.get("PK_ARTICLE_TAG_REF"));
		}
		reBean.setFlag(Constants.SUCCESS);
		renderJson(reBean);
	}
	
}

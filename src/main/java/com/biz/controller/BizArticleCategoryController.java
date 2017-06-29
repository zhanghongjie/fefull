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
import com.biz.mgr.BizArticleCategoryMgr;
import com.biz.model.BizArticleCategory;

@Before({AuthorityCharacterITR.class})
public class BizArticleCategoryController extends Controller {
	
	private ReBean<BizArticleCategory> reBean;
	
	public void index(){
		render("/manage/biz/article_category.html");
	}
	
	/**
	 * 新增编辑页面
	 */
	public void editIndex(){
		String sql = "select * from biz_article_category where PK_ARTICLE_CATEGORY=?";
		BizArticleCategory bizArticleCategory = BizArticleCategory.dao.findFirst(sql,getPara("PK_ARTICLE_CATEGORY"));
		setAttr("bizArticleCategory", Fun.nvl(bizArticleCategory,new BizArticleCategory()));
		render("/manage/biz/article_category_edit.html");
	}
	
	/**
	 * 分页查询
	 */
	public void list() {
		renderJson(BizArticleCategoryMgr.getInstance().getPage(Fun.getPara(getParaMap())));
	}
	
	/**
	 * 表单数据修改
	 */
	@Before(Tx.class)
	public void edit4Form() {
		reBean = new ReBean<BizArticleCategory>();
		BizArticleCategory model = getModel(BizArticleCategory.class);
		Date date = new Date();
		try {
			if(Fun.eqNull(model.getPkArticleCategory())){
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
		reBean = new ReBean<BizArticleCategory>();
		if(BizArticleCategoryMgr.getInstance().isRepeat(qryMap)){
			reBean.setFlag(Constants.REPEAT);
			reBean.setMsg("添加的记录已经存在!");
			renderJson(reBean);
		}else{
			reBean = ArgsTool.addObj(new BizArticleCategory(), qryMap,getRequest());
			reBean.setMsg("");
			reBean.setFlag(Constants.SUCCESS);
			renderJson(reBean);
		}
	}
	
	/**
	 * 修改数据
	 */
	public void edit() {
		reBean = new ReBean<BizArticleCategory>();
		Map<String, String> qryMap = ArgsTool.getParameterMap(getParaMap());
		String flag = ArgsTool.upObj(BizArticleCategory.dao, qryMap, "PK_ARTICLE_CATEGORY",getRequest());
		reBean.setMsg("");
		reBean.setFlag(flag);
		renderJson(reBean);
	}
	
	/**
	 * 删除数据
	 */
	public void del() {
		reBean = new ReBean<BizArticleCategory>();
		Map<String, String> qryMap = ArgsTool.getParameterMap(getParaMap());
		if(!Fun.eqNull(qryMap) && qryMap.containsKey("PK_ARTICLE_CATEGORY")){
			BizArticleCategory.dao.deleteById(qryMap.get("PK_ARTICLE_CATEGORY"));
		}
		reBean.setFlag(Constants.SUCCESS);
		renderJson(reBean);
	}
	
}

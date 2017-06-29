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
import com.biz.mgr.BizArticleTagMgr;
import com.biz.model.BizArticleTag;

@Before({AuthorityCharacterITR.class})
public class BizArticleTagController extends Controller {
	
	private ReBean<BizArticleTag> reBean;
	
	public void index(){
		render("/manage/biz/article_tag.html");
	}
	
	/**
	 * 新增编辑页面
	 */
	public void editIndex(){
		String sql = "select * from biz_article_tag where PK_ARTICLE_TAG=?";
		BizArticleTag bizArticleTag = BizArticleTag.dao.findFirst(sql,getPara("PK_ARTICLE_TAG"));
		setAttr("bizArticleTag", Fun.nvl(bizArticleTag,new BizArticleTag()));
		render("/manage/biz/article_tag_edit.html");
	}
	
	/**
	 * 分页查询
	 */
	public void list() {
		renderJson(BizArticleTagMgr.getInstance().getPage(Fun.getPara(getParaMap())));
	}
	
	/**
	 * 表单数据修改
	 */
	@Before(Tx.class)
	public void edit4Form() {
		reBean = new ReBean<BizArticleTag>();
		BizArticleTag model = getModel(BizArticleTag.class);
		Date date = new Date();
		try {
			if(Fun.eqNull(model.getPkArticleTag())){
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
		reBean = new ReBean<BizArticleTag>();
		if(BizArticleTagMgr.getInstance().isRepeat(qryMap)){
			reBean.setFlag(Constants.REPEAT);
			reBean.setMsg("添加的记录已经存在!");
			renderJson(reBean);
		}else{
			reBean = ArgsTool.addObj(new BizArticleTag(), qryMap,getRequest());
			reBean.setMsg("");
			reBean.setFlag(Constants.SUCCESS);
			renderJson(reBean);
		}
	}
	
	/**
	 * 修改数据
	 */
	public void edit() {
		reBean = new ReBean<BizArticleTag>();
		Map<String, String> qryMap = ArgsTool.getParameterMap(getParaMap());
		String flag = ArgsTool.upObj(BizArticleTag.dao, qryMap, "PK_ARTICLE_TAG",getRequest());
		reBean.setMsg("");
		reBean.setFlag(flag);
		renderJson(reBean);
	}
	
	/**
	 * 删除数据
	 */
	public void del() {
		reBean = new ReBean<BizArticleTag>();
		Map<String, String> qryMap = ArgsTool.getParameterMap(getParaMap());
		if(!Fun.eqNull(qryMap) && qryMap.containsKey("PK_ARTICLE_TAG")){
			BizArticleTag.dao.deleteById(qryMap.get("PK_ARTICLE_TAG"));
		}
		reBean.setFlag(Constants.SUCCESS);
		renderJson(reBean);
	}
	
}

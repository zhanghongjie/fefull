package com.biz.controller;

import java.util.Date;
import java.util.Map;

import com.biz.mgr.BizArticleCollectionMgr;
import com.biz.model.BizArticleCollection;
import com.frame.constants.Constants;
import com.frame.utils.ArgsTool;
import com.frame.utils.Fun;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.sys.bean.ReBean;
import com.sys.interceptor.AuthorityCharacterITR;

@Before({AuthorityCharacterITR.class})
public class BizArticleCollectionController extends Controller {
	
	private ReBean<BizArticleCollection> reBean;
	
	public void index(){
		render("/manage/biz/article_collection.html");
	}
	
	/**
	 * 新增编辑页面
	 */
	public void editIndex(){
		String sql = "select * from biz_article_collection where PK_ARTICLE_COLLECTION=?";
		BizArticleCollection bizArticleCollection = BizArticleCollection.dao.findFirst(sql,getPara("PK_ARTICLE_COLLECTION"));
		setAttr("bizArticleCollection", Fun.nvl(bizArticleCollection,new BizArticleCollection()));
		render("/manage/biz/article_collection_edit.html");
	}
	
	/**
	 * 分页查询
	 */
	public void list() {
		renderJson(BizArticleCollectionMgr.getInstance().getPage(Fun.getPara(getParaMap())));
	}
	
	/**
	 * 表单数据修改
	 */
	@Before(Tx.class)
	public void edit4Form() {
		reBean = new ReBean<BizArticleCollection>();
		BizArticleCollection model = getModel(BizArticleCollection.class);
		Date date = new Date();
		try {
			if(Fun.eqNull(model.getPkArticleCollection())){
				model.save();
			}else{
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
		reBean = new ReBean<BizArticleCollection>();
		if(BizArticleCollectionMgr.getInstance().isRepeat(qryMap)){
			reBean.setFlag(Constants.REPEAT);
			reBean.setMsg("添加的记录已经存在!");
			renderJson(reBean);
		}else{
			reBean = ArgsTool.addObj(new BizArticleCollection(), qryMap,getRequest());
			reBean.setMsg("");
			reBean.setFlag(Constants.SUCCESS);
			renderJson(reBean);
		}
	}
	
	/**
	 * 修改数据
	 */
	public void edit() {
		reBean = new ReBean<BizArticleCollection>();
		Map<String, String> qryMap = ArgsTool.getParameterMap(getParaMap());
		String flag = ArgsTool.upObj(BizArticleCollection.dao, qryMap, "PK_ARTICLE_COLLECTION",getRequest());
		reBean.setMsg("");
		reBean.setFlag(flag);
		renderJson(reBean);
	}
	
	/**
	 * 删除数据
	 */
	public void del() {
		reBean = new ReBean<BizArticleCollection>();
		Map<String, String> qryMap = ArgsTool.getParameterMap(getParaMap());
		if(!Fun.eqNull(qryMap) && qryMap.containsKey("PK_ARTICLE_COLLECTION")){
			BizArticleCollection.dao.deleteById(qryMap.get("PK_ARTICLE_COLLECTION"));
		}
		reBean.setFlag(Constants.SUCCESS);
		renderJson(reBean);
	}
	
}

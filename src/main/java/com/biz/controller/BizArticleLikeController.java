package com.biz.controller;

import java.util.Date;
import java.util.Map;

import com.biz.mgr.BizArticleLikeMgr;
import com.biz.model.BizArticleLike;
import com.frame.constants.Constants;
import com.frame.utils.ArgsTool;
import com.frame.utils.Fun;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.sys.bean.ReBean;
import com.sys.interceptor.AuthorityCharacterITR;

@Before({AuthorityCharacterITR.class})
public class BizArticleLikeController extends Controller {
	
	private ReBean<BizArticleLike> reBean;
	
	public void index(){
		render("/manage/biz/article_like.html");
	}
	
	/**
	 * 新增编辑页面
	 */
	public void editIndex(){
		String sql = "select * from biz_article_like where PK_ARTICLE_LIKE=?";
		BizArticleLike bizArticleLike = BizArticleLike.dao.findFirst(sql,getPara("PK_ARTICLE_LIKE"));
		setAttr("bizArticleLike", Fun.nvl(bizArticleLike,new BizArticleLike()));
		render("/manage/biz/article_like_edit.html");
	}
	
	/**
	 * 分页查询
	 */
	public void list() {
		renderJson(BizArticleLikeMgr.getInstance().getPage(Fun.getPara(getParaMap())));
	}
	
	/**
	 * 表单数据修改
	 */
	@Before(Tx.class)
	public void edit4Form() {
		reBean = new ReBean<BizArticleLike>();
		BizArticleLike model = getModel(BizArticleLike.class);
		Date date = new Date();
		try {
			if(Fun.eqNull(model.getPkArticleLike())){
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
		reBean = new ReBean<BizArticleLike>();
		if(BizArticleLikeMgr.getInstance().isRepeat(qryMap)){
			reBean.setFlag(Constants.REPEAT);
			reBean.setMsg("添加的记录已经存在!");
			renderJson(reBean);
		}else{
			reBean = ArgsTool.addObj(new BizArticleLike(), qryMap,getRequest());
			reBean.setMsg("");
			reBean.setFlag(Constants.SUCCESS);
			renderJson(reBean);
		}
	}
	
	/**
	 * 修改数据
	 */
	public void edit() {
		reBean = new ReBean<BizArticleLike>();
		Map<String, String> qryMap = ArgsTool.getParameterMap(getParaMap());
		String flag = ArgsTool.upObj(BizArticleLike.dao, qryMap, "PK_ARTICLE_LIKE",getRequest());
		reBean.setMsg("");
		reBean.setFlag(flag);
		renderJson(reBean);
	}
	
	/**
	 * 删除数据
	 */
	public void del() {
		reBean = new ReBean<BizArticleLike>();
		Map<String, String> qryMap = ArgsTool.getParameterMap(getParaMap());
		if(!Fun.eqNull(qryMap) && qryMap.containsKey("PK_ARTICLE_LIKE")){
			BizArticleLike.dao.deleteById(qryMap.get("PK_ARTICLE_LIKE"));
		}
		reBean.setFlag(Constants.SUCCESS);
		renderJson(reBean);
	}
	
}

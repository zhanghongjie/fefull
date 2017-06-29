package com.biz.controller;

import java.util.Date;
import java.util.Map;

import com.biz.mgr.BizArticleCommentMgr;
import com.biz.model.BizArticleComment;
import com.frame.constants.Constants;
import com.frame.utils.ArgsTool;
import com.frame.utils.Fun;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.sys.bean.ReBean;
import com.sys.interceptor.AuthorityCharacterITR;

@Before({AuthorityCharacterITR.class})
public class BizArticleCommentController extends Controller {
	
	private ReBean<BizArticleComment> reBean;
	
	public void index(){
		render("/manage/biz/article_comment.html");
	}
	
	/**
	 * 新增编辑页面
	 */
	public void editIndex(){
		String sql = "select * from biz_article_comment where PK_ARTICLE_COMMENT=?";
		BizArticleComment bizArticleComment = BizArticleComment.dao.findFirst(sql,getPara("PK_ARTICLE_COMMENT"));
		setAttr("bizArticleComment", Fun.nvl(bizArticleComment,new BizArticleComment()));
		render("/manage/biz/article_comment_edit.html");
	}
	
	/**
	 * 分页查询
	 */
	public void list() {
		renderJson(BizArticleCommentMgr.getInstance().getPage(Fun.getPara(getParaMap())));
	}
	
	/**
	 * 表单数据修改
	 */
	@Before(Tx.class)
	public void edit4Form() {
		reBean = new ReBean<BizArticleComment>();
		BizArticleComment model = getModel(BizArticleComment.class);
		Date date = new Date();
		try {
			if(Fun.eqNull(model.getPkArticleComment())){
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
		reBean = new ReBean<BizArticleComment>();
		if(BizArticleCommentMgr.getInstance().isRepeat(qryMap)){
			reBean.setFlag(Constants.REPEAT);
			reBean.setMsg("添加的记录已经存在!");
			renderJson(reBean);
		}else{
			reBean = ArgsTool.addObj(new BizArticleComment(), qryMap,getRequest());
			reBean.setMsg("");
			reBean.setFlag(Constants.SUCCESS);
			renderJson(reBean);
		}
	}
	
	/**
	 * 修改数据
	 */
	public void edit() {
		reBean = new ReBean<BizArticleComment>();
		Map<String, String> qryMap = ArgsTool.getParameterMap(getParaMap());
		String flag = ArgsTool.upObj(BizArticleComment.dao, qryMap, "PK_ARTICLE_COMMENT",getRequest());
		reBean.setMsg("");
		reBean.setFlag(flag);
		renderJson(reBean);
	}
	
	/**
	 * 删除数据
	 */
	public void del() {
		reBean = new ReBean<BizArticleComment>();
		Map<String, String> qryMap = ArgsTool.getParameterMap(getParaMap());
		if(!Fun.eqNull(qryMap) && qryMap.containsKey("PK_ARTICLE_COMMENT")){
			BizArticleComment.dao.deleteById(qryMap.get("PK_ARTICLE_COMMENT"));
		}
		reBean.setFlag(Constants.SUCCESS);
		renderJson(reBean);
	}
	
}

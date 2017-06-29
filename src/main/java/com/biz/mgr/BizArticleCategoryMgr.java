package com.biz.mgr;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.biz.model.BizArticleCategory;
import com.frame.utils.ArgsTool;
import com.frame.utils.Fun;
import com.jfinal.plugin.activerecord.Record;
import com.sys.bean.ReBean;

public class BizArticleCategoryMgr {
	
	public static final Logger logger = LoggerFactory.getLogger(BizArticleCategoryMgr.class);
	
	private BizArticleCategoryMgr() {

	}

	public static BizArticleCategoryMgr getInstance() {
		return BizArticleCategoryMgrHolder.instance;
	}

	private static class BizArticleCategoryMgrHolder {
		private static BizArticleCategoryMgr instance = new BizArticleCategoryMgr();
	}
	
	/**
	 * 分页查询
	 * @return
	 */
	public ReBean<Record> getPage(Map<String, String> mQryMap){
		ReBean<Record> reBean = null;
		StringBuilder sb = new StringBuilder("select * from biz_article_category order by PK_ARTICLE_CATEGORY desc");
		if (!Fun.eqNull(mQryMap)) {
			reBean = ArgsTool.getPageObj(this.reWhereSql(mQryMap,sb), mQryMap);
		}
		return reBean;
	}
	
	/**
	 * 条件拼接
	 * @param mQryMap
	 */
	private String reWhereSql(Map<String, String> mQryMap,StringBuilder sb){
		StringBuilder msb = new StringBuilder();
		msb.append(" and IS_VALID=1 ");
		return ArgsTool.reSql(sb, msb);
	}
	
	/**
	 * 判断添加的记录是否已经存在
	 * @param qryMap
	 * @return
	 */
	public boolean isRepeat(Map<String, String> qryMap){
		StringBuilder msb = new StringBuilder("select 1 from biz_article_category where 1=1");
		Boolean bool = true;
		if(!Fun.eqNull(qryMap.get("PK_ARTICLE_CATEGORY"))){
			msb.append(" and PK_ARTICLE_CATEGORY!=@PK_ARTICLE_CATEGORY");
			bool = false;
		}
		if(bool){
			msb.append(" and 1!=1");
		}
		Record record = ArgsTool.getObj(msb.toString(), qryMap);
		if(!Fun.eqNull(record)){
			return true;
		}
		return false;
	}
	
	/**
	 * 查询所有文章分类
	 * @return
	 */
	public List<BizArticleCategory> getAllCategory() {	
		return BizArticleCategory.dao.find("select * from biz_article_category");
	}
}

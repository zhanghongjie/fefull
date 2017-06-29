package com.biz.mgr;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.biz.model.BizArticleTag;
import com.frame.utils.ArgsTool;
import com.frame.utils.Fun;
import com.jfinal.plugin.activerecord.Record;
import com.sys.bean.ReBean;

public class BizArticleTagMgr {
	
	public static final Logger logger = LoggerFactory.getLogger(BizArticleTagMgr.class);
	
	private BizArticleTagMgr() {

	}

	public static BizArticleTagMgr getInstance() {
		return BizArticleTagMgrHolder.instance;
	}

	private static class BizArticleTagMgrHolder {
		private static BizArticleTagMgr instance = new BizArticleTagMgr();
	}
	
	/**
	 * 分页查询
	 * @return
	 */
	public ReBean<Record> getPage(Map<String, String> mQryMap){
		ReBean<Record> reBean = null;
		StringBuilder sb = new StringBuilder("select * from biz_article_tag order by PK_ARTICLE_TAG desc");
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
		StringBuilder msb = new StringBuilder("select 1 from biz_article_tag where 1=1");
		Boolean bool = true;
		if(!Fun.eqNull(qryMap.get("PK_ARTICLE_TAG"))){
			msb.append(" and PK_ARTICLE_TAG!=@PK_ARTICLE_TAG");
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
	 * 根据文章PK获取标签
	 * @param pkArticle
	 * @return
	 */
	public List<BizArticleTag> getTagsByArticleId(Integer pkArticle) {
		String sql = "SELECT t1.* "
				 + " FROM biz_article_tag t1"
				 + " INNER JOIN biz_article_tag_ref t2 ON t1.`PK_ARTICLE_TAG` = t2.`FK_ARTICLE_TAG`"
				 + " WHERE t2.`FK_ARTICLE` = ?";

		return BizArticleTag.dao.find(sql, pkArticle);
	}

	public List<BizArticleTag> getAllTags() {
		return BizArticleTag.dao.find("select * from biz_article_tag");
	}
}

package com.biz.mgr;

import java.util.Date;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.biz.model.BizArticleCollection;
import com.biz.model.BizMember;
import com.frame.utils.ArgsTool;
import com.frame.utils.Fun;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.portal.dto.request.ArticleRequest;
import com.sys.bean.ReBean;

public class BizArticleCollectionMgr {
	
	public static final Logger logger = LoggerFactory.getLogger(BizArticleCollectionMgr.class);
	
	private BizArticleCollectionMgr() {

	}

	public static BizArticleCollectionMgr getInstance() {
		return BizArticleCollectionMgrHolder.instance;
	}

	private static class BizArticleCollectionMgrHolder {
		private static BizArticleCollectionMgr instance = new BizArticleCollectionMgr();
	}
	
	/**
	 * 分页查询
	 * @return
	 */
	public ReBean<Record> getPage(Map<String, String> mQryMap){
		ReBean<Record> reBean = null;
		StringBuilder sb = new StringBuilder("select * from biz_article_collection order by PK_ARTICLE_COLLECTION desc");
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
		StringBuilder msb = new StringBuilder("select 1 from biz_article_collection where 1=1");
		Boolean bool = true;
		if(!Fun.eqNull(qryMap.get("PK_ARTICLE_COLLECTION"))){
			msb.append(" and PK_ARTICLE_COLLECTION!=@PK_ARTICLE_COLLECTION");
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
	
	public boolean isExist(Integer memberId, Integer articleId){
		StringBuilder msb = new StringBuilder("select 1 from biz_article_collection where fk_member = ? and fk_article = ?");
		BizArticleCollection bac = BizArticleCollection.dao.findFirst(msb.toString(), memberId,articleId);
		if(Fun.eqNull(bac))
			return false;
		return true;
	}

	public void save(BizMember bizMember, ArticleRequest articleRequest) {
		BizArticleCollection articleCollection = new BizArticleCollection();
		articleCollection.setFkArticle(articleRequest.getArticleId());
		articleCollection.setFkMember(bizMember.getPkMember());
		articleCollection.setMemberCreateById(bizMember.getPkMember());
		articleCollection.setMemberCreateByTime(new Date());
		articleCollection.setMemberUpdateById(bizMember.getPkMember());
		articleCollection.setMemberUpdateByTime(new Date());
		
		articleCollection.save();
	}

	public void delete(BizMember bizMember, ArticleRequest articleRequest) {
		Db.update("delete from biz_article_collection  where fk_member = ? and fk_article = ?", bizMember.getPkMember(), articleRequest.getArticleId());
	}
	
}

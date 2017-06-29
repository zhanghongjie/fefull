package com.biz.mgr;

import java.util.Date;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.biz.commom.FefullEnum;
import com.biz.model.BizArticle;
import com.biz.model.BizArticleTagRef;
import com.biz.model.BizMember;
import com.frame.utils.ArgsTool;
import com.frame.utils.Fun;
import com.jfinal.plugin.activerecord.Record;
import com.portal.dto.request.ArticleRequest;
import com.sys.bean.ReBean;

public class BizArticleMgr {
	
	public static final Logger logger = LoggerFactory.getLogger(BizArticleMgr.class);
	
	private BizArticleMgr() {

	}

	public static BizArticleMgr getInstance() {
		return BizArticleMgrHolder.instance;
	}

	private static class BizArticleMgrHolder {
		private static BizArticleMgr instance = new BizArticleMgr();
	}
	
	/**
	 * 分页查询
	 * @return
	 */
	public ReBean<Record> getPage(Map<String, String> mQryMap){
		ReBean<Record> reBean = null;
		StringBuilder sb = new StringBuilder("select * from biz_article t1 order by t1.PK_ARTICLE desc");
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
		if(!Fun.eqNull(mQryMap.get("categoryId"))){
			msb.append(" and t3.PK_ARTICLE_CATEGORY = @categoryId ");
		}
		if(!Fun.eqNull(mQryMap.get("tagCloudId"))){
			msb.append(" AND EXISTS (SELECT 1 FROM biz_article_tag_ref WHERE FK_ARTICLE = t1.`PK_ARTICLE` AND FK_ARTICLE_TAG = @tagCloudId) ");
		}
		if(!Fun.eqNull(mQryMap.get("keyword"))){
			msb.append(" AND t1.TITLE like @keyword ");
		}
		msb.append(" and t1.IS_VALID = 1");
		
		return ArgsTool.reSql(sb, msb);
	}
	
	/**
	 * 判断添加的记录是否已经存在
	 * @param qryMap
	 * @return
	 */
	public boolean isRepeat(Map<String, String> qryMap){
		StringBuilder msb = new StringBuilder("select 1 from biz_article where 1=1");
		Boolean bool = true;
		if(!Fun.eqNull(qryMap.get("PK_ARTICLE"))){
			msb.append(" and PK_ARTICLE!=@PK_ARTICLE");
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
	 * 根据ID查询文章
	 * @param id
	 * @return
	 */
	public BizArticle getObjById(Integer id){
		String sql = 
			"SELECT t1.`PK_ARTICLE`, t1.`TITLE`, t1.`IS_ORIGINAL`, t1.`CONTENT`, t1.`COVER_PICTURE`, t1.`PUBLISHED_DATE`, t1.`REPRINT_ADDRESS`,\n" +
			"         t2.`PK_MEMBER`, t2.`LOGIN_NAME`, t2.`HEAD_PORTRAIT`, t3.`PK_ARTICLE_CATEGORY` , t3.`CATEGORY_NAME`,\n" + 
			"         COUNT(t4.`PK_ARTICLE_COLLECTION`) AS favs, COUNT(t5.`PK_ARTICLE_LIKE`) AS likes\n" + 
			"  FROM biz_article t1\n" + 
			"  INNER JOIN biz_member t2 ON t1.`FK_MEMBER` = t2.`PK_MEMBER`\n" + 
			"  INNER JOIN biz_article_category t3 ON t1.`FK_ARTICLE_CATEGORY` = t3.`PK_ARTICLE_CATEGORY`\n" + 
			"  LEFT JOIN biz_article_collection t4 ON t1.`PK_ARTICLE` = t4.`FK_ARTICLE`\n" + 
			"  LEFT JOIN biz_article_like t5 ON t1.`PK_ARTICLE` = t5.`FK_ARTICLE`\n" + 
			"  WHERE t1.`PK_ARTICLE` = ?\n" + 
			"  GROUP BY t1.`PK_ARTICLE`, t1.`TITLE`, t1.`IS_ORIGINAL`, t1.`CONTENT`, t1.`COVER_PICTURE`, t1.`PUBLISHED_DATE`, t1.`REPRINT_ADDRESS`,\n" + 
			"         t2.`PK_MEMBER`, t2.`LOGIN_NAME`, t2.`HEAD_PORTRAIT`, t3.`PK_ARTICLE_CATEGORY` , t3.`CATEGORY_NAME`";

		BizArticle bizArticle = BizArticle.dao.findFirst(sql, id);
		return bizArticle;
	}
	
	/**
	 * 前端分页查询
	 * @param mQryMap
	 * @return
	 */
	public ReBean<Record> getPageByFront(Map<String, String> mQryMap){
		ReBean<Record> reBean = null;
		StringBuilder sb = new StringBuilder("");
		sb.append("SELECT\n" +
				"  t1.`PK_ARTICLE`,\n" + 
				"  t1.`TITLE`,\n" + 
				"  t1.`IS_ORIGINAL`,\n" + 
				"  t1.`CONTENT`,\n" + 
				"  t1.`COVER_PICTURE`,\n" + 
				"  t1.`PUBLISHED_DATE`,\n" + 
				"  t1.`REPRINT_ADDRESS`,\n" + 
				"  t2.`PK_MEMBER`,\n" + 
				"  t2.`LOGIN_NAME`,\n" + 
				"  t2.`HEAD_PORTRAIT`,\n" + 
				"  t3.`PK_ARTICLE_CATEGORY`,\n" + 
				"  t3.`CATEGORY_NAME`,\n" + 
				"  COUNT(t4.`PK_ARTICLE_COLLECTION`) AS favs,\n" + 
				"  COUNT(t5.`PK_ARTICLE_LIKE`) AS likes\n" + 
				"FROM\n" + 
				"  biz_article t1\n" + 
				"  INNER JOIN biz_member t2\n" + 
				"    ON t1.`FK_MEMBER` = t2.`PK_MEMBER`\n" + 
				"  INNER JOIN biz_article_category t3\n" + 
				"    ON t1.`FK_ARTICLE_CATEGORY` = t3.`PK_ARTICLE_CATEGORY`\n" + 
				"  LEFT JOIN biz_article_collection t4\n" + 
				"    ON t1.`PK_ARTICLE` = t4.`FK_ARTICLE`\n" + 
				"  LEFT JOIN biz_article_like t5\n" + 
				"    ON t1.`PK_ARTICLE` = t5.`FK_ARTICLE`\n" + 
				" group by t1.`PK_ARTICLE`,\n" + 
				"  t1.`TITLE`,\n" + 
				"  t1.`IS_ORIGINAL`,\n" + 
				"  t1.`CONTENT`,\n" + 
				"  t1.`COVER_PICTURE`,\n" + 
				"  t1.`PUBLISHED_DATE`,\n" + 
				"  t1.`REPRINT_ADDRESS`,\n" + 
				"  t2.`PK_MEMBER`,\n" + 
				"  t2.`LOGIN_NAME`,\n" + 
				"  t2.`HEAD_PORTRAIT`,\n" + 
				"  t3.`PK_ARTICLE_CATEGORY`,\n" + 
				"  t3.`CATEGORY_NAME`\n" );
		if(Fun.eqNull(mQryMap.get("sortOrder")) || mQryMap.get("sortOrder").equals(FefullEnum.SortOrder.NEWEST.getValue())){
			sb.append(" order by t1.`PUBLISHED_DATE` desc");
		} else{
			sb.append(" order by COUNT(t4.`PK_ARTICLE_COLLECTION`) desc");
		}
		
						
		if (!Fun.eqNull(mQryMap)) {
			String qrySql = this.reWhereSql(mQryMap,sb);
			if(!Fun.eqNull(mQryMap.get("memberId"))){
				StringBuilder ssb = new StringBuilder("select A.* from (");
				ssb.append(qrySql)
				   .append(") A")
				   .append("INNER JOIN biz_article_collection B ON A.PK_ARTICLE = B.FK_ARTICLE AND B.FK_MEMBER = @memberId");
				qrySql = ssb.toString();
			}
			reBean = ArgsTool.getPageObj(qrySql, mQryMap);
		}
		return reBean;
	}
	
	/**
	 * 保存前端创建的文章
	 * @return
	 */
	public BizArticle save(BizMember bizMember, ArticleRequest articleRequest){
		Date date = new Date();
		BizArticle bizArticle = new BizArticle();
		bizArticle.setFkMember(bizMember.getPkMember());
		bizArticle.setFkArticleCategory(articleRequest.getCategory().getCategoryId());
		bizArticle.setTITLE(articleRequest.getTitle());
		bizArticle.setCONTENT(articleRequest.getMaintxt());
		bizArticle.setPublishedDate(date);
		bizArticle.setIsOriginal(articleRequest.getOrigin().getOriginId());
		bizArticle.setReprintAddress(articleRequest.getReprint());
		bizArticle.setCoverPicture(articleRequest.getCover());
		bizArticle.setMemberCreateById(bizMember.getPkMember());
		bizArticle.setMemberCreateByTime(date);
		bizArticle.setMemberUpdateById(bizMember.getPkMember());
		bizArticle.setMemberUpdateByTime(date);
		bizArticle.save();
		
		if(null != articleRequest.getTagclouds()){
			BizArticleTagRef bizArticleTagRef;
			for (Integer tagCloudsId : articleRequest.getTagclouds()) {
				bizArticleTagRef = new BizArticleTagRef();
				bizArticleTagRef.setFkArticle(bizArticle.getPkArticle());
				bizArticleTagRef.setFkArticleTag(tagCloudsId);
				bizArticleTagRef.setMemberCreateById(bizMember.getPkMember());
				bizArticleTagRef.setMemberCreateByTime(date);
				bizArticleTagRef.setMemberUpdateById(bizMember.getPkMember());
				bizArticleTagRef.setMemberUpdateByTime(date);
				bizArticleTagRef.save();
			}
		}
		
		return getObjById(bizArticle.getPkArticle());
	}
}

package com.portal.dto;

import java.util.ArrayList;
import java.util.List;

import com.frame.utils.DateUtil;
import com.jfinal.plugin.activerecord.Record;
import com.portal.dto.base.BaseDto;
import com.portal.dto.base.Page;
import com.sys.bean.ReBean;

public class ArticlePage extends Page implements BaseDto<ReBean<Record>, ArticlePage>{
	
	private List<Article> reList;

	public List<Article> getReList() {
		return reList;
	}

	public void setReList(List<Article> reList) {
		this.reList = reList;
	}

	@Override
	public ArticlePage wrapper(ReBean<Record> d) {
		super.setPage(d);
		List<Article> reList = new ArrayList<Article>();
		Article article = null;
		List<Record> list = d.getReList();
		for (Record record : list) {
			article = new Article();
			article.setArticleId(record.getInt("PK_ARTICLE"));
			article.setTitle(record.getStr("TITLE"));
			article.setOrigin(new Origin(record.getInt("IS_ORIGINAL")));
			article.setEditType(record.getInt("EDIT_TYPE"));
			article.setMaintxt(record.getStr("CONTENT"));
			article.setMainSource(record.getStr("PRE_CONTENT"));
			article.setCover(record.getStr("COVER_PICTURE"));
			article.setPublishedDate(DateUtil.getCurrDate(record.getDate("PUBLISHED_DATE"), "yyyy.MM.dd HH:mm:ss") );
			article.setReprint(record.getStr("REPRINT_ADDRESS"));
			article.setAuthor(new Member(record.getInt("PK_MEMBER"), record.getStr("LOGIN_NAME"), record.getStr("HEAD_PORTRAIT")));
			article.setCategory(new ArticleCategory(record.getInt("PK_ARTICLE_CATEGORY"), record.getStr("CATEGORY_NAME")));
			article.setFav(record.getLong("favs"));
			article.setLike(record.getLong("likes"));
			article.setTagNames(record.getStr("tagNames"));
			
			reList.add(article);
		}
		
		this.setReList(reList);
		return this;
	}
	
}

package com.portal.dto;

import java.util.ArrayList;
import java.util.List;

import com.biz.mgr.BizArticleTagMgr;
import com.biz.model.BizArticle;
import com.biz.model.BizArticleTag;
import com.frame.utils.DateUtil;
import com.portal.dto.base.BaseDto;

public class Article implements BaseDto<BizArticle, Article>{

	private Integer articleId;
	
	private String title;
	
	private Origin origin;
	
	private String maintxt;
	
	private Member author;
	
	private Long fav;
	
	private String cover;
	
	private String publishedDate;
	
	private Long like;
	
	private ArticleCategory category;
	
	private List<ArticleTag> tagclouds;
	
	private String reprint;
	
	public Integer getArticleId() {
		return articleId;
	}

	public void setArticleId(Integer articleId) {
		this.articleId = articleId;
	}

	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public Origin getOrigin() {
		return origin;
	}


	public void setOrigin(Origin origin) {
		this.origin = origin;
	}


	public String getMaintxt() {
		return maintxt;
	}


	public void setMaintxt(String maintxt) {
		this.maintxt = maintxt;
	}


	public Member getAuthor() {
		return author;
	}


	public void setAuthor(Member author) {
		this.author = author;
	}


	public Long getFav() {
		return fav;
	}


	public void setFav(Long fav) {
		this.fav = fav;
	}


	public String getCover() {
		return cover;
	}


	public void setCover(String cover) {
		this.cover = cover;
	}


	public String getPublishedDate() {
		return publishedDate;
	}


	public void setPublishedDate(String publishedDate) {
		this.publishedDate = publishedDate;
	}


	public Long getLike() {
		return like;
	}


	public void setLike(Long like) {
		this.like = like;
	}


	public ArticleCategory getCategory() {
		return category;
	}


	public void setCategory(ArticleCategory category) {
		this.category = category;
	}


	public List<ArticleTag> getTagclouds() {
		return tagclouds;
	}


	public void setTagclouds(List<ArticleTag> tagclouds) {
		this.tagclouds = tagclouds;
	}

	public String getReprint() {
		return reprint;
	}

	public void setReprint(String reprint) {
		this.reprint = reprint;
	}

	@Override
	public String toString() {
		return "Article [articleId=" + articleId + ", title=" + title
				+ ", origin=" + origin + ", maintxt=" + maintxt + ", author="
				+ author + ", fav=" + fav + ", cover=" + cover
				+ ", publishedDate=" + publishedDate + ", like=" + like
				+ ", category=" + category + ", tagclouds=" + tagclouds
				+ ", reprint=" + reprint + "]";
	}

	@Override
	public Article wrapper(BizArticle d) {
		if(null == d){
			return null;
		}
		this.setArticleId(d.getPkArticle());
		this.setTitle(d.getTITLE());
		this.setOrigin(new Origin(d.getIsOriginal()));
		this.setMaintxt(d.getCONTENT());
		this.setCover(d.getCoverPicture());
		this.setPublishedDate(DateUtil.getCurrDate(d.getPublishedDate(), "yyyy.MM.dd HH:mm:ss") );
		this.setReprint(d.getReprintAddress());
		this.setAuthor(new Member(d.getInt("PK_MEMBER"), d.getStr("LOGIN_NAME"), d.getStr("HEAD_PORTRAIT")));
		this.setCategory(new ArticleCategory(d.getInt("PK_ARTICLE_CATEGORY"), d.getStr("CATEGORY_NAME")));
		this.setFav(d.getLong("favs"));
		this.setLike(d.getLong("likes"));
		
		List<ArticleTag> resList = new ArrayList<ArticleTag>();
		List<BizArticleTag> list = BizArticleTagMgr.getInstance().getTagsByArticleId(d.getPkArticle());
		for (BizArticleTag bizArticleTag : list) {
			resList.add(new ArticleTag().wrapper(bizArticleTag));
		}
		this.setTagclouds(resList);
		
		return this;
	}

}

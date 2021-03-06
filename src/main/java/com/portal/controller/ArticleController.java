package com.portal.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.biz.commom.FefullEnum;
import com.biz.mgr.BizArticleCategoryMgr;
import com.biz.mgr.BizArticleCollectionMgr;
import com.biz.mgr.BizArticleMgr;
import com.biz.mgr.BizArticleTagMgr;
import com.biz.model.BizArticle;
import com.biz.model.BizArticleCategory;
import com.biz.model.BizArticleTag;
import com.biz.model.BizMember;
import com.frame.utils.Fun;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.portal.common.Constant;
import com.portal.common.RespResultEnum;
import com.portal.common.Response;
import com.portal.common.ThreadContext;
import com.portal.controller.annotation.PermessionLimit;
import com.portal.controller.annotation.RequestJsonClass;
import com.portal.controller.interceptor.PortalInterceptor;
import com.portal.dto.Article;
import com.portal.dto.ArticleCategory;
import com.portal.dto.ArticlePage;
import com.portal.dto.ArticleTag;
import com.portal.dto.request.ArticleListRequest;
import com.portal.dto.request.ArticleRequest;
import com.sys.bean.ReBean;

@Before(PortalInterceptor.class)
public class ArticleController extends Controller{
	
	/**
	 * 获取所有分类
	 * @return
	 */
	public Response categories(){
		List<BizArticleCategory> list = BizArticleCategoryMgr.getInstance().getAllCategory();
		List<ArticleCategory> reList = new ArrayList<ArticleCategory>();
		if(!Fun.eqListNull(list)){
			for (BizArticleCategory bizArticleCategory : list) {
				reList.add(new ArticleCategory().wrapper(bizArticleCategory));
			}
		}
		return new Response().success(reList);
	}
	
	/**
	 * 获取所有标签
	 * @return
	 */
	public Response tags(){
		List<BizArticleTag> list = BizArticleTagMgr.getInstance().getAllTags();
		List<ArticleTag> reList = new ArrayList<ArticleTag>();
		if(!Fun.eqListNull(list)){
			for (BizArticleTag bizArticleTag : list) {
				reList.add(new ArticleTag().wrapper(bizArticleTag));
			}
		}
		return new Response().success(reList);
	}
	
	/**
	 * 分页查询文件列表（支持条件查询）
	 * @return
	 */
	@RequestJsonClass(cls=ArticleListRequest.class)
	public Response queryPage(){
		ArticleListRequest articleListRequest = getAttr(Constant.REQUEST_PARAM);
		
		if(null == articleListRequest || null == articleListRequest.getNowPage() || null == articleListRequest.getPageSize()){
			return new Response().failure(RespResultEnum.PARAMETER_ERR);
		}
		
		Map<String, String> mQryMap = new HashMap<>();
		mQryMap.put("categoryId", articleListRequest.getCategoryId());
		mQryMap.put("tagCloudId", articleListRequest.getTagcloudId());
		mQryMap.put("keyword", articleListRequest.getKeyword());
		mQryMap.put("sort", articleListRequest.getSort());
		mQryMap.put("nowPage", articleListRequest.getNowPage());
		mQryMap.put("pageSize", articleListRequest.getPageSize());
		
		ReBean<Record> reBean = BizArticleMgr.getInstance().getPageByFront(mQryMap);
		
		return new Response().success(new ArticlePage().wrapper(reBean));
	}
	
	/**
	 * 根据Id获取文件详情
	 * @return
	 */
	@RequestJsonClass(cls=ArticleRequest.class)
	public Response getArticleById(){
		ArticleRequest articleRequest = getAttr(Constant.REQUEST_PARAM);
		if(null == articleRequest || null == articleRequest.getArticleId()){
			return new Response().failure(RespResultEnum.PARAMETER_ERR);
		}
		BizMember bizMember = (BizMember)ThreadContext.getContext().get(ThreadContext.KEY_USER_CONTEXT);
		BizArticle bizArticle = BizArticleMgr.getInstance().getObjById(articleRequest.getArticleId(), bizMember);
		
		return new Response().success(new Article().wrapper(bizArticle));
	}
	
	/**
	 * 创建/修改文章（根据ID判断）
	 * @return
	 */
	@PermessionLimit
	@RequestJsonClass(cls=ArticleRequest.class)
	@Before(Tx.class)
	public Response saveOrUpdate(){
		ArticleRequest articleRequest = getAttr(Constant.REQUEST_PARAM);
		BizMember bizMember = (BizMember)ThreadContext.getContext().get(ThreadContext.KEY_USER_CONTEXT);
		BizArticle bizArticle;
		if(Fun.eqNull(articleRequest.getArticleId())){
			bizArticle = BizArticleMgr.getInstance().save(bizMember, articleRequest);
		} else{
			bizArticle = BizArticleMgr.getInstance().update(bizMember, articleRequest);
		}
		
		return new Response().success(new Article().wrapper(bizArticle));
	}
	
	/**
	 * 修改文章
	 * @return
	 *//*
	@PermessionLimit
	@RequestJsonClass(cls=ArticleRequest.class)
	@Before(Tx.class)
	public Response update(){
		ArticleRequest articleRequest = getAttr(Constant.REQUEST_PARAM);
		BizMember bizMember = (BizMember)ThreadContext.getContext().get(ThreadContext.KEY_USER_CONTEXT);
		BizArticle bizArticle = BizArticleMgr.getInstance().update(bizMember, articleRequest);
		
		return new Response().success(new Article().wrapper(bizArticle));
	}*/
	
	/**
	 * 删除文章
	 * @return
	 */
	@PermessionLimit
	@RequestJsonClass(cls=ArticleRequest.class)
	@Before(Tx.class)
	public Response delete(){
		ArticleRequest articleRequest = getAttr(Constant.REQUEST_PARAM);
		BizMember bizMember = (BizMember)ThreadContext.getContext().get(ThreadContext.KEY_USER_CONTEXT);
		BizArticleMgr.getInstance().delete(bizMember, articleRequest);
		
		return new Response().success();
	}
	
	/**
	 * 获取我的文章列表
	 * @return
	 */
	@PermessionLimit
	@RequestJsonClass(cls=ArticleListRequest.class)
	public Response getMyArticles(){
		ArticleListRequest articleListRequest = getAttr(Constant.REQUEST_PARAM);
		if(null == articleListRequest || null == articleListRequest.getNowPage() || null == articleListRequest.getPageSize()){
			return new Response().failure(RespResultEnum.PARAMETER_ERR);
		}
		BizMember bizMember = (BizMember)ThreadContext.getContext().get(ThreadContext.KEY_USER_CONTEXT);
		Map<String, String> mQryMap = new HashMap<>();
		mQryMap.put("categoryId", articleListRequest.getCategoryId());
		mQryMap.put("sort", String.valueOf(FefullEnum.Sort.NEWEST.getValue()));
		mQryMap.put("nowPage", articleListRequest.getNowPage());
		mQryMap.put("pageSize", articleListRequest.getPageSize());
		mQryMap.put("pkMember", String.valueOf(bizMember.getPkMember()));
		
		ReBean<Record> reBean = BizArticleMgr.getInstance().getPageByFront(mQryMap);
		
		return new Response().success(new ArticlePage().wrapper(reBean));
	}
	
	/**
	 * 收藏文章
	 * @return
	 */
	@PermessionLimit
	@RequestJsonClass(cls=ArticleRequest.class)
	@Before(Tx.class)
	public Response collection(){
		ArticleRequest articleRequest = getAttr(Constant.REQUEST_PARAM);
		if(null == articleRequest || null == articleRequest.getArticleId()){
			return new Response().failure(RespResultEnum.PARAMETER_ERR);
		}
		BizMember bizMember = (BizMember)ThreadContext.getContext().get(ThreadContext.KEY_USER_CONTEXT);
		if(BizArticleCollectionMgr.getInstance().isExist(bizMember.getPkMember(), articleRequest.getArticleId())){
			return new Response().failure(RespResultEnum.DATA_EXIST);
		}
		BizArticleCollectionMgr.getInstance().save(bizMember, articleRequest);
		return new Response().success();
	}
	
	/**
	 * 取消收藏文章
	 * @return
	 */
	@PermessionLimit
	@RequestJsonClass(cls=ArticleRequest.class)
	@Before(Tx.class)
	public Response uncollection(){
		ArticleRequest articleRequest = getAttr(Constant.REQUEST_PARAM);
		if(null == articleRequest || null == articleRequest.getArticleId()){
			return new Response().failure(RespResultEnum.PARAMETER_ERR);
		}
		BizMember bizMember = (BizMember)ThreadContext.getContext().get(ThreadContext.KEY_USER_CONTEXT);
		if(!BizArticleCollectionMgr.getInstance().isExist(bizMember.getPkMember(), articleRequest.getArticleId())){
			return new Response().failure(RespResultEnum.DATA_NOEXIST);
		}
		BizArticleCollectionMgr.getInstance().delete(bizMember, articleRequest);
		return new Response().success();
	}
	
	/**
	 * 分页查询收藏文章列表
	 * @return
	 */
	@PermessionLimit
	@RequestJsonClass(cls=ArticleListRequest.class)
	public Response queryCollectionPage(){
		ArticleListRequest articleListRequest = getAttr(Constant.REQUEST_PARAM);
		
		if(null == articleListRequest || null == articleListRequest.getNowPage() || null == articleListRequest.getPageSize()){
			return new Response().failure(RespResultEnum.PARAMETER_ERR);
		}
		BizMember bizMember = (BizMember)ThreadContext.getContext().get(ThreadContext.KEY_USER_CONTEXT);
		Map<String, String> mQryMap = new HashMap<>();
		mQryMap.put("categoryId", articleListRequest.getCategoryId());
		mQryMap.put("tagCloudId", articleListRequest.getTagcloudId());
		mQryMap.put("nowPage", articleListRequest.getNowPage());
		mQryMap.put("pageSize", articleListRequest.getPageSize());
		mQryMap.put("memberId", String.valueOf(bizMember.getPkMember()));
		
		ReBean<Record> reBean = BizArticleMgr.getInstance().getPageByFront(mQryMap);
		
		return new Response().success(new ArticlePage().wrapper(reBean));
	}
	
}

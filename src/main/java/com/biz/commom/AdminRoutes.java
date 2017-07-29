package com.biz.commom;

import com.biz.controller.BizArticleCategoryController;
import com.biz.controller.BizArticleCommentController;
import com.biz.controller.BizArticleController;
import com.biz.controller.BizArticleTagController;
import com.biz.controller.BizMemberController;
import com.biz.controller.BizNavigatorCategoryController;
import com.jfinal.config.Routes;

public class AdminRoutes extends Routes {

	@Override
	public void config() {
		add("/member", BizMemberController.class);
		add("/articleCategory", BizArticleCategoryController.class);
		add("/article", BizArticleController.class);
		add("/comment", BizArticleCommentController.class);
		add("/tag", BizArticleTagController.class);
		add("/navigatorCategory", BizNavigatorCategoryController.class);
		add("/articleTag", BizArticleTagController.class);
	}

}

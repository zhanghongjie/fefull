package com.portal.common;

import com.jfinal.config.Routes;
import com.portal.controller.ArticleController;
import com.portal.controller.LoginController;
import com.portal.controller.NavigatorController;
import com.portal.controller.UeditorController;

public class FrontRoutes extends Routes{

	@Override
	public void config() {
		add("/api/login", LoginController.class);
		add("/api/article", ArticleController.class);
		add("/api/ueditor", UeditorController.class);
		add("/api/navigator", NavigatorController.class);
	}

}

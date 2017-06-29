package com.frame.common;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;

import org.beetl.core.GroupTemplate;

import com.biz.commom.AdminRoutes;
import com.biz.model._MappingKit;
import com.frame.boot.BootRuner;
import com.frame.cache.AppInfoCache;
import com.frame.quartz.QuartzPlugin;
import com.frame.render.MyBeetlRenderFactory;
import com.frame.sms.service.SmsService;
import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.core.JFinal;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.druid.DruidPlugin;
import com.jfinal.render.ViewType;
import com.portal.common.FrontRoutes;
import com.sys.boot.InitDimCfg;
import com.sys.boot.InitLanCfg;
import com.sys.controller.CacheController;
import com.sys.controller.CommonController;
import com.sys.controller.FileUpController;
import com.sys.controller.IndexController;
import com.sys.controller.LanSettingCfgController;
import com.sys.controller.LanguageCfgController;
import com.sys.controller.LoadImgController;
import com.sys.controller.LoginController;
import com.sys.controller.MenuController;
import com.sys.controller.QrCodeController;
import com.sys.controller.RoleController;
import com.sys.controller.SysAreaController;
import com.sys.controller.SysDictController;
import com.sys.controller.VerifiedController;
/**
 * API引导式配置
 */
public class Config extends JFinalConfig {

	/**
	 * 配置常量
	 */
	public void configConstant(Constants me) {
		// 加载少量必要配置，随后可用getProperty(...)获取值
		loadPropertyFile("config.txt");
	    me.setMainRenderFactory(new MyBeetlRenderFactory());
	    // 获取GroupTemplate模板，可以设置共享变量操作
	    GroupTemplate groupTemplate=MyBeetlRenderFactory.groupTemplate;
		AppInfoCache.getInstance().setGroupTemplate(groupTemplate);
	    me.setDevMode(getPropertyToBoolean("config.devModel", false));
	    me.setViewType(ViewType.JSP);
	    me.setEncoding("UTF-8");

	    // 设置出错页面跳转
	    me.setError404View("/manage/forbid.html");
	}

	/**
	 * 配置路由
	 */
	public void configRoute(Routes me) {
		/*me.add("/", IndexController.class, "/index");	// 第三个参数为该Controller的视图存放路径
		me.add("/blog", BlogController.class);			// 第三个参数省略时默认与第一个参数值相同，在此即为 "/blog"*/
		me.add("/", CommonController.class);
		me.add("/login", LoginController.class);
		me.add("/index", IndexController.class);
		me.add("/vcode",VerifiedController.class);
		me.add("/qrCode",QrCodeController.class);
		me.add("/menu", MenuController.class);
		me.add("/languageCfg", LanguageCfgController.class);
		me.add("/lanSettingCfg", LanSettingCfgController.class);
		me.add("/role", RoleController.class);
		me.add("/dimTable", SysDictController.class);
		me.add("/cache", CacheController.class);
		me.add("/fileUp", FileUpController.class);
		me.add("/loadImg", LoadImgController.class);
		me.add("/area", SysAreaController.class);
		me.add("/dict", SysDictController.class);
		
		//具体业务路径映射
		me.add(new AdminRoutes());	// 后端路由
		me.add(new FrontRoutes());  // 前端路由
	}

	/**
	 * 配置插件
	 */
	public void configPlugin(Plugins me) {
		// 配置Druid数据库连接池插件
		DruidPlugin druidPlugin = new DruidPlugin(getProperty("jdbcUrl"), getProperty("user"), getProperty("password"));
		//配置初始化大小、最小、最大
		druidPlugin.setInitialSize(getPropertyToInt("initialSize"));
		druidPlugin.setMinIdle(getPropertyToInt("minIdle"));
		druidPlugin.setMaxActive(getPropertyToInt("maxActive"));
		me.add(druidPlugin);

		// 配置ActiveRecord插件
		ActiveRecordPlugin arp = new ActiveRecordPlugin(druidPlugin);
		me.add(arp);
		_MappingKit.mapping(arp);
		//arp.addMapping("blog", Blog.class);	// 映射blog 表到 Blog模型


        //Quartz定时任务
		QuartzPlugin quartz = new QuartzPlugin();
        quartz.setJobs("jobs.properties");
        me.add(quartz);
	}

	/**
	 * 配置全局拦截器
	 */
	public void configInterceptor(Interceptors me) {

	}

	/**
	 * 配置处理器
	 */
	public void configHandler(Handlers me) {

	}


	@Override
	public void afterJFinalStart() {
		super.afterJFinalStart();
		//1.初始化任务
	    BootRuner br = new BootRuner();
	    br.addBootable(new InitDimCfg());
	    br.addBootable(new InitLanCfg());
	    br.addBootable(new SmsService());
	    br.run();

		ServletContext servletContext = JFinal.me().getServletContext();
		//2.设置web 根目录
	    //servletContext.setAttribute("ctx", servletContext.getContextPath());
		GroupTemplate gt = AppInfoCache.getInstance().getGroupTemplate();
		Map<String,Object> shared = new HashMap<String,Object>();
		shared.put("ctx", servletContext.getContextPath());
		gt.setSharedVars(shared);
	}

	/**
	 * 建议使用 JFinal 手册推荐的方式启动项目
	 * 运行此 main 方法可以启动项目，此main方法可以放置在任意的Class类定义中，不一定要放于此
	 */
	public static void main(String[] args) {
		JFinal.start("WebRoot", 80, "/", 5);
	}
}

package com.sys.beetl;

import javax.servlet.http.HttpServletRequest;

import org.beetl.core.Context;
import org.beetl.core.Function;

import com.biz.model.SysLanguageCfg;
import com.frame.utils.Fun;
import com.sys.cache.SysInfoCache;
import com.sys.util.LanCfg;

/**
 * @Title: LanguageCfgFun.java
 * @Package com.sys.beetl
 * @Description: 语言配置beetl模板自定义方法
 * @author sos
 * @date 2016年5月12日 下午6:10:05
 * @version V1.0
 */
public class LanguageCfgFun implements Function {

	@Override
	public String call(Object[] paras, Context ctx) {
		HttpServletRequest req = (HttpServletRequest)ctx.getGlobal("request");
		Object obj = paras[0];
		if (Fun.eqNull(obj)) {
			return "";
		}
		SysLanguageCfg sysLanguageCfg = SysInfoCache.getInstance().getLanMap().get(obj);
		if(Fun.eqNull(sysLanguageCfg)){
			return obj.toString();
		}else{
			return LanCfg.getInstance().get(obj.toString(),req);
		}
	}
}

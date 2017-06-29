package com.sys.controller;

import java.util.HashMap;
import java.util.Map;

import com.biz.model.SysUser;
import com.frame.constants.Constants;
import com.frame.constants.SysEnum;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.sys.bean.ReBean;
import com.sys.cache.SysInfoCache;
import com.sys.interceptor.AuthorityCharacterITR;
import com.sys.util.LanCfg;
/**   
* @Title: LanCfgController.java 
* @Package com.sys.controller 
* @Description: 语言展示配置 
* @author sos
* @date 2016年5月13日 下午6:11:29 
* @version V1.0   
*/
@Before({AuthorityCharacterITR.class})
public class LanSettingCfgController extends Controller {
	private ReBean<SysUser>  reBean;
	public void index(){
		Map<Integer,String> map = new HashMap<Integer, String>();
		map.put(SysEnum.LAN_CFG.CN.getValue(), LanCfg.getInstance().get("中文",getRequest()));
		map.put(SysEnum.LAN_CFG.EN.getValue(), LanCfg.getInstance().get("英文",getRequest()));
		map.put(SysEnum.LAN_CFG.AR_SA.getValue(), LanCfg.getInstance().get("阿拉伯文",getRequest()));
		setAttr("lanAll", map);
		
		//当前的语言配置,默认中文
		setAttr("defLanCfg",SysEnum.LAN_CFG.CN.getValue());
		
		render("/manage/sys/lanSettingCfg.html");
	}
	/**
	 * 更新数据
	 */
	public void update() {
		reBean = new ReBean<SysUser>();
		SysUser sysUser = SysInfoCache.getInstance().getUserInfoBean(getRequest()).getSysUser();
		//sysUser.setLanType(Integer.parseInt(getPara("lanType")));
		sysUser.update();
		reBean.setFlag(Constants.SUCCESS);
		renderJson(reBean);
	}
}

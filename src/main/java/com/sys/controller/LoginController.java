package com.sys.controller;

import java.util.Map;

import com.frame.constants.Constants;
import com.frame.utils.ArgsTool;
import com.frame.utils.DateUtil;
import com.frame.utils.Fun;
import com.frame.utils.MD5;
import com.jfinal.core.Controller;
import com.sys.bean.ReBean;
import com.sys.bean.UserInfoBean;
import com.sys.cache.SysInfoCache;
import com.sys.mgr.UserMgr;

/**
 * LoginController
 */
public class LoginController extends Controller {
    private Map<String, String> qryMap;

    // cookies过期时间
    // private int cookiesTime = 1000*60*60*24*7;
    public void index() {
        // 有设置记住密码
        if (!Fun.eqNull(getCookie("keepPass"))) {
            setAttr("userName", getCookie("userName"));
            setAttr("userPass", getCookie("userPass"));
            setAttr("keepPass", "checked=\"checked\"");
        }

        render("/manage/login.html");
    }

    /**
     * 登录校验
     */
    public void verify() {
        ReBean<String> reBean = new ReBean<String>();
        qryMap = ArgsTool.getParameterMap(getParaMap());
        // 获取验证码
        String verifyCode = qryMap.get("verifyCode");
        Object code = getRequest().getSession().getAttribute(Constants.VERITY_CODE_KEY);
        if (code == null) {
            reBean.setFlag(Constants.ERROR);
            reBean.setMsg(Constants.VERITY_ERROR);
            renderJson(reBean);
            return;
        }
        if (!verifyCode.equalsIgnoreCase(code.toString())) {
            reBean.setFlag(Constants.ERROR);
            reBean.setMsg(Constants.VERITY_ERROR);
            renderJson(reBean);
            return;
        }
        /*
         * SysUser bean = null; if(getPara("keepPass")!=null){
         * if(getPara("keepPass").equals("true")){ Map<String,String>
         * qryCookieMap = new HashMap<String, String>();
         * if(qryMap.get("userName").equals(getCookie("userName"))){
         * qryCookieMap.put("userName", getCookie("userName"));
         * qryCookieMap.put("userPass",
         * DESUtils.decrypt(getCookie("userPass"))); bean =
         * SysInfoCache.getInstance().getUserInfoBean(); } } }
         */
        UserInfoBean bean = UserMgr.getInstance().getUserInfo(qryMap);
        if (Fun.eqNull(bean)) {
            reBean.setFlag(Constants.ERROR);
            reBean.setMsg(Constants.LOGIN_ERROR);
        }
        /*
         * else
         * if(Constants.USER_INVALID.equals(bean.get("ustats").toString().trim()
         * )){ //用户失效状态 reBean.setRecode(Constants.ERROR);
         * reBean.setMsg(Constants.LOGIN_INVALID); }
         */
        else {
            // 设置登录用户缓存
            /*
             * if(Fun.eqNull(SysInfoCache.getInstance().getSession())){
             * SysInfoCache.getInstance().setSession(getSession()); }
             */
            SysInfoCache.getInstance().setUserInfoBean(bean, getRequest());
            
            String vcode = MD5.encrypt(DateUtil.getCurrDate("yyyyMMddHHmmss"));
            bean.setVcode(vcode);
            bean.initMenu(bean, getRequest());
            getRequest().getSession().removeAttribute(Constants.VERITY_CODE_KEY);
            reBean.setFlag(Constants.SUCCESS);
            reBean.setMsg(bean.getSysUser().getUserName() + "用户登录成功！");
            reBean.setObj(vcode);
        }
        renderJson(reBean);
    }

    /**
     * 退出登录
     */
    public void logout() {
        // 清空用户session
        SysInfoCache.getInstance().setUserInfoBean(null, getRequest());
        // 有设置记住密码
        /*
         * if(!Fun.eqNull(getCookie("keepPass"))){
         * setAttr("userName",getCookie("userName"));
         * setAttr("userPass",getCookie("userPass"));
         * setAttr("keepPass","checked=\"checked\""); }
         */
        forwardAction("/login");
    }
}
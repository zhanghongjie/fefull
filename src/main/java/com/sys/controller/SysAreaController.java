package com.sys.controller;

import java.util.ArrayList;
import java.util.List;

import com.biz.model.SysArea;
import com.frame.constants.Constants;
import com.frame.exception.BusinessException;
import com.frame.utils.DateUtil;
import com.frame.utils.Fun;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.sys.bean.ReBean;
import com.sys.interceptor.AuthorityCharacterITR;
import com.sys.mgr.SysAreaMgr;
import com.sys.util.LanCfg;

/**   
* @Title: SysAreaController.java 
* @Package com.sys.controller 
* @Description: 地区表查询
* @author sos
* @date 2016年8月14日 上午11:49:38 
* @version V1.0   
*/
@Before({ AuthorityCharacterITR.class })
public class SysAreaController extends Controller {

    private ReBean<SysArea> reBean;

    public void index() {
    }

    /**
     * 分页查询
     */
    public void list() {
        renderJson(SysAreaMgr.getInstance().getPage(Fun.getPara(getParaMap())));
    }


    /**
     * 分步取地区节点
     */
    public void getNodes() {
        List<SysArea> list = this.reNodes4Pid(getPara("pid"));
        renderJson(list);
    }

    /**
     * 取地区初始化的值
     */
    public void getInit() {
        List<List<SysArea>> reList = new ArrayList<List<SysArea>>();
        List<SysArea> list = this.reNodes4Pid("0");
        if (Fun.eqListNull(list)) {
            throw new BusinessException("sys_area表未初始化数据！");
        }
        reList.add(list);
        list = this.reNodes4Pid(list.get(0).getID() + "");
        if (!Fun.eqListNull(list)) {
        	reList.add(list);
        	list = this.reNodes4Pid(list.get(0).getID() + "");
        	if (!Fun.eqListNull(list)) {
        		reList.add(list);
        	}
        }
        renderJson(reList);
    }

   /* *//**
     * 初始化
     *//*
    public void getInitNodes2NoSelect() {
        List<List<BizDeliverRegion>> reList = new ArrayList<List<BizDeliverRegion>>();
        List<BizDeliverRegion> list = this.reNodes4Level("1");
        if (Fun.eqListNull(list)) {
            throw new BusinessException("biz_deliver_region表未初始化数据！");
        }
        reList.add(list);
        list = this.reNodes4Pid(list.get(0).getPkDeliverRegion() + "");
        if (!Fun.eqListNull(list)) {
            reList.add(list);
            list = this.reNodes4Pid(list.get(0).getPkDeliverRegion() + "");
            if (!Fun.eqListNull(list)) {
                reList.add(list);
            }
        }

        renderJson(reList);
    }*/

    private List<SysArea> reNodes4Level(String level) {
        StringBuilder sb = new StringBuilder("SELECT * FROM sys_area t ");
        sb.append(" and REGION_LEVEL = " + level);
        sb.append(" order by REGION_NAME ");
        List<SysArea> list = SysArea.dao.find(sb.toString());
        return list;
    }

    private List<SysArea> reNodes4Pid(String pid) {
        StringBuilder sb = new StringBuilder("SELECT * FROM sys_area t where 1=1");
        if (Fun.eqNull(pid) || pid.equals("0")) {
            sb.append(" and pid=0 and id!=0");
        } else {
            sb.append(" and pid=" + pid);
        }
        return SysArea.dao.find(sb.toString());
    }

    /**
     * 根据id编号取地址
     * @param id
     * @return
     */
    public SysArea reNodes4Id(String id) {
        StringBuilder sb = new StringBuilder(
                "SELECT * FROM sys_area t WHERE id = ?");
        return SysArea.dao.findFirst(sb.toString(), id);
    }


    public boolean checkParams(int level, int pid) {
        if (Fun.eqNull(getPara("REGION_NAME"))) {
            reBean.setFlag(Constants.ERROR);
            reBean.setMsg(LanCfg.getInstance().get("未输入名字", getRequest()));
            renderJson(reBean);
            return false;
        }

        if (level < 1 || level > 3) {
            reBean.setFlag(Constants.ERROR);
            reBean.setMsg(LanCfg.getInstance().get("层级输入错误", getRequest()) + "(" + level + ")");
            renderJson(reBean);
            return false;
        }

        if (pid < 0 || (level > 1 && pid == 0)) {
            reBean.setFlag(Constants.ERROR);
            reBean.setMsg(LanCfg.getInstance().get("上层id输入错误", getRequest()) + "(" + pid + ")");
            renderJson(reBean);
            return false;
        }

        if (!Fun.eqNull(getPara("LATITUDE"))) {
            if (!DateUtil.isNumeric(getPara("LATITUDE"))) {
                reBean.setFlag(Constants.ERROR);
                reBean.setMsg(LanCfg.getInstance().get("坐标输入错误", getRequest()) + "(" + getPara("LATITUDE") + ")");
                renderJson(reBean);
                return false;
            }
        }

        if (!Fun.eqNull(getPara("LONGITUDE"))) {
            if (!DateUtil.isNumeric(getPara("LONGITUDE"))) {
                reBean.setFlag(Constants.ERROR);
                reBean.setMsg(LanCfg.getInstance().get("坐标输入错误", getRequest()) + "(" + getPara("LONGITUDE") + ")");
                renderJson(reBean);
                return false;
            }
        }

        return true;
    }

    public boolean checkParamsForEdit(int deliverId, int level, int pid) {
        // 是否存在区域
        if (deliverId <= 0) {
            reBean.setFlag(Constants.ERROR);
            reBean.setMsg(LanCfg.getInstance().get("未选择区域" + deliverId, getRequest()));
            renderJson(reBean);
            return false;
        }

        return checkParams(level, pid);
    }

    public boolean checkParamsForDel(int deliverId) {
        // 是否存在区域
        if (deliverId <= 0) {
            reBean.setFlag(Constants.ERROR);
            reBean.setMsg(LanCfg.getInstance().get("未选择区域" + deliverId, getRequest()));
            renderJson(reBean);
            return false;
        }

        return true;
    }
}

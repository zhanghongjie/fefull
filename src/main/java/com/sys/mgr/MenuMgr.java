package com.sys.mgr;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import com.biz.model.SysMenu;
import com.frame.utils.ArgsTool;
import com.frame.utils.Fun;
import com.jfinal.plugin.activerecord.Record;
import com.sys.bean.ReBean;
import com.sys.bean.UserInfoBean;
import com.sys.cache.SysInfoCache;

/**
 * 菜单
 *
 * @author lwyx
 *
 */
public class MenuMgr {
	private MenuMgr() {

	}

	public static MenuMgr getInstance() {
		return SysMenuMgrHolder.instance;
	}

	private static class SysMenuMgrHolder {
		private static MenuMgr instance = new MenuMgr();
	}
	/**
	 * 取菜单
	 *
	 * @param qryMap
	 * @return
	 */
	public List<SysMenu> getMenu() {
		return this.getMenu(null);
	}
	/**
	 * 取菜单
	 *
	 * @param qryMap
	 * @return
	 */
	public List<SysMenu> getMenu(Map<String, String> mQryMap) {
		String sql = "select * from sys_menu";
		return ArgsTool.getList(SysMenu.dao, sql, mQryMap);
	}
	/**
	 * 取所有菜单
	 */
	public List<List<SysMenu>> getAllMenu4Class() {
		String sql = "SELECT * FROM `sys_menu` a ";
		List<SysMenu> list = ArgsTool.getList(SysMenu.dao, sql, null);
		if(!Fun.eqNull(list)){
			Map<String,String> map = new TreeMap<String, String>();
			for (SysMenu item : list) {
				map.put(item.get("PK_MENU")+"", item.get("MENU_NAME")+"");
			}
		}
		return this.classMenu(list);
	}
	/**
	 * 取所有菜单(包含角色)
	 */
	public List<List<Record>> getAllMenu4Role() {
		List<List<Record>> reList = null;
		String sql = "SELECT c.*,a.`ROLE_NAME`,  a.`PK_ROLE` FROM  `sys_role` a   LEFT JOIN sys_role_menu b ON a.`PK_ROLE` = b.`FK_ROLE`  LEFT JOIN sys_menu c     ON b.`FK_MENU`=c.`PK_MENU`WHERE a.`ROLE_STATUS` = 1 ";
		List<Record> list = ArgsTool.getList(sql);
		if(!Fun.eqListNull(list)){
			String tmp = null;
			List<Record> tmpList = null;
			reList = new ArrayList<List<Record>>();
			for (Record record : list) {
				if(Fun.eqNull(tmp) || !tmp.equals(record.getStr("ROLE_NAME"))){
					if(!Fun.eqNull(tmp) && !Fun.eqListNull(tmpList)){
						reList.add(tmpList);
					}
					tmp = record.getStr("ROLE_NAME");
					tmpList = new ArrayList<Record>();
				}
				tmpList.add(record);
			}
			reList.add(tmpList);
		}
		
		
		return reList;
	}
	/**
	 * 取用户对应菜单权限
	 *
	 * @param qryMap
	 * @return
	 */
	public List<SysMenu> getUserRightMenu(UserInfoBean userInfoBean) {
		Map<String,String> mQryMap = new HashMap<String, String>();
		mQryMap.put("pkRole", userInfoBean.getSysRole().getPkRole()+"");
		String sql = "SELECT a.* FROM `sys_menu` a LEFT JOIN `sys_role_menu` b ON a.PK_MENU = b.`FK_MENU` WHERE b.`FK_ROLE` = @pkRole order by pk_menu,ORDER_NO  DESC";
		return ArgsTool.getList(SysMenu.dao, sql, mQryMap);
	}
	/**
	 * 取用户对应菜单(已分类)
	 *
	 * @param qryMap
	 * @return
	 */
	public void getUserMenu4Class(UserInfoBean userInfoBean,HttpServletRequest req) {
		List<SysMenu> list = this.getUserRightMenu(userInfoBean);
		//用户拥有的菜单放入内存
		UserInfoBean bean = SysInfoCache.getInstance().getUserInfoBean(req);
		if(!Fun.eqNull(list) && !Fun.eqNull(bean)){
			Map<String,String> map = new TreeMap<String, String>();
			for (SysMenu item : list) {
				map.put(item.get("PK_MENU")+"", item.get("MENU_NAME")+"");
			}
			bean.setMenu(map);
			bean.setMenuList(this.classMenu(list));
		}
	}
	/**
	 * 将菜单数据分类返回
	 *
	 * @param list
	 * @return
	 */
	private List<List<SysMenu>> classMenu(List<SysMenu> list) {
		if(Fun.eqNull(list)){
			return null;
		}
		List<List<SysMenu>> reList = null;
		Map<String, List<SysMenu>> map = null;
		if (!Fun.eqListNull(list)) {
			map = new HashMap<String, List<SysMenu>>();
			reList = new ArrayList<List<SysMenu>>();
			for (int i = 0; i < list.size(); i++) {
				// pid=0,顶级菜单节点
				if (list.get(i).get("PID").toString().trim().equals("0")) {
					List<SysMenu> tempList = new ArrayList<SysMenu>();
					tempList.add(list.get(i));
					map.put(list.get(i).get("PK_MENU").toString(), tempList);
				}
			}
			String index = "";
			for (int i = 0; i < list.size(); i++) {
				// pid=!0,非顶级菜单节点
				if (!list.get(i).get("PID").toString().trim().equals("0")) {
					System.out.println(list.get(i).getPID()+" "+list.get(i).getMenuName()+" "+list.get(i).getPkMenu());
					index = list.get(i).get("PID")+"";
					if(!Fun.eqNull(map.get(index))){
						map.get(index).add(list.get(i));
					}else{
						System.out.println(index);
					}
				}
			}
			Iterator<Map.Entry<String, List<SysMenu>>> it = map.entrySet().iterator();
			while (it.hasNext()) {
				reList.add(it.next().getValue());
			}
		}
		return reList;
	}
	/**
	 * 分页查询-取所有菜单
	 * @return
	 */
	public ReBean<Record> getPage(Map<String, String> mQryMap){
		ReBean<Record> reBean = null;
		StringBuilder sb = new StringBuilder("select * from sys_menu order by pk_menu,ORDER_NO  desc");
		if (!Fun.eqNull(mQryMap)) {
			reBean = ArgsTool.getPageObj(this.reWhereSql(mQryMap,sb), mQryMap);
		}
		return reBean;
	}
	/**
	 * @param mQryMap
	 */
	private String reWhereSql(Map<String, String> mQryMap,StringBuilder sb){
		StringBuilder msb = new StringBuilder();
		if(!Fun.eqNull(mQryMap.get("menuName"))){
			msb.append(" and MENU_NAME like @menuName ");
		}
		msb.append(" and MENU_STATUS=1 and IS_VALID=1 ");
		return ArgsTool.reSql(sb, msb);
	}
	
	public boolean isRepeat(Map<String, String> mQryMap){
		return false;
	}
}

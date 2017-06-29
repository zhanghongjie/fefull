package com.sys.mgr;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import com.biz.model.SysMenu;
import com.biz.model.SysRole;
import com.biz.model.SysRoleMenu;
import com.frame.constants.Constants;
import com.frame.utils.ArgsTool;
import com.frame.utils.Fun;
import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.tx.Tx;

/**   
* @Title: RoleMgr.java 
* @Package com.sys.mgr 
* @Description: 角色管理
* @author sos
* @date 2016年5月16日 下午5:32:22 
* @version V1.0   
*/
public class RoleMgr {
	private RoleMgr() {

	}

	public static RoleMgr getInstance() {
		return RoleMgrHolder.instance;
	}

	private static class RoleMgrHolder {
		private static RoleMgr instance = new RoleMgr();
	}

	/**
	 * 增加角色对应菜单
	 * @param mQryMap
	 * @return
	 */
	public Map<String,String> addRoleMenu(Map<String, String> mQryMap,HttpServletRequest req) {
		Map<String,String> reMap = new HashMap<String, String>();
		reMap.put("flag", Constants.ERROR);
		if (!Fun.eqNull(mQryMap)) {
			String roleName = (String)mQryMap.get("roleName");
			SysRole role = new SysRole().set("ROLE_NAME", roleName);
			ArgsTool.addObj(role,req);
			Integer roleId = role.getInt("PK_ROLE");
			String menus = (String)mQryMap.get("menuList");
			if(!Fun.eqNull(menus)){
				String[] menuList = menus.split("0X22");
				if(!Fun.eqNull(menuList)){
					SysRoleMenu sysRoleMenu = null;
					for (String item : menuList) {
						sysRoleMenu = new SysRoleMenu();
						sysRoleMenu.setFkRole(roleId);
						sysRoleMenu.setFkMenu(Integer.parseInt(item));
						ArgsTool.addObj(sysRoleMenu,req);
					}
				}
			}
			reMap.put("flag", Constants.SUCCESS);
		}
		return reMap;
	}
	/**
	 * 编辑角色对应菜单
	 * @param qryMap
	 * @return
	 */
	@Before(Tx.class)
	public Map<String,String> editRoleMenu(Map<String, String> mQryMap,HttpServletRequest req) {
		Map<String,String> reMap = new HashMap<String, String>();
		reMap.put("flag", Constants.ERROR);
		if(Fun.eqNull(mQryMap.get("roleId"))){
			reMap.put("msg", "roleId不能为空！");
			return reMap;
		}
		if (!Fun.eqNull(mQryMap)) {
			Db.update("delete from sys_role_menu where FK_ROLE = "+mQryMap.get("roleId"));
			String menus = (String)mQryMap.get("menuList");
			if(!Fun.eqNull(menus)){
				String[] menuList = menus.split("0X22");
				if(!Fun.eqNull(menuList)){
					SysRoleMenu sysRoleMenu = null;
					for (String item : menuList) {
						if(!Fun.eqNull(item) && !item.trim().equals("")){
							sysRoleMenu = new SysRoleMenu();
							sysRoleMenu.setFkRole(Integer.parseInt(mQryMap.get("roleId")));
							sysRoleMenu.setFkMenu(Integer.parseInt(item));
							ArgsTool.addObj(sysRoleMenu,req);
						}
					}
				}
			}
			reMap.put("flag", Constants.SUCCESS);
		}
		return reMap;
	}
	/**
	 * 取角色对应菜单
	 * 
	 * @param qryMap
	 * @return
	 */
	public List<SysMenu> getRoleMenu(Map<String, String> mQryMap) {
		if (!Fun.eqNull(mQryMap)) {
			String qrySql = "SELECT a.* FROM sys_menu a LEFT JOIN sys_role_menu b ON a.`PK_MENU` =  b.`FK_MENU` WHERE b.`FK_ROLE` = @fkRole";
			return ArgsTool.getList(SysMenu.dao, qrySql, mQryMap);
		}
		return null;
	}
	/**
	 * @param mQryMap
	 * @return
	 */
	@Before(Tx.class)
	public boolean delRoleById(Map<String, String> mQryMap) {
		boolean bool = false;
		if(!Fun.eqNull(mQryMap)){
			Db.update("delete from sys_role_menu where FK_ROLE = "+mQryMap.get("roleId"));
			Db.update("delete from sys_role where PK_ROLE = "+mQryMap.get("roleId"));
		}
		return bool;
	}
	
	
	public List<Record> getRoleMenu4Edit(Map<String, String> mQryMap) {
		List<Record> list = null;
		if (!Fun.eqNull(mQryMap) && !Fun.eqNull(mQryMap.get("roleId"))) {
			String qrySql = "SELECT a.`MENU_NAME`,c.PK_ROLE, c.ROLE_NAME, a.`PID`, a.`PK_MENU` FROM sys_menu a LEFT JOIN   (SELECT     FK_ROLE,      FK_MENU   FROM     `sys_role_menu`   WHERE FK_ROLE = @roleId) b   ON a.PK_MENU = b.FK_MENU LEFT JOIN `sys_role` c  ON b.FK_ROLE = c.`PK_ROLE`";
			list = ArgsTool.getList(qrySql,mQryMap);
		}
		//对查询出来的菜单进行排序
		
		return this.sortMenu(list);
	}
	/**
	 * 菜单排序
	 * @param list
	 * @return
	 */
	private List<Record> sortMenu(List<Record> list){
		List<Record> reList= null;
		if(!Fun.eqNull(list)){
			reList = new ArrayList<Record>();
			Map<String,List<Record>>  map = new TreeMap<String,List<Record>>();
			for (Record record : list) {
				if(!Fun.eqNull(record.get("PID")) && record.get("PID").toString().equals("0")){
					map.put(record.get("PK_MENU").toString(), new ArrayList<Record>());
					map.get(record.get("PK_MENU").toString()).add(record);
				}
			}
			for (Record record : list) {
				if(!Fun.eqNull(record.get("PID")) && !Fun.eqNull(map.get(record.get("PID").toString()))){
					map.get(record.get("PID").toString()).add(record);
				}
			}
			
			Set<Entry<String, List<Record>>> set = map.entrySet();
			Iterator<Entry<String, List<Record>>>  it = set.iterator();
			while (it.hasNext()) {
				for (Record record : it.next().getValue()) {
					reList.add(record);
				}
			}
		}
		return reList;
	}
	
	/**
	 *取角色对应菜单已分类 
	 * 
	 * @param qryMap
	 * @return
	 *//*
	public List<List<SysMenu>> getRoleMenu4Class(Map<String, String> mQryMap) {
		if (!Fun.eqNull(mQryMap)) {
			return this.classRoleMenu(this.getRoleMenu(mQryMap));
		}
		return null;
	}
	*//**
	 * 取锟矫伙拷锟斤拷应锟剿碉拷权锟斤拷
	 * 
	 * @param qryMap
	 * @return
	 *//*
	public List<VmUserMenuRightModel> getUserRightMenu(Map<String, String> mQryMap) {
		if (!Fun.equalsNull(mQryMap)) {
			String qrySql = "select * from v_m_user_menu_right";
			Map<String, String> qryMap = new HashMap<String, String>();
			ArgsTool.setMap(qryMap, "user_id", mQryMap.get("userId"));
			return ArgsTool.reList(VmUserMenuRightModel.dao, qrySql, qryMap);
		}
		return null;
	}

	*//**
	 * 锟斤拷锟剿碉拷锟斤拷莘锟斤拷喾碉拷锟�
	 * 
	 * @param list
	 * @return
	 *//*
	private List<List<VmUserMenuModel>> classMenu(List<VmUserMenuModel> list) {
		List<List<VmUserMenuModel>> reList = null;
		Map<String, List<VmUserMenuModel>> map = null;
		if (!Fun.equalsListNull(list)) {
			map = new HashMap<String, List<VmUserMenuModel>>();
			reList = new ArrayList<List<VmUserMenuModel>>();
			for (int i = 0; i < list.size(); i++) {
				// pid=0,锟斤拷锟斤拷锟剿碉拷锟节碉拷
				if (list.get(i).get("pid").toString().trim().equals("0")) {
					List<VmUserMenuModel> tempList = new ArrayList<VmUserMenuModel>();
					tempList.add(list.get(i));
					map.put(list.get(i).get("id").toString(), tempList);
				}
			}
			String index = "";
			for (int i = 0; i < list.size(); i++) {
				// pid=!0,锟角讹拷锟斤拷锟剿碉拷锟节碉拷
				if (!list.get(i).get("pid").toString().trim().equals("0")) {
					index = list.get(i).get("pid")+"";
					map.get(index).add(list.get(i));
				}
			}
			Iterator<Map.Entry<String, List<VmUserMenuModel>>> it = map.entrySet().iterator();
			while (it.hasNext()) {
				reList.add(it.next().getValue());
			}
		}
		return reList;
	}
	*//**
	 * 锟斤拷锟斤拷色锟斤拷应锟剿碉拷锟斤拷莘锟斤拷喾碉拷锟�
	 * 
	 * @param list
	 * @return
	 *//*
	private List<List<SysMenu>> classRoleMenu(List<SysRoleMenu> list) {
		List<List<SysMenu>> reList = null;
		if (!Fun.eqListNull(list)) {
			reList = new ArrayList<List<SysMenu>>();
			List<SysMenu> tempList = new ArrayList<SysMenu>();
			String roleId = list.get(0).getInt("FK_ROLE")+"";
			int i = 0;
			for (; i < list.size(); i++) {
				if (!(list.get(i).getInt("FK_ROLE")+"").equals(roleId)) {
					reList.add(tempList);
					tempList = new ArrayList<SysMenu>();
					roleId = (list.get(i).getInt("role_id")+"");
				}
				tempList.add(list.get(i));
			}
			if(i > 0){
				reList.add(tempList);
			}
		}
		return reList;
	}*/
	
}

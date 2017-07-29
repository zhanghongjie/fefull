package com.biz.mgr;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.frame.utils.ArgsTool;
import com.frame.utils.Fun;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.portal.common.FefullEnum.IsSystem;
import com.portal.dto.CategoryNavigator;
import com.portal.dto.Navigator;
import com.sys.bean.ReBean;

public class BizNavigatorMgr {
	
	public static final Logger logger = LoggerFactory.getLogger(BizNavigatorMgr.class);
	
	private BizNavigatorMgr() {

	}

	public static BizNavigatorMgr getInstance() {
		return BizArticleMgrHolder.instance;
	}

	private static class BizArticleMgrHolder {
		private static BizNavigatorMgr instance = new BizNavigatorMgr();
	}
	
	/**
	 * 分页查询
	 * @return
	 */
	public ReBean<Record> getPage(Map<String, String> mQryMap){
		ReBean<Record> reBean = null;
		StringBuilder sb = new StringBuilder("select * from biz_navigator order by PK_NAVIGATOR desc");
		if (!Fun.eqNull(mQryMap)) {
			reBean = ArgsTool.getPageObj(this.reWhereSql(mQryMap,sb), mQryMap);
		}
		return reBean;
	}
	
	/**
	 * 条件拼接
	 * @param mQryMap
	 */
	private String reWhereSql(Map<String, String> mQryMap,StringBuilder sb){
		StringBuilder msb = new StringBuilder();
		msb.append(" and IS_VALID=1 ");
		return ArgsTool.reSql(sb, msb);
	}
	
	/**
	 * 判断添加的记录是否已经存在
	 * @param qryMap
	 * @return
	 */
	public boolean isRepeat(Map<String, String> qryMap){
		StringBuilder msb = new StringBuilder("select 1 from biz_navigator where 1=1");
		Boolean bool = true;
		if(!Fun.eqNull(qryMap.get("PK_NAVIGATOR"))){
			msb.append(" and PK_NAVIGATOR!=@PK_NAVIGATOR");
			bool = false;
		}
		if(bool){
			msb.append(" and 1!=1");
		}
		Record record = ArgsTool.getObj(msb.toString(), qryMap);
		if(!Fun.eqNull(record)){
			return true;
		}
		return false;
	}
	
	/**
	 * 获取系统导航数据
	 * @return
	 */
	public List<CategoryNavigator> getSystemNavigator(){
		StringBuffer sb = new StringBuffer("SELECT t2.`PK_NAVIGATOR_CATEGORY`,\n" +
					 "             t2.`CATEGORY_NAME`,\n" + 
					 "             t1.`PK_NAVIGATOR`,\n" + 
					 "             t1.`NAVIGATOR_NAME`,\n" + 
					 "             t1.`NAVIGATOR_URL`,\n" + 
					 "             t1.`DESCRIPTION`,\n" +
					 "			   0 AS IS_FAVOR\n" + 
					 "        FROM biz_navigator t1\n" + 
					 "        INNER JOIN BIZ_NAVIGATOR_CATEGORY t2 ON t1.`FK_NAVIGATOR_CATEGORY` = t2.`PK_NAVIGATOR_CATEGORY`\n" +
					 "   	  WHERE t1.`IS_VALID` = 1\n" + 
					 "        AND t1.`IS_SYSTEM` = ?\n" + 
					 "        ORDER BY t2.`PK_NAVIGATOR_CATEGORY`");
			return convert2List(Db.find(sb.toString(), IsSystem.YES.getValue()));
	}
	
	private List<CategoryNavigator> convert2List(List<Record> recordList){
		if(Fun.eqListNull(recordList)){
			return null;
		}
		List<CategoryNavigator> categoryNavigatorList = new ArrayList<CategoryNavigator>();
		CategoryNavigator categoryNavigator = null;
		List<Navigator> navigatorList = null;
		Navigator navigator = null;
		int temp_categoryId = -1;
		for (int i = 0; i < recordList.size(); i++) {
			Record record = recordList.get(i);
			if(!record.get("PK_NAVIGATOR_CATEGORY").equals(temp_categoryId)){
				if(!Fun.eqListNull(navigatorList)){
					categoryNavigator.setNavigators(navigatorList);
					categoryNavigatorList.add(categoryNavigator);
				}
				categoryNavigator = new CategoryNavigator();
				categoryNavigator.setCategoryId(record.getInt("PK_NAVIGATOR_CATEGORY"));
				categoryNavigator.setCategoryName(record.getStr("CATEGORY_NAME"));

				temp_categoryId = categoryNavigator.getCategoryId();

				navigatorList = new ArrayList<Navigator>();
			}

			navigator = new Navigator();
			navigator.setNavigatorId(record.getInt("PK_NAVIGATOR"));
			navigator.setNavigatorName(record.getStr("NAVIGATOR_NAME"));
			navigator.setNavigatorUrl(record.getStr("NAVIGATOR_URL"));
			navigator.setDescription(record.getStr("DESCRIPTION"));
			navigator.setIsFavor(String.valueOf(record.get("IS_FAVOR")));

			navigatorList.add(navigator);

			if(i == recordList.size()-1){
				categoryNavigator.setNavigators(navigatorList);
				categoryNavigatorList.add(categoryNavigator);
			}
		}
		return categoryNavigatorList;
	}
	
	/**
	 * 获取会员导航数据
	 * @param pkMember
	 * @return
	 */
	public Map<String, List<CategoryNavigator>> getMemberNavigator(Integer pkMember) {
		String sql = 
				"SELECT\n" +
						"  t1.`PK_NAVIGATOR`,\n" + 
						"  t1.`NAVIGATOR_NAME`,\n" + 
						"  t2.`PK_NAVIGATOR_CATEGORY`,\n" + 
						"  t2.`CATEGORY_NAME`,\n" + 
						"  t1.`NAVIGATOR_URL`,\n" + 
						"  t1.`DESCRIPTION`,\n" + 
						"  t1.`IS_SYSTEM`,\n" + 
						"  CASE WHEN t3.PK_MEMBER_NAVIGATOR IS NOT NULL THEN 1 ELSE 0 END AS IS_FAVOR,\n" + 
						"  t3.PK_NAVIGATOR_CATEGORY AS MEMBER_PNC,\n" + 
						"  t3.CATEGORY_NAME AS MEMBER_CN\n" + 
						"FROM\n" + 
						"  biz_navigator t1\n" + 
						"  INNER JOIN BIZ_NAVIGATOR_CATEGORY t2\n" + 
						"    ON t1.`FK_NAVIGATOR_CATEGORY` = t2.`PK_NAVIGATOR_CATEGORY`\n" + 
						"  LEFT JOIN (SELECT s1.FK_NAVIGATOR, s1.PK_MEMBER_NAVIGATOR, s2.PK_NAVIGATOR_CATEGORY, s2.CATEGORY_NAME\n" + 
						"         FROM BIZ_MEMBER_NAVIGATOR s1\n" + 
						"   INNER JOIN BIZ_NAVIGATOR_CATEGORY s2\n" + 
						"           ON s1.FK_NAVIGATOR_CATEGORY = s2.PK_NAVIGATOR_CATEGORY\n" + 
						"        WHERE s1.FK_MEMBER = ?) t3 ON t1.`PK_NAVIGATOR` = t3.FK_NAVIGATOR\n" + 
						"WHERE t1.`IS_VALID` = 1\n" + 
						"  AND (t1.IS_SYSTEM = 1 OR t1.FK_MEMBER = ?)\n" + 
						"ORDER BY t1.`IS_SYSTEM` DESC, t2.`PK_NAVIGATOR_CATEGORY`, t1.PK_NAVIGATOR, t3.PK_NAVIGATOR_CATEGORY";
		List<Record> list = Db.find(sql, pkMember, pkMember);
		return covert2Map(list);
	}

	private Map<String, List<CategoryNavigator>> covert2Map(List<Record> recordList) {
		if(Fun.eqListNull(recordList)){
			return null;
		}
		// key: categoryId_categoryName --> List<Navigator>
		Map<String, List<Navigator>> systemCategoryNavigatorMap = new HashMap<String, List<Navigator>>();
		Map<String, List<Navigator>> memberCategoryNavigatorMap = new HashMap<String, List<Navigator>>();
		String mapKey = "";
		Navigator navigator = null;
		for (int i = 0; i < recordList.size(); i++) {
			Record record = recordList.get(i);
			if(record.get("IS_SYSTEM").equals(IsSystem.YES.getValue())){
				mapKey = record.getInt("PK_NAVIGATOR_CATEGORY") + "_" + record.getStr("CATEGORY_NAME");
				List<Navigator> navigatorList = systemCategoryNavigatorMap.get(mapKey);
				if (navigatorList == null) {
					navigatorList = new ArrayList<>();
					systemCategoryNavigatorMap.put(mapKey, navigatorList);
				}
				if (!navigatorList.contains(mapKey)) {
					navigator = new Navigator();
					navigator.setNavigatorId(record.getInt("PK_NAVIGATOR"));
					navigator.setNavigatorName(record.getStr("NAVIGATOR_NAME"));
					navigator.setNavigatorUrl(record.getStr("NAVIGATOR_URL"));
					navigator.setDescription(record.getStr("DESCRIPTION"));
					navigator.setIsFavor(String.valueOf(record.get("IS_FAVOR")));
					navigator.setIsSystem(record.getInt("IS_SYSTEM"));
					
					navigatorList.add(navigator);
				}
			} 
			if((record.get("IS_SYSTEM").equals(IsSystem.YES.getValue())&&null!=record.get("MEMBER_PNC")) 
					|| (record.get("IS_SYSTEM").equals(IsSystem.NO.getValue()))){
				mapKey = record.getInt("MEMBER_PNC") + "_" + record.getStr("MEMBER_CN");
				List<Navigator> navigatorList = memberCategoryNavigatorMap.get(mapKey);
				if (navigatorList == null) {
					navigatorList = new ArrayList<>();
					memberCategoryNavigatorMap.put(mapKey, navigatorList);
				}
				if (!navigatorList.contains(mapKey)) {
					navigator = new Navigator();
					navigator.setNavigatorId(record.getInt("PK_NAVIGATOR"));
					navigator.setNavigatorName(record.getStr("NAVIGATOR_NAME"));
					navigator.setNavigatorUrl(record.getStr("NAVIGATOR_URL"));
					navigator.setDescription(record.getStr("DESCRIPTION"));
					navigator.setIsFavor(String.valueOf(record.get("IS_FAVOR")));
					navigator.setIsSystem(record.getInt("IS_SYSTEM"));
					
					navigatorList.add(navigator);
				}
			}
		}
		
		List<CategoryNavigator> systemCategoryNavigatorList = new ArrayList<CategoryNavigator>();			
		List<CategoryNavigator> memberCategoryNavigatorList = new ArrayList<CategoryNavigator>();
		CategoryNavigator categoryNavigator = null;
		for (Map.Entry<String, List<Navigator>> entry : systemCategoryNavigatorMap.entrySet()) {
			categoryNavigator = new CategoryNavigator();
			categoryNavigator.setCategoryId(Integer.parseInt(entry.getKey().split("_")[0]));
			categoryNavigator.setCategoryName(entry.getKey().split("_")[1]);
			categoryNavigator.setNavigators(entry.getValue());
			systemCategoryNavigatorList.add(categoryNavigator);
		}
		for (Map.Entry<String, List<Navigator>> entry : memberCategoryNavigatorMap.entrySet()) {
			categoryNavigator = new CategoryNavigator();
			categoryNavigator.setCategoryId(Integer.parseInt(entry.getKey().split("_")[0]));
			categoryNavigator.setCategoryName(entry.getKey().split("_")[1]);
			categoryNavigator.setNavigators(entry.getValue());
			memberCategoryNavigatorList.add(categoryNavigator);
		}
		
		Map<String, List<CategoryNavigator>> retMap = new HashMap<String, List<CategoryNavigator>>();
		retMap.put("system", systemCategoryNavigatorList);
		retMap.put("member", memberCategoryNavigatorList);
		
		return retMap;
	}
	
}

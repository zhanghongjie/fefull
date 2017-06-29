package com.biz.mgr;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.frame.utils.ArgsTool;
import com.frame.utils.Fun;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
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
	public List<CategoryNavigator> getSysNavigator(Integer pkMember){
		StringBuffer sb = new StringBuffer("SELECT t2.`PK_NAVIGATOR_CATEGORY`,\n" +
					 "             t2.`CATEGORY_NAME`,\n" + 
					 "             t1.`PK_NAVIGATOR`,\n" + 
					 "             t1.`NAVIGATOR_NAME`,\n" + 
					 "             t1.`NAVIGATOR_URL`,\n" + 
					 "             t1.`DESCRIPTION`,\n");
		sb.append((null == pkMember) ? "0 AS IS_FAVOR\n" : "CASE WHEN t3.PK_MEMBER_NAVIGATOR IS NOT NULL THEN 1 ELSE 0 END AS IS_FAVOR\n"); 
		sb.append("        FROM biz_navigator t1\n" + 
					 "        INNER JOIN BIZ_NAVIGATOR_CATEGORY t2 ON t1.`FK_NAVIGATOR_CATEGORY` = t2.`PK_NAVIGATOR_CATEGORY`\n");
		sb.append((null != pkMember) ? "LEFT JOIN BIZ_MEMBER_NAVIGATOR t3 ON t1.`PK_NAVIGATOR` = t3.FK_NAVIGATOR AND t3.FK_MEMBER = ?" : "");
		sb.append("        WHERE t1.`IS_VALID` = 1\n" + 
					 "        AND t1.`FK_MEMBER` IS NULL\n" + 
					 "        ORDER BY t2.`PK_NAVIGATOR_CATEGORY`");
		if(null == pkMember){
			return convert(Db.find(sb.toString()));
		} else{
			return convert(Db.find(sb.toString(), pkMember));
		}
	}
	
	private List<CategoryNavigator> convert(List<Record> recordList){
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
			navigator.setIsFavor(record.getLong("IS_FAVOR"));

			navigatorList.add(navigator);

			if(i == recordList.size()-1){
				categoryNavigator.setNavigators(navigatorList);
				categoryNavigatorList.add(categoryNavigator);
			}
		}
		return categoryNavigatorList;
	}
}

package com.frame.utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSON;
import com.frame.constants.Constants;
import com.frame.exception.BaseException;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.Table;
import com.jfinal.plugin.activerecord.TableMapping;
import com.sys.bean.ReBean;
import com.sys.cache.SysInfoCache;

public class ArgsTool {
	/**
	 * 转换请求参数为普通map
	 *
	 * @param properties
	 * @return
	 */
	public static Map<String, String> getParameterMap(
			Map<String, String[]> properties) {
		// 返回值Map
		Map<String, String> returnMap = new HashMap<String, String>();
		Iterator entries = properties.entrySet().iterator();
		Map.Entry entry;
		String name = "";
		String value = "";
		while (entries.hasNext()) {
			entry = (Map.Entry) entries.next();
			name = (String) entry.getKey();
			Object valueObj = entry.getValue();
			if (null == valueObj) {
				value = "";
			} else if (valueObj instanceof String[]) {
				String[] values = (String[]) valueObj;
				for (int i = 0; i < values.length; i++) {
					value = values[i] + ",";
				}
				value = value.substring(0, value.length() - 1);
			} else {
				value = valueObj.toString();
			}
			returnMap.put(name, value);
		}
		return returnMap;
	}

	public static void setMap(Map<String, String> map, String key, String value) {
		ArgsTool.setMap(map, key, value, null);
	}

	/**
	 * @param map
	 *            设置值的map
	 * @param key
	 *            map的key
	 * @param value
	 *            map的值
	 * @param init
	 *            默认值
	 */
	public static void setMap(Map<String, String> map, String key,
			String value, String init) {
		if (!Fun.eqNull(map)) {
			if (!Fun.eqNull(value)) {
				map.put(key, value);
			} else {
				if (!Fun.eqNull(init)) {
					map.put(key, init);
				}
			}
		}
	}

	/**
	 * @param map
	 * @param key
	 * @param value
	 */
	public static void setMapDelOld(Map<String, String> map, String oldKey,
			String newKey) {
		if (!Fun.eqNull(map) && !Fun.eqNull(map.get(oldKey))
				&& !Fun.eqNull(oldKey) && !Fun.eqNull(newKey)) {
			map.put(newKey, map.get(oldKey));
			map.remove(oldKey);
		}
	}

	/**
	 * 字符串转整型
	 *
	 * @param str
	 * @param init
	 * @return
	 */
	public static int paserInt(String str, int init) {
		if (!Fun.eqNull(str)) {
			return Integer.parseInt(str);
		}
		return init;
	}

	public static int[] rePlace(int num) {
		int[] list = new int[num];
		for (int i = 0; i < num; i++) {
			list[i] = i;
		}
		return list;
	}
	
	/**
	 * 添加where条件 0X11为点位符
	 *
	 * @param sb
	 * @param sourceStr
	 *//*
	private static void addWhere1(StringBuffer sb, String sourceStr, int qryType) {
		String temp = sb.toString();
		if (qryType == 1) {
			temp = sourceStr;
		}
		int index = temp.indexOf("group");
		int index2 = temp.indexOf("order");
		if (index == -1) {
			index = 1000000;
		}
		if (index2 == -1) {
			index2 = 1000000;
		}

		if (index > index2) {
			sb.insert(index2, " where 1=1 0X11 ");
		} else if (index < index2) {
			sb.insert(index, " where 1=1 0X11 ");
		} else {
			sb.append(" where 1=1 0X11 ");
		}
	}*/

	/**
	 * 数据更新方法 注：更新字段名称要在对应表中存在，否则不处理
	 *
	 * @param model
	 * @param qryMap
	 * @param key
	 *            要更新的数据主键字段名
	 * @return
	 */
	public static <T extends Model> String upObj(T model,
			Map<String, String> qryMap, String key,HttpServletRequest req) {
		// 是否成功 1是0否
		String flag = Constants.SUCCESS;
		try {
			if (Fun.eqNull(key)) {
				key = "id";
			}
			ArgsTool.addDefFiled(qryMap,2,req);
			Model obj = model.findById(qryMap.get(key));
			if (!Fun.eqNull(obj)) {
				// Set attrsSet = obj.getAttrsEntrySet();
				String[] name = obj._getAttrNames();
				Set<Map.Entry<String, String>> set = qryMap.entrySet();
				qryMap.put(key, "");
				Map.Entry<String, String> entry = null;
				for (Iterator<Map.Entry<String, String>> it = set.iterator(); it
						.hasNext();) {
					entry = (Map.Entry<String, String>) it.next();
					if (!Fun.eqNull(entry.getValue())
							&& isContains(name, entry.getKey() + "")) {
						obj.set(entry.getKey(), entry.getValue());
					}
				}
				obj.update();
			}
		} catch (Exception e) {
			e.printStackTrace();
			flag = Constants.ERROR;
		}
		return flag;
	}

	/**
	 * 数据新增方法 注：更新字段名称要在对应表中存在，否则不处理
	 *
	 * @param model
	 * @param qryMap
	 * @param key
	 * @return
	 */
	public static <T extends Model> ReBean<T> addObj(T model,HttpServletRequest req){
		return ArgsTool.addObj(model, new HashMap<String, String>(),req);
	}
	/**
	 * 数据新增方法 注：更新字段名称要在对应表中存在，否则不处理
	 *
	 * @param model
	 * @param qryMap
	 * @param key
	 * @return
	 */
	public static <T extends Model> ReBean<T> addObj(T model,
			Map<String, String> qryMap,HttpServletRequest req) {
		ReBean<T> rebean = new ReBean<T>();
		// 是否成功 1是0否
		String flag = Constants.SUCCESS;
		if(Fun.eqNull(model)){
			throw new BaseException("ArgsTool-->addObj:输入的参数model不能为空！");
		}
		String[] attr = ArgsTool.getTableColumn(model);
		try {
			ArgsTool.addDefFiled(qryMap,1,req);
			Set<Map.Entry<String, String>> set = qryMap.entrySet();
			Map.Entry<String, String> entry = null;
			for (Iterator<Map.Entry<String, String>> it = set.iterator(); it
					.hasNext();) {
				entry = (Map.Entry<String, String>) it.next();
				if (!Fun.eqNull(entry.getValue())
						&& !entry.getValue().equals("_empty")
						&& isContains(attr, entry.getKey() + "")) {
					model.set(entry.getKey(), entry.getValue());
				}
			}
			model.save();
		} catch (Exception e) {
			e.printStackTrace();
			flag = Constants.ERROR;
		}
		rebean.setFlag(flag);
		if (flag == Constants.SUCCESS) {
			rebean.setObj(model);
		}
		return rebean;
	}

	/**
	 * @param qryMap
	 * @param type 1新增，2更新
	 */
	private static void addDefFiled(Map<String, String> qryMap,int type,HttpServletRequest req){
		if(!Fun.eqNull(qryMap)){
			if(!Fun.eqNull(SysInfoCache.getInstance().getUserInfoBean(req))){
				qryMap.put("UPDATE_BY_ID", SysInfoCache.getInstance().getUserInfoBean(req).getSysUser().getPkUser()+"");
				if(type==1){
					qryMap.put("CREATE_BY_ID", SysInfoCache.getInstance().getUserInfoBean(req).getSysUser().getPkUser()+"");
				}
			}
			if(type==1){
				qryMap.put("CREATE_BY_TIME", DateUtil.getCurrDate("yyyy-MM-dd HH:mm:ss"));
			}
			qryMap.put("UPDATE_BY_TIME", DateUtil.getCurrDate("yyyy-MM-dd HH:mm:ss"));
		}
	}
	private static boolean isContains(String[] str, String key) {
		if (!Fun.eqNull(str) && !Fun.eqNull(key)) {
			for (String string : str) {
				if (string.equals(key)) {
					return true;
				}
			}
		}
		return false;
	}




	/**
	 * map转成json
	 *
	 * @param map
	 * @return
	 */
	public static String reJson4Map(Map<String, String> map) {
		String json = "";
		if (!Fun.eqNull(map)) {
			json = JSON.toJSONString(map);
		}
		return json;
	}

	/**
	 * 对象转成json
	 *
	 * @param map
	 * @return
	 */
	public static String reJson4Obj(Object obj) {
		String json = "";
		if (!Fun.eqNull(obj)) {
			json = JSON.toJSONString(obj);
		}

		return json;
	}

	/**
	 *json转成对象
	 * @param json
	 * @return
	 */
	public static Object reObj4Json(String json, Class<?> obj) {
		Object reObj = null;
		reObj = JSON.parseObject(json, obj);
		return reObj;
	}

	/**
	 * json转map
	 *
	 * @param str
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, String> json2Map(String json) {
		Map<String, String> reObj = null;
		reObj = (Map<String, String>)JSON.parseObject(json, Map.class);
		return reObj;
	}

	/**
	 * @param obj
	 * @param key
	 * @param value
	 * @return
	 */
	public static <T extends Model> Map<String, String> reMap4Obj(List<T> obj,
			String key, String value) {
		Map<String, String> reMap = null;
		if (!Fun.eqNull(obj) && !Fun.eqNull(key)
				&& !Fun.eqNull(value)) {
			reMap = new HashMap<String, String>();
			for (T t : obj) {
				reMap.put(t.get(key) + "", t.get(value) + "");
			}
		}
		return reMap;
	}

	/**
	 * model对象转为map
	 *
	 * @param model
	 * @param qryMap
	 * @return
	 */
	public static <T extends Model> Map<String, String> model2Map(T model) {
		Map<String, String> reMap = null;
		if (!Fun.eqNull(model)) {
			reMap = new HashMap<String, String>();
			try {
				String[] names = model._getAttrNames();
				for (String attr : names) {
					reMap.put(attr, model.get(attr) + "");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return reMap;
	}

	public static <T extends Object> Map<String, String> obj2Map(T obj) {
		Map<String, String> reMap = new HashMap<String, String>();
		if (!Fun.eqNull(obj)) {
			// 取类中的变量名
			Field[] fields = obj.getClass().getDeclaredFields();
			Method[] methods = obj.getClass().getMethods();
			Map<String, Method> methodMap = new HashMap<String, Method>();
			for (int i = 0; i < methods.length; i++) {
				if (methods[i].getName().startsWith("get")) {
					methodMap.put(setFirstWord2Low(methods[i].getName()
							.substring(3)), methods[i]);
				}
			}
			String field = "";
			try {
				for (int i = 0; i < fields.length; i++) {
					field = fields[i].getName();
					reMap.put(field, methodMap.get(field).invoke(obj) + "");
				}
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		}
		return reMap;
	}

	public String setFirstWord2Up(String txt) {
		if (txt != null && !"".equals(txt.trim())) {
			txt = txt.substring(0, 1).toUpperCase() + txt.substring(1);
		}
		return txt;
	}

	public static String setFirstWord2Low(String txt) {
		if (txt != null && !"".equals(txt.trim())) {
			txt = txt.substring(0, 1).toLowerCase() + txt.substring(1);
		}
		return txt;
	}

	/**
	 * 返回map中的key
	 * 
	 * @param map
	 * @return
	 */
	public static String[] reMapKeys(Map<?, ?> map) {
		StringBuffer sb = null;
		if (null != map) {
			sb = new StringBuffer();
			for (Map.Entry<?, ?> entry : map.entrySet()) {
				sb.append(entry.getKey()).append(",");
			}
		}
		return sb != null && sb.length() > 1 ? sb.substring(0, sb.length() - 1)
				.toString().split(",") : null;
	}
	/**
	 * 将app保存图片的格式转成html格式
	 * @param con
	 * @return
	 */
	public static String repAppImg2Web(String con){
		if(!Fun.eqNull(con)){
			//String test = "抛物线<image>11111</image>求解析式<image>http://kaoshibang.kst985.com/1C6CA6BE-F1E5-4E14-87A9-B9071AEA30FF.jpg</image>";
			Pattern p = Pattern.compile("<image>([\\s\\S]*?)</image>");
			Matcher m = p.matcher(con);
			String tmp = null;
			while(m.find()){
				tmp = m.group();
				if(!Fun.eqNull(tmp)){
					con = con.replace(tmp, repImageTag(tmp));
				}
			}
		}
		return con;
	}
	private static String repImageTag(String tag){
		String imgHtml = "<br><a href='@href' target=\"_blank\"><img src='@src'/ style='max-width:300px;max-height:300px;'/></a><br>";
		if(!Fun.eqNull(tag)){
			tag = tag.replace("<image>", "").replace("</image>", "");
			tag = imgHtml.replace("@src", tag).replace("@href", tag);
		}
		return tag;
	}
	
	
	//--------------------------------新查询方法--------------------------
	public static <T extends Model> List<T> getList(T model, String sql,Map<String, String> mQryMap) {
		Map<String, Object> map = reArgs(sql,mQryMap);
		List<String> args = (List<String>)map.get("args");
		sql = (String)map.get("sql");
		if(Fun.eqListNull(args)){
			return model.find(sql);
		}else{
			return model.find(sql,args.toArray());
		}

	}
	public static List<Record> getList(String sql) {
		Map<String,String> mQryMap = new HashMap<String, String>();
		return ArgsTool.getList(sql, mQryMap);
	}
	@SuppressWarnings("unchecked")
	public static List<Record> getList(String sql,Map<String, String> mQryMap) {
		Map<String, Object> map = reArgs(sql,mQryMap);
		List<String> args = (List<String>)map.get("args");
		sql = (String)map.get("sql");
		if(Fun.eqListNull(args)){
			return Db.find(sql);
		}else{
			return Db.find(sql,args.toArray());
		}
	}
	
	
	public static <T extends Model> T getObj(T model,String sql,Map<String, String> mQryMap){
		Map<String, Object> map = reArgs(sql,mQryMap);
		List<String> args = (List<String>)map.get("args");
		sql = (String)map.get("sql");
		if(Fun.eqListNull(args)){
			return (T)model.findFirst(sql);
		}else{
			return (T)model.findFirst(sql,args.toArray());
		}
		
	}
	public static Record getObj(String sql,Map<String, String> mQryMap){
		Map<String, Object> map = reArgs(sql,mQryMap);
		List<String> args = (List<String>)map.get("args");
		sql = (String)map.get("sql");
		if(Fun.eqListNull(args)){
			return Db.findFirst(sql);
		}else{
			return Db.findFirst(sql,args.toArray());
		}
	}
	/**
	 * 返回分页对象
	 * @param sb
	 * @param mQryMap
	 * @return
	 */
	public static ReBean<Record> getPageObj(String sql,Map<String, String> mQryMap){
		ReBean<Record> reBean = null;
		if (!Fun.eqNull(mQryMap)) {
			reBean = new ReBean<Record>();
			//返回当前分页参数
			reBean = ArgsTool.getPageArg(sql, reBean, mQryMap);
			//返回分页数据
			reBean.setReList(ArgsTool.getPage(sql, reBean, mQryMap));
		}
		return reBean;
	}
	/**
	 *  返回分页数据
	 * @param sql 查询sql
	 * @param model 返回对象
	 * @param qryMap 查询参数
	 * @return
	 */
	@SuppressWarnings({"unchecked"})
	public static <T> ReBean<T> getPageArg(String sql,
			ReBean<T> model,Map<String, String> qryMap) {
		
		//TODO 去掉排序语句,查询字段替换成1
		sql = "select count(1) from ("+sql+")t";
		
		Long records  = 0L;
		Map<String, Object> map = reArgs(sql,qryMap);
		List<String> args = (List<String>)map.get("args");
		sql = (String)map.get("sql");
        if(Fun.eqListNull(args)){
			records = Db.queryLong(sql);
		}else{
			records = Db.queryLong(sql,args.toArray());
		}
		Integer pageSize = Integer.parseInt(Fun.eqNull(qryMap
				.get("pageSize")) ? Constants.PAGE_SIZE + "" : qryMap
				.get("pageSize"));
		model.setPageSize(pageSize + "");
		Integer nowPage = Integer.parseInt(Fun.eqNull(qryMap
				.get("nowPage")) ? "1" : qryMap.get("nowPage"));
		model.setNowPage(nowPage + "");
		Long totalPage = records % pageSize == 0 ? (records / pageSize)
				: (records / pageSize + 1);
		model.setTotalPage(totalPage + "");
		return model;
	}
	/**
	 * 返回查询参数
	 * @param sql 查询sql
	 * @param qryMap 前台请求参数
	 * @return
	 */
	private static Map<String,Object>reArgs(String sql,Map<String, String> qryMap){
		//按空格分隔sql
		String[] sqlSplit = sql.split("\\s+");
		Map<String,Object> reMap = new HashMap<String, Object>();
		List<String> list = null;
		Map<Integer,String> argsMap = null;
		if(!Fun.eqNull(qryMap)){
			list = new ArrayList<String>();
			argsMap = new TreeMap<Integer, String>();
			Iterator  it = qryMap.entrySet().iterator();
			Entry tmp = null;
			while (sql.indexOf("@")>0) {
				//若map遍历结束且还有map中包含的占位符未被替换
				if(it.hasNext()==false){
					if(ArgsTool.isReplaceFinish(sql, qryMap)==false){
						it = qryMap.entrySet().iterator();
					}else{
						break;
					}
				}
				tmp = (Map.Entry)it.next();
				
				int index = sql.indexOf("@"+tmp.getKey());
				if(index>1){
					
					//模糊查询需要特殊处理
					int listIndex = index4Sql(sqlSplit,"@"+tmp.getKey());
					
					if(listIndex>1 && sqlSplit[listIndex-1].toLowerCase().equals("like")){
						argsMap.put(index, "%"+(String) tmp.getValue()+"%");
					}else if(listIndex>1 && (sqlSplit[listIndex-1].toLowerCase().equals("(") || sqlSplit[listIndex-1].toLowerCase().equals("in"))){ //in查询条件
						sql = sql.replaceFirst("@"+tmp.getKey(), (String) tmp.getValue());
					}else{
						argsMap.put(index, (String) tmp.getValue());
					}
					//查询参数占位符号替换成?号
					sql = sql.replaceFirst("@"+tmp.getKey(), "?");
				}
			}
		}
		
		
		//查询参数位置调整
		if(!Fun.eqNull(argsMap)){
	        Set<Entry<Integer,String>> set = argsMap.entrySet(); 
	        // Get an iterator 
	        Iterator<Entry<Integer,String>> iterator = set.iterator(); 
	        // Display elements 
	        while (iterator.hasNext()) { 
	            Entry<?, ?> entry = (Entry<?, ?>) iterator.next(); 
	            list.add(entry.getValue().toString());
	        } 
		}
		
		//修改后的sql
		reMap.put("sql", sql);
		//查询的参数列表
		reMap.put("args", list);
		return reMap;
	}
	
	
	/**
	 * 占位符是否全部替换
	 * @param sql
	 * @param qryMap
	 * @return
	 */
	private static boolean isReplaceFinish(String sql,Map<String, String> qryMap){
		Set<Entry<String,String>> set = qryMap.entrySet(); 
        // Get an iterator 
        Iterator<Entry<String,String>> iterator = set.iterator(); 
        Entry<?, ?> entry = null; 
        while (iterator.hasNext()) { 
            entry = (Entry<?, ?>) iterator.next(); 
            if(sql.indexOf("@"+entry.getKey())>-1){
            	return false;
            }
        } 
        return true;
	}
	
	/**
	 * 返回字符串在数组中的位置
	 * @param sqlSplit
	 * @param find
	 * @return
	 */
	public static int index4Sql(String[] sqlSplit,String find){
		int i=0;
		if(!Fun.eqNull(sqlSplit)){
			for (String tmp : sqlSplit) {
				if(tmp.indexOf(find)>-1){
					return i;
				}
				i++;
			}
		}
		return 0;		
	}
	
	/**
	 * 数据库查询方法--分页
	 *
	 * @param sql 查询sql
	 * @param model 返回对象
	 * @param qryMap 查询参数
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List<Record> getPage(String sql,
			 ReBean<Record> reBean,Map<String, String> qryMap) {
		
		String pageSize = Fun.eqNull(qryMap.get("pageSize")) ?  Constants.PAGE_SIZE + "" : qryMap.get("pageSize");
		int pageSizeInt = Integer.parseInt(pageSize);
		sql = sql+" limit "+(Integer.parseInt(reBean.getNowPage())-1)* pageSizeInt+", "+pageSizeInt;
		Map<String, Object> map = reArgs(sql,qryMap);
		List<String> args = (List<String>)map.get("args");
		sql = (String)map.get("sql");
		if(Fun.eqListNull(args)){
			return Db.find(sql);
		}else{
			return Db.find(sql,args.toArray());
		}
	}
	
	/**
	 * 返回查询语句
	 * @param qrySql 查询语句
	 * @param whereSql where语句
	 * @return
	 */
	public static String reSql(StringBuilder qrySql,StringBuilder whereSql){
		if(Fun.eqNull(whereSql) || whereSql.length()<1){
			return qrySql.toString();
		}
		StringBuilder reSql = new StringBuilder(qrySql.toString());
		String mReSql = reSql.toString().toLowerCase();
		int orderIndex = mReSql.lastIndexOf(" order ");
		int groupIndex = mReSql.lastIndexOf(" group ");
		int index = reSql.length();
		if((orderIndex>groupIndex && groupIndex>0) || orderIndex<0){
			if(groupIndex>0){
				index = groupIndex;
			}
		}else if((orderIndex<groupIndex && orderIndex>0) || groupIndex<0){
			if(orderIndex>0){
				index = orderIndex;
			}
		}
		
		reSql = reSql.insert(index, " where 1=1 "+whereSql.toString());
		return reSql.toString();
	}
	
	/**
	 * 根据模型取表字段
	 * @param model
	 * @return
	 */
	public static String[] getTableColumn(Model<?>  model){
		String[] reList = model._getAttrNames();
		if(Fun.eqNull(reList) || reList.length<1){
			Table table = TableMapping.me().getTable(model.getClass());
			Map<String, Class<?>> m = table.getColumnTypeMap();
			reList = new String[m.entrySet().size()];
			int i=0;
			for(Map.Entry<String, Class<?>> entry:m.entrySet()){    
				reList[i++] = entry.getKey();
			}
		}
		return reList;
	}
	public static void main(String[] args) {
		ArgsTool.repAppImg2Web(null);
	}
}

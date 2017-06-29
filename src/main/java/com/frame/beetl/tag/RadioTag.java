package com.frame.beetl.tag;

import java.io.IOException;
import java.util.List;

import org.beetl.core.GeneralVarTagBinding;

import com.frame.utils.Fun;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

/**   
* @Title: RadioTag.java 
* @Package com.frame.beetl.tag 
* @Description: 通用单选框控件 
* @author sos
* @date 2016年8月23日 上午10:08:54 
* @version V1.0   
*/
public class RadioTag extends GeneralVarTagBinding {
	private String radioTmp;
	
	@Override
	public void render() {
		try {
			ctx.byteWriter.writeString(this.creatTagHtml());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @return
	 */
	private String creatTagHtml(){
		//表名
		String tableName = (String)getAttributeValue("tableName");
		//做为key的字段名
		String selKey = (String)getAttributeValue("selKey");
		//做为value的字段名
		String selValue = (String)getAttributeValue("selValue");
		
		//过滤条件
		String where = (String)getAttributeValue("where");
		//排序规则
		String order = (String)getAttributeValue("order");
		
		if(Fun.eqNull(tableName) || Fun.eqNull(selKey) || Fun.eqNull(selValue)){
			return "创建多选框出错，tableName、key、value参数不能为空！";
		}
		/*
		 * disabled 是否禁用该下拉列表 ，灰显不能选择
		 * readonly 下拉列表 是否只读
		 * multiple 可选择多个选项
		 * size 规定下拉列表中可见选项的数目
		 * value 默认值
		 * 
		 * options列表值
		 */
		String selectTmp = "<span>{radios}</span>";
		radioTmp = "<label><input name=\"{name}\" type=\"radio\" value=\"{key}\" {disabled} {style} {class} {checked}/><span>{value}</span></label> ";
		
		String name = (String)getAttributeValue("name");
		String mClass = (String)getAttributeValue("class");
		String style = (String)getAttributeValue("style");
		String disabled = (String)getAttributeValue("disabled");
		String value = getAttributeValue("value")+"";
		
		radioTmp = radioTmp.replace("{name}",Fun.ifNull(name,name, ""))
		.replace("{class}", Fun.ifNull(mClass,"class='"+mClass+"'", ""))
		.replace("{disabled}", Fun.ifNull(disabled,"disabled='"+disabled+"'", ""))
		.replace("{style}", Fun.ifNull(style,"style='"+style+"'", ""));
		
		
		StringBuilder sb = new StringBuilder("select distinct ");
		sb.append(selKey)
		.append(",").append(selValue)
		.append(" from ")
		.append(tableName);
		if(!Fun.eqNull(where)){
			sb.append("  where 1=1 and "+where);
		}
		sb.append(Fun.nvl2(order," order by "+order,""));
		
		List<Record> list = Db.find(sb.toString());
		sb.setLength(0);
		if(!Fun.eqListNull(list)){
			sb = this.loopDictItem(list,selKey,selValue, value+"");
			if(!Fun.eqNull(sb)){
				selectTmp = selectTmp.replace("{radios}", sb.toString());
			}
		}else{
			selectTmp = selectTmp.replace("{radios}", "");
		}
		return selectTmp;
	}
	/**
	 * 遍历字典结果集
	 * @param list 
	 * @param value 前台设置默认值
	 * @return
	 */
	private StringBuilder loopDictItem(List<Record> list,String selKey,String selValue,String initValue){
		StringBuilder sb = new StringBuilder();
		if(!Fun.eqListNull(list)){
			for(Record dictItem : list){
				if(!Fun.eqNull(dictItem)){
					sb.append(this.bulidHtml(dictItem.get(selKey),dictItem.get(selValue), initValue));
				}
			}
		}
		return sb;
	}
	
	/**
	 * 构造select标签下的option节点
	 * @param value
	 * @param label
	 * @param selectValue
	 * @param disabled
	 * @return
	 */
	private String bulidHtml(Object key, Object value, String selectValues){
		String  checkbox = radioTmp.replace("{key}", key+"").replace("{value}", value+"");
		String val = key.toString().trim();
		String[] list = selectValues.split(",");
		boolean bool = false;	
		for (String string : list) {
			if(val.equals(string)){
				checkbox = checkbox.replace("{checked}", "checked=\"checked\"");
				bool =  true;
				break;
			}	
		}
		if(!bool){
			checkbox = checkbox.replace("{checked}", "");
		}
		return checkbox;
	}
}

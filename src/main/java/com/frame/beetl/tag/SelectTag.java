package com.frame.beetl.tag;

import java.io.IOException;
import java.util.List;

import org.beetl.core.GeneralVarTagBinding;

import com.frame.utils.Fun;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

/**   
* @Title: SelectTag.java 
* @Package com.frame.beetl.tag 
* @Description: 通用下拉框控件 
* @author sos
* @date 2016年8月10日 下午5:59:51 
* @version V1.0   
*/
public class SelectTag extends GeneralVarTagBinding {
	//是否多选
	private boolean hasMultiple = false;
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
			return "创建下拉框出错，tableName、key、value参数不能为空！";
		}
		//默认显示
		String headerLabel = (String)getAttributeValue("headerLabel");
		/*
		 * disabled 是否禁用该下拉列表 ，灰显不能选择
		 * readonly 下拉列表 是否只读
		 * multiple 可选择多个选项
		 * size 规定下拉列表中可见选项的数目
		 * value 默认值
		 * 
		 * options列表值
		 */
		String selectTmp = "<select {id} {class} {name} {disabled} {readonly}  {multiple} {value} {size}  {style}>{options}</select>";
		String id = (String)getAttributeValue("id");
		String mClass = (String)getAttributeValue("class");
		String name = (String)getAttributeValue("name");
		String value = getAttributeValue("value")+"";
		String disabled = (String)getAttributeValue("disabled");
		String readonly = (String)getAttributeValue("readonly");
		String multiple = (String)getAttributeValue("multiple");
		String size = (String)getAttributeValue("size");
		String width = (String)getAttributeValue("width");
		String heigth = (String)getAttributeValue("heigth");
		String style = (String)getAttributeValue("style");
		
		selectTmp = selectTmp.replace("{id}", Fun.ifNull(id,"id='"+id+"'", ""))
		.replace("{class}", Fun.ifNull(mClass,"class='"+mClass+"'", ""))
		.replace("{name}",  Fun.ifNull(name,"name='"+name+"'", ""))
		.replace("{disabled}", Fun.ifNull(disabled,"disabled='"+disabled+"'", ""))
		.replace("{readonly}", Fun.ifNull(readonly,"readonly='"+readonly+"'", ""))
		.replace("{multiple}",  Fun.ifNull(multiple,"multiple='"+multiple+"'", ""))
		.replace("{value}", Fun.ifNull(value,"value='"+value+"'", "")+"")
		.replace("{size}", Fun.ifNull(size,"size='"+size+"'", ""))
		.replace("{width}", Fun.ifNull(width,"width=\""+width+"\"", ""))
		.replace("{heigth}", Fun.ifNull(heigth,"heigth=\""+heigth+"\"", ""))
		.replace("{style}", Fun.ifNull(style,"style='"+style+"'", ""));
		
		//是否多选框
		if(!Fun.eqNull(multiple)){
			hasMultiple = true;
			selectTmp += "\n<script type=\"text/javascript\">$(\"#"+id+"\").treeMultiselect({ enableSelectAll: true, sortable: true ,selecteds:'"+Fun.ifNull(value,value, "null")+"'});</script>";
		}
		
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
			boolean isReadonlyBool = !Fun.eqNull(readonly) && readonly.equals("readonly");
			sb = this.loopDictItem(list, isReadonlyBool, headerLabel,selKey,selValue, value+"");
			if(!Fun.eqNull(sb)){
				selectTmp = selectTmp.replace("{options}", sb.toString());
			}
		}else{
			selectTmp = selectTmp.replace("{options}", "");
		}
		return selectTmp;
	}
	/**
	 * 遍历字典结果集
	 * @param list 
	 * @param isReadonly 是否为直读下拉列表
	 * @param headerLabel 默认显示
	 * @param value 字典项值
	 * @return
	 */
	private StringBuilder loopDictItem(List<Record> list,boolean isReadonly,String headerLabel,String selKey,String selValue,String initValue){
		StringBuilder sb = new StringBuilder();
		if(!Fun.eqListNull(list)){
			//添加头部
			if(!Fun.eqNull(headerLabel) && !isReadonly){
				sb.append(this.bulidOption("", headerLabel, initValue));
			}
			if(isReadonly){
				for(Record dictItem : list){
					if(!Fun.eqNull(dictItem)){
						if(dictItem.get(selValue).equals(initValue)){
							sb.append(this.bulidOption(dictItem.get(selKey),dictItem.get(selValue), initValue));
						}
					}
				}
			}else{
				for(Record dictItem : list){
					if(!Fun.eqNull(dictItem)){
						sb.append(this.bulidOption(dictItem.get(selKey),dictItem.get(selValue), initValue));
					}
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
	private String bulidOption(Object value, Object label, String selectValue){
		String val = value.toString().trim();
		String option="\t<option value='"+val+"'";
		if(val.equals(selectValue)){
			option += " selected = 'selected'";
		}
		if(hasMultiple){
			option += " data-section='请选择'";
		}
		option +=">"+label+"</option>";
		return option;
	}
}

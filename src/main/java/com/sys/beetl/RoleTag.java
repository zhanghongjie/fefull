package com.sys.beetl;

import java.io.IOException;
import java.util.List;

import org.beetl.core.GeneralVarTagBinding;

import com.biz.model.SysRole;
import com.frame.utils.Fun;

/**   
* @Title: RoleTag.java 
* @Package com.sys.beetl 
* @Description: 系统角色下拉框
* @author sos
* @date 2016年6月13日 下午5:59:33 
* @version V1.0   
*/
public class RoleTag extends GeneralVarTagBinding {

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
		String disabled = (String)getAttributeValue("disabled");
		String readonly = (String)getAttributeValue("readonly");
		String multiple = (String)getAttributeValue("multiple");
		String value = getAttributeValue("value")+"";
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
		
		StringBuffer sb = null;
		
		List<SysRole> list = SysRole.dao.find("SELECT t.`PK_ROLE`,t.`ROLE_NAME` FROM sys_role t WHERE t.`IS_VALID`=1 AND t.`ROLE_STATUS`=1;");
		if(!Fun.eqListNull(list)){
			boolean isReadonlyBool = !Fun.eqNull(readonly) && readonly.equals("readonly");
			sb = this.loopDictItem(list, isReadonlyBool, headerLabel, value+"");
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
	private StringBuffer loopDictItem(List<SysRole> list,boolean isReadonly,String headerLabel,String value){
		StringBuffer sb = new StringBuffer();
		if(!Fun.eqListNull(list)){
			//添加头部
			if(!Fun.eqNull(headerLabel) && !isReadonly){
				sb.append(this.bulidOption("", headerLabel, value));
			}
			if(isReadonly){
				for(SysRole dictItem : list){
					if(!Fun.eqNull(dictItem)){
						if(dictItem.getPkRole().equals(value)){
							sb.append(this.bulidOption(dictItem.getPkRole(), dictItem.getRoleName(), value));
						}
					}
				}
			}else{
				for(SysRole dictItem : list){
					if(!Fun.eqNull(dictItem)){
						sb.append(this.bulidOption(dictItem.getPkRole(), dictItem.getRoleName(), value));
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
		option +=">"+label+"</option>";
		return option;
	}
}

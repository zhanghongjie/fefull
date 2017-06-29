package com.frame.beetl.tag;

import java.io.IOException;
import java.util.List;

import org.beetl.core.GeneralVarTagBinding;

import com.biz.model.SysDict;
import com.frame.utils.Fun;

/**   
* @Title: DictRadioTag.java 
* @Package com.frame.beetl.tag 
* @Description: 单选标签 
* @author sos
* @date 2016年8月14日 下午12:35:47 
* @version V1.0   
*/
public class DictRadioTag extends GeneralVarTagBinding {
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
		//字典code
		String dictCode = (String)getAttributeValue("dictCode");
		if(!Fun.eqNull(dictCode)){
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
		
		StringBuffer sb = null;
		
		List<SysDict> list = SysDict.dao.find("select * from sys_dict where DICT_TYPE=?  order by SORT_ID asc",dictCode);
		if(!Fun.eqListNull(list)){
			sb = this.loopDictItem(list, value+"");
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
	private StringBuffer loopDictItem(List<SysDict> list,String value){
		StringBuffer sb = new StringBuffer();
		if(!Fun.eqListNull(list)){
			for(SysDict dictItem : list){
				if(!Fun.eqNull(dictItem)){
					sb.append(this.bulidHtml(dictItem.getDictKey(), dictItem.getDictValue(), value));
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
	private String bulidHtml(Object key, Object value, String selectValue){
		String  radio = radioTmp.replace("{key}", key+"").replace("{value}", value+"");
		String val = key.toString().trim();
		if(val.equals(selectValue)){
			radio = radio.replace("{checked}", "checked=\"checked\"");
		}else{
			radio = radio.replace("{checked}", "");
		}
		return radio;
	}
}

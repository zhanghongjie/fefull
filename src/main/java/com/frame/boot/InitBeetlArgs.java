package com.frame.boot;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.beetl.core.GroupTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.frame.cache.AppInfoCache;
import com.frame.constants.SysEnum;
import com.frame.utils.Fun;
import com.frame.utils.PropertiesUtil;

/**   
* @Title: InitBeetlArgs.java 
* @Package org.whale.beetl.boot 
* @Description:启动时初始化 Beetl共享变量
* @author sos
* @date 2015�?2�?4�?上午11:19:24 
* @version V1.0   
*/
public class InitBeetlArgs implements Bootable{
	private static Logger log=LoggerFactory.getLogger(InitBeetlArgs.class);
	

	@Override
	public void init(){
		log.info("初始化Beetl共享变量！！-->InitBeetlArgs-->init-->初始Beetl共享变量?");
		GroupTemplate gt = AppInfoCache.getInstance().getGroupTemplate();
		Map<String,Object> shared = new HashMap<String,Object>();
		List<Entry<Object, Object>> list = PropertiesUtil.prop2List("/beetlShareVar.properties");
		if(Fun.eqListNull(list)){
			log.warn("WARN-->InitBeetlArgs-->init-->beetlShareVar.properties文件找不到，或没有配置对应的Beetl共享变量?");
		}else{
			for (Entry<Object, Object> item : list) {
				shared.put(item.getKey()+"", item.getValue());
			}
		}
		gt.setSharedVars(shared);
	}
	
	@Override
	public int initOrder() {
		return SysEnum.BOOT_TYPE.BEETL_SHARED.getValue();
	}
}

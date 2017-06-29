package com.frame.boot;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.frame.utils.Fun;
/**   
* @Title: BootRuner.java 
* @Package com.frame.boot 
* @Description:初始化任务加载器
* @author sos
* @date 2016年5月14日 下午10:42:50 
* @version V1.0   
*/
public class BootRuner {
	private static final Logger logger = LoggerFactory
			.getLogger(BootRuner.class);
	private List<Bootable> list = new ArrayList<Bootable>();

	/**
	 * 添加初始化运行类
	 * 
	 * @param mBootable
	 */
	public BootRuner addBootable(Bootable mBootable) {
		if (!Fun.eqNull(mBootable)) {
			list.add(mBootable);
		}
		return this;
	}

	public void run() {
		logger.info("BootRuner-->run:运行初始化方法");
		if(!Fun.eqListNull(list)){
			//优先级根据order从小到大排序
			for (int i = 0; i < list.size(); i++) {
				for (int j = i + 1; j < list.size(); j++) {
					Bootable temp;
					if (list.get(i).initOrder() > list.get(j).initOrder()) {
						temp = list.get(j);
						list.set(j,list.get(i));
						list.set(i, temp);
					}
				}
			}
			
			for (Bootable bootable : list) {
				bootable.init();
			}
		}
	}
}

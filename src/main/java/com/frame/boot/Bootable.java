package com.frame.boot;


/**
 * 初始化运行接口
 * @author Administrator
 *
 */
public interface Bootable {
	/**
	 * 初始化方法
	 * @param context
	 * @return
	 */
	public void init();
	/**
	 * 初始化顺序，从小到大排序
	 * @return
	 */
	public int initOrder();
}

package com.frame.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.frame.exception.BaseException;

/**
 * @Title: PropertiesUtil.java
 * @Package org.whale.rpc.comm.utils
 * @version V1.0
 */
public class PropertiesUtil {
	private static final Logger logger = Logger.getLogger(PropertiesUtil.class);
	/**
	 * 
	 * @param propertiesFileName
	 *            Properties文件
	 * @param key
	 * @return value
	 */
	public static String getProperty(String propertiesFileName, String key) {
		Properties props = new Properties();
		try {
			;
			props.load(new FileInputStream(new File(propertiesFileName)));
		} catch (IOException e) {
			logger.error("找不到文件："+ propertiesFileName);
			e.printStackTrace();
		}
		return (String) props.get(key);
	}

	/**
	 * 更新properties文件
	 * 
	 * @param key
	 * @param value
	 */
	public static void setProperty(String propertiesFileName, String key,
			String value) {
		Properties props = new Properties();
		OutputStream os = null;
		try {
			String classRootPath = PropertiesUtil.class.getResource("/")
					.toString();
			if ("Windows".indexOf(System.getProperty("os.name")) != -1)
				classRootPath = classRootPath.replace("file:/", "");
			else
				classRootPath = classRootPath.replace("file:", "");

			props.load(PropertiesUtil.class.getResourceAsStream("/"
					+ propertiesFileName));
			os = new FileOutputStream(classRootPath + propertiesFileName);
			props.put(key, value);
			props.store(os, "");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (os != null)
				try {
					os.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
	}

	/**
	 * 将properties属件文件转换成list类型数据
	 * 
	 * @param fileName
	 *            properties属件文件
	 * @return List集合
	 */
	public static List<Entry<Object, Object>> prop2List(String fileAllPath) {
		List<Entry<Object, Object>> reList = null;
		if(Fun.eqNull(fileAllPath)){
			throw new BaseException("properties属性文件路径不能为空?");
		}
		try {
			Properties props = new Properties();
			//使用BufferedReader为支持中文
			BufferedReader bf = new BufferedReader(new InputStreamReader(PropertiesUtil.class.getResourceAsStream(fileAllPath),"UTF-8"));  
			props.load(bf);
			Iterator<Entry<Object, Object>> it = props.entrySet().iterator();
			reList = new ArrayList<Entry<Object, Object>>();
			while (it.hasNext()) {
				Entry<Object, Object> entry = (Entry<Object, Object>) it.next();
				reList.add(entry);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return reList;
	}
}
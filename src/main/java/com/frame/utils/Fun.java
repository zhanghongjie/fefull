package com.frame.utils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.frame.exception.BusinessException;

/**
 * Function.java
 * description: 公共函数
 * @author
 */
public class Fun {
    /**
     * <T> 添加范型，T实际类型占位符
     * a为空返回b，否则返回a
     * @param a
     * @param b
     * @return
     */
    public static  <T>T nvl(T a,T b){
    	if(Fun.eqNull(a)){
    		return b;
    	}
    	return a;
    }
    /**
     * <T> 添加范型，T实际类型占位符
     * a不为空返回b，为空返回c
     * @param a
     * @param b
     * @param c
     * @return
     */
    public static  <T>T nvl2(T a,T b,T c){
    	if(Fun.eqNull(a)){
    		return b;
    	}
    	return c;
    }
    /**
     * 字符串比较
     * @param value
     * @param equalValue
     * @return
     */
    public static boolean eqValue(String value, String equalValue) {
        if (value != null && value.equals(equalValue)) {
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * 判断字符串是否为空
     * @param value
     * @return
     */
    public static boolean eqNull(Object value) {
        if (value != null && !value.equals("") && !value.equals("null")) {
            return false;
        }
        else {
            return true;
        }
    }
    /**
     * 判断字符串是否不为空
     * @param value
     * @return
     */
    public static void notNull(Object value,String msg) {
    	if (value == null || value.toString().trim().equals("")  || value.equals("null")) {
    		throw new BusinessException(msg);
    	}
    }
    /**
     * 判断列表是否为空
     * @param value
     * @return
     */
    public static boolean eqListNull(List<?> list) {
    	if (list != null && list.size()>0) {
    		return false;
    	}
    	else {
    		return true;
    	}
    }

    /**
     * 验证整形字符串
     * @param numStr
     * @return
     */
    public static boolean eqNum(String numStr) {
        boolean flag = false;
        try {
            Long.parseLong(numStr);
            flag = true;
        }
        catch (Exception e) {
            flag = false;
        }
        return flag;
    }
    /**
     * 根据文件后缀判断文件类型
     * @param fileName 文件名
     * @param fileType 文件类型列表，是此类型返回true
     * @return
     */
    public static boolean isFileType(String fileName,String... fileType){
		if(!Fun.eqNull(fileName)){
			for(int i=0;i<fileType.length;i++){
				int index = fileName.toLowerCase().indexOf("."+fileType[i]);
				if(index>0){
					if(fileName.toLowerCase().substring(index,fileName.length()).equals("."+fileType[i])){
						return true;
					}
				}
			}
		}
		return false;
	}

	/**
	 * 取web目录路径
	 *
	 * @return
	 */
	public static String getWebPath() {
		String path = "";
		try {
			path = Fun.class.getResource("/").toURI().getPath();
			path = new File(path).getParentFile().getParentFile()
					.getCanonicalPath();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return path;
	}
    /**
     * 按分隔符返还回字符
     * @param txt分隔的字符串
     * @param chars 分隔的标点
     * @param b 向前分隔 true 向后false
     * @return
     */
    public static String getSubString(String txt,String chars,boolean b){
    	if(Fun.eqNull(txt)){
    		return null;
    	}
    	String tempChars = chars.replace("\\", "");
    	if(txt.indexOf(tempChars)<0){
    		return txt;
    	}
    	String[] temp = txt.split(chars);
    	if(b){
    		return temp[0];
    	}else{
    		return temp[1];
    	}
    }
    @SuppressWarnings("rawtypes")
	public static Map<String, String> getPara(Map<String, String[]> map) {
		Map.Entry element = null;
		String[] value = null;
		Map<String, String> reMap = new HashMap<String, String>();

		for (Iterator iter = map.entrySet().iterator(); iter.hasNext();) {
			element = (Map.Entry) iter.next();
			value = (String[]) element.getValue();
			if (!Fun.eqNull(value) && value.length > 0) {
				reMap.put(element.getKey().toString(), value[0]);
			}
		}
		return reMap;
	}
    
    /**
     * 若a为空则返回c,否则返回b
     * @param a
     * @param b
     * @param c
     * @return
     */
    public static  <T> T ifNull(T a, T b,T c) {
		if (Fun.eqNull(a)) {
			return c;
		}
		return b;
	}
    
    /**
     * 数组合成字符�?
     * @param array
     * @param separator
     * @return
     */
    public static String join(Object[] array, String separator) {  
        if (array == null) {  
            return null;  
        }  
        int arraySize = array.length;  
        int bufSize = (arraySize == 0 ? 0 : ((array[0] == null ? 16 : array[0].toString().length()) + 1) * arraySize);  
        StringBuilder buf = new StringBuilder(bufSize);  
        for (int i = 0; i < arraySize; i++) {  
            if (i > 0) {  
                buf.append(separator);  
            }  
            if (array[i] != null) {  
                buf.append(array[i]);  
            }  
        }  
        return buf.toString();  
    }  
    //test
    public static void main(String[] args) throws IllegalAccessException, InvocationTargetException,
            NoSuchMethodException {
    	System.out.println(Fun.getSubString("qqsafqadfsf.png", "\\.", false));
    }
}

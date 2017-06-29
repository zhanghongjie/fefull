package com.frame.utils;

import java.util.Date;
import java.util.Random;


public class RandomCodeUtil {

	public static final int Length = 6;
	public static final int PayLength = 20;
	public static final int TokenLength = 32;

	public static String getRandomCode() {
		return RandomCodeUtil.getRandomCode(null);
	}

	public static String getTokenRandom(){
		String str="ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for(int i = 0 ; i < TokenLength; ++i){
			int number = random.nextInt(36);//[0,36)
			sb.append(str.charAt(number));
		}
		return sb.toString();
	}

    public static String getPayRandomCode(){
        String str="ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for(int i = 0 ; i < PayLength; ++i){
            int number = random.nextInt(36);//[0,36)
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }

    public synchronized static String getKqPayRandomCode(){
        java.text.DateFormat format1 = new java.text.SimpleDateFormat("yyyyMMddHHmmssSS");
        String code  = format1.format(new Date());
        System.out.println(code);
    	return code;
    }
    public synchronized static String getPicRandomCode(){
    	java.text.DateFormat format1 = new java.text.SimpleDateFormat("yyyyMMddHHmmssSS");
    	String code  = format1.format(new Date());
    	System.out.println(code+getRandomCode());
    	return code+getRandomCode();
    }
    /**
	 * 获取随机数字
	 * @param bit 几位随机数字
	 * @return
	 */
	public static String getRandom(int bit) {
		String randomStr = "";
		while(randomStr.length() < bit) {
			randomStr += (int)(Math.random()*9);
			if(randomStr.equals("0")){
				randomStr = ((int)(Math.random()*8)+1)+"";
			}
		}
		return randomStr;
	}
    /**
     * 随机编号生成
     * @param pre 前置字符
     * @return
     */
    public static String getRandomCode(String pre){
    	StringBuilder sb = new StringBuilder(Fun.nvl(pre, ""));
    	sb.append(RandomCodeUtil.getRandom(9));
    	java.text.DateFormat format1 = new java.text.SimpleDateFormat("SS");
    	sb.append(format1.format(new Date()));
    	return sb.toString();
    }
    public static void main(String[] args) {
    	System.out.println(RandomCodeUtil.getRandomCode("XS"));
	}
}

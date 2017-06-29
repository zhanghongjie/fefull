package com.sys.util;

import java.io.Serializable;
import java.math.BigDecimal;

import com.frame.exception.BusinessException;

public class MathUtil implements Serializable { 
	private static final long serialVersionUID = -3345205828566485102L;  
	/**
	 * 运算结果保留精度
	 */
    private static final Integer SCALE_2 = 2;
    /**
     * 运算过程中保留精度
     */
    private static final Integer SCALE_10 = 10;  
    /**
     * 小数点保留位数
     */
    private static final Integer ROUND_2 = 2;  
  
    /**
     * 判断是否等于
     * @param value1
     * @param value2
     * @return
     */
    public static boolean isEquals(BigDecimal value1, BigDecimal value2) {
    	if(value1 == null || value2 == null) {
			throw new BusinessException("调用MathUtil.isEquals()参数为null");
    	}
    	if(value1.compareTo(value2) == 0) {
    		return true;
    	} else {
    		return false;
    	}
    }
    
    /**
     * 判断是否大于，如果value1 > value2，返回true
     * @param value1
     * @param value2
     * @return
     */
    public static boolean isGreaterThan(BigDecimal value1, BigDecimal value2) {
    	if(value1 == null || value2 == null) {
			throw new BusinessException("调用MathUtil.isGreaterThan()参数为null");
    	}
    	if(value1.compareTo(value2) > 0) {
    		return true;
    	} else {
    		return false;
    	}
    }
    
    /**
     * 判断是否大于等于，如果value1 >= value2，返回true
     * @param value1
     * @param value2
     * @return
     */
    public static boolean isGreaterThanOrEqual(BigDecimal value1, BigDecimal value2) {
    	if(value1 == null || value2 == null) {
			throw new BusinessException("调用MathUtil.isGreaterThanOrEqual()参数为null");
    	}
    	if(value1.compareTo(value2) >= 0) {
    		return true;
    	} else {
    		return false;
    	}
    }
    
    
    /**
     * 判断是否小于，如果value1 < value2，返回true
     * @param value1
     * @param value2
     * @return
     */
    public static boolean isLessThan(BigDecimal value1, BigDecimal value2) {
    	if(value1 == null || value2 == null) {
			throw new BusinessException("调用MathUtil.isLessThan()参数为null");
    	}
    	if(value1.compareTo(value2) < 0) {
    		return true;
    	} else {
    		return false;
    	}
    }
    
    /**
     * 判断是否小于等于，如果value1 <= value2，返回true
     * @param value1
     * @param value2
     * @return
     */
    public static boolean isLessThanOrEqual(BigDecimal value1, BigDecimal value2) {
    	if(value1 == null || value2 == null) {
			throw new BusinessException("调用MathUtil.isLessThanOrEqual()参数为null");
    	}
    	if(value1.compareTo(value2) <= 0) {
    		return true;
    	} else {
    		return false;
    	}
    }
    
    
    /** 
     * 提供精确的加法运算。 
     * @param value1 被加数 
     * @param value2 加数 
     * @return 两个参数的和 
     */  
    public static BigDecimal add(BigDecimal value1, BigDecimal value2) {
    	if(value1 == null || value2 == null) {
			throw new BusinessException("调用MathUtil.add()参数为null");
    	}
        return value1.add(value2);  
    }
  
    /** 
     * 提供精确的减法运算。 
     *  
     * @param value1 
     *            被减数 
     * @param value2 
     *            减数 
     * @return 两个参数的差 
     */  
    public static BigDecimal sub(BigDecimal value1, BigDecimal value2) {
    	if(value1 == null || value2 == null) {
			throw new BusinessException("调用MathUtil.sub()参数为null");
    	}
        return value1.subtract(value2);  
    }
    
    /** 
     * 提供精确的乘法运算。 
     *  
     * @param value1 
     *            被乘数 
     * @param value2 
     *            乘数 
     * @return 两个参数的积 
     */  
    public static BigDecimal mul(BigDecimal value1, BigDecimal value2) {
    	if(value1 == null || value2 == null) {
			throw new BusinessException("调用MathUtil.mul()参数为null");
    	}
        return value1.multiply(value2);
    }
    
    
    /** 
     * 提供（相对）精确的除法运算，当发生除不尽的情况时， 精确到小数点以后2位，以后的数字四舍五入。 
     *  
     * @param dividend 
     *            被除数 
     * @param divisor 
     *            除数 
     * @return 两个参数的商 
     */  
    public static BigDecimal divCal2(BigDecimal dividend, BigDecimal divisor) {  
        return div(dividend, divisor, SCALE_2);  
    }
    
    /** 
     * 提供（相对）精确的除法运算，当发生除不尽的情况时， 精确到小数点以后10位，以后的数字四舍五入。 
     *  
     * @param dividend 
     *            被除数 
     * @param divisor 
     *            除数 
     * @return 两个参数的商 
     */  
    public static BigDecimal divCal10(BigDecimal dividend, BigDecimal divisor) {  
        return div(dividend, divisor, SCALE_10);  
    }
  
    /** 
     * 提供（相对）精确的除法运算。 当发生除不尽的情况时，由scale参数指定精度，以后的数字四舍五入。 
     *  
     * @param dividend 
     *            被除数 
     * @param divisor 
     *            除数 
     * @param scale 
     *            表示表示需要精确到小数点以后几位。 
     * @return 两个参数的商 
     */  
    public static BigDecimal div(BigDecimal dividend, BigDecimal divisor, Integer scale) {
    	if(dividend == null || divisor == null) {
			throw new BusinessException("调用MathUtil.div()参数为null");
    	}
        if (scale < 0) {
            throw new IllegalArgumentException(  
                    "The scale must be a positive integer or zero");  
        }
        
        return dividend.divide(divisor, scale, BigDecimal.ROUND_HALF_UP);  
    }
    
    /** 
     * 提供精确的小数位四舍五入处理。 
     *  
     * @param value 
     *            需要四舍五入的数字 默认保留2位小数点
     * @return 四舍五入后的结果 
     */  
    public static BigDecimal round2(BigDecimal value) {
    	return round(value, SCALE_2);
    }
    
    /** 
     * 提供精确的小数位四舍五入处理。 
     *  
     * @param value 
     *            需要四舍五入的数字 
     * @param scale 
     *            小数点后保留几位 
     * @return 四舍五入后的结果 
     */  
    public static BigDecimal round(BigDecimal value, Integer scale) {
    	if(value == null) {
			throw new BusinessException("调用MathUtil.round()参数为null");
    	}
        if (scale < 0) {  
            throw new IllegalArgumentException(  
                    "The scale must be a positive integer or zero");  
        }  
        BigDecimal one = new BigDecimal("1");  
        return value.divide(one, scale, BigDecimal.ROUND_HALF_UP);  
    }
    
    /** 
     * 提供精确的小数位处理（舍弃后面的小数，没有四舍五入）
     *  
     * @param value 
     *            需要精确处理的数字
     * @param scale 
     *            小数点后保留几位
     * @return
     */  
    public static BigDecimal roundDown(BigDecimal value, Integer scale) {
    	if(value == null) {
			throw new BusinessException("调用MathUtil.roundDown()参数为null");
    	}
        if (scale < 0) {  
            throw new IllegalArgumentException(  
                    "The scale must be a positive integer or zero");  
        }  
        BigDecimal one = new BigDecimal("1");  
        return value.divide(one, scale, BigDecimal.ROUND_DOWN);  
    }
    
    /** 
     * 提供精确的小数位处理（舍弃后面的小数，没有四舍五入，保留2位）
     *  
     * @param value 
     *            需要精确处理的数字 
     * @return
     */  
    public static BigDecimal roundDown2(BigDecimal value) {
    	return roundDown(value, ROUND_2);
    }
    
    public static String formatScale2(BigDecimal value) {
    	if(value == null) {
			throw new BusinessException("调用MathUtil.formatScale2()参数为null");
    	}
    	java.text.DecimalFormat df = new java.text.DecimalFormat("#.00");  
    	return df.format(value);
    }
    
    public static String formatScale3(BigDecimal value) {
    	if(value == null) {
			throw new BusinessException("调用MathUtil.formatScale3()参数为null");
    	}
    	java.text.DecimalFormat df = new java.text.DecimalFormat("###,###.00");  
    	return df.format(value);
    }
    
    /**百分比例、小数适配单位*/
	public static final BigDecimal PERCENT_DECIMAL_ADAPT_UNIT = new BigDecimal(100);
    /**万分比例、小数适配单位*/
	public static final BigDecimal PERCENT_DECIMAL_ADAPT_UNIT_W = new BigDecimal(10000);
	
	/**
	 *
	 * 百分比转换为小数值
	 * @param percentVal 需转换的百分比值
	 * @return 转换后的小数值
	 */
	public static BigDecimal percentToDecimal(BigDecimal percentVal) {
		return percentVal == null ? null : MathUtil.divCal10(percentVal, PERCENT_DECIMAL_ADAPT_UNIT);
	}
	
	/**
	 *
	 * 万分比转换为小数值
	 * @param percentVal 需转换的百分比值
	 * @return 转换后的小数值
	 */
	public static BigDecimal percentWToDecimal(BigDecimal percentVal) {
		return percentVal == null ? null : MathUtil.divCal10(percentVal, PERCENT_DECIMAL_ADAPT_UNIT_W);
	}
	
	/**
	 * 小数值转换为百分比
	 * @param decimal 需转换小数值
	 * @return 百分比
	 */
	public static BigDecimal decimalToPercent(BigDecimal decimal) {
		return decimal == null ? null : MathUtil.mul(decimal, PERCENT_DECIMAL_ADAPT_UNIT);
	}
	
	/**
	 * 小数值转换为万分比
	 * @param decimal 需转换小数值
	 * @return 百分比
	 */
	public static BigDecimal decimalWToPercent(BigDecimal decimal) {
		return decimal == null ? null : MathUtil.mul(decimal, PERCENT_DECIMAL_ADAPT_UNIT_W);
	}
	
	/**千分比例、小数适配单位*/
	public static final BigDecimal PERMILLAGE_DECIMAL_ADAPT_UNIT = new BigDecimal(1000);
	 /**
	 *
	 * 千分比转换为小数值
	 * @param permillageVal 需转换的千分比值
	 * @return 转换后的小数值
	 */
	public static BigDecimal permillageToDecimal(BigDecimal permillageVal) {
		return permillageVal == null ? null : MathUtil.divCal10(permillageVal, PERMILLAGE_DECIMAL_ADAPT_UNIT);
	}
	
	/**
	 * 小数值转换为千分比
	 * @param decimal 需转换小数值
	 * @return 千分比
	 */
	public static BigDecimal decimalToPermillage(BigDecimal decimal) {
		return decimal == null ? null : MathUtil.mul(decimal, PERMILLAGE_DECIMAL_ADAPT_UNIT);
	}
	
	/** 
     * 是否是整数倍（被除数是否是除数的倍数，是返回true，否返回false）
     *  
     * @param dividend 
     *            被除数 
     * @param divisor 
     *            除数 
     * @return 
     */  
    public static boolean isTimes(BigDecimal dividend, BigDecimal divisor) {  
    	if(dividend == null || divisor == null) {
			throw new BusinessException("调用MathUtil.isTimes()参数为null");
    	}
    	if (divisor.compareTo(BigDecimal.ZERO) == 0) {
            throw new IllegalArgumentException(  
                    "The divisor can not be zero");  
        }
    	
    	if(dividend.remainder(divisor)== BigDecimal.ZERO){
    		return true;
    	}else{
    		return false;
    	}
    	
          
    }
    
	
	public static void main(String[] args) {
	}

}

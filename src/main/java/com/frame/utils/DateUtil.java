package com.frame.utils;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;



/**
 * <p>Description:提供常用静态类,常用静态类如时间等</p>
 */

public class DateUtil {
	private static Calendar curdate = Calendar.getInstance();




    /**
     * funcion   getCurrDate
     * desc      获取今天所在的年月日（格式：yyyy-mm-dd）
     * @param    sDateFormat时间格式如：YYYY-MM-DD 或MM
     * @return   String 当时间的格式串，例如2007-05-11
     * @see
     */
	public static String getCurrDate() {
		String sDateFormat = "yyyy-MM-dd HH:mm:ss";
		return DateUtil.getCurrDate(sDateFormat);
	}
    public static String getCurrDate(String sDateFormat) {
    	Calendar gc = new GregorianCalendar();
        java.util.Date date = gc.getTime();
        SimpleDateFormat sf = new SimpleDateFormat(sDateFormat);
        String result = sf.format(date);
        return result;
    }
    /**
     * parseDate
     * 按YYYYMMDD格式解析日期字符串为日期
     * @param dateStr，日期字符串，例如'20070501'
     * @return Date 日期
     */
    public static Date parseDate(String dateStr) {
        if (dateStr == null ||dateStr.equals("")) return null;
    	SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
        try {
            Date date = sf.parse(dateStr);
        return date;
        } catch (Exception ex) {
           ex.printStackTrace();
           return null;
        }
    }
    /**
     * parseDate
     * sDateFormat 如YYYYMMDD格式解析日期字符串为日期
     * @param dateStr，日期字符串，例如'20070501'
     * @return Date 日期
     */
    public static Date parseDate(String dateStr,String sDateFormat) {
        if (dateStr == null ||dateStr.equals("")) return null;
    	SimpleDateFormat sf = new SimpleDateFormat(sDateFormat);
        try {
            Date date = sf.parse(dateStr);
        return date;
        } catch (Exception ex) {
           ex.printStackTrace();
           return null;
        }
    }
    /**
     * 按YYYYMMDDHH24MISS格式解析日期字符串为日期
     * @param dateStr，日期字符串，例如'20070501011102'
     * @return Date 日期
     */
    public static Date parseDateFull(String dateStr) {
    	SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddhhmmss");
        try {
            Date date = sf.parse(dateStr);
        return date;
        } catch (Exception ex) {
           ex.printStackTrace();
           return null;
        }
    }

    /**
     * 按给定格式输出系统时间的字符串
     * @param formatStr
     * @return
     */
    public static String getSysDateByFormat(String formatStr) {
        String datestr = "";

        try {
            java.text.DateFormat df = new java.text.SimpleDateFormat(formatStr);
            java.util.Date date = new java.util.Date();
            datestr = df.format(date);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return datestr;
    }

    /**
     * 检查是否过期(YYYYMMDD)
     * @param expiredDate 过期的日期字符串，例如'20070501'
     * @return boolean true-过期  false-没有过期
     */
    public static boolean checkExpired(String expiredDate) {
        if (expiredDate == null||expiredDate.equals("")||expiredDate.equals("0")) return false;

    	Date expDate = DateUtil.parseDate(expiredDate);
    	Date curDate = new java.util.Date();

    	Calendar cal = new GregorianCalendar();
    	cal.setTime(expDate);
    	Calendar calCur = new GregorianCalendar();
    	calCur.setTime(curDate);

    	if (cal.compareTo(calCur)>=0) {
    	    return false;
    	} else {
    		return true;
    	}
    }

    /**
     * 判断是否过期（时间段内）
     * @param inureDate 生效日期字符串，例如'20020101'
     * @param expiredDate 失效日期字符串，例如'20080101'
     * @return
     */
    public static boolean checkExpired(String inureDate, String expiredDate) {
        if (inureDate == null||inureDate.equals("")) return false;
        if (expiredDate == null||expiredDate.equals("")) return false;

        Date inuDate = DateUtil.parseDate(inureDate);
        Date expDate = DateUtil.parseDate(expiredDate);
        Date curDate = new java.util.Date();

        Calendar calInu = new GregorianCalendar();
        calInu.setTime(inuDate);

        Calendar calExp = new GregorianCalendar();
        calExp.setTime(expDate);

        Calendar calCur = new GregorianCalendar();
        calCur.setTime(curDate);

        int i = calCur.compareTo(calInu);
        int j = calCur.compareTo(calExp);
        if (i>0&&j<=0) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 检查是否过期(YYYYMMDDHH24MISS)
     * @param expiredDate 失效日期字符串，例如'20080101235959'
     * @return boolean true-过期 false-没有过期
     */
    public static boolean checkExpiredFull(String expiredDate) {
    	Date expDate = DateUtil.parseDateFull(expiredDate);
    	Date curDate = new java.util.Date();

    	Calendar cal = new GregorianCalendar();
    	cal.setTime(expDate);
    	Calendar calCur = new GregorianCalendar();
    	calCur.setTime(curDate);

    	if (cal.compareTo(calCur)>=0) {
    	    return false;
    	} else {
    		return true;
    	}
    }

    /**
     * 判断是否在两个时间段内（YYYYMMDDHH24MISS）
     * @param inureDate 生效日期字符串，例如'20080101235959'
     * @param expiredDate 失效日期字符串，例如'20080101235959'
     * @return boolean true-过期 false-没有过期
     */
    public static boolean checkExpiredFull(String inureDate, String expiredDate) {
        if (inureDate == null||inureDate.equals("")) return false;
        if (expiredDate == null||expiredDate.equals("")) return false;

        Date inuDate = DateUtil.parseDateFull(inureDate);
        Date expDate = DateUtil.parseDateFull(expiredDate);
        Date curDate = new java.util.Date();

        Calendar calInu = new GregorianCalendar();
        calInu.setTime(inuDate);

        Calendar calExp = new GregorianCalendar();
        calExp.setTime(expDate);

        Calendar calCur = new GregorianCalendar();
        calCur.setTime(curDate);

        int i = calCur.compareTo(calInu);
        int j = calCur.compareTo(calExp);
        if (i>0&&j<0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 时间检查，看当前时间是否在时段允许范围内，<br>
     * 共12个数字，1-2表示起始的日期 3-4表示结束日期 5-8表示起始时间（小时分钟） 9-12表示结束时间（小时分钟）<br>
     * 例如：010408302330 表示 允许时间范围为周一到周四的8：30-晚上23：30<br>
     * @param timeAllowed，格式：'010408302330'
     * @return boolean true-在允许时段 false-不在允许时段
     */
    public static boolean checkTime(String timeAllowed) {
        if (timeAllowed == null || timeAllowed.equals("")) return true;

        if (timeAllowed.length()!=10) return true;
        if (!isNumeric(timeAllowed)) return true;

        int beginWeekDay = Integer.parseInt(timeAllowed.substring(0,1));
        int endWeekDay = Integer.parseInt(timeAllowed.substring(1,2));
        int beginTime = Integer.parseInt(timeAllowed.substring(2,6));
        int endTime = Integer.parseInt(timeAllowed.substring(6));

        Calendar cal = GregorianCalendar.getInstance();


        //int curWeekDay = cal.get(getWeek());
        int curWeekDay = getWeekDay();
        int curTime = cal.get(Calendar.HOUR_OF_DAY)*100+cal.get(Calendar.MINUTE);

        StringBuffer buf = new StringBuffer();

        buf.append("week:").append(beginWeekDay).append(":").append(endWeekDay);
        buf.append("time:").append(beginTime).append(":").append(endTime);
        buf.append(" cur week:").append(curWeekDay).append(" cur time:").append(curTime);
        //System.out.println(buf.toString());

        if ((curWeekDay >= beginWeekDay && curWeekDay <= endWeekDay)&&(curTime > beginTime && curTime <= endTime)) {
            return true;
        }
        else {
            return false;
        }
    }
    /**
     * 是否为数字
     * @param num
     * @return
     */
    public static boolean isNumeric(String num) {
	   try {
	    Double.parseDouble(num);
	    return true;
	   } catch (NumberFormatException e) {
	    return false;
	   }
	}

    /**
     * 按一定格式获取几天前或几天后的日期字符串
     * @param days，日期时长
     * @param timeFormat，日期格式 例如'yyyyMMdd'
     * @return String 日期字符串
     */
    public static String getSomeDaysAgoDate(int days,String timeFormat){
        Calendar calInu = new GregorianCalendar();
        calInu.add(Calendar.DATE,days);
        java.util.Date d = calInu.getTime();
        SimpleDateFormat sf = new SimpleDateFormat(timeFormat);
        String newdate = sf.format(d);
        return newdate;
    }

    /**
     * 获取星期几<br>
     *（原先的cal的返回是1-周日，2-周一...，现在要变成1-周一，... 7-周日)
     * @return int 星期几
     */
    public static int getWeekDay() {
        Calendar cal = GregorianCalendar.getInstance();
        int ret = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (ret == 0) ret = 7;

        return ret;
    }

    /**
     * 获取某个月的最后一天的字符串
     * @param dateStr 输入日期，格式可以是'yyyyMM'或yyyyMMdd
     * @return String 日期字符串，例如'20080131'
     */
    public static String getLastDayOfMonth(String dateStr) {
        if(dateStr.length()<8){
            dateStr = dateStr + "01";
        }
        Calendar calendar = new GregorianCalendar();

        String format = "yyyyMMdd";
        if( dateStr != null && dateStr.trim().length() == 6){
            format = "yyyyMM";
        }
        SimpleDateFormat bartDateFormat = new SimpleDateFormat(format);
        try {
            java.util.Date date = bartDateFormat.parse(dateStr);
            calendar.setTime(date);
        }
        catch (Exception ex) {System.out.println(ex.getMessage());}

        calendar.roll(Calendar.MONDAY, 0);//0取本月最后一天，1取下一个月的最后一天，-1取上一个月的最后一天
        calendar.roll(Calendar.DATE, 0 - calendar.get(Calendar.DATE));
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return bartDateFormat.format(calendar.getTime()).substring(6,8);
    }

    /**
     * 获取输入的时间运算后时间
     * @author guoss
     * @param dt  当前时间
     * @param iIndex
     *           1:年
     *           2:月
     *           3:日
     *           4:小时
     *           5:分钟
     *           6:秒
     *        iDistance 间隔
     */
    public static String getTime(String sDate,int iIndex,int iDistance){
      String sTemp;
      try{
         Calendar cal = Calendar.getInstance();

         cal.set(Calendar.YEAR,Integer.parseInt(sDate.substring(0,4),10));
         cal.set(Calendar.MONTH,Integer.parseInt(sDate.substring(4,6),10)-1);
         cal.set(Calendar.DAY_OF_MONTH,Integer.parseInt(sDate.substring(6,8),10));

         if(iIndex==1){
            cal.add(Calendar.YEAR,iDistance);
         }else if(iIndex==2){
            cal.add(Calendar.MONTH,iDistance);
         }else if(iIndex==3){
            cal.add(Calendar.DAY_OF_MONTH,iDistance);
         }
         sTemp = cal.get(Calendar.YEAR)+"";
         sTemp += DateUtil.getFullLength(cal.get(Calendar.MONTH)+1,2);
         sTemp += DateUtil.getFullLength(cal.get(Calendar.DAY_OF_MONTH),2);
      }catch(Exception e){
         System.out.println(e.toString());
         sTemp = "20020101";
      }
      return sTemp;
    }

    /**
     * 在一个整数前面补给定个数零
     * @param  iOral : 原来的数
     * @param  iLength : 补充为几位
     * @return String : 补了一些零后使之达到一个个数（前补零）
     */
    private static String getFullLength(int iOral,int iLength){
       int iIndex = (""+iOral).length();
       if(iIndex>iLength){
          return ""+iOral;
       }
       String sTemp = ""+iOral;
       for(int i=0;i<iLength-iIndex;i++){
          sTemp = "0"+sTemp;
       }
       return sTemp;
    }

    /**
     * 获取上个月份
     * @param dateStr
     * @return
     */
    public static String getLastMonth(String dateStr){
    	if(Fun.eqNull(dateStr)){
    		return null;
    	}
        Calendar calendar = new GregorianCalendar();
        String format = "yyyyMMdd";
        if( dateStr != null && dateStr.trim().length() == 6){
            format = "yyyyMM";
        }
        SimpleDateFormat bartDateFormat = new SimpleDateFormat(format);
        try {
            java.util.Date date = bartDateFormat.parse(dateStr);
            calendar.setTime(date);
        }catch (Exception ex) {System.out.println(ex.getMessage());}
        calendar.add(Calendar.MONTH, -1);
        return bartDateFormat.format(calendar.getTime()).toString();
    }

    /**
     * 获取上一天
     * @param dateStr
     * @return
     */
    public static String getLastDay(String dateStr){
    	if(Fun.eqNull(dateStr)){
    		return null;
    	}
        Calendar calendar = new GregorianCalendar();
        String format = "yyyyMMdd";
        if( dateStr != null && dateStr.trim().length() == 6){
            format = "yyyyMM";
        }
        SimpleDateFormat bartDateFormat = new SimpleDateFormat(format);
        try {
            java.util.Date date = bartDateFormat.parse(dateStr);
            calendar.setTime(date);
        }catch (Exception ex) {System.out.println(ex.getMessage());}
        calendar.add(Calendar.DATE, -1);
        return bartDateFormat.format(calendar.getTime()).toString();
    }

    /**
     * 获取当前的年份
     * @return
     */
    public static String getYear() {
        Calendar cal = GregorianCalendar.getInstance();
        int ret = cal.get(Calendar.YEAR);
        return String.valueOf(ret);
    }

    /**
     * 获取当前的月份
     * @return
     */
    public static String getMonth() {
        Calendar cal = GregorianCalendar.getInstance();
        int ret = cal.get(Calendar.MONTH)+1;
        return String.valueOf(ret);
    }

    /**
     * 获取几个月前的月份
     * @param month 当前月
     * @param monthNum 月数
     * @return
     */
    public static String getLastMonths(String month,int monthNum){
    	if(Fun.eqNull(month)){
    		return null;
    	}
        Calendar calendar = new GregorianCalendar();
        String format = "yyyyMMdd";
        if( month != null && month.trim().length() == 6){
            format = "yyyyMM";
        }
        SimpleDateFormat bartDateFormat = new SimpleDateFormat(format);
        try {
            java.util.Date date = bartDateFormat.parse(month);
            calendar.setTime(date);
        }catch (Exception ex) {System.out.println(ex.getMessage());}
        calendar.add(Calendar.MONTH, -monthNum);
        return bartDateFormat.format(calendar.getTime()).toString();
    }

    /**
     * 获取几个月后的月份
     * @param month 当前月
     * @param monthNum 月数
     * @return
     */
    public static String getNextMonths(String month,int monthNum){
    	if(Fun.eqNull(month)){
    		return null;
    	}
        Calendar calendar = new GregorianCalendar();
        String format = "yyyyMMdd";
        if( month != null && month.trim().length() == 6){
            format = "yyyyMM";
        }
        SimpleDateFormat bartDateFormat = new SimpleDateFormat(format);
        try {
            java.util.Date date = bartDateFormat.parse(month);
            calendar.setTime(date);
        }catch (Exception ex) {System.out.println(ex.getMessage());}
        calendar.add(Calendar.MONTH, +monthNum);
        return bartDateFormat.format(calendar.getTime()).toString();
    }

    /**
     * 获取两个时间相差的天数
     * @param startDate 开始时间
     * @param endDate 结束时间
     * @return
     */
    public static long getSubDate(String startDate,String endDate){
    	if(Fun.eqNull(startDate) || Fun.eqNull(endDate)){
    		return 0L;
    	}
        Calendar calendar = new GregorianCalendar();
        String format = "yyyyMMdd";
        if( startDate != null && startDate.trim().length() == 6){
            format = "yyyyMM";
        }
        SimpleDateFormat bartDateFormat = new SimpleDateFormat(format);
        try {
            java.util.Date date = bartDateFormat.parse(startDate);
            calendar.setTime(date);
        }catch (Exception ex) {System.out.println(ex.getMessage());}

        Calendar calendar1 = new GregorianCalendar();
        String format1 = "yyyyMMdd";
        if( endDate != null && endDate.trim().length() == 6){
            format1 = "yyyyMM";
        }
        SimpleDateFormat bartDateFormat1 = new SimpleDateFormat(format1);
        try {
            java.util.Date date = bartDateFormat1.parse(endDate);
            calendar1.setTime(date);
        }catch (Exception ex) {System.out.println(ex.getMessage());}

        return (calendar1.getTime().getTime()-calendar.getTime().getTime())/(24*60*60*1000);
    }
    
    /**
     * 获取两个时间相差的小时数
     * @param date1
     * @param date2
     * @return
     */
    public static Long getSubHour(Date date1 ,Date date2){
    	if(!Fun.eqNull(date1) && !Fun.eqNull(date2)){
    		return Math.abs((date1.getTime()-date2.getTime())/(60*60*1000));
    	}
    	return -1L;
    }

	public static String getDefaultFirstDate(int circle_id,int Date_Bwtween){
		String tj_date = "";

		if (circle_id == 1){
			curdate.add(Calendar.DATE,1 - Date_Bwtween);
			java.util.Date d = curdate.getTime();
			SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
			tj_date = sf.format(d);
			curdate.add(Calendar.DATE,Date_Bwtween - 1);
		}
		else if(circle_id == 11){
			curdate.add(Calendar.DATE,1);
			curdate.add(Calendar.MONTH,-1 * Date_Bwtween);
			java.util.Date d = curdate.getTime();
			SimpleDateFormat sf = new SimpleDateFormat("yyyyMM");
			tj_date = sf.format(d);
			curdate.add(Calendar.MONTH,Date_Bwtween);
			curdate.add(Calendar.DATE,-1);
		}
		else if(circle_id ==2){
			curdate.add(Calendar.DATE,1);
			java.util.Date d = curdate.getTime();
			SimpleDateFormat sf = new SimpleDateFormat("dd");
			int dd = Integer.parseInt(sf.format(d));
			if ( dd <=7){
				dd = 4;
				curdate.add(Calendar.MONTH,-1);
			}
			else if ( dd <= 14 )  dd = 1;
			else if ( dd <= 21) dd = 2;
			else dd = 3;

			d = curdate.getTime();
			sf = new SimpleDateFormat("yyyyMM");
			tj_date = sf.format(d) + "0" + String.valueOf(dd);
			int yyyy = Integer.parseInt(tj_date) / 10000;
			int mm = (Integer.parseInt(tj_date) / 100 ) % 100;
			int day = Integer.parseInt(tj_date) % 100;
			day = day - (Date_Bwtween % 4) + 1;
			int i = 0;
			while (1>0)
			{
				if (day > 0) break;
				day += 4;
				i++;
			}
			mm = mm - i - (Date_Bwtween / 4);
			i = 0;
			while (1>0)
			{
				if (mm > 0) break;
				mm += 12;
				i++;
			}
			yyyy -= i;
			tj_date = String.valueOf(yyyy * 10000 + mm * 100 + day);
			if(dd == 4) curdate.add(Calendar.MONTH,1);
			curdate.add(Calendar.DATE,-1);
		}
		else if(circle_id == 21){
			curdate.add(Calendar.DATE,1);
			curdate.add(Calendar.YEAR,-1 * Date_Bwtween);
			java.util.Date d = curdate.getTime();
			SimpleDateFormat sf = new SimpleDateFormat("yyyy");
			tj_date = sf.format(d);
			curdate.add(Calendar.YEAR,Date_Bwtween);
			curdate.add(Calendar.DATE,-1);
		}

		return tj_date;
	}
    /**
     * 检查是否为日期(yyyyMM)
     * @param strDate
     * @return
     */
	public static boolean checkIsDate(String strDate){

		DateFormat   formatter   =   new   SimpleDateFormat( "yyyyMM");


        try{
            Date   date   =   (Date)formatter.parse(strDate);
            return   strDate.equals(formatter.format(date));
        }catch(Exception   e){
            return   false;
        }

	}

    //日期格式化
	public static String formatData(double pass,String formatstr){
		try
		{
			java.text.DecimalFormat nf = (java.text.DecimalFormat)java.text.NumberFormat.getInstance();
			nf.applyPattern(formatstr);
			return nf.format(pass);
		}
		catch(Exception e){return String.valueOf(pass);}
	}

	//中高端中
	public static Double getDateProcess(int cur_date){
		if(String.valueOf(cur_date).length() != 8) return null;
		int mm = cur_date / 100 % 100;
		int days = 0;
		for(int i = 0;i < mm;i++) days += getDays(cur_date / 100);
		return 1 - 0.2 * days / 365;
	}

	/* 返回某月的天数 */
	public static int getDays(int mon)
	{
		int mon_mm = mon % 100;
		int mon_yyyy = mon / 100;
		int count;
		if(mon_mm < 13)
		{
			if(((mon_yyyy % 400 == 0) || (mon_yyyy % 4 == 0 && mon_yyyy % 100 > 0)) && mon_mm == 2) count = 29;
			else if((mon_mm == 1) || (mon_mm == 3) || (mon_mm == 5) || (mon_mm == 7) || (mon_mm == 8) || (mon_mm == 10) || (mon_mm == 12)) count = 31;
			else if((mon_mm == 4) || (mon_mm == 6) || (mon_mm == 9) || (mon_mm == 11)) count = 30;
			else count = 28;
		}
		else count = 1;
		return count;
	}

  ////转化成2007-01-01 把"6/22/2007 0:0:0"这种格式的时间转换成20070622的格式
  public static String toyyyyMMdd(String str){

     String tempStr[]=null;
     int tempInt=0;
     String returnStr="";
     if(!str.equals("") && str!=null){
     tempStr=str.split(" ");
     tempStr=tempStr[0].split("/");
     returnStr=tempStr[2]+"-";
     if(Integer.parseInt(tempStr[0])<10){
       returnStr+="0"+tempStr[0]+"-";
     }else{
       returnStr+=tempStr[0]+"-";
     }
     if(Integer.parseInt(tempStr[1])<10){
       returnStr+="0"+tempStr[1];
     }else{
       returnStr+=tempStr[1];
     }
     }
     return returnStr;
  }

  //转化成20070101
  public static String toyyyyMMdd1(String str){

       String tempStr[]=null;
       int tempInt=0;
       String returnStr="";
       if(str!=null && !str.equals("")){
       tempStr=str.split(" ");
       tempStr=tempStr[0].split("/");
       returnStr=tempStr[2];
       if(Integer.parseInt(tempStr[0])<10){
         returnStr+="0"+tempStr[0];
       }else{
         returnStr+=tempStr[0];
       }
       if(Integer.parseInt(tempStr[1])<10){
         returnStr+="0"+tempStr[1];
       }else{
         returnStr+=tempStr[1];
       }
       }
       return returnStr;
    }

  /**
   * 判断是否为合法的日期时间字符串
   * @param str_input 输入日期，格式可以是'yyyyMM'或yyyyMMdd
   * return boolean;符合为true,不符合为false
   */
    public static  boolean isDate(String str_input,String rDateFormat){
      if (str_input!=null) {
             SimpleDateFormat formatter = new SimpleDateFormat(rDateFormat);
             formatter.setLenient(false);
             try {
                 formatter.format(formatter.parse(str_input));
             } catch (Exception e) {
                 return false;
             }
             return true;
         }
      return false;

    }

    /**
     * 获取前几天或后几天日期 yyyyMMdd
     * @param dateStr
     * @param 天数
     * @return 20080229
     */
    public static String getSomeDaysAgoOrAfter(String dateStr,int dcount){
    	if(Fun.eqNull(dateStr)){
    		return null;
    	}
        Calendar calendar = new GregorianCalendar();
        String format = "yyyyMMdd";
        if( dateStr != null && dateStr.trim().length() == 6){
            format = "yyyyMM";
        }
        SimpleDateFormat bartDateFormat = new SimpleDateFormat(format);
        try {
            java.util.Date date = bartDateFormat.parse(dateStr);
            calendar.setTime(date);
        }catch (Exception ex) {System.out.println(ex.getMessage());}
        calendar.add(Calendar.DATE, dcount);
        return bartDateFormat.format(calendar.getTime()).toString();
    }

    /**
     * 获取某个月的最后一天的字符串 yyyyMMdd
     * @param dateStr 输入日期，格式可以是'yyyyMM'或yyyyMMdd
     * @return String 日期字符串，例如'20080131'
     */
    public static String getLastDateOfMonth(String dateStr) {
        if(dateStr.length()<8){
            dateStr = dateStr + "01";
        }
        Calendar calendar = new GregorianCalendar();

        String format = "yyyyMMdd";
        if( dateStr != null && dateStr.trim().length() == 6){
            format = "yyyyMM";
        }
        SimpleDateFormat bartDateFormat = new SimpleDateFormat(format);
        try {
            java.util.Date date = bartDateFormat.parse(dateStr);
            calendar.setTime(date);
        }
        catch (Exception ex) {System.out.println(ex.getMessage());}

        calendar.roll(Calendar.MONDAY, 0);//0取本月最后一天，1取下一个月的最后一天，-1取上一个月的最后一天
        calendar.roll(Calendar.DATE, 0 - calendar.get(Calendar.DATE));
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return bartDateFormat.format(calendar.getTime());
    }

    public static String getCurrDate(Date date,String sDateFormat) {
		SimpleDateFormat sf = new SimpleDateFormat(sDateFormat);
		String result = sf.format(date);
		return result;
	}

    /**
     * 获取近几年map，以map形式返回
     */
    public static Map getLastYear(int yearCounter){
    	if(yearCounter < 1)
    		return null;
    	Map yearMap = new LinkedHashMap<Integer, Integer>();
    	for (int i = yearCounter; i > 0; i--) {
    		yearMap.put(Integer.parseInt(DateUtil.getCurrDate("yyyy"))-i+1, yearCounter-i);
		}
    	return yearMap;
    }

    /**
     * 取两间隔年份中的所有年份
     * @param beginYear
     * @param endYear
     * @return
     */
    public static String[] getBetweenYears(int beginYear,int endYear){
    	if(endYear<beginYear){
    		return null;
    	}
    	StringBuffer sb = new StringBuffer();
    	String[] re = null;
    	for (int i = 0,len=endYear-beginYear; i <= len; i++) {
    		sb.append(beginYear+i)
    		.append(",");
		}
    	re = sb.substring(0,sb.length()-1).split(",");
    	return re;
    }
    /**
     * 测试用的main函数
     * @param args
     */
    public static void main(String[] args) {
    	//String dateStr = "2013/11/5 8:10:11";

    	//Date date = DateUtil.parseDate(dateStr, "yyyy/MM/dd HH:mm:ss");
    	//System.out.println(DateUtil.getCurrDate("yyyy"));
//    	System.out.println(DateUtil.getCurrDate(date,"yyyyMMdd"));
//    	System.out.println(DateUtil.getCurrDate(date,"HH:mm:ss"));
    	Map map = getLastYear(3);
    	Set<Integer> key = map.keySet();
        for (Iterator it = key.iterator(); it.hasNext();) {
        	 Integer s = (Integer) it.next();
             System.out.println(s+":"+map.get(s));
        }
    }
}
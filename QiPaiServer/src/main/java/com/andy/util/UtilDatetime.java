package com.andy.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

/**
 * 系统时间的帮助类
 * 
 */
public class UtilDatetime {
	//datetime,date,time,year,month,day,hour,minute,second
	public static final String KEY_DATETIME= "datetime";
	public static final String KEY_DATE= "date";
	public static final String KEY_TIME= "time";
	public static final String KEY_YEAR= "year";
	public static final String KEY_MONTH= "month";
	public static final String KEY_DAY= "day";
	public static final String KEY_HOUR= "hour";
	public static final String KEY_MINUTE= "minute";
	public static final String KEY_SECOND= "second";
	public static final int MINUTE_MS = 60000;

	/**
	 * 获得当前字符日期yyyyMMdd，长度8，格式为：yyyyMMdd
	 * 
	 * @return
	 */
	public static String getDateString() {
		return toDateString(new java.util.Date(), "yyyyMMdd");
	}
	
	/**
	 * 获得当前字符日期yyyyMMdd，长度10，格式为：yyyy-MM-dd
	 * 
	 * @return
	 */
	public static String getDateString2() {
		return toDateString(new java.util.Date(), "yyyy-MM-dd");
	}

	/**
	 * 获得装换后的字符日期时间，长度14，格式为：yyyyMMddHHmmss
	 * 
	 * @return
	 */
	public static String getDatetimeFromText(Date date) {
		return toDateString(date, "yyyyMMddHHmmss");
	}
	
	/**
	 * 获得装换后的字符日期时间，格式为：yyyy-MM-dd HH:mm:ss
	 * 
	 * @return
	 */
	public static String getDatetimeString(Date date) {
		return toDateString(date, "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 获得当前字符日期时间，长度14，格式为：yyyyMMddHHmmss
	 * 
	 * @return
	 */
	public static String getDatetimeString() {
		return toDateString(new java.util.Date(), "yyyyMMddHHmmss");
	}

	/**
	 * 获得当前字符日期时间毫秒，长度6，格式为：HHmmss
	 * 
	 * @return
	 */
	public static String getTimeString() {
		return toDateString(new java.util.Date(), "HHmmss");
	}
	
	/**
	 * 获得当前字符日期时间毫秒，长度8，格式为：HH:mm:ss
	 * 
	 * @return
	 */
	public static String getTimeString2() {
		return toDateString(new java.util.Date(), "HH:mm:ss");
	}

	/**
	 * 获得当前字符时间，长度8，格式为：yyyy-MM-dd HH:mm:ss.SSS
	 * 
	 * @return
	 */
	public static String getTimestampString() {
		return toDateString(new java.util.Date(), "yyyy-MM-dd HH:mm:ss.SSS");
	}

	/**
	 * 获得当前字符日期时间加天数的日期毫秒，长度8，格式为：yyyyMMddHHmmss
	 * 
	 * @param days
	 * @return
	 */
	public static String getEndDatetimeString(int days) {
		return toDateString(new java.util.Date(), "yyyyMMdd", days);
	}

	protected static String toDateString(java.util.Date date, String format) {
		if (date == null) {
			return "";
		}
		SimpleDateFormat dateFormat = format != null ? new SimpleDateFormat(
				format) : new SimpleDateFormat();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return dateFormat.format(date);
	}

	protected static String toDateString(java.util.Date date, String format,
			int days) {
		if (date == null) {
			return "";
		}
		SimpleDateFormat dateFormat = format != null ? new SimpleDateFormat(
				format) : new SimpleDateFormat();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, days);
		return dateFormat.format(calendar.getTime());
	}

	/**
	 * 取当前日期时间<br>
	 * Map的key值包含：datetime,date,time,year,month,day,hour,minute,second
	 * 
	 * add by zhangzy
	 * 
	 * @return 日期时间相关点
	 */
	public static Map<String, String> getCurrentDateTime() {
		Date d = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		String datetime = format.format(d);
		Map<String, String> map = new HashMap<String, String>();
		map.put(KEY_DATETIME, datetime);
		map.put(KEY_DATE, datetime.substring(0, 8));
		map.put(KEY_TIME, datetime.substring(8));
		map.put(KEY_YEAR, datetime.substring(0, 4));
		map.put(KEY_MONTH, datetime.substring(4, 6));
		map.put(KEY_DAY, datetime.substring(6, 8));
		map.put(KEY_HOUR, datetime.substring(8, 10));
		map.put(KEY_MINUTE, datetime.substring(10, 12));
		map.put(KEY_SECOND, datetime.substring(12));
		return map;
	}

	/**
	 * 返回包含一个日期的 Variant (Date)，这一日期还加上了一段时间间隔<br>
	 * 例：<br>
	 * 取当前时间的年份减1的日期<br>
	 * UtilDatetime.dateAdd("y", -1, new Date());<br>
	 * return "20070415"<br>
	 * 
	 * 2008-04-15 add by zhangzy
	 * 
	 * @param interval
	 *            "y"-年,"m"-月,"d"-日
	 * @param number
	 *            要加上的时间间隔的数目。其数值可以为正数（得到未来的日期），也可以为负数（得到过去的日期）。
	 * @param date
	 *            传入的日期
	 * @return
	 */
	public static String dateAdd(String interval, int number, Date date) {
		return dateAdd(interval, number, date, "yyyyMMdd");
	}

	/**
	 * 返回包含一个日期的 Variant (Date)，这一日期还加上了一段时间间隔<br>
	 * 例：<br>
	 * 取当前时间的年份减1的日期<br>
	 * UtilDatetime.dateAdd("y", -1, new Date());<br>
	 * return "20070415"
	 * 
	 * @param interval
	 *            "y"-年,"m"-月,"d"-日
	 * @param number
	 *            要加上的时间间隔的数目。其数值可以为正数（得到未来的日期），也可以为负数（得到过去的日期）。
	 * @param date
	 *            传入的日期
	 * @param format
	 *            日期格式化字符串
	 * @return
	 */
	public static String dateAdd(String interval, int number, Date date,
			String format) {
		// interval, number, date
		if (date == null) {
			return "";
		}
		SimpleDateFormat dateFormat = format != null ? new SimpleDateFormat(
				format) : new SimpleDateFormat();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		if (interval.equals("y")) {
			calendar.add(Calendar.YEAR, number);
		} else if (interval.equals("m")) {
			calendar.add(Calendar.MONTH, number);
		} else if (interval.equals("d")) {
			calendar.add(Calendar.DATE, number);
		}
		return dateFormat.format(calendar.getTime());
	}
	
	public static Date dateAdd(int interval, int number, Date date) {
		// interval, number, date
		if (date == null) {
			return new Date();
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(interval, number);
		return calendar.getTime();
	}

	/**
	 * 将字符转成java.util.Date对象
	 * 
	 * @param str
	 *            支持模式yyyyMMdd, yyMMdd, yyyyMMddHHmmss, yyyy-MM-dd HH:mm:ss.SSS
	 * @return
	 */
	public static java.util.Date toDate(String str) {
		if (str == null || str.trim().length() == 0) {
			return null;
		}
		int len = str.length();
		if (len == 8) {
			return str2Date(str, "yyyyMMdd");
		} else if (len == 6) {
			return str2Date(str, "yyMMdd");
		} else if (len == 14) {
			return str2Date(str, "yyyyMMddHHmmss");
		} else if (len == 23) {
			return str2Date(str, "yyyy-MM-dd HH:mm:ss.SSS");
		} else if (len == 19) {
			return str2Date(str, "yyyy-MM-dd HH:mm:ss");
		} else if (len == 10) {
			return str2Date(str, "yyyy-MM-dd");
		}
		return null;
	}

	/**
	 * 
	 * @param SourceStr
	 * @param FormatString
	 * @return
	 */
	public static java.util.Date str2Date(String SourceStr, String FormatString) {
		try {
			java.text.SimpleDateFormat f = new java.text.SimpleDateFormat(
					FormatString);
			return f.parse(SourceStr);
		} catch (Throwable e) {
			return null;
		}
	}

	/**
	 * 
	 * @param str
	 *            时区的简称或全称。
	 * @return
	 */
	public static java.util.TimeZone toTimeZone(String str) {
		TimeZone result = null;
		try {
			result = (str == null || str.trim().length() == 0) ? null
					: TimeZone.getTimeZone(str);
		} catch (Throwable t) {
		}
		return result;
	}

	/**
	 * 对日期字符串进行格式化输出
	 * 
	 * @param strDate
	 *            要处理的日期字符串 8位为日期，6位为时间
	 * @param format
	 *            格式化类型
	 * @return 格式化后的日期字符串
	 */
	public static String FormatDate(String strDate, String format) {
		String strOutDate = strDate;
		int len;

		len = strDate.length();
		if ((len != 6) && (len != 8))
			strOutDate = strDate;
		else {
			if (len == 6) {
				Date date = str2Date(strDate, "HHmmss");
				strOutDate = toDateString(date, format);
			} else {
				Date date = str2Date(strDate, "yyyyMMdd");
				strOutDate = toDateString(date, format);
			}
		}
		return strOutDate;
	}

	/**
	 * 把格式化时间转换为标准时间
	 * 
	 * @param strTime
	 *            格式化的时间
	 * @param format
	 *            格式化类型
	 * @param kind
	 *            KIND_DATE为日期，KIND_TIME为时间
	 * @return 标准时间
	 */
	public static String DeFormatTime(String strDate, String format, int kind) {
		String strOutDate = strDate;
		if (strDate.length() == format.length()) {
			Date date = str2Date(strDate, format);
			if (kind == KIND_DATE) {
				strOutDate = toDateString(date, "yyyyMMdd");
			} else if (kind == KIND_TIME) {
				strOutDate = toDateString(date, "HHmmss");
			}
		}
		return strOutDate;
	}

	public static final int KIND_DATE = 1;
	public static final int KIND_TIME = 2;

//	public static void main(String[] args) {
//		System.out.println(UtilDatetime.getDateString());
//		System.out.println(UtilDatetime.getDatetimeString());
//		System.out.println(UtilDatetime.getTimestampString());
//		System.out.println(getEndDatetimeString(5));
//		System.out.println(Long2FormatDate("1212422400000","yyyyMMdd"));
//	}
	//将系统LONG转换为格式化的时间
	public static String Long2FormatDate(String StrDate,String format){
		  Long longDate = new Long(StrDate);
		  GregorianCalendar   gc = new GregorianCalendar();   
		  SimpleDateFormat   df   = new SimpleDateFormat(format);   
		  gc.setTimeInMillis(longDate.longValue());   
//		  System.out.println(df.format(gc.getTime()));
		  return df.format(gc.getTime());
	}
	
	
	/**
	 * 日期字符串比较大小<br>
	 * @param compareDateStr1<br>
	 * @param compareDateStr2<br>
	 * @return -1 = compareDateStr1 less than compareDateStr2<br>
	 * 			0 = compareDateStr1 equal compareDateStr2<br>
	 * 			1 = compareDateStr1 biger than compareDateStr2<br>
	 * 			2 = parameter error
	 */
	public static int compareDataString(String compareDateStr1, String compareDateStr2) {
		if (compareDateStr1 == null || compareDateStr1.trim().length() == 0
				|| compareDateStr2 == null || compareDateStr2.trim().length() == 0)
			return 2;
		Date compareDate1 = toDate(compareDateStr1);
		Date compareDate2 = toDate(compareDateStr2);
		
		try{
			return compareDate1.compareTo(compareDate2);
		}catch (Exception e){
			return 2;
		}
	}
	
	/**
	 * 日期long字符串比较大小<br>
	 * @param compareDateLong1<br>
	 * @param compareDateStr2<br>
	 * @return -1 = compareDateLong1 less than compareDateStr2<br>
	 * 			0 = compareDateLong1 equal compareDateStr2<br>
	 * 			1 = compareDateLong1 biger than compareDateStr2<br>
	 * 			2 = parameter error
	 */
	public static int compareData(long compareDateLong1, String compareDateStr2) {
		if (compareDateStr2 == null || compareDateStr2.trim().length() == 0)
			return 2;
		Date compareDate1 = new Date(compareDateLong1);
		Date compareDate2 = toDate(compareDateStr2);
		
		try{
			return compareDate1.compareTo(compareDate2);
		}catch (Exception e){
			return 2;
		}
	}
	
	/**
	 * 将输入的BigDecimal 加上传入毫秒数 转换为系统14为日期格式
	 * @param BigDecimal time 
	 * 		  ms 相加毫秒数
	 * @return 返回日期格式："yyyyMMddHHmmss"
	 */
	public static String getEnableTime(long time,int ms) {
		long c = (new Date()).getTime();
		long d =  time * ms;
		long f = c + d;
		Date finalDate = new Date(f);
		return getDatetimeFromText(finalDate);
	}
	
	/**
	 * 计算两个时间之间相隔天数
	 * @param startDayStr 开始日期
	 * @param endDayStr 结束日期
	 * @return
	 */
	public static int getIntervalDays(String startDayStr, String endDayStr) {
		Date startDay = toDate(startDayStr);
		Date endDay = toDate(endDayStr);
		// 确保startday在endday之前
		if (startDay.after(endDay)) {
			Date cal = startDay;
			startDay = endDay;
			endDay = cal;
		}
		// 分别得到两个时间的毫秒数
		long sl = startDay.getTime();
		long el = endDay.getTime();
		long ei = el - sl;
		// 根据毫秒数计算间隔天数
		return (int) (ei / (1000 * 60 * 60 * 24));
	}
	
	/**
	 * 将时间转换为系统Long
	 * @param strDate
	 * @return
	 */
	public static long str2Long(String strDate) {
		Date date = str2Date(strDate, "yyyy-MM-dd HH:mm:ss");
		return date.getTime();
	}
	
	/**
	 * 将时间转换为int秒级
	 * @param date 日期
	 * @return int 秒级
	 */
	public static int getTimeSecond(Date date){
		long timel = date.getTime();
		int time = (int) (timel/1000);
		return time;
	}
	
	/**
	 * 将时间转换为int秒级
	 * @return
	 */
	public static int getTimeSecond(){
		return (int) (System.currentTimeMillis()/1000);
	}
	
	/**
	 * 获取下一小时的时间
	 * @return
	 */
	public static int getNextHourSecond(){
		long timel = new Date().getTime();
		int time = (int) (timel/1000);
		return time+3600-time%3600;
	}
	/**
	 * 获取前一个半小时的时间
	 * @return
	 */
	public static int getPreHalfHourSecond(){
		long timel = new Date().getTime();
		int time = (int) (timel/1000);
		return time-time%1800;
	}
	
	/**
	 * 将秒级转换为时间Date,适用mySQL
	 * @param second
	 * @return
	 */
	public static Date second2Date(int second){
		if(second != 0){
			long mi = ((long)second)*1000;
			return new Date(mi);
		}
		return null;
	}
	
	/**
	 * 获取今日零点的秒数
	 * @return
	 */
	public static int getTodaySecond(){
		Calendar c = Calendar.getInstance();
		return (int)(c.getTimeInMillis() * 0.001 
				- c.get(Calendar.HOUR_OF_DAY) * 3600 
				- c.get(Calendar.MINUTE) * 60 
				- c.get(Calendar.SECOND));
	}
	
	/**
	 * 获取日期零点的秒数
	 * @return
	 */
	public static int getDateZeroSecond(Date date){
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return (int)(c.getTimeInMillis() * 0.001 
				- c.get(Calendar.HOUR_OF_DAY) * 3600 
				- c.get(Calendar.MINUTE) * 60 
				- c.get(Calendar.SECOND));
	}
	
	/**
	 * 获取时间戳对应零点的秒数
	 * @return
	 */
	public static int getDateZeroSecond(long timeInMillis){
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(timeInMillis);
		return (int)(c.getTimeInMillis() * 0.001 
				- c.get(Calendar.HOUR_OF_DAY) * 3600 
				- c.get(Calendar.MINUTE) * 60 
				- c.get(Calendar.SECOND));
	}
	
	/**
	 * 获取微秒数
	 * @return
	 */
	public static String getUstime(){
		String curr = System.currentTimeMillis()+RandomUtils.generateNumString(4);
		String result = curr.substring(0, 10)+"."+curr.substring(10);
		return result;
	}
	
	/**
	 * 判断是否同一天
	 * @param dateA
	 * @param dateB
	 * @return
	 */
	public static boolean areSameDay(Date dateA,Date dateB) {
	    Calendar calDateA = Calendar.getInstance();
	    calDateA.setTime(dateA);

	    Calendar calDateB = Calendar.getInstance();
	    calDateB.setTime(dateB);

	    return calDateA.get(Calendar.YEAR) == calDateB.get(Calendar.YEAR)
	            && calDateA.get(Calendar.MONTH) == calDateB.get(Calendar.MONTH)
	            &&  calDateA.get(Calendar.DAY_OF_MONTH) == calDateB.get(Calendar.DAY_OF_MONTH);
	}
	
	/**
	 * 判断是否同一天
	 * @param timeA
	 * @param timeB
	 * @return
	 */
	public static boolean areSameDay(int timeA,int timeB) {
		Calendar calDateA = Calendar.getInstance();
		calDateA.setTimeInMillis(timeA*1000l);
		
		Calendar calDateB = Calendar.getInstance();
		calDateB.setTimeInMillis(timeB*1000l);
		
		return calDateA.get(Calendar.YEAR) == calDateB.get(Calendar.YEAR)
				&& calDateA.get(Calendar.MONTH) == calDateB.get(Calendar.MONTH)
				&&  calDateA.get(Calendar.DAY_OF_MONTH) == calDateB.get(Calendar.DAY_OF_MONTH);
	}
	
	public static void main(String[] args) {
		System.out.println(getDateZeroSecond(1385606782000l));
		System.out.println(getNextHourSecond());
	}
	
	
}

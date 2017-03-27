package com.nbcedu.bas.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
/**
 * 
 * <p>时间操作</p>
 * @author 黎青春
 * Create at:2012-4-18 下午03:36:39
 */
public class DateUtils {

	public static final String YMD = "yyyyMMdd";
	public static final String YMD_SLASH = "yyyy/MM/dd";
	public static final String YMD_DASH = "yyyy-MM-dd";
	public static final String YMD_DASH_WITH_TIME = "yyyy-MM-dd H:m";
	public static final String YDM_SLASH = "yyyy/dd/MM";
	public static final String YDM_DASH = "yyyy-dd-MM";
	public static final String HM = "HHmm";
	public static final String HM_COLON = "HH:mm";
	public static final long DAY = 24 * 60 * 60 * 1000L;

	private static final Map<String, DateFormat> DFS = new HashMap<String, DateFormat>();

	private DateUtils() {
	}

	public static DateFormat getFormat(String pattern) {
		DateFormat format = DFS.get(pattern);
		if (format == null) {
			format = new SimpleDateFormat(pattern);
			DFS.put(pattern, format);
		}
		return format;
	}
	/**
	 * 时间格式化(字符串 转成时间 )
	 * @author 黎青春
	 * @param source
	 * @param pattern
	 * @return
	 */
	public static Date parse(String source, String pattern) {
		if (source == null) {
			return null;
		}
		Date date;
		try {
			date = getFormat(pattern).parse(source);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
		return date;
	}
	/**
	 * 时间格式化(时间 转成 字符串)
	 * @author 黎青春
	 * @param date	时间
	 * @param pattern	格式
	 * @return
	 */
	public static String format(Date date, String pattern) {
		if (date == null) {
			return null;
		}
		return getFormat(pattern).format(date);
	}

	/**
	 * 判断时间是否有效
	 * @author 黎青春
	 * @param year 年
	 * @param month 月(1-12)
	 * @param day 日(1-31)
	 * @return 输入的年、月、日是否是有效日期
	 */
	public static boolean isValid(int year, int month, int day) {
		if (month > 0 && month < 13 && day > 0 && day < 32) {
			// month of calendar is 0-based
			int mon = month - 1;
			Calendar calendar = new GregorianCalendar(year, mon, day);
			if (calendar.get(Calendar.YEAR) == year && calendar.get(Calendar.MONTH) == mon
					&& calendar.get(Calendar.DAY_OF_MONTH) == day) {
				return true;
			}
		}
		return false;
	}
	/**
	 * 时间转化日历
	 * @author 黎青春
	 * @param date
	 * @return
	 */
	private static Calendar convert(Date date) {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		return calendar;
	}

	/**
	 * 返回指定年数位移后的日期
	 * @author 黎青春
	 * @param date
	 * @param offset
	 * @return
	 */
	public static Date yearOffset(Date date, int offset) {
		return offsetDate(date, Calendar.YEAR, offset);
	}

	/**
	 * 指定月数位移后的日期
	 * @author 黎青春
	 * @param date
	 * @param offset
	 * @return
	 */
	public static Date monthOffset(Date date, int offset) {
		return offsetDate(date, Calendar.MONTH, offset);
	}

	/**
	 * 指定天数位移后的日期
	 * @author 黎青春
	 * @param date
	 * @param offset
	 * @return
	 */
	public static Date dayOffset(Date date, int offset) {
		return offsetDate(date, Calendar.DATE, offset);
	}

	/**
	 * 指定日期相应位移后的日期
	 * @author 黎青春
	 * @param date 参考日期
	 * @param field 位移单位，见 {@link Calendar}
	 * @param offset 位移数量，正数表示之后的时间，负数表示之前的时间
	 * @return 位移后的日期
	 */
	public static Date offsetDate(Date date, int field, int offset) {
		Calendar calendar = convert(date);
		calendar.add(field, offset);
		return calendar.getTime();
	}

	/**
	 * 当月第一天的日期
	 * @author 黎青春
	 * @param date
	 * @return
	 */
	public static Date firstDay(Date date) {
		Calendar calendar = convert(date);
		calendar.set(Calendar.DATE, 1);
		return calendar.getTime();
	}

	/**
	 * 当月最后一天的日期
	 * @author 黎青春
	 * @param date
	 * @return
	 */
	public static Date lastDay(Date date) {
		Calendar calendar = convert(date);
		calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));
		return calendar.getTime();
	}

	/**
	 * 两个日期间的差异天数
	 * @author 黎青春
	 * @param date1 参照日期
	 * @param date2 比较日期
	 * @return 参照日期与比较日期之间的天数差异，正数表示参照日期在比较日期之后，0表示两个日期同天，负数表示参照日期在比较日期之前
	 */
	public static int dayDiff(Date date1, Date date2) {
		long diff = date1.getTime() - date2.getTime();
		return (int) (diff / DAY);
	}

	public static long getDate(Date date,int minute){
		Calendar calendar = Calendar.getInstance();
//		System.out.println(calendar.get(Calendar.YEAR)+"-"+calendar.get(Calendar.MONTH)+"-"+calendar.get(Calendar.DATE)+"-"+calendar.get(Calendar.HOUR)+"-"+calendar.get(Calendar.MINUTE));
		calendar.add(Calendar.MINUTE, minute);
//		System.out.println(calendar.get(Calendar.YEAR)+"-"+calendar.get(Calendar.MONTH)+"-"+calendar.get(Calendar.DATE)+"-"+calendar.get(Calendar.HOUR)+"-"+calendar.get(Calendar.MINUTE));
		return (calendar.getTime()).getTime();
	}
	
	/**
	 * 本月 第一天
	 * @author 黎青春
	 * @return
	 */
	public static String getMonthFirstDay() {       
	    Calendar calendar = Calendar.getInstance();       
	    calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));       
	    return format(calendar.getTime(),YMD_DASH);       
	}       
	      
	/**
	 *  本月 最后一天      
	 * @author 黎青春
	 * @return
	 */
	public static String getMonthLastDay() {       
	    Calendar calendar = Calendar.getInstance();       
	    calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));       
	    return format(calendar.getTime(),YMD_DASH); 
	}  
	
	/**
	 * 前一天
	 * 方法名称:getSpecifiedDayBefore
	 * 作者:lqc
	 * 创建日期:2016年4月7日
	 * 方法描述:  
	 * @param date
	 * @return Date
	 */
	public static Date getSpecifiedDayBefore(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		date = calendar.getTime();
		return date;
	}
	
	/**
	 * 后一天
	 * 方法名称:getSpecifiedDayAfter
	 * 作者:lqc
	 * 创建日期:2016年4月7日
	 * 方法描述:  
	 * @param date
	 * @return Date
	 */
	public static Date getSpecifiedDayAfter(Date date){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, +1);
		date = calendar.getTime();
		return date;
	}
	
}

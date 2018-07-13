package com.hy.commons.date;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * <p>
 * Title: 日期操作类

 * </p>
 * <p>
 * Description: 完成一些对日期的计算工作

 * </p>
 */
public class DateUtil {

	/**
	 * 显示日期的格式,yyyy-MM-dd
	 */
	public static final String DATE_FORMAT = "yyyy-MM-dd";

	/**
	 * 显示日期的格式,yyyy-MM-dd HH:mm:ss
	 */
	public static final String TIMEF_FORMAT = "yyyy-MM-dd HH:mm:ss";

	/**
	 * 显示日期的格式,yyyy年MM月dd日HH时mm分ss秒

	 */
	public static final String ZHCN_TIME_FORMAT = "yyyy年MM月dd日HH时mm分ss秒";

	/**
	 * 显示日期的格式,yyyyMMddHHmmss
	 */
	public static final String TIME_STR_FORMAT = "yyyyMMddHHmmss";
	/**
	 * 显示日期的格式,yyyyMMddHHmmssSSS
	 */
	public static final String TIMESSS_STR_FORMAT = "yyyyMMddHHmmssSSS";
	/**
	 * 显示日期的格式,yyyyMMdd
	 */
	public static final String DATE_STR_FORMAT = "yyyyMMdd";

	/**
	 * DateFormat,格式:yyyy-MM-dd
	 */
	private static DateFormat dateFormat;

	/**
	 * DateFormat,格式:yyyy-MM-dd HH:mm:ss
	 */
	private static DateFormat dateTimeFormat;

	/**
	 * DateFormat,格式:yyyyMMddHHmmss
	 */
	private static DateFormat dateTimeStrFormat;

	/**
	 * DateFormat,格式:yyyy年MM月dd日HH时mm分ss秒

	 */
	private static DateFormat zhcnDateTimeStrFormat;

	static {
		dateFormat = new SimpleDateFormat(DATE_FORMAT);
		dateTimeFormat = new SimpleDateFormat(TIMEF_FORMAT);
		dateTimeStrFormat = new SimpleDateFormat(TIME_STR_FORMAT);
		zhcnDateTimeStrFormat = new SimpleDateFormat(ZHCN_TIME_FORMAT);
	}

	/**
	 * 获取当前时间在＋－n分钟后的字符串时间

	 * 
	 * @param n
	 *            int
	 * @return String
	 */
	public static String getTimebyMinAfter(int n) {
		Calendar now = Calendar.getInstance(TimeZone.getDefault());
		now.add(Calendar.MINUTE, n);
		return (dateTimeFormat.format(now.getTime()));
	}

	/**
	 * 获取当前时间在＋－n秒后的字符串时间
	 * 
	 * @param n
	 *            int
	 * @return String
	 */
	public static String getTimebySecAfter(int n) {
		Calendar now = Calendar.getInstance(TimeZone.getDefault());
		now.add(Calendar.SECOND, n);
		return (dateTimeFormat.format(now.getTime()));
	}
	
	/**
	 * 获取当前时间在＋－n分钟后的时间(java.util.Date)

	 * 
	 * @param n
	 *            int
	 * @return String
	 */
	public static Date getTimebyMinAfterDate(int n) {
		Calendar now = Calendar.getInstance(TimeZone.getDefault());
		now.add(Calendar.MINUTE, n);
		return now.getTime();
	}

	/**
	 * 获取定义的DateFormat格式
	 * 
	 * @param formatStr
	 * @return
	 */
	private static DateFormat getDateFormat(String formatStr) {
		if (formatStr.equalsIgnoreCase(DATE_FORMAT)) {
			return dateFormat;
		} else if (formatStr.equalsIgnoreCase(TIMEF_FORMAT)) {
			return dateTimeFormat;
		} else {
			return new SimpleDateFormat(formatStr);
		}
	}

	/**
	 * 将Date转换成字符串“yyyy-mm-dd hh:mm:ss”的字符串

	 * 
	 * @param date
	 *            日期
	 * @return String 字符串

	 */
	public static String dateToDateString(Date date) {
		return dateToDateString(date, TIMEF_FORMAT);
	}

	/**
	 * 将Date转换成formatStr格式的字符串
	 * 
	 * @param date
	 * @param formatStr
	 * @return
	 */
	public static String dateToDateString(Date date, String formatStr) {
		DateFormat df = getDateFormat(formatStr);
		return df.format(date);
	}

	/**
	 * 将小时数换算成返回以毫秒为单位的时间
	 * 
	 * @param hours
	 * @return
	 */
	public static long getMicroSec(BigDecimal hours) {
		BigDecimal bd;
		bd = hours.multiply(new BigDecimal(3600 * 1000));
		return bd.longValue();
	}

	/**
	 * 获取今天的日期，格式自定
	 * 
	 * @param pattern -
	 *            设定显示格式
	 * @return String - 返回今天的日期

	 */
	public static String getToday(String pattern) {
		Calendar now = Calendar.getInstance(TimeZone.getDefault());
		DateFormat sdf = getDateFormat(pattern);
		sdf.setTimeZone(TimeZone.getDefault());
		return (sdf.format(now.getTime()));
	}

	// 得到系统当前的分钟数,如当前时间是上午12:00,系统当前的分钟数就是12*60
	public static int getCurrentMinutes() {
		Calendar now = Calendar.getInstance(TimeZone.getDefault());
		int iMin = now.get(Calendar.HOUR_OF_DAY) * 60
				+ now.get(Calendar.MINUTE);
		return iMin;
	}

	/**
	 * 获取当前日期时间yyyy年MM月dd日HH时mm分ss秒的形式
	 * 
	 * @return 当前日期时间yyyy年MM月dd日HH时mm分ss秒的形式
	 */
	public static String getCurZhCNDateTime() {
		return dateToDateString(new Date(), ZHCN_TIME_FORMAT);
	}

	/**
	 * @return 得到本月月份 如09
	 */
	public static String getMonth() {
		Calendar now = Calendar.getInstance();
		int month = now.get(Calendar.MONTH) + 1;
		String monStr = String.valueOf(month);

		// 对于10月份以下的月份,加"0"在前面

		if (month < 10)
			monStr = "0" + monStr;
		return monStr;
	}

	/**
	 * 根据失效时间判断是否依然有效
	 * 
	 * @param expireTime
	 *            失效时间的字符串形式,格式要求:yyMMddHHmmss
	 * @return true:失效 false:没有失效
	 * @throws ParseException
	 */
	public static boolean isTimeExpired(String expireTime)
			throws ParseException {
		boolean ret = false;

		// SimpleDateFormat不是线程安全的,所以每次调用new一个新的对象

		Date date = new SimpleDateFormat(TIME_STR_FORMAT).parse(expireTime);
		Calendar expire = Calendar.getInstance();
		expire.setTime(date);
		Calendar now = Calendar.getInstance();
		// 将毫秒字段设为0,保持精度一致性

		now.set(Calendar.MILLISECOND, 0);
		if (now.after(expire)) {
			ret = true;
		}
		return ret;
	}

	/**
	 * 根据开始时间和可用时间计算出失效时间

	 * 
	 * @param startTime
	 *            开始时间

	 * @param availableTime
	 *            有效时长（单位：秒）
	 * @return 失效时间
	 * @throws ParseException
	 */
	public static String getExpireTimeByCalculate(Date startTime,
			int availableTime) {

		Calendar expire = Calendar.getInstance();

		// 设置为开始时间

		expire.setTime(startTime);

		// 开始时间向后有效时长秒得到失效时间
		expire.add(Calendar.SECOND, availableTime);

		// 返回失效时间字符串

		// SimpleDateFormat不是线程安全的,所以每次调用new一个新的对象

		return new SimpleDateFormat(TIME_STR_FORMAT).format(expire.getTime());

	}

	/**
	 * 将Date转换为yyMMddHHmmss的形式
	 * @param date 日期
	 * @return 日期的字符串形式,格式：yyMMddHHmmss
	 */
	public static String convertDateToString(Date date) {
		return new SimpleDateFormat(TIME_STR_FORMAT).format(date);
	}
	 /**
     * 将Date转换为字符串
     * @param format 自定义格式
     * @param date 日期
     * @return 日期的字符串形式,格式：yyMMddHHmmss
     */
    public static String convertDateToString(Date date, String format) {
        return new SimpleDateFormat(format).format(date);
    }
	/**
	 * 将yyMMddHHmmss形式的字符串转换成Date的形式
	 * @return Date对象
	 * @throws ParseException
	 */
	public static Date convertStringToDate(String date) throws ParseException {
		return new SimpleDateFormat(TIME_STR_FORMAT).parse(date);
	}
	/**
	 * 字符串转化为日期
	 * @param date 日期字符串
	 * @param formatString 格式化字符串
	 * @return
	 * @throws ParseException
	 */
	public static Date convertStringToDate(String date,String formatString) throws ParseException {
		return new SimpleDateFormat(formatString).parse(date);
	}
	/**
	 * 将yy-MM-dd形式的字符串转换成Date的形式
	 * @return Date对象
	 * @throws ParseException
	 */
	public static Date convertSimpleStringToDate(String date) throws ParseException {
		return new SimpleDateFormat(DATE_FORMAT).parse(date);
	}

	/**
	 * @param date
	 *            yyyyMMddHHmmss格式的日期字符转换为yyyy年MM月dd日HH时mm分ss秒格式的字符串

	 * @return yyyy年MM月dd日HH时mm分ss秒格式的日期字符串

	 * @throws ParseException
	 */
	public static String convertStringToZhCN(String date) throws ParseException {
		return zhcnDateTimeStrFormat.format(dateTimeStrFormat.parse(date));
	}

	/**
	 * 时间字符串转换成日期时间的形式
	 * 
	 * @param date
	 *            yy-MM-dd HH:mm:ss形式的日期字符串
	 * @return Date对象
	 * @throws ParseException
	 */
	public static Date convertSimpleStringToDateTime(String date) throws ParseException {
		return new SimpleDateFormat(TIMEF_FORMAT).parse(date);
	}

	/**
	 * 获取昨日日期 返回格式：yyyy-MM-dd
	 */
	public static String getYesterdayDate() {
		// 获取昨日的日期

		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, -1);
		Date yesterday = cal.getTime();
		return new SimpleDateFormat(DATE_FORMAT).format(yesterday);
	}
	
	/**
	 * 获取明天日期 返回格式：yyyy-MM-dd
	 */
	public static String getTomorrowDateStr() {
		// 获取昨日的日期

		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, 1);
		Date tomorrow = cal.getTime();
		return new SimpleDateFormat(DATE_FORMAT).format(tomorrow);
	}
	
	/**
	 * 获取明天日期 返回格式：yyyy-MM-dd
	 */
	public static Date getTomorrowDate() {
		// 获取昨日的日期

		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, 1);
		Date tomorrow = cal.getTime();
		return tomorrow;
	}
	
	/**
	 * 根据Calendar对象、字符串日期类型获得字符串日期

	 * @param date Calendar对象
	 * @param strDatetype 字符串日期类型（1：XXXX年XX月XX日，2：XX月XX日，3，XXXX年，4：XXXX-XX-XX，5：XX-XX，6：XXXX）

	 * @return
	 */
	public static String getStrDate(Calendar calendar,int strDateType){
		String year = String.valueOf(calendar.get(Calendar.YEAR));
		String month = (calendar.get(Calendar.MONTH) + 1) < 10 ? "0" + (calendar.get(Calendar.MONTH) + 1) : String.valueOf

((calendar.get(Calendar.MONTH) + 1));
		String day = calendar.get(Calendar.DAY_OF_MONTH) < 10 ? "0" + calendar.get(Calendar.DAY_OF_MONTH) : String.valueOf

(calendar.get(Calendar.DAY_OF_MONTH));
		String strDate = "";
		
		switch (strDateType) {
		case 1:
			strDate = year + "年" + month + "月" + day + "日";
			break;
		case 2:
			strDate = month + "月" + day + "日";
			break;
		case 3:
			strDate = year + "年";
			break;
		case 4:
			strDate = year + "-" + month + "-" + day;
			break;
		case 5:
			strDate = month + "-" + day;
			break;
		case 6:
			strDate = year;
			break;
		default:
			strDate = year + "-" + month + "-" + day;
			break;
		}
		
		return strDate;
	}
	
	/**
	 * 根据原来的时间（Date）获得相对偏移 N 月的时间（Date）

	 * @param protoDate 原来的时间（java.util.Date）

	 * @param dateOffset（向前移正数，向后移负数）

	 * @return 时间（java.util.Date）

	 */
	public static Date getOffsetMonthDate(Date protoDate,int monthOffset){
		Calendar cal = Calendar.getInstance();
		cal.setTime(protoDate);
		cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) - monthOffset);
		return cal.getTime();
	}
	
	/**
	 * 根据原来的时间（Date）获得相对偏移 N 天的时间（Date）

	 * @param protoDate 原来的时间（java.util.Date）

	 * @param dateOffset（向前移正数，向后移负数）

	 * @return 时间（java.util.Date）

	 */
	public static Date getOffsetDayDate(Date protoDate,int dateOffset){
		Calendar cal = Calendar.getInstance();
		cal.setTime(protoDate);
		cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) - dateOffset);
		return cal.getTime();
	}
	
	/**
	 * 根据原来的时间（Date）获得相对偏移 N 小时的时间（Date）

	 * @param protoDate 原来的时间（java.util.Date）

	 * @param offsetHour （向前移正数，向后移负数）

	 * @return 时间（java.util.Date）

	 */
	public static Date getOffsetHourDate(Date protoDate,int offsetHour){
		Calendar cal = Calendar.getInstance();
		cal.setTime(protoDate);
		cal.set(Calendar.HOUR_OF_DAY, cal.get(Calendar.HOUR_OF_DAY) - offsetHour);
		return cal.getTime();
	}
	
	/**
	 * 获得两个日前之间相差的天数

	 * @param startDate 开始日期

	 * @param endDate 结束日期
	 * @return 天数
	 */
	public static int getDayCountBetweenDate(Date startDate, Date endDate){
		Calendar startCalendar = Calendar.getInstance();
		startCalendar.setTime(startDate);	
		Calendar endCalendar = Calendar.getInstance();
		endCalendar.setTime(endDate);
	
		int i = 0;
		
		while(endCalendar.compareTo(startCalendar) >= 0){
			startCalendar.set(Calendar.DAY_OF_MONTH, startCalendar.get(Calendar.DAY_OF_MONTH) + 1);
			i++;
		}
		
		return i;
	}
	/**
	 * 根据原来的时间（Date）获得相对偏移 N 天的时间（Date）


	 * @param protoDate 原来的时间（java.util.Date）


	 * @param dateOffset（向前移正数，向后移负数）

	 * @param type 指定不同的格式（1：年月日，2：年月日时，3：年月日时分）

	 * @return 时间（java.util.Date），没有时分秒


	 */
	public static Date getOffsetSimpleDate(Date protoDate,int dateOffset,int type){
		Calendar cal = Calendar.getInstance();
		cal.setTime(protoDate);
		cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) - dateOffset);
		if (type == 1) {
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
		}
		if (type == 2) {
			cal.set(Calendar.MINUTE, 0);
		}
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}
	
	/**
	 * 时间转为化为字符串

	 * 格式为：yyyyMMddHHmmssSSS
	 * @return
	 */
	public static String getDateToString(){
		SimpleDateFormat dateFormat = new SimpleDateFormat(TIMESSS_STR_FORMAT);
		Date date = new Date();
		String str = dateFormat.format(date);
		return str;
	}

	/**
	 * 增加一天
	 * @param s
	 * @param n
	 * @return
	 */
	public static String addDay(String s, int n) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

			Calendar cd = Calendar.getInstance();
			cd.setTime(sdf.parse(s));
			cd.add(Calendar.DATE, n);// 增加一天
			// cd.add(Calendar.MONTH, n);//增加一个月
			return sdf.format(cd.getTime());
		} catch (Exception e) {
			return null;
		}
	}
	/**
	 * 增加月数
	 * @param s
	 * @param n
	 * @return
	 */
	public static String addMonth(String s, int n) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Calendar cd = Calendar.getInstance();
			cd.setTime(sdf.parse(s));
			cd.add(Calendar.MONTH, n);//增加一个月
			return sdf.format(cd.getTime());
		} catch (Exception e) {
			return null;
		}
	}
	/**
	 * 时间比较大于返回1小于返回-1
	 * @param date
	 * @return
	 */
	public static int dateCompare(Date date1,Date date2){
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        try {
            cal1.setTime(date1);
            cal2.setTime(date2);

        } catch (Exception e) {
            e.printStackTrace();
        }
        cal1.roll(Calendar.DAY_OF_MONTH, 1);
        return cal1.getTime().compareTo(cal2.getTime());
    }
}

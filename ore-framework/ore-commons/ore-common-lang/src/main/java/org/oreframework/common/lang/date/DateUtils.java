package org.oreframework.common.lang.date;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/** 
 * @author  huangzz
 * @version  [1.0.0, 2015-9-22]
 */
public class DateUtils
{
    
    private static DateFormat dateTimeStrFormat;
    private static DateFormat zhcnDateTimeStrFormat;
    private static DateFormat dateFormat;
    private static DateFormat dateTimeFormat;
    
    static {
        dateFormat = new SimpleDateFormat(DateFormatConstants.DATE_FORMAT);
        dateTimeFormat = new SimpleDateFormat(DateFormatConstants.TIMEF_FORMAT);
        dateTimeStrFormat = new SimpleDateFormat(DateFormatConstants.TIME_STR_FORMAT);
        zhcnDateTimeStrFormat = new SimpleDateFormat(DateFormatConstants.ZHCN_TIME_FORMAT);
    }
    
    /**
     * 获取需要执行的统计的日期数组
     * 
     * @return 格式['2011-01-01',2011-01-02']
     */
    public static String[] getExecDay(Date lastDate) {
        String[] day = null;
        // 获取昨天日期
        Date yesdate = null;
        try {
            yesdate = DateUtils.convertDateToDate(DateCalculation.getYesterdayDate(), DateFormatConstants.DATE_FORMAT);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // 获取上次最后执行日期与昨天相隔天数
        int dayCount = DateCalculation.getDayCountBetweenDate(lastDate, yesdate);
        if (dayCount <= 0) {
            return day;
        }
        if (dayCount == 1) {
            return new String[] { DateCalculation.getYesterdayDateStr() };
        } else {
            day = new String[dayCount];
            for (int i = 0; i < dayCount; i++) {
                String date = DateCalculation.addDay(lastDate, i);
                day[i] = date;
            }
        }
        return day;
    }

    /**
     * 获取需要执行的统计的年-月数组
     * 
     * @return 格式['2011-01',2011-01']
     */
    public static String[] getExecYearMonth(Date lastYearMonth) {
        String[] yearMonth = null;
        // 获取上个月日期
        Date lastMonth = DateCalculation.getOffsetMonthDate(new Date(), 1);
        try {
            lastMonth = DateUtils.convertDateToDate(lastMonth, DateFormatConstants.DATE_YEAE_MONTH);
            //          System.out.println(lastMonth);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        // 获取上次最后执行日期与昨天相隔天数
        int monthCount = DateCalculation.getMonthCountBetweenDate(lastYearMonth, lastMonth);
        if (monthCount <= 0) {
            return yearMonth;
        }
        if (monthCount == 1) {
            return yearMonth = new String[] { DateUtils.convertDateToString(lastMonth, DateFormatConstants.DATE_YEAE_MONTH) };
        } else {
            yearMonth = new String[monthCount];
            for (int i = 0; i < monthCount; i++) {
                String date = DateCalculation.addMonth(lastYearMonth, i, DateFormatConstants.DATE_YEAE_MONTH);
                yearMonth[i] = date;
            }
        }
        return yearMonth;
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
     * 将Date转换成yyyy-MM-dd的字符串
     * 
     * @param date
     * @param formatStr
     * @return
     */
    public static String getDateString(Date date) {
        DateFormat df = getDateFormat(DateFormatConstants.DATE_FORMAT);
        return df.format(date);
    }

    
    /**
     * 获取定义的DateFormat格式
     * 
     * @param formatStr
     * @return
     */
    private static DateFormat getDateFormat(String formatStr) {
        if (formatStr.equalsIgnoreCase(DateFormatConstants.DATE_FORMAT)) {
            return dateFormat;
        } else if (formatStr.equalsIgnoreCase(DateFormatConstants.TIMEF_FORMAT)) {
            return dateTimeFormat;
        } else {
            return new SimpleDateFormat(formatStr);
        }
    }
    
    /**
     * 将Date转换为yyMMddHHmmss的形式

     * 
     * @param date
     * @return 日期的字符串形式,格式：yyMMddHHmmss
     */
    public static String convertDateToString(Date date, String format) {
        return new SimpleDateFormat(format).format(date);
    }
    
    /**
     * 将Date转换为yyyyMMddHHmmss的形式

     * 
     * @param date
     * @return 日期的字符串形式,格式：yyyyMMddHHmmss
     */
    public static String convertDateToString(Date date) {
        return new SimpleDateFormat(DateFormatConstants.TIME_STR_FORMAT).format(date);
    }

    
    /**
     * String与Timestamp互转
     */
    public static Timestamp convertDateToTimestamp(Date date) {
        return Timestamp.valueOf(DateUtils.convertDateToString(date, "yyyy-MM-dd HH:mm:ss"));

    }

    /**
     * Date（ java.util.Date ）和Timestamp互转
     */
    public static Date convertTimestampToDate(Timestamp ts) {
        Date date = new Date();
        date = ts;
        return date;

    }
    
    /**
     * 将yyMMddHHmmss形式的字符串转换成Date的形式

     * 
     * @param date
     *            yyMMddHHmmss形式的日期字符串
     * @return Date对象
     * @throws ParseException
     */
    public static Date convertStringToDate(String date) throws ParseException {
        return new SimpleDateFormat(DateFormatConstants.TIME_STR_FORMAT).parse(date);
    }

    /**
     * 字符串转化为日期
     * @param date 日期字符串
     * @param formatString 格式化字符串
     * @return
     * @throws ParseException
     */
    public static Date convertStringToDate(String date, String formatString) throws ParseException {
        return new SimpleDateFormat(formatString).parse(date);
    }

    /**
     * 日期转化为格式化日期
     * @param date 日期
     * @param formatString 格式化字符串
     * @return
     * @throws ParseException
     */
    public static Date convertDateToDate(Date date, String formatString) throws ParseException {
        return new SimpleDateFormat(formatString).parse(convertDateToString(date, formatString));
    }

    /**
     * 字符串转化为格式化字符串格式
     * @param date 日期
     * @param formatString 格式化字符串
     * @return
     * @throws ParseException
     */
    public static String convertStringToString(String date, String formatString) throws ParseException {
        return new SimpleDateFormat(formatString).format(date);
    }

    /**
     * 将yy-MM-dd形式的字符串转换成Date的形式


     * 
     * @param date
     *            yy-MM-dd形式的日期字符串
     * @return Date对象
     * @throws ParseException
     */
    public static Date convertSimpleStringToDate(String date) throws ParseException {
        return new SimpleDateFormat(DateFormatConstants.DATE_FORMAT).parse(date);
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
        return new SimpleDateFormat(DateFormatConstants.TIMEF_FORMAT).parse(date);
    }

    
    /* return String type datetime */
	public String getDateForString(String pattern) {
		String date = new SimpleDateFormat(pattern).format(Calendar
				.getInstance().getTime());
		return date;
	}

	public static String getDateString(String dateFormat) {
		String date = new SimpleDateFormat(dateFormat).format(Calendar
				.getInstance().getTime());
		return date;
	}

	public static String getCurrentDate(String pattern) {
		if (pattern == null) {
			throw new IllegalArgumentException("input string parameter is null");
		}
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		Date now = new Date();
		// System.out.println(now);
		return sdf.format(now);
	}

	public static boolean compareTime(String currrentTime, String beforeTime,
			long compareTo) throws ParseException {

		String format = "yyyyMMddHHmmss";
		SimpleDateFormat sf = new SimpleDateFormat(format);
		Date d1 = sf.parse(currrentTime);
		Date d2 = sf.parse(beforeTime);
		long m1 = Math.abs(d1.getTime() - d2.getTime());
		compareTo = compareTo * 1000;

		if (m1 > compareTo) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean compareDate(String currrentTime, String runTime,
			long compareTo) throws ParseException {

		String format = "yyyyMMddHHmmss";
		SimpleDateFormat sf = new SimpleDateFormat(format);
		Date d1 = sf.parse(currrentTime);
		Date d2 = sf.parse(runTime);
		long m1 = d1.getTime() - d2.getTime();
		compareTo = compareTo * 1000;

		if (m1 > compareTo) {
			return true;
		} else {
			return false;
		}
	}

	
    @SuppressWarnings("deprecation")
    public static void main(String[] args) throws ParseException {
        System.out.println("今天:" + DateCalculation.getDateSection(0)[0].toLocaleString() + "至" + DateCalculation.getDateSection(0)[1].toLocaleString());
        System.out.println("近一周:" + DateCalculation.getDateSection(1)[0].toLocaleString() + "至" + DateCalculation.getDateSection(1)[1].toLocaleString());
        System.out.println("近一月:" + DateCalculation.getDateSection(3)[0].toLocaleString() + "至" + DateCalculation.getDateSection(3)[1].toLocaleString());
        System.out.println("本月:" + DateCalculation.getDateSection(2)[0].toLocaleString() + "至" + DateCalculation.getDateSection(2)[1].toLocaleString());
        
       // System.out.println(getDayCountBetweenDate(new Date(), getAssignDate(new Date(), -1, -1, 22)) - 1);
        
        /*System.out.println(
                getDayCountBetweenDate(
                        convertDateToDate(convertStringToDate("2012-10-19 00:00:35", DateFormatConstants.DATE_FORMAT),DateFormatConstants.DATE_FORMAT), 
                        convertDateToDate(convertStringToDate("2012-12-18 11:40:00", DateFormatConstants.DATE_FORMAT),DateFormatConstants.DATE_FORMAT)
                        ) -1 + "天"
                        );*/
        
        System.out.println(convertDateToDate(new Date(), DateFormatConstants.DATE_FORMAT));
    }
}

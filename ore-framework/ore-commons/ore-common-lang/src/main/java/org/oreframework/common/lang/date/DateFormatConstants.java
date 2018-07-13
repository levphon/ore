package org.oreframework.common.lang.date;


/** 
 * @author  huangzz
 * @version  [1.0.0, 2015-9-22]
 */
public class DateFormatConstants {
	
	public static final String TIME24FORMATER = "yyyyMMddHHmmss";// 24进制时间格式
	public static final String DATE_FORMAT = "yyyy-MM-dd";
	public static final String DATE_HOUR_FORMAT = "yyyy-MM-dd HH:mm";
	public static final String DATE_YEAE_MONTH = "yyyy-MM";
	public static final String TIMEF_FORMAT = "yyyy-MM-dd HH:mm:ss";
	public static final String ZHCN_TIME_FORMAT = "yyyy年MM月dd日HH时mm分ss秒";
	public static final String ZHCN_TIME_FORMAT1 = "yyyy年MM月dd日HH时mm分";
	public static final String TIME_STR_FORMAT = "yyyyMMddHHmmss";
	public static final String TIMESSS_STR_FORMAT = "yyyyMMddHHmmssSSS";
	public static final String DATE_STR_FORMAT = "yyyyMMdd";
	public static final String timePattern = "yyMMddHHmm";
	public static final String FORMAT_YYYYMMDDHHMMSS = "yyyyMMddHHmmss";
	public static final String FORMAT_YYYYMMDD = "yyyyMMdd";
	public static final String FORMAT_YYYYMM = "yyyyMM";
	public static final String FORMAT_HHMMSS = "HHmmss";
	public static final String FORMAT_YYYY_MM_DDHHMMSS = "yyyy-MM-dd HH:mm:ss";
	public static final String SEARCHMSG_DATEFORMAT = "MM/dd/yyyy";// 娑堟伅鎼滅储鏃ユ湡鏍煎紡
	
	public static final String DATE_TYPE_YYYY_MM_DD = "yyyy-MM-dd";
	public static final String DATE_TYPE_YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd hh:mm:ss";
	public static final String DATE_TYPE_YYYYMMDDHHMMSS = "yyyyMMddHHmmss";

	static final long HOUR_TIME = 60L * 60 * 1000;
	static final long DAY = 24L * 60 * 60 * 1000;
	public static final int YEAR = 0;
	public static final int MONTH = 1;
	public static final int WEEK = 2;
	public static final int DAYX = 3;
	public static final int HOUR = 4;
	public static final int MINUTE = 5;
	public static final int SECOND = 6;
	public static final int MILLISECOND = 7;
	public static final int MINUTEOFDAY = 8;
	public static final long DAY_UNIT = 24 * 60 * 60 * 1000;
	public static final long HOUR_UNIT = 60 * 60 * 1000;
	public static final long MINUT_UNIT = 60 * 1000;
	public static final long SECOND_UNIT = 1000;
	
	// 日期常量
	static enum DateConstants {
		TODAY(0), NEARLYWEEK(1), MONTH(2), NEARLYMONTH(3);
		public int value;

		DateConstants(int value) {
			this.value = value;
		}
	}

}

package com.hy.commons.date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.DataFormatException;

import org.apache.commons.lang.time.DateUtils;

import com.hy.commons.util.StringUtil;
/**
 * Description:
 * @author  LiChunming
 * @version V1.0 
 * @createDateTime：2010-12-30 下午01:52:10 
 * @Company: MSD. 
 * @Copyright: Copyright (c) 2010
 */
public class DatesqlUtil {
	/** 
	 * 该类主要服务于sql中基于时间的统计查询，在写sql的过程中建议不要使用to_char或者to_date等oracle函数 
	 * 这样不利用索引（除非你对to_char进行了类似索引的操作 
	 * ），比如：在表的logintime字段上建立了索引，但是在sql中使用to_char(logintime,'yyyy-MM-dd') 
	 * 作为检索条件的时候，数据库在logintime上建立的索引就没用了。在数据量很大的时候会影响检索的速度。 
	 *  提供如下方法：  
	 *  1、获取当前时间（按天截取时间） 
	 *  2、根据指定时间提供天、周、旬、月、季度、年的开始时间，结束时间（时间格式采java.util.Date） 
	 *  3、给定字符串类型的startTime和endTime，工具类负责类型的转换（String转换成Date）  
	 *  注意： 
	 *  1、在sql中使用开始时间和最后时间的时候，为了保证统计数据的正确性， 
	 *    sql按给出的例子组织：t.logintime >= startTime and t.loginTime <= entTime  
	 *  2、时间的字符串格式采用 yyyy-MM-dd 
	 *  
	 */  
	  
	    private static SimpleDateFormat sDateFormat = new SimpleDateFormat(  
	            "yyyy-MM-dd");  
	  
	    public static final int FIRSTTEN = 1 ;  
	    public static final int MIDTEN = 2;  
	    public static final int LASTTEN = 3;  
	      
	    public static final int FIRSTQUARTER = 1;  
	    public static final int SECONDQUARTER = 2;  
	    public static final int THIRDQUARTER = 3;  
	    public static final int FORTHQUARTER = 4;  
	      
	    private static Pattern pattern = Pattern  
	            .compile("^[1-9]\\d{3}-[01]?\\d-[0|1|2|3]?\\d$"); // 2010-12-22  
	  
	    /** 
	     * 获取当前系统时间按天截取的时间 
	     * @return 
	     */  
	    public static Date getSystemTranceDay(){  
	        return DateUtils.truncate(new Date(), Calendar.DATE);  
	    }  
	      
	    /** 
	     * 功能：根据指定时间获取当前天的开始和结束时间，以date数组返回 
	     * 逻辑： 
	     * 1、appointDate is null ,set default value sysdate 
	     * 2、get date[] 
	     * @param appointDate 
	     * @return 
	     */  
	    public static Date[] getDateArrByDay(Date appointDate){  
	        Date stime = null;  
	        Date etime = null;  
	        Date[] date = new Date[2];  
	        //未完  
	        if(appointDate == null){  
	            appointDate = new Date();  
	        }  
	        stime = DateUtils.truncate(appointDate,Calendar.DATE);  
	        etime = DateUtils.addSeconds(DateUtils.truncate(DateUtils.addDays(appointDate, 1), Calendar.DATE),-1);  
	          
	        date[0] = stime;  
	        date[1] = etime;  
	        return date;  
	    }  
	    /** 
	     * 功能：根据指定时间获取当前天前向几天或向后几天的开始和结束时间，以date数组返回 ,
	     * 逻辑： 
	     * 1、appointDate is null ,set default value sysdate 
	     * 2、get date[] 
	     * @param appointDate  指定日期时间
	     * @param appointIndex 向前几天 当为正数时为向前的天数，如果为负为向后的天数,等于0为当天。
	     * @return 
	     */  
	    public static Date[] getDateArrByDay(Date appointDate,int appointIndex){  
	        Date stime = null;  
	        Date etime = null;  
	        Date[] date = new Date[2];  
	        //未完  
	        if(appointDate == null){  
	            appointDate = new Date();  
	        }  
	        if(appointIndex==0){
				stime = DateUtils.truncate(appointDate,Calendar.DATE);  
		        etime = DateUtils.addSeconds(DateUtils.truncate(DateUtils.addDays(appointDate, 1), Calendar.DATE),-1);  
	        }else if(appointIndex<0){
	        	stime = DateUtils.truncate(DateUtils.addDays(appointDate,appointIndex),Calendar.DATE);
	        	etime = DateUtils.addSeconds(DateUtils.truncate(DateUtils.addDays(appointDate, 1), Calendar.DATE),-1);  
	        }else if(appointIndex>0){
	        	stime = DateUtils.truncate(appointDate,Calendar.DATE);  
		        etime = DateUtils.addSeconds(DateUtils.truncate(DateUtils.addDays(appointDate, appointIndex+1), Calendar.DATE),-1);
	        	
	        }
	        date[0] = stime;  
	        date[1] = etime;  
	        return date;  
	    }  
	      
	    /** 
	     * 功能：根据指定时间获取当前星期的开始和结束时间，以date数组返回 
	     * @param appointDate  指定日期时间
	     * @return 
	     */  
	    public static Date[] getDateArrByWeek(Date appointDate){  
	        Date stime = null;  
	        Date etime = null;  
	        Date[] date = new Date[2];  
	        if(appointDate == null){  
	            appointDate = new Date();  
	        }  
	          
	        Calendar calendar = Calendar.getInstance();  
	        calendar.setTime(appointDate);  
	        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);  
	        System.out.println(dayOfWeek);  
	          
	        calendar.add(Calendar.DAY_OF_MONTH, -dayOfWeek+2);  
	          
	        stime = DateUtils.truncate(calendar.getTime(), Calendar.DATE);  
	        calendar.add(Calendar.DAY_OF_MONTH, 7);  
	        etime = DateUtils.addSeconds(DateUtils.truncate(calendar.getTime(), Calendar.DATE), -1);  
	          
	        date[0] = stime;  
	        date[1] = etime;  
	          
	        return date;  
	    }  
	    /** 
	      * 功能：根据指定时间获取当前周前向几周或向后几周的开始和结束时间，以date数组返回 ,
	     * @param appointDate  指定日期时间
	     * @param appointIndex 向前几周 当为正数时为向前的周数，如果为负为向后的周数,等于0为当周。
	     * @return 
	     */  
	    public static Date[] getDateArrByWeek(Date appointDate,int appointIndex){  
	        Date stime = null;  
	        Date etime = null;  
	        Date[] date = new Date[2];  
	        if(appointDate == null){  
	            appointDate = new Date();  
	        }  
	        Calendar calendar = Calendar.getInstance();
	        //设置当天的时间
	        calendar.setTime(appointDate); 
	        //获取当天是本周的第几天
	        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);  
	        
	        if(appointIndex==0){
	        	calendar.add(Calendar.DAY_OF_MONTH, -dayOfWeek+2);  
	  	        stime = DateUtils.truncate(calendar.getTime(), Calendar.DATE);  
	  	        calendar.add(Calendar.DAY_OF_MONTH, 7);  
	  	        etime = DateUtils.addSeconds(DateUtils.truncate(calendar.getTime(), Calendar.DATE), -1);  
	        	
	        }else if(appointIndex<0){
	        	calendar.add(Calendar.DAY_OF_MONTH, -dayOfWeek+2+7*appointIndex);  
	  	        stime = DateUtils.truncate(calendar.getTime(), Calendar.DATE);  
	  	        calendar.add(Calendar.DAY_OF_MONTH, 7+Math.abs((7*appointIndex)));  
	  	        etime = DateUtils.addSeconds(DateUtils.truncate(calendar.getTime(), Calendar.DATE), -1); 
	        	
	        }else if(appointIndex>0){
	        	calendar.add(Calendar.DAY_OF_MONTH, -dayOfWeek+2);  
	  	        stime = DateUtils.truncate(calendar.getTime(), Calendar.DATE);  
	  	        calendar.add(Calendar.DAY_OF_MONTH, 7+7*appointIndex);  
	  	        etime = DateUtils.addSeconds(DateUtils.truncate(calendar.getTime(), Calendar.DATE), -1); 
	        }
	          
	        date[0] = stime;  
	        date[1] = etime;  
	          
	        return date;  
	    }  
	    /** 
	     * 功能：根据指定的时间和上中下旬的其中一个，获取开始时间和结束时间 
	     * @param appointDate 
	     * @param appointIndex 
	     * @return 
	     */  
	    public static Date[] getDateArrByTenDays(Date appointDate,int appointIndex ){  
	        Date stime = null;  
	        Date etime = null;  
	        Date[] date = new Date[2];  
	        if(appointDate == null){  
	            appointDate = new Date();  
	        }  
	        //init date  
	        Calendar calendar = Calendar.getInstance();  
	        calendar.setTime(appointDate);  
	        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);  
	        int maxDayOfMonth = calendar.getMaximum(Calendar.DAY_OF_MONTH);  
	          
	        Date tempDate = DateUtils.truncate(DateUtils.addDays(appointDate, -dayOfMonth + 1), Calendar.DATE);  
	          
	        if(appointIndex == FIRSTTEN){  
	            stime = tempDate;  
	            etime = DateUtils.addSeconds(DateUtils.addDays(stime, 10), -1);  
	        }  
	          
	        if(appointIndex == MIDTEN){  
	            stime = DateUtils.addDays(tempDate, 10);  
	            etime = DateUtils.addSeconds(DateUtils.addDays(stime, 10), -1);  
	        }  
	          
	        if(appointIndex == LASTTEN){  
	            stime = DateUtils.addDays(tempDate, 20);  
	            etime = DateUtils.addSeconds(DateUtils.addDays(tempDate, maxDayOfMonth), -1);  
	        }  
	          
	        date[0] = stime;  
	        date[1] = etime;   
	        return date;  
	    }  
	      
	    /** 
	     * 功能:根据指定时间获取相应月份的开始时间和结束时间 
	     * @param appointDate 
	     * @return 
	     */  
	    public static Date[] getDateArrByMonth(Date appointDate,int appointIndex){  
	        Date stime = null;  
	        Date etime = null;  
	        Date[] date = new Date[2];  
	        if(appointDate == null){  
	            appointDate = new Date();  
	        }  
	        Calendar calendar = Calendar.getInstance();  
	        calendar.setTime(appointDate);  
 	        //获取当天是当月的第几天
 	        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);  
 	        //获取本有最大天数
 	        int maxDayOfMonth = calendar.getMaximum(Calendar.DAY_OF_MONTH);  
	          
	        if(appointIndex==0){
	 	          
	 	        appointDate = DateUtils.truncate(appointDate, Calendar.DATE);  
		        stime = DateUtils.truncate(DateUtils.addDays(appointDate, -dayOfMonth+1), Calendar.DATE);  
		        etime = DateUtils.addSeconds(DateUtils.addDays(stime, maxDayOfMonth), -1);  
	        }else if(appointIndex<0){
	        	appointDate = DateUtils.truncate(appointDate, Calendar.DATE);
	        	etime = DateUtils.addSeconds(DateUtils.addDays(appointDate, maxDayOfMonth-dayOfMonth+1), -1);  
	        	calendar.setTime(DateUtils.addMonths(appointDate, appointIndex));  
	        	//设置日期为当月的第一天
	        	calendar.set(Calendar.DAY_OF_MONTH,1);
	        	//获取当前时间
		 	    appointDate = calendar.getTime();
		 	    //日期转化为2010-12-30的格式
	        	stime = DateUtils.truncate(appointDate, Calendar.DATE); 
		       
	        }else if(appointIndex>0){
	        	stime = DateUtils.truncate(DateUtils.addDays(appointDate, -dayOfMonth+1), Calendar.DATE);  
	        	calendar.setTime(DateUtils.addMonths(appointDate, appointIndex)); 
	        	//设置日期为当月的第一天
	        	calendar.set(Calendar.DAY_OF_MONTH,1);
	        	//设置日期增加一个月
	        	calendar.add(Calendar.MONTH,1); 
	        	//设置日期减一天，就相当于。当月的最后一天
	        	calendar.add(Calendar.DAY_OF_MONTH,-1); 
	        	//获取当前时间
	        	appointDate = calendar.getTime();
	        	//日期转化为2010-12-30的格式
	 	        appointDate = DateUtils.truncate(appointDate, Calendar.DATE);
	 	        //
	 	        etime = DateUtils.addSeconds(DateUtils.addDays(appointDate, 1), -1);  
	        	
	        }
	          
	        date[0] = stime;  
	        date[1] = etime;  
	          
	        return date;  
	    }  
	      
	    /** 
	     * 功能:根据指定时间获取相应月份的开始时间和结束时间 
	     * @param appointDate 
	     * @return 
	     */  
	    public static Date[] getDateArrByMonth(Date appointDate){  
	        Date stime = null;  
	        Date etime = null;  
	        Date[] date = new Date[2];  
	        if(appointDate == null){  
	            appointDate = new Date();  
	        }  
	          
	        //init date  
	        Calendar calendar = Calendar.getInstance();  
	        calendar.setTime(appointDate);  
	        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);  
	        int maxDayOfMonth = calendar.getMaximum(Calendar.DAY_OF_MONTH);  
	          
	        appointDate = DateUtils.truncate(appointDate, Calendar.DATE);  
	          
	        stime = DateUtils.truncate(DateUtils.addDays(appointDate, -dayOfMonth+1), Calendar.DATE);  
	        etime = DateUtils.addSeconds(DateUtils.addDays(stime, maxDayOfMonth), -1);  
	          
	        date[0] = stime;  
	        date[1] = etime;  
	          
	        return date;  
	    }  
	    /** 
	     * 功能：根据指定时间所在的当前年，获取指定季度的开始时间和结束时间 
	     * @param appointDate 指定当前年 
	     * @param appointIndex 
	     * @return 
	     * @throws IllegalArgumentException 
	     */  
	    public static Date[] getDateArrByQuarter(Date appointDate,int appointIndex) throws IllegalArgumentException{  
	        Date stime = null;  
	        Date etime = null;  
	        Date[] date = new Date[2];  
	        if(appointDate == null){  
	            appointDate = new Date();  
	        }  
	        int month = appointDate.getMonth();  
	        Date tempDate = DateUtils.truncate(appointDate, Calendar.YEAR);  
	        if(appointIndex == FIRSTQUARTER){  
	            stime = tempDate;  
	        }else if(appointIndex == SECONDQUARTER){  
	            stime = DateUtils.addMonths(tempDate, 3);  
	        }else if(appointIndex == THIRDQUARTER ){  
	            stime = DateUtils.addMonths(tempDate, 6);  
	        }else if(appointIndex == FORTHQUARTER){  
	            stime = DateUtils.addMonths(tempDate, 9);  
	        }  
	        etime = DateUtils.addSeconds(DateUtils.addMonths(stime, 3), -1);  
	          
	        date[0] = stime;  
	        date[1] = etime;  
	          
	        return date;  
	    }  
	      
	    /** 
	     * 功能：根据指定时间，获取年的开始时间和结束时间 
	     * @param appointDate 
	     * @return 
	     */  
	    public static Date[] getDateArrByYear(Date appointDate){  
	        Date stime = null;  
	        Date etime = null;  
	        Date[] date = new Date[2];  
	        if(appointDate == null){  
	            appointDate = new Date();  
	        }  
	        stime = DateUtils.truncate(appointDate, Calendar.YEAR);  
	        etime = DateUtils.addSeconds(DateUtils.addYears(stime, 1), -1);  
	          
	        date[0] = stime;  
	        date[1] = etime;  
	          
	        return date;  
	    }  
	      
	    /** 
	     * 逻辑： 1、检查startTime,endTime的有效性（是否为空，数据格式）， 异常处理: 1、两个参数都为空，抛出空指针异常 
	     * 2、数据格式不对，直接抛出 3、一个参数为空，另一个参数格式正确的情况下，为空的参数采用系统时间，为了保证startTime <= 
	     * endTime,工具类会做适当的调整 2、转换 3、返回值是个Date[2]数组，date[0] 保存startTime值,date[1] 
	     * 保存startTime值，其中startTime <= endTime 
	     *  
	     * @param startTime 
	     * @param endTime 
	     * @return 
	     */  
	    public static Date[] convertDateClass(String startTime, String endTime)  
	            throws NullPointerException, DataFormatException, ParseException {  
	        Date stime = null;  
	        Date etime = null;  
	        Date[] date = new Date[2];  
	  
	        if (StringUtil.isEmpty(startTime) && StringUtil.isEmpty(endTime)) {  
	            throw new NullPointerException("两个参数不能同时为空");  
	        }  
	  
	        if (StringUtil.isEmpty(startTime) && !StringUtil.isEmpty(endTime)) {  
	            // 先判断endTime格式是否正确  
	            Matcher matcher = pattern.matcher(endTime);  
	            if (matcher.matches()) {  
	                stime = DateUtils.truncate(new Date(), Calendar.DATE); // 当天的开始时间，比如：当前时间为2010-12-27 11:31:30 这里stime的时间是2010-12-27 0:0:0  
	                etime = DateUtils.truncate(sDateFormat.parse(endTime),Calendar.DATE);  
	            } else {  
	                throw new DataFormatException(  
	                        "参数endTime的格式不正确！正确的格式 yyyy-MM-dd 比如：2010-12-12！");  
	            }  
	        }  
	        if (!StringUtil.isEmpty(startTime) && StringUtil.isEmpty(endTime)) {  
	            Matcher matcher = pattern.matcher(startTime);  
	            if (matcher.matches()) {  
	                // 提供转换  
	                etime = DateUtils.truncate(new Date(), Calendar.DATE); // 当天的开始时间，比如：当前时间为2010-12-27 11:31:30 这里stime的时间是2010-12-27 0:0:0  
	                stime = DateUtils.truncate(sDateFormat.parse(startTime),Calendar.DATE);  
	            } else {  
	                throw new DataFormatException(  
	                        "参数startTime的格式不正确！正确的格式 yyyy-MM-dd 比如：2010-12-12！");  
	            }  
	        }  
	  
	        if (!StringUtil.isEmpty(startTime) && !StringUtil.isEmpty(endTime)) {  
	            Matcher sMatcher = pattern.matcher(startTime);  
	            Matcher eMatcher = pattern.matcher(endTime);  
	            if (sMatcher.matches() && eMatcher.matches()) {  
	  
	                stime = DateUtils.truncate(sDateFormat.parse(startTime),  
	                        Calendar.DATE);  
	                etime = DateUtils.truncate(sDateFormat.parse(endTime),  
	                        Calendar.DATE);  
	                
	  
	            } else {  
	                throw new DataFormatException(  
	                        "请检查参数startTime、endTime的格式是否正确！正确的格式 yyyy-MM-dd 比如：2010-12-12！");  
	            }  
	        }  
	  
	        if (!stime.before(etime)) {  
	            Date temp = stime;  
	            stime = etime;  
	            etime = temp;  
	            temp = null;  
	        }  
	          
	        date[0] = stime;  
	        date[1] = etime;  
	        return date;  
	    }  
	    /**
	     * 字符串转化为开始时间
	     * @param startTime 格式  2010-12-27
	     * @return 返回结束 2010-12-27 00:00:00
	     * @throws ParseException 
	     * @throws DataFormatException 
	     */
	    public static Date convertStartDate(String startTime) throws ParseException, DataFormatException{
	    	Date stime=null;
	    	if (!StringUtil.isEmpty(startTime)) {  
	    		Matcher sMatcher = pattern.matcher(startTime);  
	    		if(sMatcher.matches()){
	    			stime = DateUtils.truncate(sDateFormat.parse(startTime), Calendar.DATE); // 当天的开始时间，比如：当前时间为2010-12-27 11:31:30 这里stime的时间是2010-12-27 0:0:0
	    		}else{
						throw new DataFormatException(
						 "请检查参数startTime、endTime的格式是否正确！正确的格式 yyyy-MM-dd 比如：2010-12-12！");
	    		}
	        } else{
	        	throw new NullPointerException("开始时间不能为空");  
	        }
	    	return stime;
	    }
	    /**
	     * 字符串转化为开始时间
	     * @param startTime 格式  2010-12-27
	     * @return 返回结束 2010-12-27 00:00:00
	     * @throws ParseException 
	     * @throws DataFormatException 
	     */
	    public static String convertStartString(String startTime) throws ParseException, DataFormatException{
	    	Date stime=null;
	    	if (!StringUtil.isEmpty(startTime)) {  
	    		Matcher sMatcher = pattern.matcher(startTime);  
	    		if(sMatcher.matches()){
	    			stime = DateUtils.truncate(sDateFormat.parse(startTime), Calendar.DATE); // 当天的开始时间，比如：当前时间为2010-12-27 11:31:30 这里stime的时间是2010-12-27 0:0:0
	    		}else{
						throw new DataFormatException(
						 "请检查参数startTime、endTime的格式是否正确！正确的格式 yyyy-MM-dd 比如：2010-12-12！");
	    		}
	        } else{
	        	throw new NullPointerException("开始时间不能为空");  
	        }
	    	return DateUtil.convertDateToString(stime, DateUtil.TIMEF_FORMAT);
	    }
	    /**
	     * 日期转化为开始时间
	     * @param startTime 
	     * @return 
	     */
	    public static Date convertStartDate(Date startTime){
	    	Date stime=null;
	    	if(null!=startTime){
	    		stime = DateUtils.truncate(startTime, Calendar.DATE); // 当天的开始时间，比如：当前时间为2010-12-27 11:31:30 这里stime的时间是2010-12-27 0:0:0
	    	}
	    	return stime;
	    }
	    /**
	     * 字符串转化为结束时间
	     * @param endTime  2010-12-27
	     * @return 2010-12-27 59:59:59
	     * @throws DataFormatException 
	     * @throws ParseException 
	     */
	    public static Date convertEndDate(String endTime) throws DataFormatException, ParseException{
	    	Date endtime=null;
	    	if (!StringUtil.isEmpty(endTime)) {  
	    		Matcher sMatcher = pattern.matcher(endTime);  
	    		if(sMatcher.matches()){
	    			endtime = DateUtils.truncate(sDateFormat.parse(endTime), Calendar.DATE); // 当天的开始时间，比如：当前时间为2010-12-27 11:31:30 这里stime的时间是2010-12-27 0:0:0  
	    	    	endtime=DateUtils.addSeconds(DateUtils.addDays(endtime, 1), -1);
	    	    	
	    		}else{
						throw new DataFormatException(
						 "请检查参数startTime、endTime的格式是否正确！正确的格式 yyyy-MM-dd 比如：2010-12-12！");
	    		}
	           
	        } else{
	        	 throw new NullPointerException("结束时间不能为空");  
	        }
	    	
	    	return endtime;
	    }
	    /**
	     * 字符串转化为结束时间
	     * @param endTime  2010-12-27
	     * @return 2010-12-27 59:59:59
	     * @throws DataFormatException 
	     * @throws ParseException 
	     */
	    public static String convertEndString(String endTime) throws DataFormatException, ParseException{
	    	Date endtime=null;
	    	if (!StringUtil.isEmpty(endTime)) {  
	    		Matcher sMatcher = pattern.matcher(endTime);  
	    		if(sMatcher.matches()){
	    			endtime = DateUtils.truncate(sDateFormat.parse(endTime), Calendar.DATE); // 当天的开始时间，比如：当前时间为2010-12-27 11:31:30 这里stime的时间是2010-12-27 0:0:0  
	    	    	endtime=DateUtils.addSeconds(DateUtils.addDays(endtime, 1), -1);
	    	    	
	    		}else{
						throw new DataFormatException(
						 "请检查参数startTime、endTime的格式是否正确！正确的格式 yyyy-MM-dd 比如：2010-12-12！");
	    		}
	           
	        } else{
	        	 throw new NullPointerException("结束时间不能为空");  
	        }
	    	
	    	return DateUtil.convertDateToString(endtime, DateUtil.TIMEF_FORMAT);
	    }
	    /**
	     * 日期转化为结束时间
	     * @param endTime
	     * @return
	     */
	    public static Date convertEndDate(Date endTime){
	    	Date etime=null;
	    	if(null!=endTime){
	    		etime = DateUtils.truncate(endTime, Calendar.DATE); // 当天的开始时间，比如：当前时间为2010-12-27 11:31:30 这里stime的时间是2010-12-27 0:0:0  
	    		etime=DateUtils.addSeconds(DateUtils.addDays(etime, 1), -1);
	    	}
	    	return etime;
	    }
	    public static void main(String[] args) throws DataFormatException, ParseException {
//	    	Date date1=convertStartDate("2010-12-12");
//	    	Date date2=convertStartDate(new Date());
//	    	Date date3=convertEndDate("2010-12-12");
//	    	Date date4=convertEndDate(new Date());
//	    	System.out.println(DateUtil.dateToDateString(date1, DateUtil.TIMEF_FORMAT) );
//	    	System.out.println(DateUtil.dateToDateString(date2, DateUtil.TIMEF_FORMAT) );
//	    	System.out.println(DateUtil.dateToDateString(date3, DateUtil.TIMEF_FORMAT) );
//	    	System.out.println(DateUtil.dateToDateString(date4, DateUtil.TIMEF_FORMAT) );
	    	   String start="2010-12-12";
	    	   String end="2010-12-12";
	        	System.out.println(convertStartString(start) );
	        	System.out.println(convertEndString(end) );
	    }

}

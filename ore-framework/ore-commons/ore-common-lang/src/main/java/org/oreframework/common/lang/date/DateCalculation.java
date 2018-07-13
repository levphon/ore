package org.oreframework.common.lang.date;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.SimpleTimeZone;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.oreframework.common.lang.date.DateFormatConstants.DateConstants;

/**
 * 完成一些对日期的计算工作
 * 
 * @author huangzz
 * @version [1.0.0, 2015-3-18]
 */
public class DateCalculation
{
    
    private static DateFormat dateTimeFormat;
    
    /**
     * 获取上N月或N年
     * 
     * @param m
     * @param n
     * @return
     */
    public static String getPreviousMonthFirst(int m, int n)
    {
        String str = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        
        Calendar lastDate = Calendar.getInstance();
        lastDate.set(Calendar.DATE, 1);// 设为当前月的1号
        lastDate.add(Calendar.MONTH, m);// 减一个月，变为下月的1号
        // lastDate.add(Calendar.DATE,-1);//减去一天，变为当月最后一天
        
        str = sdf.format(lastDate.getTime());
        String[] yyymmdd = str.split("-");
        return yyymmdd[n];
    }
    
    /**
     * 输入两种YYYY-DD,YYYY-MM-DD将转换成yyyy-MM-dd HH:mm:ss格式字符串
     * 
     * @param inputStr
     * @return
     */
    public static Date getYyyyMmDdHHmmssDate(String inputStr)
    {
        
        // String dateString = "2001-10-01 00:00:00";
        Date d = new Date();
        try
        {
            // SimpleDateFormat formatter = new SimpleDateFormat(
            // "yyyy-MM-dd HH:mm:ss");
            String[] inputString = inputStr.split("-");
            SimpleDateFormat ymdFormat = null;
            SimpleDateFormat ymFormat = null;
            
            if (inputString.length > 2)
            {
                // 时间格式为yyyy-MM-dd
                ymdFormat = new SimpleDateFormat("yyyy-MM-dd");
                d = ymdFormat.parse(inputStr);
            }
            else
            {
                ymFormat = new SimpleDateFormat("yyyy-MM");
                d = ymFormat.parse(inputStr);
            }
            // dateString = formatter.format(d);
        }
        catch (ParseException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        return d;
    }
    
    /**
     * 获取同一天内，当前时间跟minuteTime的毫秒时间间隔
     * 
     * 
     * @param currentTime 当前时间，包括小时和分钟，精度：分钟 形如 23:59
     * 
     * @return long
     */
    public static long getBetweenMillisTimes(String currentTime)
    {
        Date currentTimes = new Date();
        long currentMillisTimes = currentTimes.getTime();
        SimpleDateFormat datformatter = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat minuteFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String currentDate = datformatter.format(currentTimes);
        currentDate = currentDate + " " + currentTime;
        long betweenMinllisTime = 0;
        try
        {
            // 查询毫秒时间差
            
            Date lastTime = minuteFormatter.parse(currentDate);
            long lastMillisTimes = lastTime.getTime();
            betweenMinllisTime = lastMillisTimes - currentMillisTimes;
        }
        catch (ParseException e)
        {
            
        }
        if (betweenMinllisTime <= 0)
        {
            // 如果为负数
            
            betweenMinllisTime = betweenMinllisTime + (24 * 60 * 60 * 1000L);
        }
        return betweenMinllisTime;
    }
    
    /**
     * 获取当前日期字符串,时间都必须是14位长度的24小时制入库，
     * 
     * @param timePattern
     * @return String
     */
    public final static String getCurrentTime(String timePattern)
    {
        // Modify by y65679 2009-05-20 入库时间必须是24小时制，不能使用yyyyMMddhhmms(12小时制)
        SimpleDateFormat dfmt = new SimpleDateFormat(timePattern);
        return dfmt.format(new Date());
    }
    
    /**
     * 
     * @param time
     * @return
     */
    public static String getDayString(long time)
    {
        String timePattren = "yyyyMMdd";
        Date date = new Date(time);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(timePattren);
        return simpleDateFormat.format(date);
    }
    
    /***
     * 取得当前月前一月
     */
    public static String getFrountMonth()
    {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, -1);
        SimpleDateFormat s = new SimpleDateFormat("yyyyMM");
        return s.format(c.getTime());
    }
    
    /**
     * 计算2个日期之间的间隔天数,startDate和endDate的时间格式是yyyy-MM-dd
     * 
     * @param startDate
     * @param endDate
     * @return
     */
    public static int getIntervalDays(String startDate, String endDate)
    {
        int intervalDays = 0;
        // startDateTemp和endDateTemp的时间格式是yyyyMMdd
        String startDateTemp = startDate.substring(0, 4) + startDate.substring(5, 7) + startDate.substring(8);
        
        String endDateTemp = endDate.substring(0, 4) + endDate.substring(5, 7) + endDate.substring(8);
        
        SimpleDateFormat formatDate = new SimpleDateFormat("yyyyMMdd");
        
        Date date1 = new Date();
        Date date2 = new Date();
        
        try
        {
            date1 = formatDate.parse(startDateTemp);
            date2 = formatDate.parse(endDateTemp);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        
        try
        {
            long len = date2.getTime() - date1.getTime();
            intervalDays = (int)(len / (24 * 60 * 60 * 1000));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return intervalDays;
    }
    
    /**
     * 获取上个月 指定日 指定小时 指定分的 yyyyMMddHHmmss 格式的值
     * 
     * @param isCurr
     * @param timePattern
     * 
     * @return String
     */
    public final static String getLastMonthTime(String farmatTime)
    {
        // yyyyMM farmatTime
        SimpleDateFormat dateFormat = new SimpleDateFormat(farmatTime);
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -1);
        
        return dateFormat.format(calendar.getTime());
    }
    
    /**
     * 获取下一个月的日期时间 指定返回格式
     * 
     * @param farmatTime 时间格式
     * @return String
     */
    public final static String getNextMonthTime(String farmatTime)
    {
        // yyyyMM farmatTime
        SimpleDateFormat dateFormat = new SimpleDateFormat(farmatTime);
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, +1);
        return dateFormat.format(calendar.getTime());
    }
    
    /**
     * 获取下个月 指定日 指定小时 指定分的 yyyyMMddHHmmss 格式的值
     * 
     * 
     * @param isCurr
     * @param timePattern
     * @return String
     */
    public final static String getNextOrCurrMonthTime(int day, int hour, int min, int second, boolean isCurr)
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DateFormatConstants.TIME24FORMATER);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, day);
        calendar.set(Calendar.MINUTE, min);
        calendar.set(Calendar.SECOND, second);
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        if (!isCurr)
        {
            calendar.add(Calendar.MONTH, +1);
        }
        
        return dateFormat.format(calendar.getTime());
    }
    
    private static long getTimes(String currentTime)
    {
        Date currentTimes = new Date();
        SimpleDateFormat datformatter = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat minuteFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String currentDate = datformatter.format(currentTimes);
        currentDate = currentDate + " " + currentTime;
        try
        {
            // 查询毫秒时间差
            
            Date lastTime = minuteFormatter.parse(currentDate);
            return lastTime.getTime();
        }
        catch (ParseException e)
        {
            return 0;
        }
    }
    
    /**
     * 
     * @param time
     * @return
     */
    public static String getTimeString(long time)
    {
        String timePattren = DateFormatConstants.TIME24FORMATER;
        Date date = new Date(time);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(timePattren);
        return simpleDateFormat.format(date);
    }
    
    /**
     * 计算一个日期N天之后的日期
     * 
     * @param intervalDays
     * @return
     */
    public static String getTodayAfter(int intervalDays)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, intervalDays);
        Date date = calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String dayAfter = format.format(date);
        return dayAfter;
    }
    
    /**
     * 获取当前的日期
     * 
     * 
     * @param intervalDays
     * @return
     */
    public static String getContrastDay()
    {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1);
        Date date = calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        String dayAfter = format.format(date);
        return dayAfter;
    }
    
    /**
     * 计算一个日期N天之后的日期
     * 
     * @param intervalDays
     * @return
     */
    public static int getTodayAfter(String intervalDays)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, Integer.parseInt(intervalDays));
        Date date = calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        String dayAfter = format.format(date);
        return Integer.parseInt(dayAfter);
    }
    
    /**
     * 计算一个日期N天之前的日期
     * 
     * @param intervalDays
     * @return
     */
    public static int getTodayBefore(String intervalDays)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, Integer.parseInt("-" + intervalDays));
        Date date = calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        String dayBefore = format.format(date);
        
        return Integer.parseInt(dayBefore);
    }
    
    /**
     * 获取当前日期之前或之后 amount 天或月或年等（field ）的 时间点
     * 
     * @param field - 日历字段。 比如 日 Calendar.DATE
     * @param amount - 为字段添加的日期或时间量。 比如 5天前 就是 -5
     * @param dateFormat － yyyyMMdd
     * @return String
     */
    public static String getTodayDiffer(int field, int amount, String dateFormat)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.add(field, amount);
        Date date = calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat(dateFormat);
        return format.format(date);
    }
    
    /**
     * 获取当前的年月
     * 
     * @param time
     * 
     * @return String
     */
    public static String getYD(long time)
    {
        String timePattren = "yyyyMM";
        Date date = new Date(time);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(timePattren);
        return simpleDateFormat.format(date);
    }
    
    /**
     * 获取指定时间串中的年月参数
     * 
     * @param srcTime
     * @throws ParseException [参数说明]
     * @return String [返回类型说明]
     */
    public static String fetchYD(String srcTime)
        throws ParseException
    {
        SimpleDateFormat formatDate = new SimpleDateFormat(DateFormatConstants.TIME24FORMATER);
        Date lastData = formatDate.parse(srcTime);
        return getYD(lastData.getTime());
    }
    
    /**
     * 判断某指定时间 比 当前时间 + time 早
     * 
     * 
     * @param time
     * @return
     */
    public static boolean isGreaterCur(String time, int min)
    {
        long tem1 = new Date().getTime() - 60L * 1000 * min;
        Date date1 = new Date(tem1);
        
        SimpleDateFormat formatDate = new SimpleDateFormat(DateFormatConstants.TIME24FORMATER);
        String guoqu = formatDate.format(date1);
        
        int b = time.compareTo(guoqu);
        boolean result = b < 0 ? true : false;
        
        return result;
    }
    
    /**
     * 得到节点的时间
     * 
     * @param count节点数量
     * @param taskTime执行时间，如23:00
     */
    public static String[][] timerNodeArrs(final int count, String taskTime, int beforeFileTime)
    {
        long delay = getTimes(taskTime);
        final int HOUR = 24;
        int len = count;
        int timeLe = HOUR / len;// 时间长度
        int mod = HOUR % len;// 还剩下的
        int[] timeArr = new int[len];
        long[] startTimeLongArr = new long[len];
        long[] endTimeLongArr = new long[len];
        
        // 给数组初始值
        
        for (int i = 0; i < timeArr.length; i++)
        {
            timeArr[i] = timeLe;
        }
        
        // 把剩下的平均分配进去
        while (0 < mod--)
        {
            len--;
            timeArr[len] = timeArr[len] + 1;
        }
        
        // 初始开始时间和结束时间
        int i = endTimeLongArr.length;
        while (0 < i--)
        {
            startTimeLongArr[i] = delay - timeArr[i] * DateFormatConstants.HOUR_TIME;
            endTimeLongArr[i] = delay;
            delay -= timeArr[i] * DateFormatConstants.HOUR_TIME;
        }
        
        // 第1个平台还需处理前一天的事
        
        startTimeLongArr[0] -= beforeFileTime * DateFormatConstants.DAY;
        String[] startTimeStrArr = new String[count];
        String[] endTimeStrArr = new String[count];
        
        for (int j = 0; j < endTimeLongArr.length; j++)
        {
            startTimeStrArr[j] = getTimeString(startTimeLongArr[j]);
            endTimeStrArr[j] = getTimeString(endTimeLongArr[j]);
        }
        
        return new String[][] {startTimeStrArr, endTimeStrArr};
    }
    
    /**
     * 取得当前系统时间，精确到毫秒.
     * 
     * @param time
     * @return
     */
    public static String getSystemTime(long time)
    {
        String timePattren = "yyyy-MM-dd HH:mm:ss.SSS";
        Date date = new Date(time);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(timePattren);
        return simpleDateFormat.format(date);
    }
    
    /**
     * 计算一个日期N天之后的日期 指定格式
     * 
     * @param intervalDays 天数
     * @param strformat 指定格式
     * @return String
     */
    public static String getTodayAfter(int intervalDays, String strformat)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, intervalDays);
        Date date = calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat(strformat);
        String dayAfter = format.format(date);
        return dayAfter;
    }
    
    /**
     * 将时间串转换为指定格式的时间串
     * 
     * @param scrTime 时间串 如：20090101000000
     * @param timeFormat 格式 如：yyyy-MM-dd HH:mm:ss
     * @return String [] 如： 2009-01-01 00:00:00
     */
    public static String getFormatTime(String scrTime, String timeFormat)
    {
        String timeValue = null;
        SimpleDateFormat formatDate = new SimpleDateFormat(DateFormatConstants.TIME24FORMATER);
        try
        {
            Date lastData = formatDate.parse(scrTime);
            
            formatDate = new SimpleDateFormat(timeFormat);
            timeValue = formatDate.format(lastData);
        }
        catch (Exception e1)
        {
            // 使用系统时间
            timeValue = getCurrentTime(timeFormat);
        }
        return timeValue;
    }
    
    /**
     * 根据传入日期字符串获取指定月份最后一天
     * 
     * @param dateStr(yyyyMM)- 为null时或不足六位时返回当月最后一天
     * @return [参数说明]最后一天的日期
     * @return int [返回类型说明]
     */
    public static String getLastDay(String dateStr, String format)
    {
        SimpleDateFormat formatDate = null;
        if (null == format)
        {
            formatDate = new SimpleDateFormat(DateFormatConstants.TIME24FORMATER);
        }
        else
        {
            formatDate = new SimpleDateFormat(format);
        }
        Calendar cal = Calendar.getInstance();
        if (dateStr != null && dateStr.length() == 6)
        {
            int year = Integer.parseInt(dateStr.substring(0, 4));
            int month = Integer.parseInt(dateStr.substring(4, 6)) - 1;
            cal.set(year, month, 1);
        }
        cal.set(Calendar.DATE, 1);// 把日期设置为当月第一天
        cal.roll(Calendar.DATE, -1);// 日期回滚一天，也就是最后一天
        return formatDate.format(cal.getTime());
    }
    
    public static String getAfterMinuteTime(int minute)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, minute);
        Date date = calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat(DateFormatConstants.TIME24FORMATER);
        String minuteAfter = format.format(date);
        return minuteAfter;
    }
    
    /**
     * 计算2个日期之间的间隔天数,startDate和endDate
     * 
     * @param startDate
     * @param endDate
     * @param dateFormat
     * @return
     */
    public static int getComDays(String startDate, String endDate, String dateFormat)
    {
        int intervalDays = 0;
        SimpleDateFormat formatDate = null;
        if (null == startDate)
        {
            return intervalDays;
        }
        if (null == endDate)
        {
            return intervalDays;
        }
        if (dateFormat != null)
        {
            formatDate = new SimpleDateFormat(dateFormat);
        }
        else
        {
            formatDate = new SimpleDateFormat("yyyyMMdd");
        }
        
        Date date1 = new Date();
        Date date2 = new Date();
        
        try
        {
            date1 = formatDate.parse(startDate);
            date2 = formatDate.parse(endDate);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        try
        {
            long len = date2.getTime() - date1.getTime();
            if (len < 0)
            {
                len = java.lang.Math.abs(len);
            }
            intervalDays = (int)(len / (24 * 60 * 60 * 1000));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return intervalDays;
    }
    
    /**
     * 得到二个日期间的间隔天数
     */
    public static String getTwoDay(String sj1, String sj2)
    {
        SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
        long day = 0;
        try
        {
            java.util.Date date = myFormatter.parse(sj1);
            java.util.Date mydate = myFormatter.parse(sj2);
            day = (date.getTime() - mydate.getTime()) / (24 * 60 * 60 * 1000);
        }
        catch (Exception e)
        {
            return "";
        }
        return day + "";
    }
    
    // 获取当天时间
    public String getNowTime(String dateformat)
    {
        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat(dateformat);// 可以方便地修改日期格式
        String hehe = dateFormat.format(now);
        return hehe;
    }
    
    // 获得当前日期与本周日相差的天数
    @SuppressWarnings("unused")
    private int getMondayPlus()
    {
        Calendar cd = Calendar.getInstance();
        // 获得今天是一周的第几天，星期日是第一天，星期二是第二天......
        int dayOfWeek = cd.get(Calendar.DAY_OF_WEEK) - 1; // 因为按中国礼拜一作为第一天所以这里减1
        if (dayOfWeek == 1)
        {
            return 0;
        }
        else
        {
            return 1 - dayOfWeek;
        }
    }
    
    // 获得下个月最后一天的日期
    public String getNextMonthEnd()
    {
        String str = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        
        Calendar lastDate = Calendar.getInstance();
        lastDate.add(Calendar.MONTH, 1);// 加一个月
        lastDate.set(Calendar.DATE, 1);// 把日期设置为当月第一天
        lastDate.roll(Calendar.DATE, -1);// 日期回滚一天，也就是本月最后一天
        str = sdf.format(lastDate.getTime());
        return str;
    }
    
    /**
     * 时间比较
     * 
     * @param historyDate
     * @param timePattern
     * @param timeUnit
     * @return
     */
    public static boolean compareTime(String historyDate, String timePattern, int timeUnit)
    {
        String currentDate = getCurrentTime(timePattern);
        long historyTime = 0;
        long currentTime = 0;
        
        SimpleDateFormat dfmt = new SimpleDateFormat(timePattern);
        
        try
        {
            historyTime = dfmt.parse(historyDate).getTime();
            currentTime = dfmt.parse(currentDate).getTime();
            long len = currentTime - historyTime;
            
            // 以天为单位的时间差
            int intervalDays = (int)(len / (timeUnit));
            if (intervalDays > 0)
            {
                return true;
            }
            System.out.println(intervalDays);
            
        }
        catch (ParseException e)
        {
            return false;
        }
        return false;
    }
    
    /**
     * 
     * <验证时间是否在有效期> <功能详细描述,如果时间在有效期范围内true>
     * 
     * @param source 传过来的时间
     * @param cycle 有效期多�?
     * 
     * 
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类�?�类#方法、类#成员]
     */
    public static boolean compareTime(String source, int cycle)
    {
        // YYYYMMDDHHMMSS
        SimpleDateFormat dfmt = new SimpleDateFormat("yyyyMMddHHmmss");
        long currentDate = new Date().getTime();
        long sourceDate = 0;
        boolean bool = false;
        try
        {
            sourceDate = dfmt.parse(source).getTime();
            long len = currentDate - sourceDate;
            int intervalDays = (int)(len / (24 * 60 * 60 * 1000));
            // 客户端传来的etag时间比当前时间小于配置的周期
            if (intervalDays < cycle)
            {
                bool = true;
            }
        }
        catch (ParseException e)
        {
            // TODO Auto-generated catch block
            bool = false;
        }
        return bool;
    }
    
    /**
     * @author zsj 返回时间的字符串形式
     */
    public static String getNumericStrTime(String time)
    {
        if (time == null || "".equals(time))
        {
            return "";
        }
        String timeStr = time.replaceAll("[^0-9]", "");
        if (timeStr.length() < 14)
        {
            return timeStr;
        }
        else
        {
            return timeStr.substring(0, 14);
        }
    }
    
    public static String getTodayAfter(String date, int intervalDays)
    {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddhhmmss");
        Date date1 = null;
        try
        {
            date1 = format.parse(date);
        }
        catch (ParseException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (date1 == null)
        {
            return "";
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date1);
        calendar.add(Calendar.DATE, intervalDays);
        Date date2 = calendar.getTime();
        String dayAfter = format.format(date2);
        return dayAfter;
    }
    
    /**
     * <获取正确格式时间> <从数据库中取出时间有时最后会多出.0, �?去掉, 此方法支持截取时间格式有 '09-07-29 15:18:23' '09-07-29 15:18' '2009-07-29
     * 15:18:23' '2009-07-29 15:18' '2009-07-29'�? >
     * 
     * @param time
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类�?�类#方法、类#成员]
     */
    public static String getRightFormatTime(String time, boolean removeZero)
    {
        Pattern pattern = Pattern.compile("((^\\d{2,4}-\\d{1,2}-\\d{1,2}( \\d{1,2}(:\\d{1,2}){1,2})?))");
        Matcher matcher = pattern.matcher(time);
        if (matcher.find())
        {
            String newTime = matcher.group();
            if (removeZero)
            {
                newTime = newTime.replaceAll("-0", "-");
            }
            return newTime;
        }
        return "";
    }
    
    /**
     * 得到前一�?
     * 
     * 
     * 
     * <功能详细描述>
     * 
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类�?�类#方法、类#成员]
     */
    public static String getTodayCurrent()
    {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 0); // 得到当天
        Date date = calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd000000");
        return format.format(date);
    }
    
    /**
     * 得到前一个星期的星期�?
     * 
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类�?�类#方法、类#成员]
     */
    @SuppressWarnings("static-access")
    public static String getWeekCurrent()
    {
        
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.WEDNESDAY, 0);
        Date date = calendar.getTime();
        
        Calendar now = Calendar.getInstance();
        now.setTime(date);
        int today = now.get(Calendar.DAY_OF_WEEK);
        int first_day_of_week = now.get(Calendar.DATE) + 2 - today; // 星期�?
        now.set(now.DATE, first_day_of_week);
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd000000");
        return format.format(now.getTime());
    }
    
    /**
     * 得到前一个月
     * 
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类�?�类#方法、类#成员]
     */
    public static String getMonthCurrent()
    {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, 0); // 得到前一个月
        Date date = calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyyMM01000000");
        return format.format(date);
    }
    
    /**
     * ��Ҫ��ת��ʱ�����ʾ��ʽ ����oldpattern�������ڸ�ʽ����:yyyyMMddhhmmss ��ʽ�����ĺ���μ�JDK simpleDateFormat��
     * newpattern�������ڸ�ʽ
     */
    public static String timeTransform(String time, String oldpattern, String newpattern)
    {
        // ��ӡ������Ϣ
        String oldtime = null;
        if (oldpattern == null)
        {
            throw new IllegalArgumentException("the old pattern is null");
        }
        if (newpattern == null)
        {
            throw new IllegalArgumentException("the new pattern is null");
        }
        SimpleDateFormat olddatepattern = new SimpleDateFormat(oldpattern);
        Date now;
        try
        {
            now = olddatepattern.parse(time);
            // ��ԭ4��pattern�������ڣ��ٺ�ԭ4�ıȽϣ��ɴ�4���ʱ���Ƿ�Ϸ�
            oldtime = olddatepattern.format(now);
            if (!oldtime.equals(time))
            {
                throw new IllegalArgumentException("using Illegal time");
            }
        }
        catch (ParseException e)
        {
            throw new IllegalArgumentException("using [" + oldpattern + "] parse [" + time + "] failed");
        }
        SimpleDateFormat newdatepattern = new SimpleDateFormat(newpattern);
        
        return newdatepattern.format(now);
    }
    
    /**
     * ��ȡָ����ʽ�ĵ�ǰ���� ����pattern�����ڸ�ʽ����:yyyyMMddhhmmss ��ʽ�����ĺ���μ�JDK simpleDateFormat��
     */
    public static String getCurrentDate(String pattern)
    {
        if (pattern == null)
        {
            throw new IllegalArgumentException("input string parameter is null");
        }
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        Date now = new Date();
        return sdf.format(now);
    }
    
    /**
     * �����ڳ�����ת�����ַ� ����time��long���Ӹ�������ʱ�䣺1970��1��1��0����ĺ����� pattern, String, ת����Ŀ���ʽ
     */
    public static String long2TimeStr(long time, String pattern)
    {
        if (pattern == null)
        {
            throw new IllegalArgumentException("pattern parameter can not be null");
        }
        Date dt = new Date(time);
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(dt);
    }
    
    /**
     * ��������ת�����ַ� ����time��Date pattern, String, ת����Ŀ���ʽ
     */
    public static String date2TimeStr(Date time, String pattern)
    {
        if (pattern == null)
        {
            throw new IllegalArgumentException("pattern parameter can not be null");
        }
        if (time == null)
        {
            throw new IllegalArgumentException("time parameter can not be null");
        }
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(time);
    }
    
    /**
     * ���������һ������Ŀǰֻ���ǣ��꣬�£��ܣ��գ�ʱ���֡��롢���� ����date, long��ԭʼʱ�� delta��int�����Ĵ�С unit, int,
     * ���ĵ�λ��YEAR, MONTH, DAY, HOUR, MINUTE, SECOND, MILLISECOND ���أ�long���Ӹ�������ʱ�䣺1970��1��1��0����ĺ�����
     */
    public static long addDate(long date, int delta, int unit)
    {
        if ((unit < DateFormatConstants.YEAR) || (unit > DateFormatConstants.MILLISECOND))
        {
            throw new IllegalArgumentException(
                "time unit must in [YEAR, MONTH, WEEK, DAY, HOUR, MINUTE, SECOND, MILLISECOND], others not support");
        }
        Date dt = new Date(date);
        Calendar calendar = getLocalCalendar(dt);
        // �����
        switch (unit)
        {
            case DateFormatConstants.YEAR:
                calendar.add(Calendar.YEAR, delta);
                break;
            case DateFormatConstants.MONTH:
                calendar.add(Calendar.MONTH, delta);
                break;
            case DateFormatConstants.WEEK:
                calendar.add(Calendar.DAY_OF_WEEK, delta);
                break;
            case DateFormatConstants.DAYX:
                calendar.add(Calendar.DAY_OF_MONTH, delta);
                break;
            case DateFormatConstants.HOUR:
                calendar.add(Calendar.HOUR, delta);
                break;
            case DateFormatConstants.MINUTE:
                calendar.add(Calendar.MINUTE, delta);
                break;
            case DateFormatConstants.SECOND:
                calendar.add(Calendar.SECOND, delta);
                break;
            case DateFormatConstants.MILLISECOND:
                calendar.add(Calendar.MILLISECOND, delta);
        }
        
        Date ndt = calendar.getTime();
        return ndt.getTime();
    }
    
    public static String addDate(long date, int delta, int unit, String pattern)
    {
        if (pattern == null)
        {
            throw new IllegalArgumentException("pattern parameter can not be null");
        }
        return long2TimeStr(addDate(date, delta, unit), pattern);
    }
    
    public static long timeStr2Long(String time, String pattern)
    {
        return timeStr2Date(time, pattern).getTime();
    }
    
    /**
     * ���ַ�ת���������� ����time��String�������ַ� pattern, String, ����ĸ�ʽ ���أ�Date��������
     */
    public static Date timeStr2Date(String time, String pattern)
    {
        if (time == null)
        {
            throw new IllegalArgumentException("time parameter can not be null");
        }
        if (pattern == null)
        {
            throw new IllegalArgumentException("pattern parameter can not be null");
        }
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        try
        {
            return sdf.parse(time);
        }
        catch (ParseException e)
        {
            throw new IllegalArgumentException("using [" + pattern + "] parse [" + time + "] failed");
        }
    }
    
    /**
     * ��ȡ�����ַ��ĳһ���� ����date����Ч�������ַ� pattern�����ڸ�ʽ�ַ�
     * part��ʱ�䲿�ֵ�ָʾ��ֻ���ǣ�YEAR,MONTH,WEEK,DAY,HOUR,MINUTE,SECOND��MILLISECOND
     */
    public static int getDatePart(String date, String pattern, int part)
    {
        if (date == null)
        {
            throw new IllegalArgumentException("date parameter is null");
        }
        if (pattern == null)
        {
            throw new IllegalArgumentException("pattern parameter is null");
        }
        if ((part < DateFormatConstants.YEAR) || (part > DateFormatConstants.MINUTEOFDAY))
        {
            throw new IllegalArgumentException("the part parameter must be in [YEAR,MONTH, DAY, HOUR, MINUTE, SECOND]");
        }
        Date dt = timeStr2Date(date, pattern);
        return getDatePart(dt, part);
    }
    
    public static int getDatePart(Date date, int part)
    {
        if (date == null)
        {
            throw new IllegalArgumentException("date parameter is null");
        }
        if ((part < DateFormatConstants.YEAR) || (part > DateFormatConstants.MINUTEOFDAY))
        {
            throw new IllegalArgumentException("the part parameter must be in [YEAR,MONTH, DAY, HOUR, MINUTE, SECOND]");
        }
        Calendar calendar = getLocalCalendar(date);
        int result = 0;
        switch (part)
        {
            case DateFormatConstants.YEAR:
                result = calendar.get(Calendar.YEAR);
                break;
            case DateFormatConstants.MONTH:
                result = calendar.get(Calendar.MONTH);
                break;
            case DateFormatConstants.WEEK:
                result = calendar.get(Calendar.DAY_OF_WEEK);
                break;
            case DateFormatConstants.DAYX:
                result = calendar.get(Calendar.DAY_OF_MONTH);
                break;
            case DateFormatConstants.HOUR:
                result = calendar.get(Calendar.HOUR_OF_DAY);
                break;
            case DateFormatConstants.MINUTE:
                result = calendar.get(Calendar.MINUTE);
                break;
            case DateFormatConstants.SECOND:
                result = calendar.get(Calendar.SECOND);
                break;
            case DateFormatConstants.MILLISECOND:
                result = calendar.get(Calendar.MILLISECOND);
                break;
            case DateFormatConstants.MINUTEOFDAY:
                result = calendar.get(Calendar.HOUR_OF_DAY) * 60 + calendar.get(Calendar.MINUTE);
        }
        return result;
    }
    
    public static String getNextPeriodTime(Date galeday, String pattern, int part)
    {
        // ��ӡ������Ϣ
        
        if (galeday == null)
        {
            throw new IllegalArgumentException("date parameter is null");
        }
        if (pattern == null)
        {
            throw new IllegalArgumentException("pattern parameter is null");
        }
        if ((part < DateFormatConstants.YEAR) || (part > DateFormatConstants.DAY))
        {
            throw new IllegalArgumentException("the part parameter must be in [YEAR,MONTH, WEEK, DAY]");
        }
        String result = null;
        Calendar caldeduct = getLocalCalendar(galeday);
        Calendar calnow = getLocalCalendar(new Date());
        switch (part)
        {
            case DateFormatConstants.DAYX: // �۷�����Ϊÿ��
                return recursiveGet(caldeduct, calnow, pattern, Calendar.DAY_OF_MONTH, Calendar.HOUR, Calendar.HOUR);
            case DateFormatConstants.WEEK: // ����Ϊÿ��
                return recursiveGetWeek(caldeduct,
                    calnow,
                    pattern,
                    Calendar.DAY_OF_WEEK,
                    Calendar.DAY_OF_MONTH,
                    0,
                    Calendar.DAY_OF_WEEK);
            case DateFormatConstants.YEAR: // ����Ϊÿ��
                return recursiveGet(caldeduct, calnow, pattern, Calendar.YEAR, Calendar.MONTH, Calendar.MONTH);
            case DateFormatConstants.MONTH: // ����Ϊÿ��
                return recursiveGet(caldeduct,
                    calnow,
                    pattern,
                    Calendar.MONTH,
                    Calendar.DAY_OF_MONTH,
                    Calendar.DAY_OF_MONTH);
            default:
                result = "unsupport period : " + String.valueOf(part);
        }
        return result;
    }
    
    private static String recursiveGetWeek(Calendar caldeduct, Calendar calnow, String pattern, int largepart,
        int part, int gap, int step)
    {
        int deduct = caldeduct.get(step);
        int now = calnow.get(step);
        if (step == Calendar.DAY_OF_WEEK)
        {
            gap = deduct - now;
        }
        if (deduct > now)
        {
            calnow.add(step, gap);
            return DateCalculation.date2TimeStr(calnow.getTime(), pattern);
        }
        else if (deduct < now)
        {
            calnow.add(step, 7 + gap);
            return DateCalculation.date2TimeStr(calnow.getTime(), pattern);
        }
        else
        {
            switch (step)
            {
                case Calendar.DAY_OF_WEEK:
                    step = Calendar.HOUR;
                    break;
                case Calendar.HOUR:
                    step = Calendar.MINUTE;
                    break;
                case Calendar.MINUTE:
                    step = Calendar.SECOND;
                    break;
                case Calendar.SECOND:
                    step = Calendar.MILLISECOND;
                    break;
                case Calendar.MILLISECOND:
                    return date2TimeStr(calnow.getTime(), pattern);
            }
            return recursiveGetWeek(caldeduct, calnow, pattern, largepart, part, gap, step);
        }
    }
    
    private static String recursiveGet(Calendar caldeduct, Calendar calnow, String pattern, int largepart, int part,
        int step)
    {
        // ��ӡ������Ϣ
        
        int deduct = caldeduct.get(step);
        int now = calnow.get(step);
        if (deduct > now)
        {
            calnow.set(part, caldeduct.get(part));
            if (largepart == Calendar.YEAR)
            {
                calnow.set(Calendar.DAY_OF_MONTH, caldeduct.get(Calendar.DAY_OF_MONTH));
            }
            return DateCalculation.date2TimeStr(calnow.getTime(), pattern);
        }
        else if (deduct < now)
        {
            calnow.add(largepart, 1);
            calnow.set(part, caldeduct.get(part));
            if (largepart == Calendar.YEAR)
            {
                calnow.set(Calendar.DAY_OF_MONTH, caldeduct.get(Calendar.DAY_OF_MONTH));
            }
            return DateCalculation.date2TimeStr(calnow.getTime(), pattern);
        }
        else
        {
            switch (step)
            {
                case Calendar.YEAR:
                    step = Calendar.MONTH;
                    break;
                case Calendar.MONTH:
                    step = Calendar.DATE;
                    break;
                case Calendar.DAY_OF_MONTH:
                    step = Calendar.HOUR;
                    break;
                case Calendar.HOUR:
                    step = Calendar.MINUTE;
                    break;
                case Calendar.MINUTE:
                    step = Calendar.SECOND;
                    break;
                case Calendar.SECOND:
                    step = Calendar.MILLISECOND;
                    break;
                case Calendar.MILLISECOND:
                    return date2TimeStr(calnow.getTime(), pattern);
            }
            return recursiveGet(caldeduct, calnow, pattern, largepart, part, step);
        }
    }
    
    /**
     * ��ö���ʱ���������������ĵ�ǰ���� ����date��Date��������
     */
    @SuppressWarnings({"unused", "static-access"})
    public static Calendar getLocalCalendar(Date date)
    {
        // ����ΪGMT+08:00ʱ��
        String[] ids = TimeZone.getAvailableIDs(8 * 60 * 60 * 1000);
        if (ids.length == 0)
        {
            throw new IllegalArgumentException("get id of GMT+08:00 time zone failed");
        }
        SimpleTimeZone stz = new SimpleTimeZone(8 * 60 * 60 * 1000, ids[0]);
        // ����Calendar���󣬲�����Ϊָ��ʱ��
        // Calendar calendar = new GregorianCalendar(stz);
        Calendar calendar = new GregorianCalendar(TimeZone.getDefault());
        // ���óɿ��ݷ�ʽ
        if (!calendar.isLenient())
        {
            calendar.setLenient(true);
        }
        // ����SUNDAYΪÿ�ܵĵ�һ��
        calendar.setFirstDayOfWeek(calendar.SUNDAY);
        // ��������ĵ�ǰʱ��
        calendar.setTime(date);
        return calendar;
    }
    
    /**
     * 获取当前时间在＋－n分钟后的字符串时间
     * 
     * 
     * @param n int
     * @return String
     */
    public static String getTimebyMinAfter(int n)
    {
        Calendar now = Calendar.getInstance(TimeZone.getDefault());
        now.add(Calendar.MINUTE, n);
        return (dateTimeFormat.format(now.getTime()));
    }
    
    /**
     * 获取当前时间在＋－n秒后的字符串时间
     * 
     * @param n int
     * @return String
     */
    public static String getTimebySecAfter(int n)
    {
        Calendar now = Calendar.getInstance(TimeZone.getDefault());
        now.add(Calendar.SECOND, n);
        return (dateTimeFormat.format(now.getTime()));
    }
    
    /**
	 * 获取当前时间在＋－n分钟后的时间(java.util.Date)
	 * 
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
	 * 将Date转换成字符串“yyyy-mm-dd hh:mm:ss”的字符串
	 * 
	 * @param date
	 *            日期
	 * @return String 字符串
	 */
	public static String dateToDateString(Date date) {
		return DateUtils.dateToDateString(date, DateFormatConstants.TIMEF_FORMAT);
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
		return DateUtils.dateToDateString(new Date(), DateFormatConstants.ZHCN_TIME_FORMAT);
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
	 * @return 得到本月第几天
	 */
	public static String getDay() {
		Calendar now = Calendar.getInstance();
		int day = now.get(Calendar.DAY_OF_MONTH);
		String dayStr = String.valueOf(day);

		// 对于10月份以下的月份,加"0"在前面

		if (day < 10)
			dayStr = "0" + dayStr;
		return dayStr;
	}

	/**
	 * @return 获取指定日期月份 如09
	 */
	public static String getMonth(Date date) {
		Calendar now = Calendar.getInstance();
		now.setTime(date);
		int month = now.get(Calendar.MONTH) + 1;
		String monStr = String.valueOf(month);
		// 对于10月份以下的月份,加"0"在前面
		if (month < 10)
			monStr = "0" + monStr;
		return monStr;
	}

	/**
	 * @return 获取指定日期天数
	 */
	public static String getDay(Date date) {
		Calendar now = Calendar.getInstance();
		now.setTime(date);
		int day = now.get(Calendar.DAY_OF_MONTH);
		String dayStr = String.valueOf(day);
		// 对于10月份以下的月份,加"0"在前面
		if (day < 10)
			dayStr = "0" + dayStr;
		return dayStr;
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

		Date date = new SimpleDateFormat(DateFormatConstants.TIME_STR_FORMAT).parse(expireTime);
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
	 * 
	 * @param startTime
	 *            开始时间
	 * 
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

		return new SimpleDateFormat(DateFormatConstants.TIME_STR_FORMAT).format(expire.getTime());

	}

	/**
	 * 获取当天日期 返回格式：yyyy-MM-dd
	 */
	public static Date getTodayDate() {
		// 获取昨日的日期
		Calendar cal = Calendar.getInstance();
		Date today = cal.getTime();
		return today;
	}

	/**
	 * 获取昨日日期 返回格式：yyyy-MM-dd
	 */
	public static String getYesterdayDateStr() {
		// 获取昨日的日期
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, -1);
		Date yesterday = cal.getTime();
		return new SimpleDateFormat(DateFormatConstants.DATE_FORMAT).format(yesterday);
	}

	/**
	 * 获取昨日日期 返回格式：yyyy-MM-dd
	 */
	public static Date getYesterdayDate() {
		// 获取昨日的日期
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, -1);
		Date yesterday = cal.getTime();
		return yesterday;
	}

	/**
	 * 获取明天日期 返回格式：yyyy-MM-dd
	 */
	public static String getTomorrowDateStr() {
		// 获取昨日的日期
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, 1);
		Date tomorrow = cal.getTime();
		return new SimpleDateFormat(DateFormatConstants.DATE_FORMAT).format(tomorrow);
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
	 * 
	 * @param date
	 *            Calendar对象
	 * @param strDatetype
	 *            字符串日期类型（1：XXXX年XX月XX日，2：XX月XX日，3，XXXX年，4：XXXX-XX-XX，5：XX-XX，6：
	 *            XXXX）
	 * 
	 * @return
	 */
	public static String getStrDate(Calendar calendar, int strDateType) {
		String year = String.valueOf(calendar.get(Calendar.YEAR));
		String month = (calendar.get(Calendar.MONTH) + 1) < 10 ? "0"
				+ (calendar.get(Calendar.MONTH) + 1) : String.valueOf

		((calendar.get(Calendar.MONTH) + 1));
		String day = calendar.get(Calendar.DAY_OF_MONTH) < 10 ? "0"
				+ calendar.get(Calendar.DAY_OF_MONTH) : String.valueOf

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
	 * 
	 * @param protoDate
	 *            原来的时间（java.util.Date）
	 * 
	 * @param dateOffset
	 *            （向前移正数，向后移负数）
	 * 
	 * @return 时间（java.util.Date）
	 */
	public static Date getOffsetMonthDate(Date protoDate, int monthOffset) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(protoDate);
		// cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) - monthOffset);错误写法
		cal.add(Calendar.MONTH, -monthOffset);
		return cal.getTime();
	}

	/**
	 * 根据原来的时间（Date）获得相对偏移 N 天的时间（Date）
	 * 
	 * @param protoDate
	 *            原来的时间（java.util.Date）
	 * 
	 * @param dateOffset
	 *            （向前移正数，向后移负数）
	 * 
	 * @return 时间（java.util.Date）
	 */
	public static Date getOffsetDayDate(Date protoDate, int dateOffset) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(protoDate);
		cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH)
				- dateOffset);
		return cal.getTime();
	}

	/**
	 * 根据原来的时间（Date）获得相对偏移 N 小时的时间（Date）
	 * 
	 * @param protoDate
	 *            原来的时间（java.util.Date）
	 * 
	 * @param offsetHour
	 *            （向前移正数，向后移负数）
	 * 
	 * @return 时间（java.util.Date）
	 */
	public static Date getOffsetHourDate(Date protoDate, int offsetHour) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(protoDate);
		cal.set(Calendar.HOUR_OF_DAY, cal.get(Calendar.HOUR_OF_DAY)
				- offsetHour);
		return cal.getTime();
	}

	/**
	 * 获取指定月份和日子的日期(未做日期效验)
	 * 
	 * @param currentDate
	 *            当前日期
	 * @param assignYear
	 *            指定年份,-1代表年不做修改
	 * @param assignMonth
	 *            指定月份,从0开始,超过月最大值自动往后加一个月年,-1代表月不做修改
	 * @param assignDay
	 *            指定日,从1开始,超过日最大值往后加一个月,-1代表日不做修改
	 * @return 修改后的日期
	 */
	public static Date getAssignDate(Date currentDate, int assignYear,
			int assignMonth, int assignDay) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(currentDate);
		if (assignYear > -1) {
			cal.set(Calendar.YEAR, assignYear);
		}
		if (assignMonth > -1) {
			cal.set(Calendar.MONTH, assignMonth);
		}
		if (assignDay > -1) {
			cal.set(Calendar.DAY_OF_MONTH, assignDay);
		}
		return cal.getTime();
	}

	/**
	 * 获得两个日前之间相差的天数,有时分秒的影响
	 * 
	 * @param startDate
	 *            开始日期
	 * 
	 * @param endDate
	 *            结束日期
	 * @return 天数
	 */
	public static int getDayCountBetweenDate(Date startDate, Date endDate) {
		Calendar startCalendar = Calendar.getInstance();
		startCalendar.setTime(startDate);
		Calendar endCalendar = Calendar.getInstance();
		endCalendar.setTime(endDate);
		int i = 0;
		while (endCalendar.compareTo(startCalendar) >= 0) {
			startCalendar.set(Calendar.DAY_OF_MONTH,
					startCalendar.get(Calendar.DAY_OF_MONTH) + 1);
			i++;
		}
		return i;
	}

	/**
	 * 获得两个日前之间相差的月份
	 * 
	 * @param startDate
	 *            开始日期
	 * 
	 * @param endDate
	 *            结束日期
	 * @return 月数
	 */
	public static int getMonthCountBetweenDate(Date startDate, Date endDate) {
		Calendar startCalendar = Calendar.getInstance();
		startCalendar.setTime(startDate);
		Calendar endCalendar = Calendar.getInstance();
		endCalendar.setTime(endDate);
		int i = 0;
		while (endCalendar.compareTo(startCalendar) >= 0) {
			startCalendar.set(Calendar.MONTH,
					startCalendar.get(Calendar.MONTH) + 1);
			i++;
		}
		return i;
	}

	/**
	 * 根据原来的时间（Date）获得相对偏移 N 天的时间（Date）
	 * 
	 * 
	 * @param protoDate
	 *            原来的时间（java.util.Date）
	 * 
	 * 
	 * @param dateOffset
	 *            （向前移正数，向后移负数）
	 * 
	 * @param type
	 *            指定不同的格式（1：年月日，2：年月日时，3：年月日时分）
	 * 
	 * @return 时间（java.util.Date），没有时分秒
	 */
	public static Date getOffsetSimpleDate(Date protoDate, int dateOffset,
			int type) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(protoDate);
		cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH)
				- dateOffset);
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
	 * 
	 * 格式为：yyyyMMddHHmmssSSS
	 * 
	 * @return
	 */
	public static String getDateToString() {
		SimpleDateFormat dateFormat = new SimpleDateFormat(DateFormatConstants.TIMESSS_STR_FORMAT);
		Date date = new Date();
		String str = dateFormat.format(date);
		return str;
	}

	/**
	 * 时间转为化为字符串
	 * 
	 * 格式为：yyyy-MM-dd HH:mm:ss
	 * 
	 * @return
	 */
	public static String getTodayTimeString() {
		SimpleDateFormat dateFormat = new SimpleDateFormat(DateFormatConstants.TIMEF_FORMAT);
		Date date = new Date();
		String str = dateFormat.format(date);
		return str;
	}

	/**
	 * 增加一天
	 * 
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
	 * 增加一天
	 * 
	 * @param s
	 * @param n
	 * @return
	 */
	public static String addDay(Date s, int n) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Calendar cd = Calendar.getInstance();
			cd.setTime(s);
			cd.add(Calendar.DATE, n);// 增加一天
			// cd.add(Calendar.MONTH, n);//增加一个月
			return sdf.format(cd.getTime());
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 增加一个月
	 * 
	 * @param s
	 * @param n
	 * @return
	 */
	public static String addMonth(Date m, int n) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Calendar cd = Calendar.getInstance();
			cd.setTime(m);
			cd.add(Calendar.MONTH, n);// 增加一个月
			return sdf.format(cd.getTime());
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 增加一个月
	 * 
	 * @param s
	 * @param n
	 * @return
	 */
	public static String addMonth(Date m, int n, String formatstring) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(formatstring);
			Calendar cd = Calendar.getInstance();
			cd.setTime(m);
			cd.add(Calendar.MONTH, n);// 增加一个月
			return sdf.format(cd.getTime());
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 获取这个月第一天
	 * 
	 * @return
	 */
	public static Date getFirstDayOfMonth() {
		Calendar ca = Calendar.getInstance();
		ca.set(Calendar.DAY_OF_MONTH, 1);
		Date firstDate = ca.getTime();
		ca.add(Calendar.MONTH, 1);
		ca.add(Calendar.DAY_OF_MONTH, -1);
		return firstDate;
	}

	/**
	 * 获这个月的最后一天
	 * 
	 * @return
	 */
	public static Date getLastDayOfMonth() {
		Calendar ca = Calendar.getInstance();
		ca.set(Calendar.DAY_OF_MONTH, 1);
		ca.add(Calendar.MONTH, 1);
		ca.add(Calendar.DAY_OF_MONTH, -1);
		Date lastDate = ca.getTime();
		return lastDate;
	}

	/**
	 * 获查询日期区间 今天(0), 近一周(1), 本月(2),近一月(3) ;
	 * 
	 * @return Date[0] 开始时间 Date[1] 结束时间
	 */
	public static Date[] getDateSection(int dateType) {
		Date[] dateSection = new Date[2];
		if (DateConstants.TODAY.value == dateType) {
			dateSection[0] = getTodayDate();
			dateSection[1] = dateSection[0];
		} else if (DateConstants.NEARLYWEEK.value == dateType) {
			dateSection[0] = getOffsetDayDate(getTodayDate(), 7);
			dateSection[1] = getTodayDate();
		} else if (DateConstants.NEARLYMONTH.value == dateType) {
			dateSection[0] = getOffsetMonthDate(getTodayDate(), 1);
			dateSection[1] = getTodayDate();
		} else if (DateConstants.MONTH.value == dateType) {
			dateSection[0] = getFirstDayOfMonth();
			dateSection[1] = getLastDayOfMonth();
		} else {
			dateSection = null;
		}
		return dateSection;
	};
}

/*     */ package org.sword.lang;
/*     */ 
/*     */ import java.text.ParseException;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Date;
/*     */ 
/*     */ public class DateTimeUtil
/*     */ {
/*     */   public static final String DEFAULT_FORMAT_DATE = "yyyy-MM-dd";
/*     */   public static final String DEFAULT_FORMAT_TIME = "HH:mm:ss";
/*     */   public static final String DEFAULT_FORMAT_DATETIME = "yyyy-MM-dd HH:mm:ss";
/*     */ 
/*     */   public static Date getDateTime(String dateString, String format)
/*     */   {
/*  26 */     SimpleDateFormat sf = new SimpleDateFormat(format);
/*  27 */     Date date = null;
/*     */     try {
/*  29 */       date = sf.parse(dateString);
/*     */     } catch (ParseException e) {
/*  31 */       e.printStackTrace();
/*     */     }
/*  33 */     return date;
/*     */   }
/*     */ 
/*     */   public static Date getDateTime(String dateTimeString)
/*     */   {
/*  42 */     return getDateTime(dateTimeString, "yyyy-MM-dd HH:mm:ss");
/*     */   }
/*     */ 
/*     */   public static Date getDate(String date)
/*     */   {
/*  51 */     return getDateTime(date, "yyyy-MM-dd");
/*     */   }
/*     */ 
/*     */   public static Date getTime(String time)
/*     */   {
/*  60 */     return getDateTime(time, "HH:mm:ss");
/*     */   }
/*     */ 
/*     */   public static String toDateString(Date date, String format)
/*     */   {
/*  71 */     SimpleDateFormat sf = new SimpleDateFormat(format);
/*  72 */     return sf.format(date);
/*     */   }
/*     */ 
/*     */   public static String toDateTimeStr(Date date)
/*     */   {
/*  81 */     return toDateString(date, "yyyy-MM-dd HH:mm:ss");
/*     */   }
/*     */ 
/*     */   public static String toDateStr(Date date)
/*     */   {
/*  90 */     return toDateString(date, "yyyy-MM-dd");
/*     */   }
/*     */ 
/*     */   public static String toTimeStr(Date date)
/*     */   {
/*  99 */     return toDateString(date, "HH:mm:ss");
/*     */   }
/*     */ 
/*     */   public static String todayStr()
/*     */   {
/* 107 */     return currentDateStr();
/*     */   }
/*     */ 
/*     */   public static Date today()
/*     */   {
/* 115 */     return current();
/*     */   }
/*     */ 
/*     */   public static Date getToday(String time)
/*     */   {
/* 124 */     String today = todayStr();
/* 125 */     return getDateTime(today + " " + time, "yyyy-MM-dd HH:mm:ss");
/*     */   }
/*     */ 
/*     */   public static Date now()
/*     */   {
/* 133 */     return current();
/*     */   }
/*     */ 
/*     */   public static String nowStr()
/*     */   {
/* 141 */     return currentStr();
/*     */   }
/*     */ 
/*     */   public static String currentTimeStr()
/*     */   {
/* 149 */     return toDateString(new Date(), "HH:mm:ss");
/*     */   }
/*     */ 
/*     */   public static String currentDateStr()
/*     */   {
/* 157 */     return toDateString(new Date(), "yyyy-MM-dd");
/*     */   }
/*     */ 
/*     */   public static String currentStr()
/*     */   {
/* 165 */     return toDateString(new Date(), "yyyy-MM-dd HH:mm:ss");
/*     */   }
/*     */ 
/*     */   public static Date current()
/*     */   {
/* 173 */     return new Date();
/*     */   }
/*     */ 
/*     */   public static String format(String text, Date date)
/*     */   {
/* 183 */     int start = text.indexOf("{");
/* 184 */     int end = text.indexOf("}");
/* 185 */     while ((start > 0) && (end > 0)) {
/* 186 */       String subStr = text.substring(start, end + 1);
/* 187 */       String format = text.substring(start + 1, end);
/* 188 */       String dateStr = toDateString(date, format);
/* 189 */       text = text.replace(subStr, dateStr);
/*     */ 
/* 191 */       start = text.indexOf("{");
/* 192 */       end = text.indexOf("}");
/*     */     }
/* 194 */     return text;
/*     */   }
/*     */ 
/*     */   public static boolean isDate(String dateString)
/*     */   {
/* 203 */     return tryParse(dateString);
/*     */   }
/*     */ 
/*     */   public static boolean tryParse(String dateString)
/*     */   {
/* 212 */     Date date = getDateTime(dateString);
/* 213 */     return (date != null);
/*     */   }
/*     */ }

/* Location:           E:\wechat_code\wechat4jDemo-master\WebContent\WEB-INF\lib\sword-lang-2.0.0.jar
 * Qualified Name:     org.sword.lang.DateTimeUtil
 * JD-Core Version:    0.5.2
 */
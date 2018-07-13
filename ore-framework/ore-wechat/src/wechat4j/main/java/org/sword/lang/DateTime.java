/*    */ package org.sword.lang;
/*    */ 
/*    */ import java.util.Calendar;
/*    */ import java.util.Date;
/*    */ 
/*    */ public class DateTime extends Date
/*    */ {
/* 16 */   public static final DateTime MAX_DATE_TIME = new DateTime(9999, 12, 31, 23, 59, 59);
/* 17 */   public static final DateTime MIN_DATE_TIME = new DateTime(1, 1, 1, 0, 0, 0);
/*    */   private static final long serialVersionUID = 1L;
/*    */   private Date date;
/*    */   private Calendar calendar;
/*    */ 
/*    */   public DateTime(long ticks)
/*    */   {
/* 24 */     this.date = new Date(ticks);
/*    */   }
/*    */ 
/*    */   public DateTime(int year, int month, int day) {
/* 28 */     this.calendar.set(year, month, day);
/* 29 */     this.date = this.calendar.getTime();
/*    */   }
/*    */ 
/*    */   public DateTime(int year, int month, int day, int hour, int minute, int second) {
/* 33 */     this.calendar.set(year, month, day, hour, minute, second);
/* 34 */     this.date = this.calendar.getTime();
/*    */   }
/*    */ 
/*    */   public DateTime(String dateString) {
/* 38 */     this.date = DateTimeUtil.getDate(dateString);
/*    */   }
/*    */ 
/*    */   public String toString() {
/* 42 */     return DateTimeUtil.toDateTimeStr(this.date);
/*    */   }
/*    */ 
/*    */   public String toString(String format) {
/* 46 */     return DateTimeUtil.toDateString(this.date, format);
/*    */   }
/*    */ 
/*    */   public Date toDate() {
/* 50 */     return this.date;
/*    */   }
/*    */ }

/* Location:           E:\wechat_code\wechat4jDemo-master\WebContent\WEB-INF\lib\sword-lang-2.0.0.jar
 * Qualified Name:     org.sword.lang.DateTime
 * JD-Core Version:    0.5.2
 */
/*    */ package org.sword.lang;
/*    */ 
/*    */ import java.io.ByteArrayInputStream;
/*    */ import java.io.ByteArrayOutputStream;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ 
/*    */ public class StreamUtils
/*    */ {
/*    */   public static String streamToString(InputStream is)
/*    */   {
/* 23 */     ByteArrayOutputStream baos = new ByteArrayOutputStream();
/* 24 */     int i = -1;
/*    */     try {
/* 26 */       while ((i = is.read()) != -1)
/* 27 */         baos.write(i);
/*    */     }
/*    */     catch (IOException e) {
/* 30 */       e.printStackTrace();
/*    */     }
/* 32 */     return baos.toString();
/*    */   }
/*    */ 
/*    */   public static InputStream strToStream(String str)
/*    */   {
/* 41 */     InputStream is = new ByteArrayInputStream(str.getBytes());
/* 42 */     return is;
/*    */   }
/*    */ }

/* Location:           E:\wechat_code\wechat4jDemo-master\WebContent\WEB-INF\lib\sword-lang-2.0.0.jar
 * Qualified Name:     org.sword.lang.StreamUtils
 * JD-Core Version:    0.5.2
 */
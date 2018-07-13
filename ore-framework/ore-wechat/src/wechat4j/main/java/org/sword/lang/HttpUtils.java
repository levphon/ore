/*     */ package org.sword.lang;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.InputStream;
/*     */ import org.apache.http.Consts;
/*     */ import org.apache.http.HttpEntity;
/*     */ import org.apache.http.HttpResponse;
/*     */ import org.apache.http.client.fluent.Request;
/*     */ import org.apache.http.client.fluent.Response;
/*     */ import org.apache.http.entity.ContentType;
/*     */ import org.apache.http.entity.mime.MultipartEntityBuilder;
/*     */ import org.apache.http.util.EntityUtils;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public class HttpUtils
/*     */ {
/*  26 */   private static Logger logger = Logger.getLogger(HttpUtils.class);
/*     */   public static final int timeout = 10;
/*     */ 
/*     */   public static String post(String url)
/*     */   {
/*  37 */     return post(url, "");
/*     */   }
/*     */ 
/*     */   public static String post(String url, String data)
/*     */   {
/*  47 */     return httpPost(url, data);
/*     */   }
/*     */ 
/*     */   public static String post(String url, InputStream instream)
/*     */   {
/*     */     try
/*     */     {
/*  58 */       HttpEntity entity = Request.Post(url)
/*  59 */         .bodyStream(instream, ContentType.create("text/html", Consts.UTF_8))
/*  60 */         .execute().returnResponse().getEntity();
/*  61 */       return ((entity != null) ? EntityUtils.toString(entity) : null);
/*     */     } catch (Exception e) {
/*  63 */       logger.error("post请求异常，" + e.getMessage() + "\n post url:" + url);
/*  64 */       e.printStackTrace();
/*     */     }
/*  66 */     return null;
/*     */   }
/*     */ 
/*     */   public static String get(String url)
/*     */   {
/*  75 */     return httpGet(url);
/*     */   }
/*     */ 
/*     */   private static String httpPost(String url, String data)
/*     */   {
/*     */     try
/*     */     {
/*  87 */       HttpEntity entity = Request.Post(url)
/*  88 */         .bodyString(data, ContentType.create("text/html", Consts.UTF_8))
/*  89 */         .execute().returnResponse().getEntity();
/*  90 */       return ((entity != null) ? EntityUtils.toString(entity) : null);
/*     */     } catch (Exception e) {
/*  92 */       logger.error("post请求异常，" + e.getMessage() + "\n post url:" + url);
/*  93 */       e.printStackTrace();
/*     */     }
/*  95 */     return null;
/*     */   }
/*     */ 
/*     */   public static String postFile(String url, File file)
/*     */   {
/* 105 */     return postFile(url, null, file);
/*     */   }
/*     */ 
/*     */   public static String postFile(String url, String name, File file)
/*     */   {
/*     */     try
/*     */     {
/* 117 */       HttpEntity reqEntity = MultipartEntityBuilder.create().addBinaryBody(name, file).build();
/* 118 */       Request request = Request.Post(url);
/* 119 */       request.body(reqEntity);
/* 120 */       HttpEntity resEntity = request.execute().returnResponse().getEntity();
/* 121 */       return ((resEntity != null) ? EntityUtils.toString(resEntity) : null);
/*     */     } catch (Exception e) {
/* 123 */       logger.error("postFile请求异常，" + e.getMessage() + "\n post url:" + url);
/* 124 */       e.printStackTrace();
/*     */     }
/* 126 */     return null;
/*     */   }
/*     */ 
/*     */   public static byte[] getFile(String url)
/*     */   {
/*     */     try
/*     */     {
/* 137 */       Request request = Request.Get(url);
/* 138 */       HttpEntity resEntity = request.execute().returnResponse().getEntity();
/* 139 */       return EntityUtils.toByteArray(resEntity);
/*     */     } catch (Exception e) {
/* 141 */       logger.error("postFile请求异常，" + e.getMessage() + "\n post url:" + url);
/* 142 */       e.printStackTrace();
/*     */     }
/* 144 */     return null;
/*     */   }
/*     */ 
/*     */   private static String httpGet(String url)
/*     */   {
/*     */     try
/*     */     {
/* 155 */       HttpEntity entity = Request.Get(url)
/* 156 */         .execute().returnResponse().getEntity();
/* 157 */       return ((entity != null) ? EntityUtils.toString(entity) : null);
/*     */     } catch (Exception e) {
/* 159 */       logger.error("get请求异常，" + e.getMessage() + "\n get url:" + url);
/* 160 */       e.printStackTrace();
/*     */     }
/* 162 */     return null;
/*     */   }
/*     */ }

/* Location:           E:\wechat_code\wechat4jDemo-master\WebContent\WEB-INF\lib\sword-lang-2.0.0.jar
 * Qualified Name:     org.sword.lang.HttpUtils
 * JD-Core Version:    0.5.2
 */
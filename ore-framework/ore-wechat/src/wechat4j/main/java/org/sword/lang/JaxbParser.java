/*     */ package org.sword.lang;
/*     */ 
/*     */ import com.sun.org.apache.xml.internal.serialize.OutputFormat;
/*     */ import com.sun.org.apache.xml.internal.serialize.XMLSerializer;
/*     */ import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.io.StringWriter;
/*     */ import javax.xml.bind.JAXBContext;
/*     */ import javax.xml.bind.Marshaller;
/*     */ import javax.xml.bind.Unmarshaller;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public class JaxbParser
/*     */ {
/*  28 */   private static Logger logger = Logger.getLogger(JaxbParser.class);
/*     */   private Class clazz;
/*     */   private String[] cdataNode;
/*     */ 
/*     */   public JaxbParser(Class clazz)
/*     */   {
/*  38 */     this.clazz = clazz;
/*     */   }
/*     */ 
/*     */   public void setCdataNode(String[] cdataNode)
/*     */   {
/*  46 */     this.cdataNode = cdataNode;
/*     */   }
/*     */ 
/*     */   public String toXML(Object obj)
/*     */   {
/*  55 */     String result = null;
/*     */     try {
/*  57 */       JAXBContext context = JAXBContext.newInstance(new Class[] { obj.getClass() });
/*  58 */       Marshaller m = context.createMarshaller();
/*  59 */       m.setProperty("jaxb.encoding", "UTF-8");
/*  60 */       m.setProperty("jaxb.formatted.output", Boolean.valueOf(true));
/*  61 */       m.setProperty("jaxb.fragment", Boolean.valueOf(true));
/*  62 */       OutputStream os = new ByteOutputStream();
/*  63 */       StringWriter writer = new StringWriter();
/*  64 */       XMLSerializer serializer = getXMLSerializer(os);
/*  65 */       m.marshal(obj, serializer.asContentHandler());
/*  66 */       result = os.toString();
/*     */     } catch (Exception e) {
/*  68 */       e.printStackTrace();
/*     */     }
/*  70 */     logger.info("response text:" + result);
/*  71 */     return result;
/*     */   }
/*     */ 
/*     */   public Object toObj(InputStream is)
/*     */   {
/*     */     try
/*     */     {
/*  83 */       JAXBContext context = JAXBContext.newInstance(new Class[] { this.clazz });
/*  84 */       Unmarshaller um = context.createUnmarshaller();
/*  85 */       Object obj = um.unmarshal(is);
/*  86 */       return obj;
/*     */     } catch (Exception e) {
/*  88 */       logger.error("post data parse error");
/*  89 */       e.printStackTrace();
/*     */     }
/*  91 */     return null;
/*     */   }
/*     */ 
/*     */   public Object toObj(String xmlStr)
/*     */   {
/* 100 */     InputStream is = new ByteArrayInputStream(xmlStr.getBytes());
/* 101 */     return toObj(is);
/*     */   }
/*     */ 
/*     */   private XMLSerializer getXMLSerializer(OutputStream os)
/*     */   {
/* 110 */     OutputFormat of = new OutputFormat();
/* 111 */     formatCDataTag();
/* 112 */     of.setCDataElements(this.cdataNode);
/* 113 */     of.setPreserveSpace(true);
/* 114 */     of.setIndenting(true);
/* 115 */     of.setOmitXMLDeclaration(true);
/* 116 */     XMLSerializer serializer = new XMLSerializer(of);
/* 117 */     serializer.setOutputByteStream(os);
/* 118 */     return serializer;
/*     */   }
/*     */ 
/*     */   private void formatCDataTag()
/*     */   {
/* 125 */     for (int i = 0; i < this.cdataNode.length; ++i)
/* 126 */       this.cdataNode[i] = "^" + this.cdataNode[i];
/*     */   }
/*     */ }

/* Location:           E:\wechat_code\wechat4jDemo-master\WebContent\WEB-INF\lib\sword-lang-2.0.0.jar
 * Qualified Name:     org.sword.lang.JaxbParser
 * JD-Core Version:    0.5.2
 */
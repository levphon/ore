package com.hy.commons.signature; 

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import com.hy.commons.encrypt.Md5Encrypt;

/**
 * Description:
 * 通过MD5及密钥对字符串进行加密
 * @author  LiChunming
 * @version V1.0 
 * @createDateTime：2013-1-11 上午11:25:59 
 * @Company: MSD. 
 * @Copyright: Copyright (c) 2011
 **/
public class Md5SignUtils {
	/** 
     * 功能：生成签名结果
     * @param sArray 要加密的数组
     * @param key 安全校验码
     * @return 签名结果字符串
     */
    public static String buildObjectSign(Object object, String key) {
    	//把对象转为Hash
    	HashMap sArray= ObjecttoHashMap(object);
    	String mysign=buildMashSign(sArray,key);
        return mysign;
    }
	/** 
     * 功能：生成签名结果
     * @param sArray 要加密的数组
     * @param key 安全校验码
     * @return 签名结果字符串
     */
    public static String buildMashSign(Map<String,Object> sArray, String key) {
    	//把key当作参数
    	sArray.put("key", key);
    	//把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串
        String prestr = createLinkString(sArray);  
        String mysign = Md5Encrypt.md5(prestr).toUpperCase();
        return mysign;
    }
    /**
	 * 对字符串进行MD5加密
	 * @param prestr 待加密的字符串
	 * @param aKey 密钥
	 * @return
	 */
	public static String buildStringSign(String prestr, String aKey) {
		  String mysign = Md5Encrypt.md5(prestr).toUpperCase();
	      return mysign;
	}
	/**
	 * 对字符数组进行MD5加密
	 * @param args 字符数组
	 * @param key 密钥
	 * @return
	 */
	public static String buildStringSign(String[] args, String key) {
		if (args == null || args.length == 0) {
			return (null);
		}
		StringBuffer str = new StringBuffer();
		for (int i = 0; i < args.length; i++) {
			str.append(args[i]);
		}
		return (buildStringSign(str.toString(), key));
	}
	/**
	 * 对象转为HashMap
	 * @param object
	 * @return
	 */
	 public static HashMap ObjecttoHashMap(Object object) {
		  HashMap<String, Object> data = new HashMap<String, Object>();
		  JSONObject jsonObject = JSONObject.fromObject(object);
		  Iterator it = jsonObject.keys();
		  while (it.hasNext()) {
		   String key = String.valueOf(it.next());
		   Object value = jsonObject.get(key);
		   data.put(key, value);
		  }
		  return data;
	}
	 /** 
     * 功能：把数组所有元素排序，并按照"参数=参数值"的模式用"&"字符拼接成字符串
     * @param params 需要排序并参与字符拼接的参数组
     * @return 拼接后字符串
     */
    public static String createLinkString(Map<String,Object> params){
        List<String> keys = new ArrayList<String>(params.keySet());
        //对参数字母进行排序
        Collections.sort(keys);
        String prestr = "";
        for (int i = 0; i < keys.size(); i++) {
            String key = (String) keys.get(i);
            String value = params.get(key).toString();
            if (i == keys.size() - 1) {//拼接时，不包括最后一个&字符
                prestr = prestr + key + "=" + value;
            } else {
                prestr = prestr + key + "=" + value + "&";
            }
        }
        return prestr;
    }
    /** 
     * 功能：把数组所有元素按照"参数=参数值"的模式用"&"字符拼接成字符串
     * 应用场景：使用场景：GET方式请求时，对URL的中文进行编码
     * @param params 需要排序并参与字符拼接的参数组
     * @param input_charset 编码格式
     * @return 拼接后字符串
     */
    public static String createLinkString_urlencode(Map<String,String> params, String input_charset){
        List<String> keys = new ArrayList<String>(params.keySet());
        //对参数字母进行排序
        Collections.sort(keys);
        String prestr = "";
        for (int i = 0; i < keys.size(); i++) {
            String key = (String) keys.get(i);
            String value = (String) params.get(key);
            try {
                prestr = prestr + key + "=" + URLEncoder.encode(value,input_charset) + "&";
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return prestr;
    }
}
 
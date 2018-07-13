package com.hy.commons.signature; 

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

/**
 * Description:
 * @author  LiChunming
 * @version V1.0 
 * @createDateTime：2013-1-11 下午02:56:09 
 * @Company: MSD. 
 * @Copyright: Copyright (c) 2011
 **/
public class HashSignUtils {
private static String encodingCharset = "UTF-8";
	/**
	 * 对对象进行Hash加密
	 * @param object 待加密的对象
	 * @param aKey 密钥
	 * @return
	 */
	public static String buildObjectSign(Object object, String aKey){
		Map<String,Object> sArray=ObjecttoHashMap(object);
		String sign=buildMapSign(sArray, aKey);
		return sign;
	}
	/**
	 * 对Map进行Hash加密
	 * @param sArray 待加密的Map对象
	 * @param aKey 密钥
	 * @return
	 */
	public static String buildMapSign(Map<String,Object> sArray, String aKey){
		//把key当作参数
    	sArray.put("key", aKey);
    	//把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串
		String str=createLinkString(sArray);
		String sign=buildStringSign(str,aKey);
		return sign;
	}
	/**
	 * 对字符数组进行Hash加密
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
	 * 对字符串进行Hash加密
	 * @param aValue 待加密的字符串
	 * @param aKey 密钥
	 * @return
	 */
	public static String buildStringSign(String aValue, String aKey) {
		byte k_ipad[] = new byte[64];
		byte k_opad[] = new byte[64];
		byte keyb[];
		byte value[];
		try {
			keyb = aKey.getBytes(encodingCharset);
			value = aValue.getBytes(encodingCharset);
		} catch (UnsupportedEncodingException e) {
			keyb = aKey.getBytes();
			value = aValue.getBytes();
		}

		Arrays.fill(k_ipad, keyb.length, 64, (byte) 54);
		Arrays.fill(k_opad, keyb.length, 64, (byte) 92);
		for (int i = 0; i < keyb.length; i++) {
			k_ipad[i] = (byte) (keyb[i] ^ 0x36);
			k_opad[i] = (byte) (keyb[i] ^ 0x5c);
		}

		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("SHA1");
		} catch (NoSuchAlgorithmException e) {

			return null;
		}
		md.update(k_ipad);
		md.update(value);
		byte dg[] = md.digest();
		md.reset();
		md.update(k_opad);
		md.update(dg, 0, 16);
		dg = md.digest();
		return toHex(dg).toUpperCase();
	}
	public static String toHex(byte input[]) {
		if (input == null)
			return null;
		StringBuffer output = new StringBuffer(input.length * 2);
		for (int i = 0; i < input.length; i++) {
			int current = input[i] & 0xff;
			if (current < 16)
				output.append("0");
			output.append(Integer.toString(current, 16));
		}
		return output.toString();
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
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		  String value="UnsupportedEncodingException2222222222222222222222";
		  String key="69cl522AV6q613Ii4W6u8K6XuW8vM1N6bFgyv769220IuYe9u37N4y7rI4Pl";
		  String str= buildStringSign(value,key);
		  System.out.println(str);
	}

}
 
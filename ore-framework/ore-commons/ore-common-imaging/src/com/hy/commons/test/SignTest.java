package com.hy.commons.test; 

import java.util.HashMap;
import java.util.Map;

import com.hy.commons.signature.HashSignUtils;
import com.hy.commons.signature.Md5SignUtils;

/**
 * Description:
 * @author  LiChunming
 * @version V1.0 
 * @Company: MSD. 
 * @Copyright: Copyright (c) 2011
 **/
public class SignTest {
	public static String  key="69CL522AV6Q613II4W6U8K6XUW8VM1N6BFGYV769220IUYE9U37N4Y7RI4PL";
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		 mapSign();
		 objectSign();
	}
	public static String mapSign(){
		Map<String,Object> sArray=new HashMap<String,Object>();
		sArray.put("id", 100);
		sArray.put("name", "sunney");
		sArray.put("age", 18);
		sArray.put("mobile", "15989569150");
		sArray.put("addr", "");
		String ss1=Md5SignUtils.buildMashSign(sArray, key);
		String ss2=HashSignUtils.buildMapSign(sArray, key);
		System.out.println("Md5SignUtils:"+ss1);
		System.out.println("HashSignUtils:"+ss2);
		return ss1;
	}
	public static String objectSign(){
		SignTestDO sign=new SignTestDO();
		sign.setId(100l);
		sign.setMobile("15989569150");
		sign.setName("sunney");
		sign.setAge(18);
		sign.setAddr("");
		//String ss=Md5SignUtils.buildObjectSign(sign, key);
		String ss=HashSignUtils.buildObjectSign(sign, key);
		System.out.println("objectSign:"+ss);
		return ss;
		
	}

}
 
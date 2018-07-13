package com.hy.commons.test;


/** 
 * Description:
 * @author:Sunney
 * @version:V2.0  
 * @Company:MSD
 * @Copyright:Copyright(c) 2013
 */
public class MainTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
//        String ss=Md5Encrypt.md5("String");
//        System.out.println(ss);
//        //String ss1=Md5Encrypt.twiceMD5("String");
//        //System.out.println(ss1);
		int counts=41;
		int perZise=20;
		int pageindex=(counts/perZise)+ (counts % perZise>0?1:0);
		System.out.println(pageindex);
	}

}

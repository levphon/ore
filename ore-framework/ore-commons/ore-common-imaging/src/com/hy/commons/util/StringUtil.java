/**
 * 
 */
package com.hy.commons.util;

import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Title: StringUtil.java
 * @Description: String工具类
 * @author Nassir.wen
 * @date 2010-10-13 上午09:43:03
 * @version V1.0
 * @Company: MSD.
 * @Copyright Copyright (c) 2010
 */
public class StringUtil {
	/**
	 * 把字符串null对象附值为""
	 * 
	 * @param text
	 * @return
	 */
	public static String formatString(String text) {
		if (text == null) {
			return "";
		}
		return text;
	}

	/**
	 * 字符串是否有效
	 * 
	 * @param str
	 * @return 有效返回true
	 */
	public static boolean isValidString(String str) {
		if (str == null || str.trim().length() <= 0) {
			return false;
		}
		return true;
	}

	/**
	 * 整型是否有效
	 * 
	 * @param i
	 * @return 有效返回true
	 */
	public static boolean isValidInt(Integer i) {
		if (i == null || "null".equals(String.valueOf(i))) {
			return false;
		}
		return true;
	}

	/**
	 * 浮点型是否有效
	 * 
	 * @param i
	 * @return 有效返回true
	 */
	public static boolean isValidLong(Long i) {
		if (i == null || "null".equals(String.valueOf(i))) {
			return false;
		}
		return true;
	}

	/**
	 * 字节转字符串
	 * 
	 * @param buf
	 * @param charset
	 *            编码
	 * @return
	 */
	public static String getStringByCharset(byte[] buf, String charset) {
		String str = null;
		try {
			str = new String(buf, charset);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return str;
	}

	/**
	 * 过滤所有特殊字符
	 * 
	 * @param str
	 * @return
	 */
	public static String filterSpecicalChar(String str) {
		if (!isValidString(str)) {
			return str;
		}
		// 待写
		return str;
	}

	public static String filterSqlString(String str) {
		if (!isValidString(str)) {
			return str;
		}
		if (str.indexOf("'") >= 0) {
			str = str.replaceAll("'", "''");
		}
		return str;
	}

	/**
	 * 删除字符串中的html标签
	 * 
	 * @param html
	 * @return
	 */
	public static String filterHtmlTag(String str) {
		if (!isValidString(str)) {
			return str;
		}
		// 去掉所有html元素,
		String rstr = str.replaceAll("\\&[a-zA-Z]{1,10};", "").replaceAll(
				"<[^>]*>", "");
		rstr = rstr.replaceAll("[(/>)<]", "");
		return rstr;
	}

	/**
	 * 删除所有的空白字符
	 * 
	 * @param str
	 * @return
	 */
	public static String filterBlank(String str) {
		if (!isValidString(str)) {
			return str;
		}
		str = str.replaceAll("&nbsp;", "");
		Pattern p = Pattern.compile("\\s+");//
		Matcher m = p.matcher(str);
		str = m.replaceAll("");
		return str;
	}

	/**
	 * 判断是否为数字
	 * 
	 * @param str
	 * @return 是返回true
	 */
	public static boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern.matcher(str);
		if (!isNum.matches()) {
			return false;
		}
		return true;
	}

	/**
	 * 判断两个对象是否相等,针对基本数据类型/基本数据封装类型
	 * 
	 * @param v1
	 * @param v2
	 * @return
	 */
	public static boolean isEq(Object v1, Object v2) {
		if (v1 == v2) {
			// System.out.println("v1(" + v1 + " == " + v2 + ")v2");
			return true;
		}
		if (String.valueOf(v1).equals(String.valueOf(v2))) {
			// System.out.println("v1.toString()(" + v1 + " eq " + v2 +
			// ")v2.toString()");
			return true;
		}
		return false;
	}

	public static boolean isEmpty(String str) {
		return ((str == null) || (str.trim().length() == 0));
	}

	public static void main(String[] args) {
		// 测试
		Object obj = "";
		System.out.println(obj == null);
		System.out.println(String.valueOf(obj).equals(null));
		System.out.println(String.valueOf(obj).equals("null"));
		System.out.println(String.valueOf(obj).equals(""));
		int v1 = 1;
		int v2 = 1;
		System.out.println(isEq(v1, v2));
		final Integer v3 = 1;
		Integer v4 = 1;
		System.out.println(isEq(v3, v4));
		// System.out.println(isEq(v1, v4));
		System.out.println(StringUtil.isValidString(""));
		System.out.println("-------------------");
		String a = " aa    aa, 5";
		System.out.println(a);
		System.out.println(filterBlank(a));
		System.out.println(filterSpecicalChar(a));
	}
}

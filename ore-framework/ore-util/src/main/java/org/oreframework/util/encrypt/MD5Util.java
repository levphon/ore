package org.oreframework.util.encrypt;

import org.apache.commons.lang.StringUtils;
import org.oreframework.util.encrypt.Md5Encrypt;

/**
 * MD5工具类
 * 
 * @Description: MD5工具类
 * @date 2013年10月17日 上午10:47:21 Copyright: Copyright (c) 2013 Company: GLSX.
 * @version V1.0
 */
public class MD5Util {
	public static final String md5(String text) {
		if (StringUtils.isBlank(text)) {
			return null;
		} else {
			return Md5Encrypt.md5(text);
		}
	}
	
	public static void main(String[] args) {
		System.out.println(MD5Util.md5(MD5Util.md5("888888")));
	}
}

package org.oreframework.util.path;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;

/** 
 * classpath工具类
 * 
 * @author  huangzz
 * @version  [1.0.0, Mar 22, 2015]
 */
public class ClassPath {

	public static String getRealPath(String filename) {
		Class<ClassPath> theClass = ClassPath.class;
		java.net.URL u = theClass.getResource(filename);
		return u.getPath();

	}

	/**
	 * 获得配置文件的当前物理路径
	 * 
	 * @param file
	 * @return 文件相对路径
	 * 
	 * @return String 文件的当前物理路径
	 */
	public static String getFilePath(String file) {
		URL cfgURL = ClassPath.class.getClassLoader().getResource(file);
		String cfgpath = cfgURL.getFile();
		try {
			cfgpath = URLDecoder.decode(cfgpath, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return cfgpath;
	}

	public static void main(String[] args) {
		// 打印.java的路径
		System.out.println(getRealPath("."));
		// 打印.class的路径
		System.out.println(getRealPath("/"));
		String path = getRealPath("/");
		File file = new File(path.substring(0, path.indexOf("WEB-INF")));
		System.out.println(file.getPath());
	}
}

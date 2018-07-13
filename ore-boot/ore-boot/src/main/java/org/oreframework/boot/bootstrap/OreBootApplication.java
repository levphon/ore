package org.oreframework.boot.bootstrap;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.apache.commons.lang.ClassUtils;
import org.oreframework.boot.env.EnvConfigurer;
import org.oreframework.boot.main.AbsOreBootStarter;
import org.oreframework.commons.beanutils.properties.PropertiesUtil;
import org.oreframework.util.jar.SearchClass;
import org.oreframework.util.path.ResourceUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 脚步启动引导类
 * @author huangzz
 * 2017年3月18日
 */
public class OreBootApplication {

	private final static Logger logger = LoggerFactory.getLogger(OreBootApplication.class);

	private final static String START_CLASSNAMEKEY = "ore.starter.class-name";

	private static void start(String[] args) {

		String starterClass = null;

		try {
			InputStream is = ResourceUtils.getResourceAsStream(OreBootApplication.class.getClassLoader(),
					"META-INF/starter.properties");

			URL url = OreBootApplication.class.getResource("/config/application.properties");
			if (null == url) {
				logger.info("****[ /config/application.properties ]**** file not existence! Server stop!");
			} else {
				String fileName = url.getPath();
				PropertiesUtil propertiesUtil = new PropertiesUtil(fileName);
				starterClass = propertiesUtil.getProperty(START_CLASSNAMEKEY);
			}

			PropertiesUtil propertiesUtil = new PropertiesUtil(is);
			if (null == starterClass) {
				starterClass = propertiesUtil.getProperty(START_CLASSNAMEKEY);
			}
			String path = OreBootApplication.class.getResource("/").getPath();
			logger.error("****[ " + path + " ]**** search path!");

			String className = SearchClass.searchPackage(starterClass, path);
			if (null == className) {
				logger.error("****[ " + starterClass + " ]**** class is not existence! Server stop!");
				System.exit(0);
			}

			AbsOreBootStarter bootStarter = (AbsOreBootStarter) ClassUtils.getClass(className).newInstance();
			bootStarter.run(args);
		} catch (InstantiationException e) {
			logger.error(e.toString());
		} catch (IllegalAccessException e) {
			logger.error(e.toString());
		} catch (ClassNotFoundException e) {
			logger.error(e.toString());
		} catch (LinkageError e) {
			logger.error(e.toString());
		} catch (IOException e) {
			logger.error("****[ /META-INF/starter.properties ]**** file not existence! Server stop!");
			System.exit(0);
		}
	}

	public static void main(String[] args) {
		for (int i = 0; i < args.length; i++) {
			String envArgs = args[i];
			if (envArgs.indexOf("env=") > -1) {
				String[] envs = envArgs.split("=");
				EnvConfigurer.env = envs[1];
				logger.warn("配置项：env=" + EnvConfigurer.env);
			}
		}
		start(args);
	}

}

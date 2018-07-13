package org.oreframework.commons.db.dbutil.db;

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;



/**
 * ConnectionFactory provide a factory class that produces all database
 * connections from here, and it provides methods for shutdown and restart data
 * sources as well as reading and saving configuration parameters from/to file.
 * 
 * 数据库连接工厂类, 所有的数据库连接都从这里产生, 提供关闭和重启数据源的方法, 以及读取和保存 配置参数到文件的能力.
 * 
 * 2005-08-19 Using Apache DBCP as database connection pool provider. 使用 Apache
 * DBCP 作为连接池的提供类.
 * 
 * @link http://jakarta.apache.org/commons/dbcp/
 * 
 * Dependency: commons-collections.jar commons-pool.jar commons-dbcp.jar
 * j2ee.jar (for the javax.sql classes)
 * 
 * If you using this class with Tomcat's web application, then all the above
 * jars not need to be added because the Tomcat it self has included these class
 * libs. 如果你在 Tomcat 的 Web Application 中调用这个类, 以上的 JAR 都不用单独 加入的, 因为 Tomcat
 * 默认已经自带了这些类库.
 * 
 * @author BeanSoft
 * @version 1.2 2005-11-25
 */
public class ConnectionFactory {

	/** Database password */
	private static String password;

	/** Database username */
	private static String user;

	/** JDBC URL */
	private static String url;

	/**
	 * JDBC driver class name
	 */
	private static String driver;

	/**
	 * DEBUG flag, default value is true, returns a connection that directly
	 * fetched using JDBC API; if falg is false, returns a connection returned
	 * by the connection pool, if u want depoly the application, please make
	 * this flag be false. 调试标记, 默认值为 true, 则返回直接使用 JDBC 获取的连接; 如果标记为 false,
	 * 则从连接池中返回器连接, 发布程序时请将这个标志 设置为 false.
	 */
	private static boolean DEBUG = true;

	/** Connection properties value */
	private static Properties props = new Properties();

	/** The data source object, added at 2005-08-19 */
	private static DataSource dataSource = null;

	// Load configuration from resource /ConnectionFactory.properties
	static {
		loadConfiguration();
	}

	private ConnectionFactory() {
	}

	/**
	 * Factory method: obtain a database connection. 工厂方法: 获取一个数据库连接.
	 * 
	 * 
	 * @return Connection a java.sql.Connection object
	 */
	public static Connection getConnection() {
		try {
			Connection conn = null;

			// Debug mode, obtain connection directly through JDBC API
			if (DEBUG) {

				Class.forName(driver);

				conn = DriverManager.getConnection(url, user, password);
			} else {
				// TODO
				// // Looking data source through JNDI tree
				// DataSource dataSource = (DataSource) getInitialContext()
				// .lookup(jndi);
				// conn = dataSource.getConnection();
				conn = setupDataSource().getConnection();
			}

			return conn;
		} catch (Exception ex) {
			System.err.println("Error: Unable to get a connection: " + ex);
			ex.printStackTrace();
		}

		return null;
	}

	/**
	 * Load and parse configuration.
	 */
	public static void loadConfiguration() {
		try {
			props.load(new ByteArrayInputStream(readConfigurationString()
					.getBytes()));

			// Load DEBUG flag, default to true
			DEBUG = Boolean.valueOf(props.getProperty("debug", "true"))
					.booleanValue();
			password = props.getProperty("jdbc.password", null);
			user = props.getProperty("jdbc.user", null);
			url = props.getProperty("jdbc.url", null);
			driver = props.getProperty("jdbc.driver");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Save the current configuration properties.
	 */
	public static void saveConfiguration() {
		saveConfiguration(getProperties());
	}

	/**
	 * Read content string from configuration file. Because
	 * Class.getResourceAsStream(String) sometimes cache the contents, so here
	 * used this method. 读取配置文件中的字符串. 因为 Class 类的 getResourceAsStream(String)
	 * 方法有时候会出现缓存, 因此 不得已使用了这种办法.
	 * 
	 * @return String, null if failed
	 */
	public static String readConfigurationString() {
		try {
			java.io.FileInputStream fin = new java.io.FileInputStream(
					getConfigurationFilePath());
			java.io.ByteArrayOutputStream bout = new java.io.ByteArrayOutputStream();

			int data;
			while ((data = fin.read()) != -1) {
				bout.write(data);
			}
			bout.close();
			fin.close();

			return bout.toString();
		} catch (Exception ex) {
			System.err.println("Unable to load ConnectionFactory.properties:"
					+ ex.getMessage());
			ex.printStackTrace();
		}
		return null;
	}

	/**
	 * Get the configuration file's real physical path.
	 */
	private static String getConfigurationFilePath() {
		//return StringUtil.getRealFilePath("/ConnectionFactory.properties");
		return "";
	}

	/**
	 * Save string content of a java.util.Properties object. 保存配置文件中的字符串.
	 * 
	 * @param props
	 *            configuration string
	 * @return operation result
	 */
	protected static boolean saveConfigurationString(String props) {
		if (props == null || props.length() <= 0)
			return false;
		try {
			FileWriter out = new FileWriter(getConfigurationFilePath());
			out.write(props);
			out.close();

			return true;
		} catch (Exception ex) {
			System.err
					.println("Unable save configuration string to ConnectionFactory.properties:"
							+ ex);
			ex.printStackTrace();
		}

		return false;
	}

	/**
	 * Returns the current database connection properties.
	 * 
	 * @return Properties object
	 */
	public static Properties getProperties() {
		return props;
	}

	/**
	 * Save configuration properties.
	 * 
	 * @param props
	 *            Properties
	 * @return operation result
	 */
	public static boolean saveConfiguration(Properties props) {
		if (props == null || props.size() <= 0)
			return false;

		try {
			FileOutputStream out = new FileOutputStream(
					getConfigurationFilePath());
			props.store(out, "");
			out.close();

			return true;
		} catch (Exception ex) {
			System.err.println("Unable to save ConnectionFactory.properties:"
					+ ex.getMessage());
			ex.printStackTrace();
		}

		return false;
	}

	/**
	 * Create a DataSource instance based on the Apache DBCP. 创建基于 Apache DBCP 的
	 * DataSource.
	 * 
	 * @return a poolable DataSource
	 */
	public static DataSource setupDataSource() {
		if (dataSource == null) {
			BasicDataSource ds = new BasicDataSource();
			ds.setDriverClassName(driver);
			ds.setUsername(user);
			ds.setPassword(password);
			ds.setUrl(url);

			dataSource = ds;
		}

		return dataSource;
	}

	/**
	 * Display connection status of current data source.
	 * 
	 * 显示当前数据源的状态.
	 */
	public static String getDataSourceStats() {
		BasicDataSource bds = (BasicDataSource) setupDataSource();
		StringBuffer info = new StringBuffer();

		info.append("Active connection numbers: " + bds.getNumActive());
		info.append("\n");
		info.append("Idle connection numbers: " + bds.getNumIdle());

		return info.toString();
	}

	/**
	 * Shut down the data source, if want use it again, please call
	 * setupDataSource().
	 */
	public static void shutdownDataSource() {
		BasicDataSource bds = (BasicDataSource) setupDataSource();
		try {
			bds.close();
		} catch (SQLException e) {
			// TODO auto generated try-catch
			e.printStackTrace();
		}
	}

	/**
	 * Restart the data source. 重新启动数据源.
	 */
	public static void restartDataSource() {
		shutdownDataSource();
		setupDataSource();
	}

	/** Test method */
	public static void main(String[] args) {
		Connection conn = ConnectionFactory.getConnection();

		//DatabaseUtil dbUtil = new DatabaseUtil();
		//dbUtil.setConnection(conn);

		// try {
		// java.sql.ResultSet rs = conn.createStatement().executeQuery(
		// "SELECT MAX(ID) FROM items");
		//
		// while(rs.next()) {
		// System.out.println(rs.getString(1));
		// }
		//
		// rs.close();
		//
		// } catch (Exception ex) {
		// ex.printStackTrace();
		// }

		//System.out.println(dbUtil.getAllCount("SELECT MAX(ID) FROM items"));

		System.out.println(conn);

		try {
			conn.close();
		} catch (Exception ex) {
			// ex.printStackTrace();
		}

		conn = ConnectionFactory.getConnection();

		System.out.println(conn);

		try {
			conn.close();
		} catch (Exception ex) {
			// ex.printStackTrace();
		}

		System.exit(0);
	}

}

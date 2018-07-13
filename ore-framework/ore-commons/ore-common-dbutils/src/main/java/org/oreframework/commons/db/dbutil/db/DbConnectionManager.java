package org.oreframework.commons.db.dbutil.db;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.commons.dbcp.ConnectionFactory;
import org.apache.commons.dbcp.DriverManagerConnectionFactory;
import org.apache.commons.dbcp.PoolableConnectionFactory;
import org.apache.commons.dbcp.PoolingDriver;
import org.apache.commons.pool.impl.GenericObjectPool;
import org.oreframework.commons.db.dbutil.DBUtilConfig;




/**
 * @author huangzz
 * @version
 */
public class DbConnectionManager {

	public static String JTDS_DRIVER = "";

	public static String JTDS_URL = "";

	public static String JTDS_USER = "";

	public static String JTDS_PASSWORDS = "";

	/**
	 * 数据库连接池
	 * 
	 * @see http://jakarta.apache.org/commons/dbcp/index.html
	 */
	private static PoolingDriver driver = null;

	/**
	 * 设置一个数据库连接池
	 * 
	 * @param name
	 *            连接池的名称
	 * @param url
	 *            数据源
	 * @throws SQLException
	 */
	private static void setUpDriverPool(String name, String url, String user,
			String password) throws SQLException {
		if ((driver == null) || driver.getPoolNames().length < 2) {
			try {
				/**
				 * 首先创建一个对象池来保存数据库连接
				 * 
				 * 使用 commons.pool 的 GenericObjectPool对象
				 */
				GenericObjectPool connectionPool = new GenericObjectPool();
				connectionPool.setMinIdle(0);
				connectionPool.setMaxIdle(10);
				connectionPool.setMaxActive(50);

				/**
				 * 创建一个 DriverManagerConnectionFactory对象 连接池将用它来获取一个连接
				 */
				ConnectionFactory connectionFactory = new DriverManagerConnectionFactory(
						url, user, password);

				/**
				 * 创建一个PoolableConnectionFactory 对象。
				 */
				@SuppressWarnings("unused")
				PoolableConnectionFactory poolableConnectionFactory = new PoolableConnectionFactory(
						connectionFactory, connectionPool, null, null, false,
						true);

				/**
				 * 注册PoolingDriver。
				 */
				Class.forName("org.apache.commons.dbcp.PoolingDriver");

				driver = (PoolingDriver) DriverManager
						.getDriver("jdbc:apache:commons:dbcp:");

				driver.registerPool(name, connectionPool);

			} catch (ClassNotFoundException e) {
				throw new RuntimeException(e);
			}
		}
	}

	public static void main(String[] args) {
		shutDownDriver();
	}

	/**
	 * 关闭所有数据库连接池
	 * 
	 */
	public static void shutDownDriver() {

		try {
			PoolingDriver driver = (PoolingDriver) DriverManager
					.getDriver("jdbc:apache:commons:dbcp:");
			for (int i = 0; i < 3; i++) {
				driver.closePool(poolName);
			}
		} catch (SQLException sqle) {
			throw new RuntimeException(sqle);
		}
	}

	static String poolName = "easyshop";

	static boolean loaded;

	// public static final String
	// JSQLCONNET_DRIVER="com.jnetdirect.jsql.JSQLDriver";
	// public static final String
	// JSQLCONNET_URL="jdbc:JSQLConnect://127.0.0.1/database=shopping/user=allen/password=allen";

	// MS Server
	// public static final String
	// JTDS_DRIVER="net.sourceforge.jtds.jdbc.Driver";
	// public static final String
	// JTDS_URL="jdbc:jtds:sqlserver://127.0.0.1:1433/wdmc;user=sa;password=";

	// MySQL

	private static void load() {
		// TDS_URL=jdbc:mysql://localhost:3306/test?user=root&password=1&amp;useUnicode=true&amp;characterEncoding=utf-8
		
		JTDS_DRIVER = DBUtilConfig.getConfig("JTDS_DRIVER");
		JTDS_URL = DBUtilConfig.getConfig("JTDS_URL");
		JTDS_USER = DBUtilConfig.getConfig("JTDS_USER");
		JTDS_PASSWORDS = DBUtilConfig.getConfig("JTDS_PASSWORDS");

		String driver = null;
		String url = null;
		try {
			Class.forName(JTDS_DRIVER);
			setUpDriverPool(poolName, JTDS_URL, JTDS_USER, JTDS_PASSWORDS);
			loaded = true;
		} catch (ClassNotFoundException cnfe) {
			throw new RuntimeException("无法装入数据库引擎: " + driver);
		} catch (SQLException sqle) {
			throw new RuntimeException("无法打开数据库连接:" + url);
		}
	}

	/**
	 * 取得一个数据库连接对象。
	 * 
	 * 因为可能使用两个不同的数据库， 所以依据report的值来确定使用那个数据库。
	 * 
	 * @param report
	 * @return
	 */
	public static Connection getConnection() {
		Connection con = null;

		// 装载jdbc驱动
		if (!loaded)
			load();

		try {
			con = DriverManager.getConnection("jdbc:apache:commons:dbcp:"
					+ poolName);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return con;
	}

	/**
	 * 执行清理过程
	 * 
	 * <li>关闭数据库连接</li>
	 * <li>关闭语句对象</li>
	 * <li>关闭结果集</li>
	 * 
	 * @param con
	 * @param s
	 * @param rs
	 */
	public static void closeAll(Connection con, Statement s, ResultSet rs) {
		try {
			if (rs != null) {
				rs.close();
				rs = null;
			}

			if (s != null) {
				s.close();
				s = null;
			}

			if (con != null) {
				con.close();
				con = null;
			}
		} catch (SQLException sqle) {
			// nothing to do, forget it;
		}
	}

}
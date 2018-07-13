package org.oreframework.commons.db.dbutil.db;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.oreframework.commons.db.dbutil.DBUtilConfig;


/**
 * @author huangzz
 * @version
 */
public class DbConnectionProxy {

	private Connection getDSConnection(String DSName) {

		Connection con = null;
		Context ic = null;
		try {
			ic = new InitialContext();
			DataSource ds = (DataSource) ic.lookup(DSName);
			con = ds.getConnection();
		} catch (NamingException e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
		}
		return con;

	}

	private String usePool, DsName;

	private void load() {
		
		usePool = DBUtilConfig.getConfig("UseConnectionPool");
		DsName = DBUtilConfig.getConfig("DataSourceName");
	}

	private Connection getConn() {
		if (usePool == null){
			load();
		}
			

		Connection con = null;

		if (usePool.equalsIgnoreCase("true") && DsName != null){
			con = getDSConnection(DsName);
		}
		else{
			con = getJDBCPConnection();
		}
			
			

		return con;
	}

	private Connection getJDBCPConnection() {
		return DbConnectionManager.getConnection();
	}

	static DbConnectionProxy proxy;

	private static DbConnectionProxy getInstance() {
		if (proxy == null)
			proxy = new DbConnectionProxy();
		return proxy;

	}

	public static Connection getConnection() {
		return getInstance().getConn();

	}

	public static void closeConnection(Connection con, PreparedStatement ps) {
		try {
			if (ps != null) {
				ps.close();
				ps = null;
			}
			if (con != null) {
				con.close();
				con = null;
			}
		} catch (SQLException e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
		}
	}

	public static void closeConnection(Connection con, Statement ps) {
		try {
			if (ps != null) {
				ps.close();
				ps = null;
			}
			if (con != null) {
				con.close();
				con = null;
			}
		} catch (SQLException e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
		}
	}

	public static void closeConnection(Connection con, PreparedStatement ps,
			ResultSet rs) {
		try {
			if (rs != null) {
				rs.close();
				rs = null;
			}
			if (ps != null) {
				ps.close();
				ps = null;
			}
			if (con != null) {
				con.close();
				con = null;
			}
		} catch (SQLException e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
		}

	}

	public static void closeConnection(Connection con, Statement ps,
			ResultSet rs) {
		try {
			if (rs != null) {
				rs.close();
				rs = null;
			}
			if (ps != null) {
				ps.close();
				ps = null;
			}
			if (con != null) {
				con.close();
				con = null;
			}
		} catch (SQLException e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
		}

	}

}

package org.oreframework.commons.db.dbutil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.oreframework.commons.db.dbutil.db.DbConnectionProxy;


/**
 * 数据库操作工具类
 * 
 * @author huangzz
 * @version
 */
public final class DBUtil {

	private static DBUtil dbUtil;

	private DBUtil() {

	}

	/**
	 * DBUtil单例
	 * 
	 * @return dbUtil
	 */
	public synchronized static DBUtil createInstance() {
		if (dbUtil == null) {
			dbUtil = new DBUtil();
		}
		return dbUtil;
	}

	/**
	 * 查询指定多少行记录数据
	 * 
	 * @param sql
	 * @param rowHandler
	 * @return
	 * @throws DBUtilException
	 */
	public static Object query(String sql, ResultSetRowHandler rowHandler)
			throws DBUtilException {
		return (Object) query(sql, rowHandler, null);
	}

	/**
	 * 查询指定多少行记录数据
	 * 
	 * @param sql
	 * @param rowHandler
	 * @param param
	 * @return
	 * @throws DBUtilException
	 */
	public static Object query(String sql, ResultSetRowHandler rowHandler,
			Object[] param) throws DBUtilException {
		return (Object) execute(sql, rowHandler,
				new DefaultPreparedStatementSetter(param));
	}

	/**
	 * 查询指定多少行记录数据
	 * 
	 * @param sql
	 * @param rowhandler
	 * @param setParamHandler
	 * @return
	 */
	private static Object execute(String sql, ResultSetRowHandler rowhandler,
			PreparedStatementSetter setParamHandler) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		con = DbConnectionProxy.getConnection();

		try {
			ps = con.prepareStatement(sql);
			if (null != setParamHandler) {
				setParamHandler.setValues(ps);
			}
			rs = ps.executeQuery();

			if (rs != null) {
				return rowhandler.handle(rs, 1);
			}
		} catch (SQLException e) {
			throw new DBUtilException(e);
		} finally {
			DbConnectionProxy.closeConnection(con, ps, rs);

		}
		return null;

	}

	/**
	 * 用于存取PrepareStament参数
	 * 
	 * @author 黄宗志
	 * @date 2008-4-2 下午09:20:11
	 * @version
	 */
	private static class DefaultPreparedStatementSetter implements
			PreparedStatementSetter {
		private Object[] params;

		public DefaultPreparedStatementSetter(Object[] params) {
			this.params = params;
		}

		/**
		 * 设置值
		 * 
		 * @param ps
		 * @throws SQLException
		 */
		public void setValues(PreparedStatement ps) throws SQLException {
			if (params == null) {
				return;
			}
			for (int i = 0; i < params.length; i++) {
				Object param = params[i];
				if (param instanceof SqlParam) {
					setValue(ps, i, (SqlParam) param);
				} else {
					setValue(ps, i, new SqlParam(param));
				}
			}
		}

		/**
		 * 设置参数值
		 * 
		 * @param ps
		 * @param i
		 * @param param
		 * @throws SQLException
		 */
		private void setValue(PreparedStatement ps, final int i, SqlParam param)
				throws SQLException {
			ps.setObject(i + 1, param.getValue(), param.getSqlTypes());
		}
	}

	/**
	 * 查询列表数据
	 * 
	 * @param sql
	 * @param handler
	 * @return
	 */
	public static Object queryList(String sql, ResultSetHandler handler) {
		return queryList(sql, handler, null);
	}

	/**
	 * 查询列表数据
	 * 
	 * @param sql
	 * @param handler
	 * @param param
	 * @return
	 * @throws DBUtilException
	 */
	public static Object queryList(String sql, ResultSetHandler handler,
			Object[] param) throws DBUtilException {
		return executeList(sql, handler, null, param);
	}

	/**
	 * 查询列表数据
	 * 
	 * @param sql
	 * @param handler
	 * @param setParamHandler
	 * @param param
	 * @return
	 * @throws DBUtilException
	 */
	public static Object queryList(String sql, ResultSetHandler handler,
			PreparedStatementSetter setParamHandler, Object[] param)
			throws DBUtilException {
		return executeList(sql, handler, setParamHandler, param);
	}

	/**
	 * 查询列表数据
	 * 
	 * @param sql
	 * @param handler
	 * @param setParamHandler
	 * @param param
	 * @return
	 */
	private static Object executeList(String sql, ResultSetHandler handler,
			PreparedStatementSetter setParamHandler, Object[] param) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		con = DbConnectionProxy.getConnection();

		try {
			ps = con.prepareStatement(sql);
			if (null != setParamHandler) {
				setParamHandler.setValues(ps);
			}
			rs = ps.executeQuery();

			if (rs != null) {
				return handler.handle(rs);
			}
		} catch (SQLException e) {
			throw new DBUtilException(e);
		} finally {
			DbConnectionProxy.closeConnection(con, ps, rs);
		}
		return null;

	}

	/**
	 * 更新一条数据
	 * 
	 * @param sql
	 * @return
	 * @throws DBUtilException
	 */
	public static int update(String sql) throws DBUtilException {
		return update(sql, null, null);
	}

	/**
	 * 更新一条数据
	 * 
	 * @param sql
	 * @param setter
	 * @param bean
	 * @return
	 * @throws DBUtilException
	 */
	public static int update(String sql, StatementParamHandler setter,
			Object bean) throws DBUtilException {
		Connection conn = null;
		PreparedStatement ps = null;
		int n = 0;
		try {
			conn = DbConnectionProxy.getConnection();
			ps = conn.prepareStatement(sql);
			if (setter != null) {
				setter.convert(ps, bean);
			}

			n = ps.executeUpdate();
		} catch (SQLException e) {
			throw new DBUtilException(e);
		} finally {
			DbConnectionProxy.closeConnection(conn, ps);

		}
		return n;
	}

	/**
	 * 更新一条数据，并返回键值
	 * 
	 * @param sql
	 * @param setter
	 * @param bean
	 * @return
	 * @throws DBUtilException
	 */
	public static int updateAndReturnKey(String sql,
			StatementParamHandler setter, Object bean) throws DBUtilException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int key = 0;
		try {
			conn = DbConnectionProxy.getConnection();
			ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			if (setter != null) {
				setter.convert(ps, bean);
			}
			ps.executeUpdate();
			rs = ps.getGeneratedKeys();
			if (rs.next()) {
				key = rs.getInt(1);
			}
		} catch (SQLException e) {
			throw new DBUtilException(e);
		} finally {
			DbConnectionProxy.closeConnection(conn, ps, rs);
		}
		return key;
	}

	/**
	 * 批更新数据
	 * 
	 * @param sql
	 * @return
	 * @throws DBUtilException
	 */
	public static int batchUpdate(String sql) throws DBUtilException {
		return batchUpdate(sql, null, null);
	}

	/**
	 * 批更新数据
	 * 
	 * @param sql
	 * @param setter
	 * @param list
	 * @return
	 * @throws DBUtilException
	 */
	public static int batchUpdate(String sql, StatementParamHandler setter,
			Object object) throws DBUtilException {
		Connection conn = null;
		PreparedStatement ps = null;
		int n = 0;
		try {
			conn = DbConnectionProxy.getConnection();
			ps = conn.prepareStatement(sql);
			setter.convert(ps, object);
			n = ps.executeBatch().length;
		} catch (SQLException e) {
			throw new DBUtilException(e);
		} finally {
			DbConnectionProxy.closeConnection(conn, ps);
		}
		return n;
	}
}

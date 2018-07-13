package org.oreframework.commons.db.dbutil;

import java.util.Map;
import java.util.HashMap;
import java.util.Date;
import java.sql.*;
import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * 存储过程输出参数的封装
 * 
 * @author huangzz
 * @version
 */
public class SqlParam {
	private int sqlType;

	private Object value;

	private String name;

	private static Map<Class<?>, Object> typeMap = new HashMap<Class<?>, Object>();

	static {
		typeMap.put(Short.class, new Integer(Types.TINYINT));
		typeMap.put(short.class, new Integer(Types.TINYINT));
		typeMap.put(Integer.class, new Integer(Types.INTEGER));
		typeMap.put(int.class, new Integer(Types.INTEGER));
		typeMap.put(Long.class, new Integer(Types.BIGINT));
		typeMap.put(long.class, new Integer(Types.BIGINT));

		typeMap.put(Float.class, new Integer(Types.FLOAT));
		typeMap.put(float.class, new Integer(Types.FLOAT));
		typeMap.put(Double.class, new Integer(Types.DOUBLE));
		typeMap.put(double.class, new Integer(Types.DOUBLE));

		typeMap.put(BigDecimal.class, new Integer(Types.DECIMAL));
		typeMap.put(BigInteger.class, new Integer(Types.BIGINT));

		typeMap.put(String.class, new Integer(Types.VARCHAR));
		typeMap.put(Date.class, new Integer(Types.DATE));
		typeMap.put(Timestamp.class, new Integer(Types.TIMESTAMP));
		typeMap.put(Time.class, new Integer(Types.TIME));

		typeMap.put(Clob.class, new Integer(Types.CLOB));
		typeMap.put(Blob.class, new Integer(Types.BLOB));
	}

	public SqlParam() {
	}

	public SqlParam(int sqlTypes, Object value, String name) {
		this.sqlType = sqlTypes;
		this.value = value;
		this.name = name;
	}

	public SqlParam(int sqlTypes, Object value) {
		this(sqlTypes, value, null);
	}

	public SqlParam(int sqlTypes) {
		this(sqlTypes, null, null);
	}

	public SqlParam(Object value) {
		if (value == null) {
			throw new DBUtilException("value can't be null");
		}
		this.setSqlTypes(class2Type(value.getClass()));
		this.setValue(value);
	}

	public void setSqlTypes(int sqlTypes) {
		this.sqlType = sqlTypes;
	}

	public int getSqlTypes() {
		return this.sqlType;
	}

	public void setName(String _name) {
		this.name = _name;
	}

	public String getName() {
		return this.name;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	private static int class2Type(Class<? extends Object> clazz) {
		Integer type = (Integer) typeMap.get(clazz);
		if (type == null) {
			throw new DBUtilException("unsupport class " + clazz.getName());
		}
		return type.intValue();

	}

	public String toString() {
		final StringBuffer sb = new StringBuffer();
		sb.append("SqlParam");
		sb.append("{sqlType=").append(sqlType);
		sb.append(", value=").append(value);
		sb.append(", name='").append(name).append('\'');
		sb.append('}');
		return sb.toString();
	}
}

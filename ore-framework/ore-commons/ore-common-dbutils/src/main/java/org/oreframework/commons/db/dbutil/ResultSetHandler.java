package org.oreframework.commons.db.dbutil;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author huangzz
 * @version
 */
public interface ResultSetHandler {
	public Object handle(ResultSet rs) throws SQLException;
}

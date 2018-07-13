package org.oreframework.commons.db.dbutil;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author huangzz
 * @version
 */
public interface ResultSetRowHandler {
	public Object handle(ResultSet rs, int row) throws SQLException;
}

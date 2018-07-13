package org.oreframework.commons.db.dbutil;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author huangzz
 * @version
 */

public interface StatementParamHandler {

	public void convert(PreparedStatement stmt, Object o) throws SQLException;
}

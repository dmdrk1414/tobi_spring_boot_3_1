package springbook.user.dao.test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface StatementStrategy {

  PreparedStatement makePreparedStatement(Connection connection) throws SQLException;
}

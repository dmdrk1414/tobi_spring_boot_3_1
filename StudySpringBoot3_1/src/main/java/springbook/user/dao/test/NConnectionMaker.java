package springbook.user.dao.test;

import springbook.user.constant.DbConstant;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class NConnectionMaker implements ConnectionMaker {

  @Override
  public Connection makeConnection() throws ClassNotFoundException, SQLException {
    // Class.forName("com.mysql.jdbc.Driver");

    // H2 Database 연결
    // DB 연결을 위한 Connection을 가져온다.
    Connection connection = DriverManager.getConnection(DbConstant.JDBC_URL.getStr(),
        DbConstant.ID.getStr(), DbConstant.PASSWORD.getStr());

    return connection;
  }
}

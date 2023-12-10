package springbook.user.book.p67;

import springbook.user.constant.DbConstant;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class NUserDao {
    /**
     * N사의 connection을 이용해 데이터베이스의 확장성을 생각한다.
     *
     * @return
     */
    public Connection getConnection() throws SQLException, ClassNotFoundException {
        // Class.forName("com.mysql.jdbc.Driver");

        // H2 Database 연결
        // DB 연결을 위한 Connection을 가져온다.
        Connection connection = DriverManager.getConnection(DbConstant.JDBC_URL.getStr(), DbConstant.ID.getStr(), DbConstant.PASSWORD.getStr());

        return connection;
    }
}

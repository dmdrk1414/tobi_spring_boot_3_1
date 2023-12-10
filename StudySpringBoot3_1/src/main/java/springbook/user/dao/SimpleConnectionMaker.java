package springbook.user.dao;

import springbook.user.constant.DbConstant;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SimpleConnectionMaker {

    /**
     * 상속을 통해서 만들어진 getConnection의 구현 코드가 매 DAO 클래스마다 중복된다.
     * DB 커넥션과 관련된 부분을 서브클래스가 아니라, 아예 별도의 클래스에 담는다.
     *
     * @return
     */
    public Connection makeNewConnection() throws ClassNotFoundException, SQLException {
        // Class.forName("com.mysql.jdbc.Driver");

        // H2 Database 연결
        // DB 연결을 위한 Connection을 가져온다.
        Connection connection = DriverManager.getConnection(DbConstant.JDBC_URL.getStr(), DbConstant.ID.getStr(), DbConstant.PASSWORD.getStr());

        return connection;
    }
}

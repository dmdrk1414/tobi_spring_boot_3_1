package springbook.user.dao;

import springbook.user.domain.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collection;

public class UserDao {
    public void add(User user) throws SQLException, ClassNotFoundException {
//        Class.forName("com.mysql.jdbc.Driver");

        // H2 Database 연결
        // DB 연결을 위한 Connection을 가져온다.
        Connection connection = DriverManager.getConnection("jdbc:h2:mem:testdb;MODE=MySQL", "sa", "");

        // SQL을 담은 Statement(또는 PreparedStatement)을 만든다.
        PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO users(id, name, password) VALUES (?, ?, ?) "
        );
        preparedStatement.setString(1, user.getId());
        preparedStatement.setString(2, user.getName());
        preparedStatement.setString(3, user.getPassword());

        // 만들어진 Statement을 실행한다.
        preparedStatement.execute();

        // 작업 중에 생성된 Connection, Statement, ResultSet 같은 리소리는 작업을 마친 후 반드시 닫아준다.
        preparedStatement.close();
        connection.close();
    }
}

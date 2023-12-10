package springbook.user.dao;

import lombok.AllArgsConstructor;
import springbook.user.domain.User;

import java.sql.*;
import java.util.Collection;

public class UserDao {
    public void add(User user) throws SQLException, ClassNotFoundException {
        // DB 연결을 위한 Connection을 가져온다.
        Connection connection = getConnection();

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

    public User get(String id) throws ClassNotFoundException, SQLException {
        // DB 연결을 위한 Connection을 가져온다.
        Connection connection = getConnection();

        // SQL을 담은 Statement(또는 PreparedStatement)을 만든다.
        PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT * FROM users WHERE id = ?"
        );
        preparedStatement.setString(1, id);

        // 만들어진 Statement를 실행한다.
        ResultSet resultSet = preparedStatement.executeQuery();

        // resultSet의 다음을 찾는다 (초기에는 null이다.)
        resultSet.next();

        String searchId = resultSet.getString("id");
        String searchName = resultSet.getString("name");
        String searchPassword = resultSet.getString("password");
        User user = new User(searchId, searchName, searchPassword);

        // 작업 중에 생성된 Connectin, Statement, ResultSet 같은 리소스는 작업을 마친 후 반드기 닫아준다.
        resultSet.close();
        preparedStatement.close();
        connection.close();

        return user;
    }

    /**
     * 중복된 코드를 독립적인 메소드로 만들어서 중복을 제거했다.
     *
     * @return
     */
    private Connection getConnection() throws SQLException {
        // Class.forName("com.mysql.jdbc.Driver");

        // H2 Database 연결
        // DB 연결을 위한 Connection을 가져온다.
        Connection connection = DriverManager.getConnection("jdbc:h2:mem:testdb;MODE=MySQL", "sa", "");

        return connection;
    }
}

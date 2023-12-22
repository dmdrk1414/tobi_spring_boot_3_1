package springbook.user.dao;

import lombok.NoArgsConstructor;
import springbook.user.domain.User;

import javax.sql.DataSource;
import java.sql.*;

@NoArgsConstructor // 빈을 사용하기 위해 기본 생성자 생성
public class UserDao {
    private DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void add(User user) throws SQLException, ClassNotFoundException {
        Connection connection = dataSource.getConnection();

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
        Connection connection = dataSource.getConnection();

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

    public void deleteAll() throws SQLException {
        Connection connection = dataSource.getConnection();

        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM users");
        preparedStatement.execute();

        preparedStatement.close();
        connection.close();
    }

    public int getCount() throws SQLException {
        Connection connection = dataSource.getConnection();

        PreparedStatement preparedStatement = connection.prepareStatement("SELECT count(*) FROM users");

        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        int count = resultSet.getInt(1);

        resultSet.close();
        preparedStatement.close();
        connection.close();

        return count;
    }
}

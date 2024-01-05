package springbook.user.dao;

import lombok.NoArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
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
        StatementStrategy strategy = new AddStatement(user);
        jdbcContextWithStatementStrategy(strategy);
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

        User user = null;

        // resultSet의 다음을 찾는다 (초기에는 null이다.)
        if (resultSet.next()) {
            String searchId = resultSet.getString("id");
            String searchName = resultSet.getString("name");
            String searchPassword = resultSet.getString("password");
            user = new User(searchId, searchName, searchPassword);
        }

        // 작업 중에 생성된 Connectin, Statement, ResultSet 같은 리소스는 작업을 마친 후 반드기 닫아준다.
        resultSet.close();
        preparedStatement.close();
        connection.close();

        if (user == null) {
            throw new EmptyResultDataAccessException(1);
        }

        return user;
    }

    public void deleteAll() throws SQLException {
        // 선정한 전략 클래스의 오브젝트 생성
        StatementStrategy strategy = new DeleteAllStatement();

        // 컨텍스트 호출, 전략 오브젝트 전달
        jdbcContextWithStatementStrategy(strategy);
    }

    public int getCount() throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = dataSource.getConnection();

            preparedStatement = connection.prepareStatement("SELECT count(*) FROM users");

            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return resultSet.getInt(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    // ps.close() 메소드에서도 SQLException 이 발생할 수 있기 때문에
                    // 이를 잡아줘야 한다.
                    // 그렇지 않으면 Connection을 close() 하지 못하고 메소드를 빠져날갈 수 있다.
                }
            }
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    // ps.close() 메소드에서도 SQLException 이 발생할 수 있기 때문에
                    // 이를 잡아줘야 한다.
                    // 그렇지 않으면 Connection을 close() 하지 못하고 메소드를 빠져날갈 수 있다.
                }
            }

            if (connection != null) {
                try {
                    // Connection 반환
                    connection.close();
                } catch (SQLException e) {
                }
            }
        }
    }

    public void jdbcContextWithStatementStrategy(StatementStrategy strategy) throws SQLException {
        Connection connection = null;

        PreparedStatement preparedStatement = null;
        try {
            connection = dataSource.getConnection();
            // 예외가 발생할 가능성이 있는 코드를
            // 모두 try 블록으로 묶어준다.
            preparedStatement = strategy.makePreparedStatement(connection);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            // 예외가 발생했을때 부가적인 작업을 해줄 수 있도록 catch 블록을 둔다.
            // 아직은 예외를 다시 메소드 밖으로 던지는 것밖에 없다.
            throw e;
        } finally {
            // finally이므로 try 블록에서 예외가 발생했을 때나 안 했을때나 모두 실행된다.
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    // ps.close() 메소드에서도 SQLException 이 발생할 수 있기 때문에
                    // 이를 잡아줘야 한다.
                    // 그렇지 않으면 Connection을 close() 하지 못하고 메소드를 빠져날갈 수 있다.
                }
            }

            if (connection != null) {
                try {
                    // Connection 반환
                    connection.close();
                } catch (SQLException e) {
                }
            }
        }
    }
}

package springbook.user.dao;

import lombok.NoArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import springbook.user.domain.User;

import javax.sql.DataSource;
import java.sql.*;

@NoArgsConstructor // 빈을 사용하기 위해 기본 생성자 생성
public class UserDao {
    private DataSource dataSource;
    private JdbcContext jdbcContext;

    public void setDataSource(DataSource dataSource) {
        this.jdbcContext = new JdbcContext(); // jdbcContext 생성(ioC)
        this.jdbcContext.setDataSource(dataSource); // 의존 오브젝트 주입(DI)
        this.dataSource = dataSource;
    }


    public void add(final User user) throws SQLException, ClassNotFoundException {
        this.jdbcContext.workWithStatementStrategy(
                new StatementStrategy() {
                    @Override
                    public PreparedStatement makePreparedStatement(Connection connection) throws SQLException {
                        PreparedStatement preparedStatement = connection.prepareStatement(
                                "INSERT INTO users(id, name, password) VALUES (?, ?, ?)"
                        );

                        preparedStatement.setString(1, user.getId());
                        preparedStatement.setString(2, user.getName());
                        preparedStatement.setString(3, user.getPassword());

                        return preparedStatement;
                    }
                }
        );
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
        this.jdbcContext.workWithStatementStrategy(
                new StatementStrategy() {
                    @Override
                    public PreparedStatement makePreparedStatement(Connection connection) throws SQLException {
                        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM users");
                        return preparedStatement;
                    }
                }
        );
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
}

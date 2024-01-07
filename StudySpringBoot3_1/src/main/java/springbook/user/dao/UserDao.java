package springbook.user.dao;

import lombok.NoArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;
import springbook.user.domain.User;

import javax.sql.DataSource;
import java.sql.*;

@NoArgsConstructor // 빈을 사용하기 위해 기본 생성자 생성
public class UserDao {
    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;

    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.dataSource = dataSource;
    }


    public void add(final User user) throws SQLException, ClassNotFoundException {
        String sql = "INSERT  INTO users(id, name, password) VALUES (?, ?, ?)";
        this.jdbcTemplate.update(sql, user.getId(), user.getName(), user.getPassword());
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
        String sql = "DELETE FROM users";
        this.jdbcTemplate.update(sql);

        // 콜백 형식의 jdbcTemplate.update 사용방법
//        this.jdbcTemplate.update(
//                new PreparedStatementCreator() {
//                    @Override
//                    public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
//                        return con.prepareStatement("DELETE FROM users");
//                    }
//                }
//        );
    }


    public int getCount() throws SQLException {
        String sql = "SELECT  COUNT(*) FROM users";
        return this.jdbcTemplate.queryForObject(sql, Integer.class);
//        return this.jdbcTemplate.query(
//                new PreparedStatementCreator() {
//                    @Override
//                    public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
//                        return con.prepareStatement("SELECT  COUNT(*) FROM users");
//                    }
//                },
//                new ResultSetExtractor<Integer>() {
//                    // ResultSet에서 추출하는 값은 다양하기 때문에 타입을 지정해준다.
//                    @Override
//                    public Integer extractData(ResultSet rs) throws SQLException, DataAccessException {
//                        rs.next();
//                        return rs.getInt(1);
//                    }
//                }
//        );
    }
}

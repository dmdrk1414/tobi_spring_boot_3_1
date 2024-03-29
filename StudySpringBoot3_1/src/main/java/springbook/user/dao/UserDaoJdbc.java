package springbook.user.dao;

import lombok.NoArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import springbook.user.constant.Level;
import springbook.user.domain.User;
import springbook.user.exception.DuplicateUserIdException;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

// TODO: 1/7/24
// p275
// 1. userMapper을 DI용 프로퍼티로 만들고 싶다.
// 2. sql을 외부에서 가져오고싶다.
@NoArgsConstructor // 빈을 사용하기 위해 기본 생성자 생성
public class UserDaoJdbc implements UserDao {

  private JdbcTemplate jdbcTemplate;
  private RowMapper<User> userMapper =
      new RowMapper<User>() {
        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
          User user = new User();
          user.setId(rs.getString("id"));
          user.setName(rs.getString("name"));
          user.setPassword(rs.getString("password"));
          user.setLevel(Level.valueOf(rs.getInt("level")));
          user.setLogin(rs.getInt("login"));
          user.setRecommend(rs.getInt("recommend"));
          return user;
        }
      };

  public void setDataSource(DataSource dataSource) {
    this.jdbcTemplate = new JdbcTemplate(dataSource);
  }

  /**
   * 만약 jdbcTemplate을 DI으로 이용해 주입할 생각이면
   *
   * @param jdbcTemplate
   */
  public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
    // 만약 DI으로 주입하고 싶다면
    // JdbcTemplate을 -> JdbcOperations interface을 이용해 구현을 한다.
    this.jdbcTemplate = jdbcTemplate;
  }


  public void add(final User user) throws DuplicateUserIdException {
    try {
      String sql = "INSERT INTO users(id, name, password, level, login, recommend) VALUES (?, ?, ?, ?, ?, ?)";
      this.jdbcTemplate.update(sql, user.getId(), user.getName(), user.getPassword(),
          user.getLevel().intValue(), user.getLogin(), user.getRecommend());
    } catch (DuplicateKeyException e) {
      // DataAccessException의 상속을 받는 DuplicateKeyException을 try으로 처리한다.
      throw new DuplicateUserIdException(e);
    }
  }

  public User get(String id) {
    String sql = "SELECT * FROM users WHERE id = ?";
    return this.jdbcTemplate.queryForObject(sql,
        // sql에 바인딩할 파라미터 값, 가변인자 대신 배열을 사용한다.
        new Object[]{id},
        this.userMapper
    );
  }

  public void deleteAll() {
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


  public int getCount() {
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

  public List<User> getAll() {
    return this.jdbcTemplate.query("SELECT * FROM users order by id",
        // query의 리턴 타입은 List<T>이다.
        // query()는 제네릭 메소드로 타입은 파라미터로 넘기는 RowMapper<T> 콜백 오브젝트에서 결정된다.
        this.userMapper);
  }

  public void update(final User user) {
    this.jdbcTemplate.update(
        "update users set name = ?, password = ?, level = ?, login = ?,"
            + "recommend = ? where id = ?", user.getName(), user.getPassword(),
        user.getLevel().intValue(), user.getLogin(), user.getRecommend(),
        user.getId()
    );
  }
}

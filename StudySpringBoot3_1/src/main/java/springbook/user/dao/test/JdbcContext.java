package springbook.user.dao.test;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class JdbcContext {

  private DataSource dataSource;


  public void setDataSource(DataSource dataSource) {
    this.dataSource = dataSource;
  }

  public void executeSql(final String query) throws SQLException {
    // 컨텍스트 호출, 전략 오브젝트 전달
    workWithStatementStrategy(
        new StatementStrategy() {
          @Override
          public PreparedStatement makePreparedStatement(Connection connection)
              throws SQLException {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            return preparedStatement;
          }
        }
    );
  }

  public void workWithStatementStrategy(StatementStrategy strategy) throws SQLException {
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

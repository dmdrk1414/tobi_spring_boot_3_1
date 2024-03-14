package springbook.user.dao.test;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * p 123 부가 기능의 추가 DB를 얼마나 많이 연결해서 사용하는지 파악을 할수있다. makeConnection()에서 DB 연결횟수 카운터를 증가시킨다.
 * CountingConnectionMaker는 자신의 관심사인 DB 연결횟수를 카운팅 작업을 마치며 실제 DB 커넥션을 만들어주는 realConnectionMaker에 저장된
 * ConnectionMaker 타입 오브젝트의 makeConnection()을 호출하여 그결과를 DAO에게 돌려준다.
 */
public class CountingConnectionMaker implements ConnectionMaker {

  int counter = 0;
  private ConnectionMaker realConnectionMaker;

  public CountingConnectionMaker(ConnectionMaker realConnectionMaker) {
    this.realConnectionMaker = realConnectionMaker;
  }

  @Override
  public Connection makeConnection() throws ClassNotFoundException, SQLException {
    // p123
    // DB 연결을 할때마다 conter을 한다.
    this.counter++;

    return realConnectionMaker.makeConnection();
  }

  public int getCounter() {
    return counter;
  }
}

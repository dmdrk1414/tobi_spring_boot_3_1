package springbook.user.dao;

import springbook.user.domain.User;

import java.sql.*;

public class UserDao {
    // p76 인터페이스를 통해 오브젝트에 접근하므로 구체적인 클래스 정보를 알필요가 없다.
    private final ConnectionMaker connectionMaker;
    // filed에 선언을 하면 데이터가 망가진다(싱글톤이기에)
//    private Connection connection; // p110 로컬 변수를 이용한 개별적으로 바뀌는 정보를 로컬변수르 정의
//    private User user; // p110 로컬 변수를 이용한 개별적으로 바뀌는 정보를 로컬변수르 정의

    // p81 UserDao을 생성하는 클라이언트에게 ConnectionMaker을 생성하는 책임을 준다.
    public UserDao(ConnectionMaker connectionMaker) {
        // p76 하지만 여기에는 클래스 이름이 나온다.
        this.connectionMaker = connectionMaker;
    }

    public void add(User user) throws SQLException, ClassNotFoundException {
        // DB 연결을 위한 Connection을 가져온다.
        // simpleConnectionMaker을 이용한 Connection을 생성한다.
        // p76 인터페이스에 정의된 메소드를 사용하므로 클래스가 바뀐다고 해도 메소드 이름이 변경될 걱정은 없다.
        Connection connection = connectionMaker.makeConnection();

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
        // simpleConnectionMaker을 이용한 Connection을 생성한다.
        // p76 인터페이스에 정의된 메소드를 사용하므로 클래스가 바뀐다고 해도 메소드 이름이 변경될 걱정은 없다.
        Connection connection = connectionMaker.makeConnection();

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
}

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.support.SQLErrorCodeSQLExceptionTranslator;
import org.springframework.jdbc.support.SQLExceptionTranslator;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import springbook.user.dao.UserDaoJdbc;
import springbook.user.domain.User;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.hamcrest.CoreMatchers.is;

//@DirtiesContext // 테스트 메서드에서 애플리케이션 컨텍스트의 구성이나 상태를 변경한다는 것을 테스트 컨텍스트 프레임워크에 알려준다.
@ExtendWith(SpringExtension.class) // xml의 DI을 하기위한 JUNIT5의 확장
@ContextConfiguration(locations = "/xml/test-applicationContext.xml")
public class UserDaoTest {
    // 타입 방식의 자동 와이어링
    // 만약 UserDao가 2개이면
    //      타입으로 가져올 빈 하나를 선택할 수 없는 경우에는 변수의 이름과 같은 이름의 빈이 있는지 확인
    //
    @Autowired
    private UserDaoJdbc userDao;
    @Autowired
    private UserDaoJdbc userDao2;
    @Autowired
    private DataSource dataSource;
    private User user1;
    private User user2;
    private User user3;
    @Autowired
    private ApplicationContext context;

    @BeforeEach
    public void setUp() {
        this.user1 = new User("gyumee", "박성철", "springno1");
        this.user2 = new User("leegw700", "이길원", "springno2");
        this.user3 = new User("bumhin", "박범진", "springno3");

        // test 용 xml을 설정하여 따로 DI을 하였다.
//        DataSource dataSource = new SingleConnectionDataSource("jdbc:mysql://localhost/testdb", "root", "qkrtmdcks1!", true);
//        userDao.setDataSource(dataSource);
    }

    @Test
    public void addAndGet() throws Exception {
        // given
        // 테스트를 위한 deleteAll 추가
        userDao.deleteAll();
        assertThat(userDao.getCount()).isEqualTo(0);

        User user = new User("dmdrk1414", "박승찬", "1234");

        // when
        userDao.add(user);

        assertThat(userDao.getCount()).isEqualTo(1);

        User user2 = userDao.get(user.getId());

        // then
        assertThat(user2.getName()).contains(user.getName());
        assertThat(user2.getPassword()).contains(user.getPassword());
    }

    @Test
    public void count_을_세세하게_테스트() throws Exception {
        // given

        // when
        // then
        userDao.deleteAll();
        assertThat(userDao.getCount()).isEqualTo(0);

        userDao.add(user1);
        assertThat(userDao.getCount()).isEqualTo(1);

        userDao.add(user2);
        assertThat(userDao.getCount()).isEqualTo(2);

        userDao.add(user3);
        assertThat(userDao.getCount()).isEqualTo(3);
    }

    @Test
    public void getUserFailure() throws SQLException {
        // given
        userDao.deleteAll();

        // then
        assertThat(userDao.getCount()).isEqualTo(0);

        String searchId = "unknown_id";
        assertThatThrownBy(() -> userDao.get(searchId))
                .isInstanceOf(EmptyResultDataAccessException.class);
    }

    @Test
    public void getAll() throws SQLException {
        userDao.deleteAll();

        List<User> user0 = userDao.getAll();
        assertThat(user0.size()).isEqualTo(0);

        userDao.add(user1); // id: gyumee
        List<User> users1 = userDao.getAll();
        assertThat(users1.size()).isEqualTo(1);
        checkSameUser(user1, users1.get(0));

        userDao.add(user2); // id: leegw700
        List<User> users2 = userDao.getAll();
        assertThat(users2.size()).isEqualTo(2);
        checkSameUser(user1, users2.get(0));
        checkSameUser(user2, users2.get(1));

        userDao.add(user3); // id: bumjin
        List<User> users3 = userDao.getAll();
        assertThat(users3.size()).isEqualTo(3);
        checkSameUser(user3, users3.get(0));
        checkSameUser(user1, users3.get(1));
        checkSameUser(user2, users3.get(2));

    }

    private void checkSameUser(User user1, User user2) {
        assertThat(user1.getId()).isEqualTo(user2.getId());
        assertThat(user1.getName()).isEqualTo(user2.getName());
        assertThat(user1.getPassword()).isEqualTo(user2.getPassword());
    }

    @Test
    public void sqlExceptionTranslate() {
        userDao.deleteAll();

        try {
            userDao.add(user1);
            userDao.add(user1);
        } catch (DuplicateKeyException ex) {
            SQLException sqlException = (SQLException) ex.getRootCause();
            SQLExceptionTranslator set = new SQLErrorCodeSQLExceptionTranslator(this.dataSource);

            assertThat(set.translate(null, null, sqlException))
                    .isEqualTo(DuplicateKeyException.class);
        }
    }
}

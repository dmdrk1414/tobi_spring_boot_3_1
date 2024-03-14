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
import springbook.user.constant.Level;
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
    private UserDaoJdbc dao;
    @Autowired
    private DataSource dataSource;
    private User user1;
    private User user2;
    private User user3;
    @Autowired
    private ApplicationContext context;

    @BeforeEach
    public void setUp() {
        this.user1 = new User("gyumee", "박성철", "springno1", Level.BASIC, 1, 0);
        this.user2 = new User("leegw700", "이길원", "springno2", Level.SILVER, 55, 10);
        this.user3 = new User("bumhin", "박범진", "springno3", Level.GOLD, 100, 40);

        // test 용 xml을 설정하여 따로 DI을 하였다.
//        DataSource dataSource = new SingleConnectionDataSource("jdbc:mysql://localhost/testdb", "root", "qkrtmdcks1!", true);
//        userDao.setDataSource(dataSource);
    }

    @Test
    public void addAndGet() throws Exception {
        // given
        // 테스트를 위한 deleteAll 추가
        dao.deleteAll();
        assertThat(dao.getCount()).isEqualTo(0);

        // when
        dao.add(user1);

        assertThat(dao.getCount()).isEqualTo(1);

        User user2 = dao.get(user1.getId());

        // p323 서비스 추상화 추가.
        User userget1 = dao.get(user1.getId());
        checkSameUser(userget1, user1);

        User userget2 = dao.get(user2.getId());
        checkSameUser(userget2, user2);

        // then
        assertThat(user2.getName()).contains(user1.getName());
        assertThat(user2.getPassword()).contains(user1.getPassword());
    }

    @Test
    public void count_을_세세하게_테스트() throws Exception {
        // given

        // when
        // then
        dao.deleteAll();
        assertThat(dao.getCount()).isEqualTo(0);

        dao.add(user1);
        assertThat(dao.getCount()).isEqualTo(1);

        dao.add(user2);
        assertThat(dao.getCount()).isEqualTo(2);

        dao.add(user3);
        assertThat(dao.getCount()).isEqualTo(3);
    }

    @Test
    public void getUserFailure() throws SQLException {
        // given
        dao.deleteAll();

        // then
        assertThat(dao.getCount()).isEqualTo(0);

        String searchId = "unknown_id";
        assertThatThrownBy(() -> dao.get(searchId))
                .isInstanceOf(EmptyResultDataAccessException.class);
    }

    @Test
    public void getAll() throws SQLException {
        dao.deleteAll();

        List<User> user0 = dao.getAll();
        assertThat(user0.size()).isEqualTo(0);

        dao.add(user1); // id: gyumee
        List<User> users1 = dao.getAll();
        assertThat(users1.size()).isEqualTo(1);
        checkSameUser(user1, users1.get(0));

        dao.add(user2); // id: leegw700
        List<User> users2 = dao.getAll();
        assertThat(users2.size()).isEqualTo(2);
        checkSameUser(user1, users2.get(0));
        checkSameUser(user2, users2.get(1));

        dao.add(user3); // id: bumjin
        List<User> users3 = dao.getAll();
        assertThat(users3.size()).isEqualTo(3);
        checkSameUser(user3, users3.get(0));
        checkSameUser(user1, users3.get(1));
        checkSameUser(user2, users3.get(2));

    }

    private void checkSameUser(User user1, User user2) {
        assertThat(user1.getId()).isEqualTo(user2.getId());
        assertThat(user1.getName()).isEqualTo(user2.getName());
        assertThat(user1.getPassword()).isEqualTo(user2.getPassword());
        assertThat(user1.getLevel()).isEqualTo(user2.getLevel());
        assertThat(user1.getLogin()).isEqualTo(user2.getLogin());
        assertThat(user1.getRecommend()).isEqualTo(user2.getRecommend());
    }

    @Test
    public void sqlExceptionTranslate() {
        dao.deleteAll();

        try {
            dao.add(user1);
            dao.add(user1);
        } catch (DuplicateKeyException ex) {
            SQLException sqlException = (SQLException) ex.getRootCause();
            SQLExceptionTranslator set = new SQLErrorCodeSQLExceptionTranslator(this.dataSource);

            assertThat(set.translate(null, null, sqlException))
                    .isEqualTo(DuplicateKeyException.class);
        }
    }
}

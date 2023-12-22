import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import springbook.user.dao.UserDao;
import springbook.user.domain.User;

import javax.sql.DataSource;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.*;
import static org.hamcrest.CoreMatchers.is;

@DirtiesContext // 테스트 메서드에서 애플리케이션 컨텍스트의 구성이나 상태를 변경한다는 것을 테스트 컨텍스트 프레임워크에 알려준다.
@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = "/xml/applicationContext.xml")
public class UserDaoTest {
    // 타입 방식의 자동 와이어링
    // 만약 UserDao가 2개이면
    //      타입으로 가져올 빈 하나를 선택할 수 없는 경우에는 변수의 이름과 같은 이름의 빈이 있는지 확인
    //
    @Autowired
    private UserDao userDao;
    @Autowired
    private UserDao userDao2;
    @Autowired
    private DataSource source;
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

        DataSource dataSource = new SingleConnectionDataSource("jdbc:mysql://localhost/testdb", "root", "qkrtmdcks1!", true);
        userDao.setDataSource(dataSource);
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
}

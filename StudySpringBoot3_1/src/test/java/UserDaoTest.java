import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import springbook.user.dao.UserDao;
import springbook.user.domain.User;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.*;
import static org.hamcrest.CoreMatchers.is;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = "/xml/applicationContext.xml")
public class UserDaoTest {
    private UserDao dao;
    private User user1;
    private User user2;
    private User user3;
    @Autowired
    private ApplicationContext context;

    @BeforeEach
    public void setUp() {
        dao = this.context.getBean("userDao", UserDao.class);

        this.user1 = new User("gyumee", "박성철", "springno1");
        this.user2 = new User("leegw700", "이길원", "springno2");
        this.user3 = new User("bumhin", "박범진", "springno3");
    }

    @Test
    public void addAndGet() throws Exception {
        // given
        // 테스트를 위한 deleteAll 추가
        dao.deleteAll();
        assertThat(dao.getCount()).isEqualTo(0);

        User user = new User("dmdrk1414", "박승찬", "1234");

        // when
        dao.add(user);

        assertThat(dao.getCount()).isEqualTo(1);

        User user2 = dao.get(user.getId());

        // then
        assertThat(user2.getName()).contains(user.getName());
        assertThat(user2.getPassword()).contains(user.getPassword());
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
}

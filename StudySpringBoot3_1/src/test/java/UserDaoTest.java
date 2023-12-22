import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;
import springbook.user.dao.UserDao;
import springbook.user.domain.User;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.*;
import static org.hamcrest.CoreMatchers.is;

@Component
public class UserDaoTest {
    @Test
    public void addAndGet() throws Exception {
        // given
        GenericXmlApplicationContext context =
                new GenericXmlApplicationContext("xml/applicationContext.xml");

        UserDao dao = context.getBean("userDao", UserDao.class);

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
        ApplicationContext context = new GenericXmlApplicationContext("xml/applicationContext.xml");

        UserDao dao = context.getBean("userDao", UserDao.class);
        User user1 = new User("dmdrk1414", "박승찬", "1234");
        User user2 = new User("dmdrk14142", "박승찬2", "1234");
        User user3 = new User("dmdrk14143", "박승찬3", "1234");

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
        ApplicationContext context = new GenericXmlApplicationContext("xml/applicationContext.xml");

        // when
        UserDao dao = context.getBean("userDao", UserDao.class);
        dao.deleteAll();

        // then
        assertThat(dao.getCount()).isEqualTo(0);

        String searchId = "unknown_id";
        assertThatThrownBy(() -> dao.get(searchId))
                .isInstanceOf(EmptyResultDataAccessException.class);
    }
}

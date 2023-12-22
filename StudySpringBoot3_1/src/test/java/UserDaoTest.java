import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.stereotype.Component;
import springbook.user.dao.UserDao;
import springbook.user.domain.User;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.useDefaultDateFormatsOnly;
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
}

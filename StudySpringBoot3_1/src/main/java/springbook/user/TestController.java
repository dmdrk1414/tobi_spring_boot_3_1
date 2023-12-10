package springbook.user;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import springbook.user.dao.UserDao;
import springbook.user.domain.User;

import java.sql.SQLException;

@RestController
public class TestController {
    @GetMapping("user/dao")
    public String userDaoTest() throws SQLException, ClassNotFoundException {
        UserDao userDao = new UserDao();

        User user = new User("Whiteship", "박승찬", "married");
        userDao.add(user);

        System.out.println(user.getId() + " 등록 성공");

        User user2 = userDao.get(user.getId());
        System.out.println(user2.getName());

        return user2.toString();
    }
}

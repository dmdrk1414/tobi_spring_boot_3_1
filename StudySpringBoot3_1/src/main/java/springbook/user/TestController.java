package springbook.user;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import springbook.user.dao.UserDao;
import springbook.user.domain.User;

import java.sql.SQLException;

@RestController
public class TestController {
    @GetMapping("test")
    public Boolean testGetController() throws SQLException, ClassNotFoundException {
        UserDao userDao = new UserDao();
        userDao.add(new User("1", "test", "1234"));

        return true;
    }
}

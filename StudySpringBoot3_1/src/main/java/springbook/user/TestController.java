package springbook.user;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import springbook.user.dao.DUserDao;
import springbook.user.dao.NUserDao;
import springbook.user.dao.UserDao;
import springbook.user.domain.User;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class TestController {
    @GetMapping("user/dao")
    public String userDaoTest() throws SQLException, ClassNotFoundException {
        NUserDao nUserDao = new NUserDao();
        DUserDao dUserDao = new DUserDao();

        User user_N = new User("NUser", "박승찬", "married");
        User user_D = new User("DUser", "박승찬", "married");

        nUserDao.add(user_N);
        dUserDao.add(user_D);

        User searchNUser = nUserDao.get(user_N.getId());
        User searchDUser = dUserDao.get(user_D.getId());

        List<User> list = new ArrayList<>();
        list.add(searchNUser);
        list.add(searchDUser);

        return list.toString();
    }
}

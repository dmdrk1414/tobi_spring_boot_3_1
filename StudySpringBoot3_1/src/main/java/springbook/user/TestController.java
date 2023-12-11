package springbook.user;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import springbook.user.dao.*;
import springbook.user.domain.User;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class TestController {
//    @GetMapping("user/dao")
//    public String userDaoTest() throws SQLException, ClassNotFoundException {
//        NUserDao nUserDao = new NUserDao();
//        DUserDao dUserDao = new DUserDao();
//
//        User user_N = new User("NUser", "박승찬", "married");
//        User user_D = new User("DUser", "박승찬", "married");
//
//        nUserDao.add(user_N);
//        dUserDao.add(user_D);
//
//        User searchNUser = nUserDao.get(user_N.getId());
//        User searchDUser = dUserDao.get(user_D.getId());
//
//        List<User> list = new ArrayList<>();
//        list.add(searchNUser);
//        list.add(searchDUser);
//
//        return list.toString();
//    }

//    @GetMapping("user/dao/p72/test")
//    public String p72Test() throws SQLException, ClassNotFoundException {
//        UserDao userDao = new UserDao();
//
//        User user = new User("User", "박승찬", "married");
//
//        userDao.add(user);
//
//        User searchUser = userDao.get(user.getId());
//
//        return searchUser.toString();
//    }

//    @GetMapping("user/dao/p75/test")
//    public String p72Test() throws SQLException, ClassNotFoundException {
//        UserDao userDao = new UserDao();
//
//        User user = new User("User", "박승찬", "married");
//
//        userDao.add(user);
//
//        User searchUser = userDao.get(user.getId());
//
//        return searchUser.toString();
//    }

//    @GetMapping("user/dao/p81/test")
//    public String p72Test() throws SQLException, ClassNotFoundException {
//        // client(컨트롤러)에게 DConnectionMaker을 만드는 책임을 넘긴다.
//        NConnectionMaker nConnectionMaker = new NConnectionMaker();
//        DConnectionMaker dConnectionMaker = new DConnectionMaker();
//
//        UserDao nUserDao = new UserDao(nConnectionMaker);
//        UserDao dUserDao = new UserDao(dConnectionMaker);
//
//        User user_N = new User("NUser", "박승찬", "married");
//        User user_D = new User("DUser", "박승찬", "married");
//
//        nUserDao.add(user_N);
//        dUserDao.add(user_D);
//
//        User searchNUser = nUserDao.get(user_N.getId());
//        User searchDUser = dUserDao.get(user_D.getId());
//
//        List<User> list = new ArrayList<>();
//        list.add(searchNUser);
//        list.add(searchDUser);
//
//        return list.toString();
//    }

    @GetMapping("user/dao/p89/test")
    public String p72Test() throws SQLException, ClassNotFoundException {
        // client(컨트롤러)에게 DConnectionMaker을 만드는 책임을 넘긴다.
        NConnectionMaker nConnectionMaker = new NConnectionMaker();
        DConnectionMaker dConnectionMaker = new DConnectionMaker();

        UserDao nUserDao = new DaoFactory().userDaoNConnection();
        UserDao dUserDao = new DaoFactory().userDaoDConnection();

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

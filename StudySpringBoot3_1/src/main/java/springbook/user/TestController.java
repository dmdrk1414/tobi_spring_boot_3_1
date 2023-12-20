package springbook.user;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
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

//    @GetMapping("user/dao/p89/test")
//    public String p72Test() throws SQLException, ClassNotFoundException {
//        // client(컨트롤러)에게 DConnectionMaker을 만드는 책임을 넘긴다.
//        UserDao dUserDao = new DaoFactory().userDaoDConnection();
//
//        User user_D = new User("DUser", "박승찬", "married");
//
//        dUserDao.add(user_D);
//
//        User searchDUser = dUserDao.get(user_D.getId());
//
//        List<User> list = new ArrayList<>();
//        list.add(searchDUser);
//
//        return list.toString();
//    }

//    @GetMapping("user/dao/p97/test")
//    public String p97Test() throws SQLException, ClassNotFoundException {
//        // p97 Ioc 제어의 역전의 파트에서 DaoFactory#userDaoDConnection의 제어를 context에 의해 운영된다.
//        // DaoFactory.class에 있는 userDaoDConnection의 메서드를 Bean으로 등록
//        // Context을 이용해 객체생성 및 관리
//        // @Configuration으로 관리하는 클래스는 AnnotationConfigApplicationContext을 이용하여 관리한다.₩
//        AnnotationConfigApplicationContext context =
//                new AnnotationConfigApplicationContext(DaoFactory.class);
//        UserDao dUserDao = context.getBean("userDaoDConnection", UserDao.class);
//
//        User user_D = new User("DUser", "박승찬", "married");
//
//        dUserDao.add(user_D);
//
//        User searchDUser = dUserDao.get(user_D.getId());
//
//        List<User> list = new ArrayList<>();
//        list.add(searchDUser);
//
//        return list.toString();
//    }

    @GetMapping("user/dao/p124/test")
    public String p124Test() throws SQLException, ClassNotFoundException {
        // 124
        // 기본적으로는 UserDaoTest와 같지만 설정용 클래스를 CountingDaoFactory로 변경
        // CountingConnectionMaker 빈을 가져온다.
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(CountingDaoFactory.class);
        UserDao dUserDao = context.getBean("countUserDao", UserDao.class);

        User user_D = new User("DUser", "박승찬", "married");

        dUserDao.add(user_D);

        User searchDUser = dUserDao.get(user_D.getId());

        List<User> list = new ArrayList<>();
        list.add(searchDUser);

        // p124
        // CountingConnectionMaker빈을 가져온다.
        // COuntingConnectionMaker에는 그동안 DAO를 통해 DB 커넥션을 요청한 횟수만큼 카운터가 증가.
        // DL(읜존관계 검색)을 사용하면 이름을 이용해 어떤 빈이든 가져올 수 있다.
        CountingConnectionMaker countingConnectionMaker = context.getBean("countConnectionMaker", CountingConnectionMaker.class);
        System.out.println(countingConnectionMaker.getCounter());

        return list.toString();
    }

    /**
     * DaoFactory 없이 UserDao 생성하는 방법
     *
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    @GetMapping("user/dao/p135/test")
    public String p135Test() throws SQLException, ClassNotFoundException {
        // p134
        // xml 파일의 등록한 Bean을 Context으로 이용하는 방법
        // GenericXmlApplicationContext을 많이 사용한다.
        GenericXmlApplicationContext context =
                new GenericXmlApplicationContext("xml/applicationContext.xml");

        // 관련 class을 이용한 content 이용 방법 보통 쓰지 않는다.
//        ClassPathXmlApplicationContext context =
//                new ClassPathXmlApplicationContext("/applicationContext.xml");
        UserDao dUserDao = context.getBean("userDao", UserDao.class);

        User user_D = new User("DUser", "박승찬", "married");

        dUserDao.add(user_D);

        User searchDUser = dUserDao.get(user_D.getId());

        List<User> list = new ArrayList<>();
        list.add(searchDUser);

        return list.toString();
    }

    @GetMapping("user/dao/p155/test")
    public String p155Test() throws SQLException, ClassNotFoundException {
        // p134
        // xml 파일의 등록한 Bean을 Context으로 이용하는 방법
        // GenericXmlApplicationContext을 많이 사용한다.
        GenericXmlApplicationContext context =
                new GenericXmlApplicationContext("xml/applicationContext.xml");

        // 관련 class을 이용한 content 이용 방법 보통 쓰지 않는다.
//        ClassPathXmlApplicationContext context =
//                new ClassPathXmlApplicationContext("/applicationContext.xml");
        UserDao dUserDao = context.getBean("userDao", UserDao.class);

        User user_D = new User("DUser", "박승찬", "married");

        dUserDao.add(user_D);

        User searchDUser = dUserDao.get(user_D.getId());

        List<User> list = new ArrayList<>();
        list.add(searchDUser);

        if (!user_D.getName().equals(searchDUser.getName())) {
            System.out.println("테스트 실패 name");
        } else if (!user_D.getPassword().equals(searchDUser.getPassword())) {
            System.out.println("테스트 실패 password");
        } else {
            System.out.println("조회 테스트 성공");
        }

        return list.toString();
    }
}

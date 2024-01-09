package springbook.user.dao;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import springbook.user.constant.DbConstant;

import javax.sql.DataSource;

/**
 * 팩토리 클래스 UserDao을 생성 책임을 맡았다.
 */
@Configuration // p 96 빈 팩토리를 위한 오브젝트 설정을 담당하는 클래스라고 인식.
public class DaoFactory {
    @Bean
    public DataSource dataSource() {
        SimpleDriverDataSource dataSource = new SimpleDriverDataSource();

        dataSource.setDriverClass(com.mysql.jdbc.Driver.class);
        dataSource.setUrl(DbConstant.JDBC_URL.getStr());
        dataSource.setUsername(DbConstant.ID.getStr());
        dataSource.setPassword(DbConstant.PASSWORD.getStr());

        return dataSource;
    }

    @Bean
    public UserDaoJdbc userDao() {
        UserDaoJdbc userDao = new UserDaoJdbc();
        userDao.setDataSource(dataSource());
        return userDao;
    }
}

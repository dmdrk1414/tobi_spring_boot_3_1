package springbook.user.dao;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * p124
 * 새로운 의존관계를 컨테이너가 사용할 설정정보를 이용해 만든다.
 * connectionMaker() 메소드에서 CountingConnectionMaker 타입 오브젝트를 생성
 * 실제 DB 커넥션을 만들어주는 DConnectionMaker는 이름이 realCounnectionMaker()인 메소드에서 생성한다.
 * 기존의 DAO 설정 부분을 변경하지 않고.
 * 계속해서 connectionMaker() 메소드를 통해 생성되는 오브젝트를 사용하게 된다.
 * <p>
 * 주의점은 다른 빈과의 이름이 겹치면 안된다(DaoFactory의 빈과 이름이 겹치기 금지)
 */
@Configuration
public class CountingDaoFactory {
    /**
     * * p124
     * 모든 DAO는 여전히 connectionMaker()에서
     * 만들어지는 오브젝트를 DI 받는다.
     *
     * @return
     */
//    @Bean
//    public UserDao countUserDao() {
//        return new UserDao(countConnectionMaker());
//    }

    /**
     * * p124
     * 여기서 Connection은 DConnection이다.
     *
     * @return
     */
    @Bean
    public ConnectionMaker countConnectionMaker() {
        return new CountingConnectionMaker(realConnectionMaker());
    }

    @Bean
    public ConnectionMaker realConnectionMaker() {
        return new DConnectionMaker();
    }
}

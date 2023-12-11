package springbook.user.dao;

/**
 * 팩토리 클래스 UserDao을 생성 책임을 맡았다.
 */
public class DaoFactory {
    /**
     * userDao 메소드를 호출하면 DConnectinMaker을 사용해 DB 커넥션을 가져오도록
     * 이미 설정된 UserDao 오브젝트를 돌려준다.
     * 설께도로서의 팩토리
     * 컴포넌트의 구조와 관계를 정의한 설계도 같은 역할을 한다.
     * 어떤 오브젝트가 어떤 오브젝틀르 사용하는지를 정의해높은 코드라고 생각하면 된다.
     *
     * @return
     */
    public UserDao userDaoDConnection() {
        UserDao userDao = new UserDao(getConnectionMaker());
        return userDao;
    }

    public UserDao userDaoNConnection() {
        UserDao userDao = new UserDao(getConnectionMaker());
        return userDao;
    }

    private NConnectionMaker getConnectionMaker() {
        return new NConnectionMaker();
    }
}

package springbook.user.service;

import springbook.user.dao.UserDao;

// p 331 userService 클래스 등록
public class UserService {

  UserDao userDao;

  public void setUserDao(final UserDao userDao) {
    this.userDao = userDao;
  }
}

package com.ljx.spring6.iocxml.auto.service;

import com.ljx.spring6.iocxml.auto.dao.UserDao;
import com.ljx.spring6.iocxml.auto.dao.UserDaoImpl;

public class UserServiceImpl implements UserService{

    private UserDao userDao;

    public UserDao getUserDao() {
        return userDao;
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public void addUserService() {
        System.out.println("userService方法执行了");
        /*UserDao userDao = new UserDaoImpl();
        userDao.addUserDao();*/

        userDao.addUserDao();
    }
}

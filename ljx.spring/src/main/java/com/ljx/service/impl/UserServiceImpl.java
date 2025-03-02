package com.ljx.service.impl;

import com.ljx.anno.Bean;
import com.ljx.anno.Di;
import com.ljx.dao.UserDao;
import com.ljx.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Bean
public class UserServiceImpl implements UserService {

    @Di
    private UserDao userDao;


    @Override
    public void add() {
        System.out.println("service add...");
        //调用dao方法
    }
}

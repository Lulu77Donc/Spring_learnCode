package com.ljx.dao.impl;

import com.ljx.anno.Bean;
import com.ljx.dao.UserDao;
import org.springframework.stereotype.Repository;

@Bean
public class UserDaoImpl implements UserDao {


    @Override
    public void run() {
        System.out.println("dao add...");
    }
}

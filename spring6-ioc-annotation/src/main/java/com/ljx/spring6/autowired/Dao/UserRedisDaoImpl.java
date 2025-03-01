package com.ljx.spring6.autowired.Dao;

import org.springframework.stereotype.Repository;

@Repository
public class UserRedisDaoImpl implements UserDao{
    @Override
    public void add() {
        System.out.println("dao redis...");
    }
}

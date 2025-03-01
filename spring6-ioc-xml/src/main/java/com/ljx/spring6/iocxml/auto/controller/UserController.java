package com.ljx.spring6.iocxml.auto.controller;

import com.ljx.spring6.iocxml.auto.service.UserService;
import com.ljx.spring6.iocxml.auto.service.UserServiceImpl;

public class UserController {

    private UserService userService;

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public void addUser(){
        System.out.println("controller方法执行了..");
        /*UserService userService = new UserServiceImpl();
        userService.addUserService();*/

        //调用service方法
        userService.addUserService();
    }
}

package com.ljx;

import com.ljx.bean.AnnotationApplicationContext;
import com.ljx.bean.ApplicationContext;
import com.ljx.service.UserService;

public class TestUser {
    public static void main(String[] args) {
        ApplicationContext context =
                new AnnotationApplicationContext("com.ljx");
        UserService userService = (UserService) context.getBean(UserService.class);
        System.out.println(userService);
        userService.add();
    }
}

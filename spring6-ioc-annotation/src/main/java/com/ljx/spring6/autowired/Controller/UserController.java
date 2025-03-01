package com.ljx.spring6.autowired.Controller;

import com.ljx.spring6.autowired.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class UserController {

    //注入service
    @Autowired//根据类型找到对应对象，完成注入
    private UserService userService;

    public void add(){
        System.out.println("controller..");
        userService.add();
    }
}

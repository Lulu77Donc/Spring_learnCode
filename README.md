这里写一些关于学习spring中值得记录的知识点，之前直接学了项目那里入手的springboot，现在看回spring，才会知道原来是怎样运行的

# 关于sping中如何完成自动注入
springboot中我们可以直接@AutoWired注解去实现自动注入，但是在spring中，我们通过xml配置实现自动注入。
这里我们不需要再去一个个把bean写出来，只需要在controller，service，写相应的下一层类型属性，如controller中写service类型，并且写上其set方法，直接调用相应的方法，具体如下
```java
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
```
```xml
<bean id="userController" class="com.ljx.spring6.iocxml.auto.controller.UserController"
          autowire="byType">

    </bean>
    <bean id="userService" class="com.ljx.spring6.iocxml.auto.service.UserServiceImpl"
          autowire="byType">

    </bean>
    <bean id="userDao" class="com.ljx.spring6.iocxml.auto.dao.UserDaoImpl">

    </bean>
```
spring会从属性中找到对应xml上配置的bean，这时候会根据你标签autowired去选择对应自动装配的方法

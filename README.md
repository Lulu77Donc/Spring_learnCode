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

# 基于注解管理bean
## 开启组件扫描
要想实现注解开发，spring最基本功能之一，为了不用再去xml文件中配置创建bean，我们要先开启组件扫描
基本操作是：在xml配置文件中填入
```xml
<context:component-scan base-package="com.ljx.spring6"></context:component-scan>
```
base-package是要扫描的包及其子包，开启后我们就可以在class上加入注解（@Compoent，@Controller，@Repository，@Service）,之后我们就可以在测试类中直接获取bean了

## @Autowired和@Qualifier联合注入
@Autowired注解注入的方法有好几种，最常见的属性注入，还有创建属性set方法的set注入，构造方法注入，构造方法形参注入，还有只有一个构造函数可以不用注解隐性注入。
除此之外还有一个比较特殊，需要用到两个注解
打个比方，当我们dao层接口有两个实现类，正常使用autowired是无法注入的，两个bean冲突，所以我们需要在上qualifier value值为对应bean名字，就可以实现对应bean注入，不会冲突

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

# 手写Ioc
## 原理分析
对于spring来说，Ioc绝对是其核心中的核心，管理bean，创建bean，bean的生命周期全都围绕着Ioc容器，那我们要想自己手写Ioc，就必须要先理解其原理。
首先第一点，我们要创建的对象如何交给Ioc容器管理？前面我们用注解开发就需要加上@Compoent告诉Ioc我们要让他管理bean了，所以我们需要在需要管理的bean中自定义注解@Bean，并且让spring扫描注解对应的包，我们就可以实现该功能了。接着我们要获取bean，在之前，我们是通过创建BeanFactory子类ApplicationContext，并通过getbean方法来获取bean的，所以我们还要自己写一个ApplicationContext，并定义方法，返回我们需要的bean对象。还有，我们每次想要获取bean，总不可能每次都要new一个context吧？还需要完成自动注入吧？所以我们还需要创建一个注解来定义属性注入吧。
## 步骤
综上分析，我们需要的步骤如下：
1、创建子模块
2、创建测试类 service dao
3、创建两个注解 @Di @Bean
4、创建bean容器接口 ApplicationContext定义方法，返回对象
5、实现bean容器接口
（1）返回对象
（2）根据包规则加载bean 扫描com.ljx及其子包所有类，看是否有@Bean注解，如果有，通过反射实例化

。。。
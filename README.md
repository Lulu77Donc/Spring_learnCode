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
## 手写创建bean
### 原理分析
对于spring来说，Ioc绝对是其核心中的核心，管理bean，创建bean，bean的生命周期全都围绕着Ioc容器，那我们要想自己手写Ioc，就必须要先理解其原理。
首先第一点，我们要创建的对象如何交给Ioc容器管理？前面我们用注解开发就需要加上@Compoent告诉Ioc我们要让他管理bean了，所以我们需要在需要管理的bean中自定义注解@Bean，并且让spring扫描注解对应的包，我们就可以实现该功能了。接着我们要获取bean，在之前，我们是通过创建BeanFactory子类ApplicationContext，并通过getbean方法来获取bean的，所以我们还要自己写一个ApplicationContext，并定义方法，返回我们需要的bean对象。还有，我们每次想要获取bean，总不可能每次都要new一个context吧？还需要完成自动注入吧？所以我们还需要创建一个注解来定义属性注入吧。
### 步骤
综上分析，我们需要的步骤如下：
1、创建子模块
2、创建测试类 service dao
3、创建两个注解 @Di @Bean
4、创建bean容器接口 ApplicationContext定义方法，返回对象
5、实现bean容器接口
（1）返回对象
（2）根据包规则加载bean 扫描com.ljx及其子包所有类，看是否有@Bean注解，如果有，通过反射实例化

## 手写属性注入
首先，我们要先了解属性注入在原先是怎么实现的，需要满足什么条件。第一点，我们在注入属性时需要加入@Autowired代表我们要注入，这个属性一般是一个对象。
因此我们需要创建一个注解，这个注解标记让我们知道哪些属性是要注入的。前面我们已经完成bean的创建，接着我们只需要在beanFactory的map集合中获取键值对，
再遍历获取值（就是我们的对象），接着再通过反射获取到对象的属性。随后我们便可依次遍历看看哪些属性上有注解，有的话我们就从beanFactory中找出对应类型属性的对象（service，dao） 赋值给 通过反射获取到的属性。
这样我们属性赋值就完成了


# 开发日志
## 遇到的问题
我在一直用push到github时，电脑崩溃蓝屏，我再次打开项目想要push时弹出0 file committed, 81 files failed to commit: 手写Ioc，bean的创建 cannot lock ref 'HEAD': unable to resolve reference 'refs/heads/master': reference broken
gpt反应这是cannot lock ref 'HEAD': unable to resolve reference 'refs/heads/master': reference broken，这通常是由于 Git 仓库的 HEAD 或 refs/heads/master 引用损坏导致的。
看来是因为.git文件损坏导致无法上传，github给的解决方法非常复杂。我再解决完问题后总结最方便的方法。
首先，确保github上代码已经更新，然后我们删掉原来这个项目，在新的目录里（创建新的项目）用终端打开，用git重新克隆
```git
git clone "+github仓库http链接"
```
这样我们就可以从远端仓库同步到新的项目里，如果不确定远端仓库是否安全，可以先克隆，如果没有问题，就可以直接删除旧项目。或者可以check一下，这里我还没有试过

### 补充
我们在push后如果想要撤销，可以revert，这里撤销的是本地的仓库，我们在idea终端里直接强制push，就可以覆盖掉远程仓库。但是这样很容易有风险，建议先备份好数据。不然很容易文件丢失

# 关于代理
代理在java基础中也有学过，但是就像反射一样，代理在Spring中也尤为重要，特别是AOP的实现。
前面关于代理的知识还是比较抽象，我们从简单例子实现，现在我们有一个Calculator接口，里面有加减乘除抽象方法。
```java
public interface Calculator {

    int add(int i,int j);

    int sub(int i,int j);

    int mul(int i,int j);

    int div(int i,int j);
}
```
然后我们设立一个实现类用于完成方法最基础的功能
```java
public class CalculatorImpl implements Calculator{
    @Override
    public int add(int i, int j) {

        int result = i + j;
        System.out.println("方法内部 result=" + result);
        return result;
    }

    @Override
    public int sub(int i, int j) {
        int result = i - j;
        System.out.println("方法内部 result=" + result);
        return result;
    }

    @Override
    public int mul(int i, int j) {
        int result = i * j;
        System.out.println("方法内部 result=" + result);
        return result;
    }

    @Override
    public int div(int i, int j) {
        int result = i / j;
        System.out.println("方法内部 result=" + result);
        return result;
    }
}

```
好了，现在我想要完成在这些方法基础上再加入日志功能，方法执行前和执行后我们都要执行，我们应该如何实现呢？
其实无需修改实现类中方法的代码，实在是过于麻烦，我们在原有基础上加入代理类即可。
首先我们要弄清楚，原来的功能我们不能舍弃，是不是让另一个类来实现我们现在的功能，顺便把扩展功能（日志）也实现。这里我直接引用动态代理
想要获得我们想要的代理对象，第一步，是不是应该传入我们原来实现类的对象，在代理类中通过构造器传入变量。接着我们设计一个方法，用于返回代理对象
其中核心是Proxy.newProxyInstance(classLoader,interfaces,invocationHandler)，姑且理解为JDK已经帮我们实现动态代理的方法了，封装起来，我们直接使用即可
三个参数分别对应1 ClassLoader:加载动态生成代理类的类加载器。
2 Class<?>[] interface:目标对象实现的所有接口的class类型数组。
3 InvocationHandler:设置代理对象实现目标对象方法的过程。
这里主要是invocationHandler，我们需要重写其中invoke方法，这里面包括我们基础功能和扩展功能，最后返回结果就可以了
```java
public class ProxyFactory {

    //目标对象
    private Object target;

    public ProxyFactory(Object target) {
        this.target = target;
    }

    //返回代理对象
    public Object getProxy(){
        /*
        Proxy.newProxyInstance()方法
        有三个参数
        1 ClassLoader:加载动态生成代理类的类加载器
        2 Class<?>[] interface:目标对象实现的所有接口的class类型数组
        3 InvocationHandler:设置代理对象实现目标对象方法的过程
        * */

        //1 ClassLoader:加载动态生成代理类的类加载器
        ClassLoader classLoader = target.getClass().getClassLoader();

        //2 Class<?>[] interface:目标对象实现的所有接口的class类型数组
        Class<?>[] interfaces = target.getClass().getInterfaces();

        //3 InvocationHandler:设置代理对象实现目标对象方法的过程
        InvocationHandler invocationHandler = new InvocationHandler() {

            //1 代理对象
            //2 需要重写目标对象方法
            //3 method方法里面参数
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                //方法调用前执行
                System.out.println("[动态代理][日志]" + method.getName()+"参数："+ Arrays.toString(args));
                //调用目标方法
                Object result = method.invoke(target, args);
                //方法调用后执行
                System.out.println("[动态代理][日志]" + method.getName()+"结果："+ result);
                return result;
            }
        };
        return Proxy.newProxyInstance(classLoader,interfaces,invocationHandler);

    }
}

```

别急，是不是还有很多问题
## 为什么 JDK 动态代理必须基于接口？
JDK 动态代理的原理：
Proxy.newProxyInstance() 方法会在运行时动态创建一个代理类，该类实现了目标对象的所有接口。
由于 Java 语言的设计 不允许动态继承具体类，但 允许实现接口，所以 JDK 代理要求 目标对象必须实现接口。
当我们调用 new ProxyFactory(new CalculatorImpl()).getProxy(); 时：
JDK 代理会动态创建一个 新的类，该类实现了 Calculator 接口。
代理对象实际上是这个新类的实例，它并不是 CalculatorImpl 的实例，而是 实现了相同接口的一个代理对象。

## 方法是在invoke实现的，然后才返回代理对象，我们是如何通过代理对象调用invoke的?
JDK 动态代理调用 invoke 的过程
   当你通过 代理对象 调用方法时，比如：
```
javaCalculator proxy = (Calculator) new ProxyFactory(new CalculatorImpl()).getProxy();
proxy.add(3, 5);
```
其执行过程如下：
代理对象接收 add(3, 5) 方法调用（实际上调用的是 JDK 生成的动态代理类）。
代理对象的 add 方法内部会调用 InvocationHandler.invoke() 方法。
在 invoke() 方法内部：
先执行前置增强逻辑（打印日志）。
通过 method.invoke(target, args) 反射调用目标对象的方法。
记录日志，返回结果。
最终返回计算结果 3 + 5 = 8。

总结就是：
代理对象是 JDK 生成的类，它实现了 Calculator 接口，但方法内部并没有真正执行 add() 逻辑，而是调用 InvocationHandler.invoke() 方法。
Proxy.newProxyInstance() 会在运行时生成 $Proxy0 代理类，该类的 add() 方法内部直接调用 h.invoke()。
h.invoke() 方法执行 日志增强逻辑 + 反射调用目标对象方法，最终返回方法结果。

# 事务
关于事务，主要是用于service控制dao层，为避免逻辑报错，而数据库数据改动。
开启事务需要在xml配置文件中加上注解驱动，接着在要控制事务方法或类上加上注解@Transactional(propagation=...)
这里propagation后接的一般为REQUIRES_NEW和REQUIRED，前者是开启新的事务，比如我们在service再加上一个service类，这里我用的是checkoutservice
我们在这个类里操作bookservice，两个都有事务注解，这里会当成两个注解，如果第一个执行成功，第二个失败，那么第一个事务完成就结束了，第二个事务失败回滚。
后者是都是同一个事务，其中一个失败都回滚
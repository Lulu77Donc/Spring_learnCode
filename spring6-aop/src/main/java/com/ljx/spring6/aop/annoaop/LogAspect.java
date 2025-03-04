package com.ljx.spring6.aop.annoaop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.Arrays;

//切面类
@Aspect//切面类
@Component//ioc容器
public class LogAspect {

    //设置切入点和通知类型
    //excution(访问修饰符 方法返回值 增强方法所在类型的全类名.方法名 参数列表) (..)表示任意参数 *表示任意包
    //切入点表达式： execution(public int com.ljx.spring.aop.target.Calculator.div(int,int))
    //通知类型：
    // 前置 @Before("切入点表达式配置切入点")
    @Before(value = "execution(public int com.ljx.spring6.aop.annoaop.CalculatorImpl.*(..))")
    public void beforeMethod(JoinPoint joinPoint){
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();//参数
        System.out.println("前置通知，方法名称:"+methodName+",参数："+ Arrays.toString(args));
    }

    // 返回 @AfterReturning,可以得到方法返回的结果
    @AfterReturning(value = "execution(* com.ljx.spring6.aop.annoaop.CalculatorImpl.*(..))",returning = "result")
    public void AfterReturningMethod(JoinPoint joinPoint,Object result){
        String methodName = joinPoint.getSignature().getName();
        System.out.println("返回通知，方法名称:"+methodName+",目标方法返回结果："+result);
    }

    // 异常 @AfterThrowing
    //目标方法出现异常，这个通知执行
    @AfterThrowing(value = "execution(* com.ljx.spring6.aop.annoaop.CalculatorImpl.*(..))",throwing = "ex")
    public void AfterThrowingMethod(JoinPoint joinPoint,Throwable ex){
        String methodName = joinPoint.getSignature().getName();
        System.out.println("异常通知，方法名称:"+methodName+",目标方法异常信息："+ex);
    }

    // 后置 @After()
    @After(value = "execution(* com.ljx.spring6.aop.annoaop.CalculatorImpl.*(..))")
    public void afterMethod(JoinPoint joinPoint){
        String methodName = joinPoint.getSignature().getName();
        System.out.println("后置通知，方法名称:"+methodName);
    }

    // 环绕 @Around()
    @Around(value = "execution(* com.ljx.spring6.aop.annoaop.CalculatorImpl.*(..))")
    public Object aroundMethod(ProceedingJoinPoint joinPoint){
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        String argString = Arrays.toString(args);
        Object result = null;
        try{
            System.out.println("环绕通知==目标方法之前执行");

            //调用目标方法
            result = joinPoint.proceed();

            System.out.println("环绕通知==目标方法返回之后执行");
        }catch (Throwable throwable){
            throwable.printStackTrace();
            System.out.println("环绕通知==目标方法出现异常执行");
        }finally {
            System.out.println("环绕通知==目标方法执行完毕执行");
        }
        return result;
    }

}

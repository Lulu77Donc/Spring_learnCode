package com.ljx.spring6.aop.xmlaop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.Arrays;

//切面类
@Component//ioc容器
public class LogAspect {

    //前置通知
    public void beforeMethod(JoinPoint joinPoint){
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();//参数
        System.out.println("前置通知，方法名称:"+methodName+",参数："+ Arrays.toString(args));
    }

    // 返回通知
    public void AfterReturningMethod(JoinPoint joinPoint,Object result){
        String methodName = joinPoint.getSignature().getName();
        System.out.println("返回通知，方法名称:"+methodName+",目标方法返回结果："+result);
    }

    // 异常通知
    public void AfterThrowingMethod(JoinPoint joinPoint,Throwable ex){
        String methodName = joinPoint.getSignature().getName();
        System.out.println("异常通知，方法名称:"+methodName+",目标方法异常信息："+ex);
    }

    // 后置通知
    public void afterMethod(JoinPoint joinPoint){
        String methodName = joinPoint.getSignature().getName();
        System.out.println("后置通知，方法名称:"+methodName);
    }

    // 环绕通知
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

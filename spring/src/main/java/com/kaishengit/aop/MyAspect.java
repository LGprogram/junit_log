package com.kaishengit.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

/**
 * Created by liu on 2017/1/9.
 */
@Component
@Aspect
public class MyAspect {

    @Pointcut("execution(* com.kaishengit.service..*.*(..))")
    public void pt(){}
    @Before("pt()")
    public void beforeAdvice(){
        System.out.println("beforeAdvice");
    }
    @AfterReturning(value = "pt()",returning = "result")
    public void afterAdvice(Object result){
        System.out.println("afterAdvice:"+result);
    }
    @AfterThrowing("pt()")
    public void exceptionAdvice(){
        System.out.println("exceptionAdvice");
    }
    @After("pt()")
    public void finallyAdvice(){
        System.out.println("finallyAdvice");
    }
    /*@Around("pt()")
    public void aroundAdvice(ProceedingJoinPoint proceedingJoinPoint){
        System.out.println("//before//");
        try {
            Object result = proceedingJoinPoint.proceed();
            System.out.println("//after//");
        } catch (Throwable throwable) {
            System.out.println("//exception//");
            throwable.printStackTrace();
        }finally {
            System.out.println("//finally//");
        }
    }*/
}

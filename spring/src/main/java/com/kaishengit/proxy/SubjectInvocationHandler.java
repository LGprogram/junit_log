package com.kaishengit.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created by liu on 2017/1/8.
 */
public class SubjectInvocationHandler implements InvocationHandler {

    private Object target;

    public SubjectInvocationHandler(Object target){
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if("sayHello".equals(method.getName())){
            System.out.println(".....brfore.....");
            Object result = method.invoke(target,args);
            System.out.println("...after...");
            return result;
        }else{
             return method.invoke(target,args);
        }

    }
}

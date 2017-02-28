package com.kaishengit.proxy;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * Created by liu on 2017/1/8.
 */
public class TargetMethodInterceptor implements MethodInterceptor {


    @Override
    public Object intercept(Object target, Method method, Object[] params, MethodProxy methodProxy) throws Throwable {

        System.out.println("~~~before~~~");
        Object result = methodProxy.invokeSuper(target,params);
        System.out.println("~~~after~~~");
        return result;
    }
}

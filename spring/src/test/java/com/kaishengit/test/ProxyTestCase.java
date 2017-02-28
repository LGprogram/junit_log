package com.kaishengit.test;

import com.kaishengit.proxy.*;
import net.sf.cglib.proxy.Enhancer;
import org.junit.Test;

import java.lang.reflect.Proxy;

/**
 * Created by liu on 2017/1/8.
 */
public class ProxyTestCase {

    @Test
    public void proxy() {
        Subject subject = new SubjectProxy();
        subject.sayHello();
    }

    @Test
    public void jdkProxy(){
        RelSubject relSubject = new RelSubject();
        SubjectInvocationHandler subjectInvocationHandler = new SubjectInvocationHandler(relSubject);
        Subject subject  = (Subject) Proxy.newProxyInstance(relSubject.getClass().getClassLoader(),relSubject.getClass().getInterfaces(),subjectInvocationHandler);

        subject.sayHello();
        subject.save();
    }
    @Test
    public void cglibProxy(){

        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(Target.class);
        enhancer.setCallback(new TargetMethodInterceptor());

        Target target = (Target) enhancer.create();
        target.save();
        target.update();
    }

}

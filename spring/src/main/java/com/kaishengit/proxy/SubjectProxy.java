package com.kaishengit.proxy;

/**
 * Created by liu on 2017/1/8.
 */
public class SubjectProxy implements Subject {

    private RelSubject relSubject = new RelSubject();

    @Override
    public void save() {
        relSubject.save();
    }

    @Override
    public void sayHello() {
        System.out.println("开启事务");
        try{
            relSubject.sayHello();
            System.out.println("提交事务");
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("回滚事务");
        }

    }
}

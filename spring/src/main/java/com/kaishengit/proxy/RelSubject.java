package com.kaishengit.proxy;

/**
 * Created by liu on 2017/1/8.
 */
public class RelSubject implements Subject {
    @Override
    public void save() {
        System.out.println("RelSubject save");
    }

    @Override
    public void sayHello() {
        System.out.println("RelSubject sayHello");
    }
}

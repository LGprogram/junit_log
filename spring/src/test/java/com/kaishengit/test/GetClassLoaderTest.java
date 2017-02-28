package com.kaishengit.test;

import org.junit.Test;

/**
 * Created by liu on 2017/1/7.
 */
public class GetClassLoaderTest {

    @Test
    public void test(){

        Object object = new Object();

        ClassLoader classLoader = object.getClass().getClassLoader();
        String name = classLoader.getClass().getName();
        System.out.println(name);
    }


}

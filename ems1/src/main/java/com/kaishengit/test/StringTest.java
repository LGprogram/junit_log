package com.kaishengit.test;

/**
 * Created by liu on 2017/2/22.
 */
public class StringTest {



    public  static void main(String[] args) throws ClassNotFoundException {
        String x = "abcdefg";
        StringBuffer sb = new StringBuffer(x);
        StringBuffer sb1 = sb.reverse();
        System.out.println(sb1);

        long  time = System.currentTimeMillis();
        ClassLoader classLoader = Class.forName("com.kaishengit.test.StringTest").getClassLoader();
        System.out.println(classLoader);
        ClassLoader classloader = ClassLoader.getSystemClassLoader();
        System.out.println(classloader);
        Object o = Class.forName("com.kaishengit.test.StringTest").getSuperclass();
        System.out.println(o);

    }


}

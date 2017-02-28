package com.kaishengit.test;

import com.kaishengit.Application;
import com.kaishengit.dao.UserDao;
import com.kaishengit.dao.impl.UserDaoImpl;
import com.kaishengit.pojo.User;
import com.kaishengit.service.UserService;
import com.kaishengit.service.impl.UserServiceImpl;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

/**
 * Created by liu on 2017/1/7.
 */
public class SpringTestCase {
    @Test
    public void load(){
        /*ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");*/

        /*UserDao userDao = applicationContext.getBean("userDaoImpl", UserDaoImpl.class);

        userDao.save();
        userDao.update();*/
    }
    @Test
    public void useService(){
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        //抛弃applicationContext.xml文件后
        /*ApplicationContext applicationContext = new AnnotationConfigApplicationContext(Application.class);*/
        /*严格遵循getBean()中放的是自动导入的类的类名首字母小写的类名*/
        UserService userService = (UserService) applicationContext.getBean("userServiceImpl");
        User user = new User("xiaoming","123231");
        userService.save(user);
        /*User user = userService.findById(12);
        user.setUsername("jimi_jin");

        userService.update(user);*/

       /* List<User> userList = userService.findAll();
        for (User user : userList){
            System.out.println(user);
        }
        Long l = userService.count();
        System.out.println(l);*/
    }
}

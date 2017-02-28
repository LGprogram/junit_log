package com.kaishengit.test;

import com.kaishengit.pojo.User;
import com.kaishengit.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by liu on 2017/1/9.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")//加载spr容器
/*@ContextConfiguration(classes = com.kaishengit.Application.class)*///去掉xml文件后
public class SpringAnnoationTestCase {
    @Autowired
    private UserService userService;
    @Test
    public void useService(){

        User user = new User("xiaoming","123231");
        userService.save(user);
    }





}

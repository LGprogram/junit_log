package com.kaishengit;

import com.kaishengit.dao.UserDao;
import com.kaishengit.dao.impl.UserDaoImpl;
import com.kaishengit.service.UserService;
import com.kaishengit.service.impl.UserServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * Created by liu on 2017/1/9.
 */
@Configuration//xml文件框架部分
@ComponentScan//自动扫描，从自己所在的包开始
@EnableAspectJAutoProxy//自动注解aop
public class Application {
    @Bean
    public UserDao userDao(){
        return new UserDaoImpl();
    }
    @Bean
    public UserService userService( UserDao userDao){
        UserServiceImpl userService = new UserServiceImpl();
        userService.setUserDao(userDao);
        return userService;
    }

}

package com.kaishengit.service.impl;

import com.kaishengit.dao.UserDao;
import com.kaishengit.pojo.User;
import com.kaishengit.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Named;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * Created by liu on 2017/1/8.
 */
//@Service
@Named
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;


   /* private String name;
    private Integer age;
    private List<String> list;
    private Set<Double> set;
    private Map<String,Object> map;
    private Properties properties;*/

   /* public UserServiceImpl(UserDao userDao ){
        this.userDao = userDao;
        *//*System.out.println(name);*//*
    }*/
    @Autowired
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    /*public void setName(String name) {
        this.name = name;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public void setList(List<String> list) {
        this.list = list;
    }

    public void setSet(Set<Double> set) {
        this.set = set;
    }

    public void setMap(Map<String, Object> map) {
        this.map = map;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }*/

    @Override
    @Transactional
    public void save(User user) {
        userDao.save(user);
        if(1==1){
            throw new RuntimeException("我故意的");
        }
        userDao.save(user);

    }

    @Override
    public void update(User user) {
        userDao.update(user);
    }
    @Override
    public User findById(Integer id){
        return userDao.findByID(id);
    }
    @Override
    public List<User> findAll(){
        return userDao.findAll();
    }
    @Override
    public Long count(){
        return userDao.count();
    }
}

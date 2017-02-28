package com.kaishengit.dao;

import com.kaishengit.pojo.User;

import java.util.List;

/**
 * Created by liu on 2017/1/7.
 */
public interface UserDao {

    void save(User user);
    void update(User user);
    User findByID(Integer id);
    List<User> findAll();
    void del(Integer id);
    User findByUserName(String username);
    Long count();

}

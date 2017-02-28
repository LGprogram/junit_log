package com.kaishengit.service;

import com.kaishengit.pojo.User;

/**
 * Created by liu on 2017/1/10.
 */
public interface UserService {
    void save(User user) ;
    User findById(Integer id);
}

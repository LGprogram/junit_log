package com.kaishengit.service;

import com.kaishengit.pojo.User;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by liu on 2017/1/8.
 */

public interface UserService {
    void save(User user);
    void update(User user);
    User findById(Integer id);
    List<User> findAll();
    Long count();
}
package com.kaishengit.service;

import com.kaishengit.pojo.Role;
import com.kaishengit.pojo.User;
import com.kaishengit.util.db.Page;

import java.util.List;

/**
 * Created by liu on 2017/1/12.
 */
public interface UserService {
    Page<User> findAll(Integer p,String q_name,String q_role);

    void save(User user,Integer[] roleIds);

    void delete(Integer id);

    User findById(Integer id);

    void update(User user,Integer[] roleIds);

    List<Role> findAllRole();
}

package com.kaishengit.service;

import com.kaishengit.dbutil.Page;
import com.kaishengit.pojo.Role;
import com.kaishengit.pojo.User;

import java.util.List;

/**
 * Created by liu on 2017/2/7.
 */
public interface UserService {

    List<Role> findAllRole();

    Page<User> findAll(Integer p, String q_name, String q_role);

    void save(User user, Integer[] roleIds);

    void delete(Integer id);

    User findById(Integer id);

    void update(User user, Integer[] roleIds);
}

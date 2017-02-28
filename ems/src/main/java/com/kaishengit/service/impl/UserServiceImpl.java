package com.kaishengit.service.impl;

import com.kaishengit.dbutil.Page;
import com.kaishengit.mapper.RoleMapper;
import com.kaishengit.mapper.UserMapper;
import com.kaishengit.pojo.Role;
import com.kaishengit.pojo.User;
import com.kaishengit.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * Created by liu on 2017/2/7.
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Override
    public List<Role> findAllRole() {
        return roleMapper.findAll();
    }

    @Override
    public Page<User> findAll(Integer p, String q_name, String q_role) {
        Integer totals = userMapper.count(q_name,q_role).intValue();

        Page<User> page = new Page<>(totals,p);

        List<User> userList = userMapper.findAllN2( page.getStart(),page.getPageSize(),q_name,q_role);
        page.setItems(userList);
        return page;
    }

    @Override
    public void save(User user, Integer[] roleIds) {
        userMapper.save(user);
        if(roleIds!=null){
            for(Integer roleId:roleIds){
                roleMapper.save(user.getId(), roleId);
            }
        }
    }

    @Override
    public void delete(Integer id) {
        roleMapper.deleteByUserId(id);
        userMapper.delete(id);
    }

    @Override
    public User findById(Integer id) {
        return userMapper.findById(id);
    }

    @Override
    public void update(User user, Integer[] roleIds) {
        userMapper.update(user);
        roleMapper.deleteByUserId(user.getId());
        if(roleIds!=null){
            for(Integer roleId:roleIds){
                roleMapper.save(user.getId(), roleId);
            }
        }
    }
}

package com.kaishengit.service.impl;

import com.kaishengit.dbutil.Page;
import com.kaishengit.mapper.RoleMapper;
import com.kaishengit.mapper.UserMapper;
import com.kaishengit.pojo.Role;
import com.kaishengit.pojo.User;
import com.kaishengit.service.UserService;
import com.kaishengit.service.WeiXinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
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
    @Autowired
    private WeiXinService weiXinService;
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
    @Transactional
    public void save(User user, Integer[] roleIds) {
        //1.存储用户
        userMapper.save(user);
        //2.存储角色
        if(roleIds!=null){
            for(Integer roleId:roleIds){
                roleMapper.save(user.getId(), roleId);
            }
        }
        //3.同步将用户存储到微信企业号上
        com.kaishengit.dto.wx.User wxUser = new com.kaishengit.dto.wx.User();
        System.out.println(user.getId());
        wxUser.setUserid(user.getId().toString());

        wxUser.setName(user.getUsername());
        wxUser.setMobile(user.getMobel());
        wxUser.setDepartment(Arrays.asList(roleIds));

        weiXinService.saveUser(wxUser);

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
    @Transactional
    public void update(User user, Integer[] roleIds) {
        userMapper.update(user);
        roleMapper.deleteByUserId(user.getId());
        if(roleIds!=null){
            for(Integer roleId:roleIds){
                roleMapper.save(user.getId(), roleId);
            }
        }

        com.kaishengit.dto.wx.User wxUser = new com.kaishengit.dto.wx.User();
        wxUser.setUserid(user.getId().toString());
        wxUser.setDepartment(Arrays.asList(roleIds));
        weiXinService.updateUser(wxUser);

    }
}

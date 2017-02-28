package com.kaishengit.service.impl;

import com.kaishengit.mapper.UserMapper;
import com.kaishengit.pojo.User;
import com.kaishengit.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by liu on 2017/1/10.
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    //xml中配置Mapper接口的自动扫描:自动将接口的实现类对象放入Spirng容器中,此处自动注入的是接口的实现类
    private UserMapper userMapper;
    @Override
    @Transactional
    public void save(User user){
        userMapper.save(user);
        if(1==1){
            throw new RuntimeException("故意引发");
        }
        userMapper.save(user);
    }

    @Override
    public User findById(Integer id) {
        return userMapper.findById(id);
    }
}

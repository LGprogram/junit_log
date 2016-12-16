package com.kaishengit.service;

import com.kaishengit.entity.User;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by liu on 2016/12/14.
 */
public class UserServiceTest {
    private UserService userService = new UserService();
    @Test
    public void findById() throws Exception {
        User user = userService.findById(2);
        user = userService.findById(2);
        System.out.println(user);
    }

    @Test
    public void findAll() throws Exception {
        List<User> userList = userService.findAll();
        userList = userService.findAll();
        System.out.println(userList);
    }

    @Test
    public void save() throws Exception {
        List<User> userList = userService.findAll();
        int beforeSize = userList.size();
        System.out.println("beforeSize:" + beforeSize);

        User user = new User("梨花",7,"美国","150373");
        userService.save(user);

        userList = userService.findAll();
        int afterSize = userList.size();
        System.out.println("afterSize:" + afterSize);
        Assert.assertEquals(beforeSize+1,afterSize);
    }

    @Test
    public void del() throws Exception {
        List<User> userList = userService.findAll();
        int beforeSize = userList.size();
        System.out.println("beforeSize:" + beforeSize);
        userService.del(5);
        userList = userService.findAll();
        int afterSize = userList.size();
        System.out.println("afterSize:" + afterSize);
        Assert.assertEquals(beforeSize,afterSize+1);

    }

}
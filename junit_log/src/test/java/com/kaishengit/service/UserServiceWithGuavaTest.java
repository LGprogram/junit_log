package com.kaishengit.service;

import com.kaishengit.entity.User;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by liu on 2016/12/14.
 */
public class UserServiceWithGuavaTest {
    private UserServiceWithGuava userServiceWithGuava = new UserServiceWithGuava();
    @Test
    public void findById() throws Exception {
        User user = userServiceWithGuava.findById(6);
        user = userServiceWithGuava.findById(6);
        System.out.println(user);

    }

}
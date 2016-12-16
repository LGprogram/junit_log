package com.kaishengit.service;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.LoadingCache;
import com.kaishengit.dao.UserDao;
import com.kaishengit.entity.User;
import com.kaishengit.util.Config;
import com.kaishengit.util.EmailUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.ArrayUtils;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * Created by liu on 2016/12/15.
 */
public class UserService {
    private UserDao userDao = new UserDao();
    //发送激活邮件的TOKEN缓存
    private Cache<String,String> cache = CacheBuilder.newBuilder().expireAfterWrite(6, TimeUnit.HOURS).build();
    public boolean validateUsername(String username) {
        String name = Config.get("no.signup.usernames");
        List<String> nameList = Arrays.asList(name.split(","));
        if(nameList.contains(username)){
            return false;
        }
        return userDao.findByUsername(username)==null;
    }

    public User findByEmail(String email) {
        return userDao.findByEmail(email);
    }

    public void save(String username, String password, String email, String phone) {
        User user = new User();
        user.setUsername(username);
        user.setAvatar(User.DEFAULT_AVATAR_NAME);
        user.setEmail(email);
        user.setPhone(phone);
        user.setPassword(password);
        user.setState(User.USERSTATE_UNACTIVE);

        userDao.save(user);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                String uuid= UUID.randomUUID().toString();
                String url ="http://localhost/user/active?_="+uuid;
                String html ="<h3>Dear "+username+":</h3>请点击<a href='"+url+"'>该链接</a>去激活你的账号. <br> 凯盛软件";
                //放入缓存等待6个小时
                cache.put(uuid,username);

                EmailUtil.sendHttpemail(html,email);
            }
        });
        thread.start();

    }
}

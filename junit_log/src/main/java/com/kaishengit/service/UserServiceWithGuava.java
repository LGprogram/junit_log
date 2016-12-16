package com.kaishengit.service;


import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.kaishengit.dao.UserDao;
import com.kaishengit.entity.User;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * Created by liu on 2016/12/14.
 */
public class UserServiceWithGuava {
    private static UserDao userDao = new UserDao();
    private static LoadingCache<String,User> loadingCache = CacheBuilder.newBuilder()
            .maximumSize(100)
            .expireAfterAccess(30,TimeUnit.SECONDS)
            .expireAfterWrite(30,TimeUnit.SECONDS)
            .build(new CacheLoader<String, User>() {
                @Override
                public User load(String key) throws Exception {
                    return userDao.findById(Integer.valueOf(key));
                }
            });
    public User findById(Integer id) {

        User user = null;
        try {
            user = loadingCache.get("user:" + id, new Callable<User>() {
                @Override
                public User call() throws Exception {
                    return userDao.findById(id);
                }
            });
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return user;
    }

    /*private static LoadingCache<String,User> loadingCache = CacheBuilder.newBuilder()
            .maximumSize(100)
            .expireAfterAccess(30,TimeUnit.SECONDS)
            .expireAfterWrite(30,TimeUnit.SECONDS)
            .build(new CacheLoader<String, User>() {
                @Override
                public User load(String key){
                    return userDao.findById(Integer.valueOf(key));
                }
            });
    public User findById(Integer id) {
        User user =loadingCache.getUnchecked(id.toString());
        return user;
    }*/

}

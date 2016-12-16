package com.kaishengit.service;

import com.kaishengit.dao.UserDao;
import com.kaishengit.entity.User;
import com.kaishengit.util.EhCacheUtil;
import org.junit.Test;

import java.util.List;

/**
 * Created by liu on 2016/12/13.
 */
public class UserService {
    private static final String USER_CACHE_NAME = "user";

    private UserDao userDao = new UserDao();
    private EhCacheUtil cacheUtil = new EhCacheUtil();


    public User findById(Integer id){
        User user =(User)cacheUtil.get(USER_CACHE_NAME,"user:"+id);
        if(user==null){
            user = userDao.findById(id);
            cacheUtil.put(USER_CACHE_NAME,"user:"+id,user);
        }

        return user;
    }
    public List<User> findAll() {
        List<User> userList = (List<User>) cacheUtil.get(USER_CACHE_NAME,"userList");
        if(userList == null) {
            userList = userDao.findAll();
            cacheUtil.put(USER_CACHE_NAME,"userList",userList);
        }
        return userList;
    }

    /**
     * 当保存一条新纪录时需要将缓存中的userList删除，时效性不强时可以不用删除等记录达到最大闲置时间或者存活时间自然删除
     * @param user
     */
    public void save(User user) {
        userDao.save(user);
        cacheUtil.remove(USER_CACHE_NAME,"userList");
    }

    /**
     * 当删除一条记录时需要将缓存清空，时效性不强时可以不用删除等记录达到最大闲置时间或者存活时间自然删除
     * @param id
     */
    public void del(Integer id) {
        userDao.del(id);
        cacheUtil.removeAll(USER_CACHE_NAME);
//        cacheUtil.remove(USER_CACHE_NAME,"userList");
//        cacheUtil.remove(USER_CACHE_NAME,"user:"+id);
    }


}


package com.kaishengit.dao;

import com.kaishengit.entity.User;
import com.kaishengit.util.DbHelp;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.util.List;

/**
 * Created by liu on 2016/12/15.
 */
public class UserDao {
    public User findByUsername(String username) {
        String sql = "select * from t_user where username=?";
        return DbHelp.query(sql,new BeanHandler<User>(User.class),username);
    }

    public User findByEmail(String email) {
        String sql = "select * from t_user where email=?";
        return DbHelp.query(sql,new BeanHandler<>(User.class),email);
    }

    public void save(User user) {
        String sql = "insert into t_user(username, password, email, phone, state, avatar) VALUES (?,?,?,?,?,?)";
        DbHelp.update(sql,user.getUsername(),user.getPassword(),user.getEmail(),user.getPhone(),user.getState(),user.getAvatar());
    }

    public void update(User user) {
        String sql = "update t_user set username=?,password=?,email=?,phone=?,state=?,avatar=? where id=?";
        DbHelp.update(sql,user.getUsername(),user.getPassword(),user.getEmail(),user.getPhone(),user.getState(),user.getAvatar(),user.getId());
    }

    public User findById(Integer id) {
        String sql = "select*from t_user Where id = ? ";
        return DbHelp.query(sql,new BeanHandler<>(User.class),id);
    }

    public Integer count() {
        String sql = "select count(*) from t_user";
        return DbHelp.query(sql,new ScalarHandler<Long>()).intValue();
    }

    public List<User> findAll(Integer start,Integer pageSize) {
        String sql = "select * from t_user limit ?,?";
        return DbHelp.query(sql,new BeanListHandler<User>(User.class),start,pageSize);
    }
}

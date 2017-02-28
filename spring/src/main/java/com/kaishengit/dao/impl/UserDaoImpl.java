package com.kaishengit.dao.impl;

import com.kaishengit.dao.UserDao;
import com.kaishengit.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.stereotype.Repository;

import javax.inject.Named;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by liu on 2017/1/7.
 */
//@Repository
/*@Named*/
public class UserDaoImpl implements UserDao {

    public UserDaoImpl() {
        System.out.println("UserDaoImpl is construct");
    }
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Override
    public void save(User user) {
        String sql = "insert into t_user (username,password) values(?,?)";
        jdbcTemplate.update(sql,user.getUsername(),user.getPassword());
    }

    @Override
    public void update(User user) {
        String sql ="update t_user set username=?,password=? where id=?";
        jdbcTemplate.update(sql,user.getUsername(),user.getPassword(),user.getId());
    }

    @Override
    public User findByID(Integer id) {
        String sql ="select * from t_user where id =?";
        return jdbcTemplate.queryForObject(sql, new RowMapper<User>() {
            @Override
            public User mapRow(ResultSet resultSet, int i) throws SQLException {
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setPassword(resultSet.getString("password"));
                user.setUsername(resultSet.getString("username"));
                return user;
            }
        },id);
    }

    @Override
    public List<User> findAll() {
        String sql = "select * from t_user ";
        //查找集合使用的是query，BeanPropertyRowMapper映射一个pojo或者List
        return jdbcTemplate.query(sql,new BeanPropertyRowMapper<User>(User.class));
    }

    @Override
    public void del(Integer id) {
        String sql = "delete from t_user where id =?";
        jdbcTemplate.update(sql,id);

    }

    @Override
    public User findByUserName(String username) {
        String sql = "select * from t_user where username = ?";
        return jdbcTemplate.queryForObject(sql,new BeanPropertyRowMapper<>(User.class),username);
    }

    @Override
    public Long count() {
        String sql = "select count(*) from t_user";
        //查找一列使用SingleColumnRowMapper
        return jdbcTemplate.queryForObject(sql,new SingleColumnRowMapper<Long>());
    }
}

package com.kaishengit.mapper;

import com.kaishengit.pojo.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by liu on 2017/1/4.
 */
public interface UserMapper {
    //接口完全限定名com.kaishengit.mapper.UserMapper与resource->UserMapper.xml中的<mapper namespace="com.kaishengit.mapper.UserMapper">namespace值对应
    //接口中省略方法体
    User findById(Integer id );
    void save(User user);
    List<User> findAll();
    void update(User user);
    void del(Integer id);
    User findByUsernameAndPassword(@Param("name") String username, @Param("pwd") String password);
    User findByUsernameAndPassword1(@Param("name") String username, @Param("pass") String password);
//    User findByUsernameAndPassword(Map<String,Object> param);
    User findByParam(Map<String,Object> param);
    User findByParam1(Map<String,Object> param);

    void update1(Map<String,Object> param);
    List<User> findByIds(List<Integer> ids);
    void save1(List<User> userList);
}

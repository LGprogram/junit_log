package com.kaishengit.mapper;

import com.kaishengit.pojo.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by liu on 2017/1/12.
 */
public interface UserMapper {

    List<User> findAll();



    void save(User user);

    void delete(Integer id);

    User findById(Integer id);

    void update(User user);

    Long count(@Param("q_name") String q_name,@Param("roleId") String q_role);
    List<User> findAllN1(@Param("start") Integer start,@Param("pageSize") Integer pageSize);
    List<User> findAllN2(@Param("start") Integer start,@Param("pageSize") Integer pageSize,
                         @Param("q_name") String q_name,@Param("roleId") String q_role);

    User findByUsername(String username);
}

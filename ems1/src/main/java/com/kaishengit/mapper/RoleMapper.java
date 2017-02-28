package com.kaishengit.mapper;

import com.kaishengit.pojo.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by liu on 2017/2/7.
 */
public interface RoleMapper {

    List<Role> findByUserId(Integer id);

    List<Role> findAll();

    void save(@Param("userId") Integer userId, @Param("roleId") Integer roleId);

    void deleteByUserId(Integer id);
}

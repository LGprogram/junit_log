package com.kaishengit.mapper;

import com.kaishengit.pojo.Dept;

import java.util.List;

/**
 * Created by liu on 2017/1/5.
 */
public interface DeptMapper {
    Dept findById(Integer id);
    Dept findById1(Integer id);
    List<Dept> findAll();

}

package com.kaishengit.mapper;

import com.kaishengit.pojo.Employee;

import java.util.List;

/**
 * Created by liu on 2017/1/4.
 */
public interface EmployeeMapper {

    Employee findById(Integer id);
    List<Employee> findByDeptID(Integer deptid);
    List<Employee> findAll();
}

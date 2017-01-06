package com.kaishengit.mapper;

import com.kaishengit.pojo.Employee;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by liu on 2017/1/4.
 */
public interface EmployeeMapper {

    Employee findById(Integer id);
    List<Employee> findByDeptID(Integer deptid);
    List<Employee> findAll();

    @Select("select * from t_employee where id = #{id}")
    @Results(value = {
            @Result(property = "id", column="id"),
            @Result( property = "empname",column = "empname"),
            @Result(property = "deptid" , column="deptid"),
            @Result(property = "dept" ,column="deptid",one=@One(
                    select="com.kaishengit.mapper.DeptMapper.findById1"
            ))
    })
    Employee findById1(Integer id);
}

package com.kaishengit.mapper;

import com.kaishengit.pojo.Dept;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by liu on 2017/1/5.
 */
public interface DeptMapper {

    Dept findById(Integer id);
    Dept findById1(Integer id);
    List<Dept> findAll();
    @Select("select * from t_dept where id =#{id}")
    @Results(value = {//当value里只有一个@Result时可以省略value
            @Result(property = "id" ,column="id"),
            @Result(property = "deptname",column="deptname"),
            @Result(property ="employeeList",column = "id",
                    many=@Many(
                    select = "com.kaishengit.mapper.EmployeeMapper.findByDeptID"
            ))
    })
    Dept findById2(Integer id);

}

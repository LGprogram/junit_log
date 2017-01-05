package com.kaishengit;

import com.kaishengit.mapper.EmployeeMapper;
import com.kaishengit.pojo.Dept;
import com.kaishengit.pojo.Employee;
import com.kaishengit.util.SqlSessionFactoryUtil;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.List;

/**
 * Created by liu on 2017/1/4.
 */
public class EmployeeInterfaceTestCase {
    @Test
    public void findById(){
        SqlSession sqlSession = SqlSessionFactoryUtil.getSqlSession();
        EmployeeMapper employeeMapper = sqlSession.getMapper(EmployeeMapper.class);
        Employee employee =  employeeMapper.findById(23);
        System.out.println(employee);
    }

    @Test
    public void findByDeptId(){
        SqlSession sqlSession = SqlSessionFactoryUtil.getSqlSession();
        EmployeeMapper employeeMapper = sqlSession.getMapper(EmployeeMapper.class);
        List<Employee> employees = employeeMapper.findByDeptID(12);
        for(Employee employee:employees){
            System.out.println(employee);
        }
    }
    @Test
    public void findAll(){
        SqlSession sqlSession = SqlSessionFactoryUtil.getSqlSession();
        EmployeeMapper employeeMapper = sqlSession.getMapper(EmployeeMapper.class);
        List<Employee> employees =  employeeMapper.findAll();
        for(Employee employee : employees) {
            System.out.println(employee);
            Dept dept = employee.getDept();
            System.out.println(dept);
            System.out.println("-----------------------------");
        }


        sqlSession.close();
    }
}

package com.kaishengit;

import com.kaishengit.mapper.DeptMapper;
import com.kaishengit.pojo.Dept;
import com.kaishengit.pojo.Employee;
import com.kaishengit.util.SqlSessionFactoryUtil;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.List;

/**
 * Created by liu on 2017/1/5.
 */
public class DeptInterfaceTestCase {
    @Test
    public void findById(){
        SqlSession sqlSession = SqlSessionFactoryUtil.getSqlSession();
        DeptMapper deptMapper = sqlSession.getMapper(DeptMapper.class);
        Dept dept = deptMapper.findById(12);
        System.out.println(dept);
        List<Employee> employees = dept.getEmployeeList();
        for(Employee employee:employees){
            System.out.println(employee);
        }


    }
    @Test
    public void findAll(){
        SqlSession sqlSession = SqlSessionFactoryUtil.getSqlSession();
        DeptMapper deptMapper = sqlSession.getMapper(DeptMapper.class);
        List<Dept> deptList = deptMapper.findAll();
        for(Dept dept : deptList){
            System.out.println(dept);
            List<Employee> employees = dept.getEmployeeList();
            for(Employee employee : employees){
                System.out.println(employee);
            }
        }
    }
    @Test
    public void findById2(){
        SqlSession sqlSession = SqlSessionFactoryUtil.getSqlSession();
        DeptMapper deptMapper = sqlSession.getMapper(DeptMapper.class);
        Dept dept = deptMapper.findById2(12);
        System.out.println(dept);
        List<Employee> employees = dept.getEmployeeList();

        for(Employee employee:employees){
            System.out.println(employee.getEmpname());
        }
    };

}

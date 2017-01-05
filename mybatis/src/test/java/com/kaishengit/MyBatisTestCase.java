package com.kaishengit;

import com.kaishengit.pojo.User;
import com.kaishengit.util.SqlSessionFactoryUtil;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.jdbc.SQL;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;


import java.io.IOException;
import java.io.Reader;
import java.util.List;

/**
 * Created by liu on 2017/1/4.
 */
public class MyBatisTestCase {
    @Test
    public void readXml(){
        try {
            Reader reader = Resources.getResourceAsReader("mybatis.xml");
            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
            SqlSession sqlSession = sqlSessionFactory.openSession();
            User user = sqlSession.selectOne("com.kaishengit.mapper.UserMapper.findById",1);
            System.out.println(user);

            sqlSession.close();
        } catch (IOException e) {
          e.printStackTrace();
        }

    }
    @Test
    public void findById() {
        SqlSession sqlSession = SqlSessionFactoryUtil.getSqlSession();

        User user = sqlSession.selectOne("com.kaishengit.mapper.UserMapper.findById",1);
        System.out.println(user);

        sqlSession.close();
    }

    @Test
    public void save(){
        SqlSession sqlSession = SqlSessionFactoryUtil.getSqlSession();
        User user = new User();
        user.setUsername("lgz");
        user.setPassword("123456");
        sqlSession.insert("com.kaishengit.mapper.UserMapper.save",user);
        //提交事务
        sqlSession.commit();
        sqlSession.close();
    }

    @Test
    public void update(){
        SqlSession sqlSession = SqlSessionFactoryUtil.getSqlSession(true);
        User user = new User();
        user.setId(2);
        user.setUsername("tom");
        user.setPassword("132231");
        sqlSession.update("com.kaishengit.mapper.UserMapper.update",user);
        sqlSession.close();
    }

    public void delete(){
        SqlSession sqlSession = SqlSessionFactoryUtil.getSqlSession(true);
        sqlSession.delete("com.kaishengit.mapper.UserMapper.del",2);
        sqlSession.close();

    }
    @Test
    public void findAll(){
        SqlSession sqlSession = SqlSessionFactoryUtil.getSqlSession();
        List<User> userList = sqlSession.selectList("com.kaishengit.mapper.UserMapper.findAll");
        System.out.println(userList);
    }

}

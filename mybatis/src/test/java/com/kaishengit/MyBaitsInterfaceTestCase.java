package com.kaishengit;

import com.kaishengit.mapper.UserMapper;
import com.kaishengit.pojo.User;
import com.kaishengit.util.SqlSessionFactoryUtil;
import org.apache.ibatis.session.SqlSession;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by liu on 2017/1/4.
 */
public class MyBaitsInterfaceTestCase {
    private SqlSession sqlSession;
    private UserMapper userMapper;

    @Before
    public void setup(){
        sqlSession = SqlSessionFactoryUtil.getSqlSession();
        userMapper = sqlSession.getMapper(UserMapper.class);
    }
    @After
    public void close(){

        sqlSession.close();
    }
   /* @Test
    public void findById(){
        SqlSession sqlSession = SqlSessionFactoryUtil.getSqlSession();
        //自动产生一个UserMapper接口的实现类（代理对象）
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        User user = userMapper.findById(2);
        System.out.println(user);
        sqlSession.close();
    }*/
    @Test
    public void save(){
        SqlSession sqlSession = SqlSessionFactoryUtil.getSqlSession(true);
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        User user = new User();
        user.setUsername("fankay");
        user.setPassword("545809");
        userMapper.save(user);
        //此处返回的id直接存储在对象user中
        Integer id = user.getId();
        System.out.println(id);
        sqlSession.close();
    }
    @Test
    public void findAll(){
        SqlSession sqlSession = SqlSessionFactoryUtil.getSqlSession();
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        List<User> userList = userMapper.findAll();
        for(User user:userList){
            System.out.println(user);
        }
    }
    @Test
    public void findByUsernameAndPassword(){
        SqlSession sqlSession = SqlSessionFactoryUtil.getSqlSession();
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        /*Map<String,Object> param = new HashMap<>();
        param.put("name","jamas");
        param.put("pwd","123123");*/
        User user = userMapper.findByUsernameAndPassword("jamas","123123");
        System.out.println(user);
        sqlSession.close();
    }
    @Test
    public void findByUsernameAndPassword1(){
        SqlSession sqlSession = SqlSessionFactoryUtil.getSqlSession();
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);

        User user = userMapper.findByUsernameAndPassword1("jamas","");
        System.out.println(user);
        sqlSession.close();
    }

    @Test
    public void findByParam(){
        Map<String,Object> param = new HashMap<>();
//        param.put("username","lgz");
        param.put("password","123456");
        User user = userMapper.findByParam(param);
        System.out.println(user);

    }
    @Test
    public void findByParam1(){
        Map<String,Object> param = new HashMap<>();
        param.put("username","lgz");
//        param.put("password","123456");
        User user = userMapper.findByParam(param);
        System.out.println(user);

    }
    @Test
    public void update1(){
        Map<String,Object> param = new HashMap<>();
        param.put("username","lgze");
        param.put("password","1234567");
        param.put("id",14);
        userMapper.update1(param);
        sqlSession.commit();
    }
    @Test
    public void save1(){
        User user1 = new User("lilisi","lilisi");
        User user2 = new User("lando","lilisi");
        User user3 = new User("margin","lilisi");

        List<User> userList = new ArrayList<>();
        userList.add(user1);
        userList.add(user2);
        userList.add(user3);
        userMapper.save1(userList);
        sqlSession.commit();

    }
    @Test
    public void findByIds(){
        List<Integer> idList = new ArrayList<>();
        idList.add(1);
        idList.add(2);
        idList.add(3);
        List<User> userList =  userMapper.findByIds(idList);
        for (User user : userList){
            System.out.println(user.getUsername());

        }

    }
    @Test
    public void findById(){
        /*一级缓存（默认开启）。范围在同一个SQLSession中*//*
        User user = userMapper.findById(2);
        System.out.println(user);
        user = userMapper.findById(2);
        System.out.println(user);
        user = userMapper.findById(2);
        System.out.println(user);*/
        /*二级缓存（默认关闭）范围：在同一个SqlSessionFactory中开启在XXX.xml中配置<cache/>. 注意此时的pojo实体类要实现可序列化接口Serializable*/
        SqlSession sqlSession = SqlSessionFactoryUtil.getSqlSession();
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        User user = userMapper.findById(2);
        System.out.println(user);
        sqlSession.close();
        SqlSession sqlSession1 = SqlSessionFactoryUtil.getSqlSession();
        UserMapper userMapper1 = sqlSession1.getMapper(UserMapper.class);
        User user1 = userMapper1.findById(2);
        System.out.println(user1);
        sqlSession1.close();
    }


}

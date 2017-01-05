package com.kaishengit.util;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.Reader;

/**
 * Created by liu on 2017/1/4.
 */
public class SqlSessionFactoryUtil {
    private static SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuild();

    private static SqlSessionFactory sqlSessionFactoryBuild() {
        Reader reader = null;
        try {
            reader = Resources.getResourceAsReader("mybatis.xml");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new SqlSessionFactoryBuilder().build(reader);
    }
    public static SqlSessionFactory getSqlSessionFactory(){
        return sqlSessionFactory;
    };
    public static SqlSession getSqlSession() {
        return getSqlSessionFactory().openSession();
    }

    public static SqlSession getSqlSession(boolean isAutoCommit) {
        return getSqlSessionFactory().openSession(isAutoCommit); //获取一个自动提交事务的SqlSession对象
    }

}

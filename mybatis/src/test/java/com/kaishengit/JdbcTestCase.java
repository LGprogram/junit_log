package com.kaishengit;

import org.junit.Test;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

/**
 * Created by liu on 2017/1/4.
 */
public class JdbcTestCase {
    @Test
    public void test() throws Exception {

        Class.forName("com.mysql.jdbc.Driver");
        Connection connection = DriverManager.getConnection("jdbc:mysql:///mydb", "root", "root");
        PreparedStatement preparedStatement = null;
        //开启事务
        connection.setAutoCommit(false);
        try {
            String sql = "INSERT INTO t_user(username,PASSWORD) VALUES ('jamas','123123')";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.executeUpdate();
            String sql1 = "INSERTs INTO t_user(username,PASSWORD) VALUES ('jimi','123123')";
            preparedStatement = connection.prepareStatement(sql1);
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (Exception e) {
            e.printStackTrace();
            connection.rollback();
        } finally {
            preparedStatement.close();
            connection.close();
        }

    }

}

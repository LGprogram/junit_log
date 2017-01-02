package com.kaishengit.dao;

import com.kaishengit.entity.Admin;
import com.kaishengit.util.DbHelp;
import org.apache.commons.dbutils.handlers.BeanHandler;

/**
 * Created by liu on 2016/12/28.
 */
public class AdminDao {
    public Admin findByName(String adminname) {
        String sql = "Select * from admin where adminname = ?";
        return DbHelp.query(sql,new BeanHandler<Admin>(Admin.class),adminname);
    }
}

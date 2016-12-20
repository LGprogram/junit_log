package com.kaishengit.dao;

import com.kaishengit.entity.Login_Log;
import com.kaishengit.util.DbHelp;
import org.apache.commons.dbutils.handlers.BeanHandler;

/**
 * Created by liu on 2016/12/16.
 */
public class Login_LogDao {

    public void save(String ip, Integer id) {
        String sql = "insert into t_login_log(ip,t_user_id) values(?,?)";
        DbHelp.update(sql,ip,id);
    }
}

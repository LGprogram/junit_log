package com.kaishengit.entity;

import java.sql.Timestamp;

/**
 * Created by liu on 2016/12/15.
 */
public class Login_Log {
    private Integer id;
    private Timestamp logintime;
    private String ip;
    private Integer t_user_id;

    public Integer getT_user_id() {
        return t_user_id;
    }

    public void setT_user_id(Integer t_user_id) {
        this.t_user_id = t_user_id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Timestamp getLogintime() {
        return logintime;
    }

    public void setLogintime(Timestamp logintime) {
        this.logintime = logintime;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}

package com.kaishengit.entity;

import java.sql.Timestamp;

/**
 * Created by liu on 2016/12/15.
 */
public class Login_Log {
    private Integer id;
    private Timestamp logintime;
    private String ip;

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

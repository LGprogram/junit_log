package com.kaishengit.vo;

import java.sql.Timestamp;

/**
 * Created by liu on 2016/12/29.
 */
public class UserVo {
    public static final Integer USERSTATE_UNACTIVE = 0;
    public static final Integer USERSTATE_ACTIVE = 1;
    public static final Integer USERSTATE_DISABLED = 2;
    private Integer userId;
    private String username;
    private Timestamp createtime;
    private String loginIP;
    private Timestamp lastLoginTime;
    private Integer userState;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Timestamp getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Timestamp createtime) {
        this.createtime = createtime;
    }

    public String getLoginIP() {
        return loginIP;
    }

    public void setLoginIP(String loginIP) {
        this.loginIP = loginIP;
    }

    public Timestamp getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Timestamp lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public Integer getUserState() {
        return userState;
    }

    public void setUserState(Integer userState) {
        this.userState = userState;
    }
}

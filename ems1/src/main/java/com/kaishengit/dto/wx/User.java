package com.kaishengit.dto.wx;

import java.util.List;

/**
 * Created by liu on 2017/2/24.
 */
public class User {

    /**
     * userid : zhangsan
     * name : 李四
     * department : [1]
     * mobile : 15913215421
     */

    private String userid;
    private String name;
    private String mobile;
    private List<Integer> department;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public List<Integer> getDepartment() {
        return department;
    }

    public void setDepartment(List<Integer> department) {
        this.department = department;
    }
}

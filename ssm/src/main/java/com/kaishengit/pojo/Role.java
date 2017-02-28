package com.kaishengit.pojo;

import java.io.Serializable;

/**
 * Created by liu on 2017/1/13.
 */
public class Role implements Serializable {
    private Integer id;
    private String roleName;
    private String viewName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getViewName() {
        return viewName;
    }

    public void setViewName(String viewName) {
        this.viewName = viewName;
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", roleName='" + roleName + '\'' +
                ", viewName='" + viewName + '\'' +
                '}';
    }
}

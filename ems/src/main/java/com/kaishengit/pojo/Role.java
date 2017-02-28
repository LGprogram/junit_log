package com.kaishengit.pojo;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by liu on 2017/2/7.
 */
@Data
public class Role implements Serializable {
    private Integer id;
    private String roleName;
    private String viewName;
}

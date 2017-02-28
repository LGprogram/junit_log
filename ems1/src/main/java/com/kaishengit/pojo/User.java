package com.kaishengit.pojo;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Created by liu on 2017/2/7.
 */
@Data
public class User implements Serializable {
    private Integer id;
    private String username;
    private String password;
    private List<Role> roleList;
    private String mobel;

    public String getRoleNames(){
        List<String> viewNames = Lists.newArrayList(Collections2.transform(getRoleList(), new Function<Role, String>() {
            @Override
            public String apply(Role role) {
                return role.getViewName();
            }
        }));

        StringBuilder sb = new StringBuilder();
        for(String viewName : viewNames) {
            sb.append(viewName).append(" ");
        }

        return sb.toString();

    }
}

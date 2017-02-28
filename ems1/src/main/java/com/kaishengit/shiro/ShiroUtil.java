package com.kaishengit.shiro;

import com.kaishengit.pojo.User;
import org.apache.shiro.SecurityUtils;

/**
 * Created by liu on 2017/2/6.
 */
public class ShiroUtil {
    public static User getCurrentUser(){
        return (User) SecurityUtils.getSubject().getPrincipal();
    }
    public static String getUserName(){
        return getCurrentUser().getUsername();
    }
    public static Integer getUserId(){
        return getCurrentUser().getId();
    }

    /**
     * 判断当前登录对象是否为市场部员工
     * @return
     */
    public static boolean isSales(){
        return SecurityUtils.getSubject().hasRole("market");
    }
}

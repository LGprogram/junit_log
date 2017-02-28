package com.kaishengit.shiro;

import com.kaishengit.mapper.RoleMapper;
import com.kaishengit.mapper.UserMapper;
import com.kaishengit.pojo.Role;
import com.kaishengit.pojo.User;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by liu on 2017/2/7.
 */
@Component
public class ShiroDbRealm extends AuthorizingRealm {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RoleMapper roleMapper;
    /*
    权限认证
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //返回当前登录的对象
        User user = (User) principalCollection.getPrimaryPrincipal();
        //获取当前对象拥有的角色
        List<Role> roleList = roleMapper.findByUserId(user.getId());
        System.out.println(roleList.size());
        if(!roleList.isEmpty()){
            SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
            for(Role role:roleList){
                simpleAuthorizationInfo.addRole(role.getRoleName());
            }
            return simpleAuthorizationInfo;
        }
        return null;
    }
    /*
    登录认证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) authenticationToken;
        String username = usernamePasswordToken.getUsername();
        User user = userMapper.findByUsername(username);
        if(user!= null){
            /*shiro 登录认证
            user:往shiro中token中存放的，父类的getName()方法会通过
            usernamePasswordToken得到登录时输入的password，
            与user.getPassword比较不一样则抛异常*/
            return new SimpleAuthenticationInfo(user,user.getPassword(),getName());
        }
        //返回null值的话会引起controller异常，导致登录失败
        return null;
    }
}

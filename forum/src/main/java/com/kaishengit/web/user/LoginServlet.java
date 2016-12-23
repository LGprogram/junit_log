package com.kaishengit.web.user;

import com.google.common.collect.Maps;
import com.kaishengit.entity.User;
import com.kaishengit.exception.ServiceException;
import com.kaishengit.service.UserService;
import com.kaishengit.util.Config;
import com.kaishengit.web.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

/**
 * Created by liu on 2016/12/15.
 */
@WebServlet("/login")
public class LoginServlet extends BaseServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //修改密码后登陆时，去掉登录痕迹
        req.getSession().removeAttribute("curr_user");
        forward("/user/login",req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String ip = req.getRemoteAddr();
        Integer loginLimitTimes = 3;
        Map<String,Object> result = Maps.newHashMap();
        UserService userService = new UserService();
        try{
           User user = userService.login(username,password,ip);
            HttpSession session = req.getSession();
            //将session中保存的user的avatar属性改为http://oi0ntmwcf.bkt.clouddn.com/+user.getAvatar()
            user.setAvatar(Config.get("qiniu.domain")+user.getAvatar());
            session.setAttribute("curr_user",user);

            result.put("state","success");

        }catch (ServiceException e){

            result.put("state","error");
            result.put("message",e.getMessage());


        }
        renderJSON(result,resp);
    }
}

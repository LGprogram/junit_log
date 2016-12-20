package com.kaishengit.web.user;

import com.google.common.collect.Maps;
import com.kaishengit.entity.User;
import com.kaishengit.exception.ServiceException;
import com.kaishengit.service.UserService;
import com.kaishengit.web.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * Created by liu on 2016/12/17.
 */
@WebServlet("/user/foundPwd")
public class EmailFoundPwdServlet extends BaseServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String token =  req.getParameter("token");


        UserService userService = new UserService();
        try{
            User user = userService.validateToken(token);
            req.setAttribute("user",user);
            req.setAttribute("token",token);
            forward("user/repwd_success",req,resp);
        }catch (ServiceException e){
            req.setAttribute("message",e.getMessage());
            forward("user/repwd_error",req,resp);
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
//        String username =req.getParameter("username");
        String password = req.getParameter("password");
        String token = req.getParameter("token");

        Map<String,Object> result = Maps.newHashMap();

        UserService userService = new UserService();
        try {
            userService.resetPassword(id, token, password);
            result.put("state","success");
        } catch (ServiceException ex) {
            result.put("state","error");
            result.put("message",ex.getMessage());
        }

        renderJSON(result,resp);

    }
}

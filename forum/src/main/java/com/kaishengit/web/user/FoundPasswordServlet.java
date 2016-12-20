package com.kaishengit.web.user;

import com.google.common.collect.Maps;
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
@WebServlet("/foundPassword")
public class FoundPasswordServlet extends BaseServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        forward("user/foundPassword",req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String type = req.getParameter("type");
        String value = req.getParameter("value");
        //获取当前客户端的sessionID
        String sessionId = req.getSession().getId();
        Map<String,String> result = Maps.newHashMap();
        UserService userService = new UserService();
        try{
            userService.foundPassword(type,value,sessionId);
            result.put("state","success");
        }catch (ServiceException e){
            result.put("state","error");
            result.put("message",e.getMessage());
        }
        renderJSON(result,resp);


    }
}

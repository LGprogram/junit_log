package com.kaishengit.web.user;

import com.kaishengit.web.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by liu on 2016/12/17.
 */
@WebServlet("/logout")
public class LoginOutServlet extends BaseServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        session.invalidate();
        //TODO 在服务端把客户端的缓存删除
        String message ="你已安全退出";
        req.setAttribute("message",message);
        forward("user/login",req,resp);
    }
}

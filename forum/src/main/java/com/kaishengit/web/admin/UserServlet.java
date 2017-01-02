package com.kaishengit.web.admin;

import com.kaishengit.dto.JsonResult;
import com.kaishengit.exception.ServiceException;
import com.kaishengit.service.UserService;
import com.kaishengit.util.Page;
import com.kaishengit.vo.UserVo;
import com.kaishengit.web.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by liu on 2016/12/29.
 */
@WebServlet("/admin/user")
public class UserServlet extends BaseServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String p = req.getParameter("p");
        UserService userService = new UserService();
        Page<UserVo> page = userService.findByPageNo(p);
        req.setAttribute("page",page);
        forward("admin/user",req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        String userId = req.getParameter("userId");
        UserService userService = new UserService();
        JsonResult jsonResult = new JsonResult();
        try {
            userService.doActive(action, userId);
            jsonResult.setState("success");
        }catch (ServiceException e){
            jsonResult=new JsonResult(e.getMessage());
        }
        renderJSON(jsonResult,resp);
    }
}

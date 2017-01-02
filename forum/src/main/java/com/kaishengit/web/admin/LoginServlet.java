package com.kaishengit.web.admin;

import com.kaishengit.dto.JsonResult;
import com.kaishengit.entity.Admin;
import com.kaishengit.exception.ServiceException;
import com.kaishengit.service.AdminService;
import com.kaishengit.web.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by liu on 2016/12/28.
 */
@WebServlet("/admin/login")
public class LoginServlet extends BaseServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getSession().removeAttribute("curr_admin");
        forward("/admin/login",req,resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String adminname = req.getParameter("adminname");
        String password = req.getParameter("password");
        String ip = req.getRemoteAddr();
        AdminService adminService = new AdminService();
        JsonResult jsonResult = null ;new JsonResult();
        try{
            Admin admin =  adminService.login(adminname,password,ip);
            req.getSession().setAttribute("curr_admin",admin);
            jsonResult = new JsonResult();
            jsonResult.setState("success");
        }catch(ServiceException e){
            jsonResult = new JsonResult(e.getMessage());
        }
        renderJSON(jsonResult,resp);

    }
}

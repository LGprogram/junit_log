package com.kaishengit.web.user;

import com.kaishengit.dto.JsonResult;
import com.kaishengit.entity.User;
import com.kaishengit.exception.ServiceException;
import com.kaishengit.service.UserService;
import com.kaishengit.util.Config;
import com.kaishengit.web.BaseServlet;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by liu on 2016/12/19.
 */
@WebServlet("/setting")
public class SettingServlet extends BaseServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //产生七牛上传凭证
        Auth auth = Auth.create(Config.get("qiniu.ak"),Config.get("qiniu.sk"));

        String token = auth.uploadToken(Config.get("qiniu.bucketName"));
        req.setAttribute("token",token);
        forward("user/setting",req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        User user = (User)req.getSession().getAttribute("curr_user");
        if(action.equals("email")){
            updateEmail(user,req,resp);
        }else if(action.equals("password")){
            updatePassword(user,req,resp);
        }else if(action.equals("avatar")){
            updateAvatar(user,req,resp);
        }

    }

    private void updateAvatar(User user, HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String filekey = req.getParameter("filekey");
        UserService userService = new UserService();
        userService.updateUserAvatar(user,filekey);
        JsonResult jsonResult = new JsonResult();
        jsonResult.setState(JsonResult.SUCCESS);
        renderJSON(jsonResult,resp);
    }

    public void updateEmail(User user,HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String email =req.getParameter("email");

        //TODO 修改state为0,让用户重新验证邮箱
        UserService userService = new UserService();
        userService.update(user,email);
        JsonResult jsonResult = new JsonResult();
        jsonResult.setState(JsonResult.SUCCESS);
        renderJSON(jsonResult,resp);

    }
    public void updatePassword(User user,HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String oldpassword = req.getParameter("oldpassword");
        String newpassword = req.getParameter("newpassword");
        UserService userService = new UserService();
        JsonResult jsonResult = new JsonResult();
        try{
            userService.updatePassword(user,oldpassword,newpassword);
            jsonResult.setState(JsonResult.SUCCESS);
        }catch (ServiceException e){
            jsonResult.setState(JsonResult.ERROR);
            jsonResult.setMessage(e.getMessage());


        }
        renderJSON(jsonResult,resp);

    }
}

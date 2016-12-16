package com.kaishengit.web.user;

import com.kaishengit.entity.User;
import com.kaishengit.service.UserService;
import com.kaishengit.util.Config;
import com.kaishengit.web.BaseServlet;
import org.apache.commons.codec.digest.DigestUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by liu on 2016/12/15.
 */
@WebServlet("/reg")
public class RegServlet extends BaseServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        forward("user/reg", req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String email = req.getParameter("email");
        String phone = req.getParameter("phone");

        String salt = Config.get("password.salt");
        password = DigestUtils.md5Hex(password+salt);

        Map<String,String> result = new HashMap();
        try {
            UserService userService = new UserService();
            userService.save(username, password, email, phone);

            result.put("state","success");
        } catch (Exception ex) {
            ex.printStackTrace();
            result.put("state","error");
            result.put("message","注册失败，请稍后再试");
        }
        renderJSON(result,resp);



    }


}

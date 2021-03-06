package com.kaishengit.web.user;

import com.kaishengit.entity.User;
import com.kaishengit.service.UserService;
import com.kaishengit.web.BaseServlet;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by liu on 2016/12/15.
 */
@WebServlet("/validate/email")
public class ValidateEmailServlet extends BaseServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String type = req.getParameter("type");
        String email = req.getParameter("email");
        if(StringUtils.isNotEmpty(type)&&"1".equals(type)){
            User user = (User)req.getSession().getAttribute("curr_user");
            if(user.getEmail().equals(email)){
                renderText("true",resp);
                return;
            }
        }
        UserService userService = new UserService();
        User user = userService.findByEmail(email);

        if(user == null) {
            renderText("true",resp);
        } else {
            renderText("false",resp);
        }

    }
}

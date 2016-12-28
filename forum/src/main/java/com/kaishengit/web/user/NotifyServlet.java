package com.kaishengit.web.user;

import com.kaishengit.dto.JsonResult;
import com.kaishengit.entity.Notify;
import com.kaishengit.entity.User;
import com.kaishengit.service.NotifyService;
import com.kaishengit.web.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by liu on 2016/12/26.
 */
@WebServlet("/notify")
public class NotifyServlet extends BaseServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("curr_user");
        NotifyService notifyService = new NotifyService();
        List<Notify> notifyList = notifyService.findAll(user);

        req.setAttribute("notifyList",notifyList);
        forward("user/notify",req,resp);


    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("curr_user");
        NotifyService notifyService = new NotifyService();
        List<Notify> unReadNotifyList = notifyService.findUnReadNotify(user);
        JsonResult jsonResult = new JsonResult(unReadNotifyList.size());
        renderJSON(jsonResult,resp);
    }
}

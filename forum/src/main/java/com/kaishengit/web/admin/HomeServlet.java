package com.kaishengit.web.admin;

import com.kaishengit.entity.TopicReplyCount;
import com.kaishengit.service.TopicService;
import com.kaishengit.util.Page;
import com.kaishengit.web.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by liu on 2016/12/28.
 */
@WebServlet("/admin/home")
public class HomeServlet extends BaseServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String p = req.getParameter("p");
        Page<TopicReplyCount> page = new TopicService().getTopicAndReplyNumByDayList(p);
        req.setAttribute("page",page);
        forward("admin/home",req,resp);
    }
}

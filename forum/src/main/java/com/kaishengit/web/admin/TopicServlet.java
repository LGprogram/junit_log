package com.kaishengit.web.admin;

import com.kaishengit.entity.Topic;
import com.kaishengit.exception.ServiceException;
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
@WebServlet("/admin/topic")
public class TopicServlet extends BaseServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String p = req.getParameter("p");
        TopicService topicService = new TopicService();
        Page<Topic> page = topicService.findTopicListByNodeid("",p);
        req.setAttribute("page",page);

        forward("admin/topic",req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        TopicService topicService = new TopicService();
        try{
            topicService.deleteTopicByID(id);
            renderText("success",resp);
        }catch(ServiceException e){
            renderText(e.getMessage(),resp);
        }

    }
}

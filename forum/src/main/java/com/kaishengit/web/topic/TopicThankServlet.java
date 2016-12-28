package com.kaishengit.web.topic;

import com.kaishengit.dto.JsonResult;
import com.kaishengit.entity.Topic;
import com.kaishengit.entity.User;
import com.kaishengit.exception.ServiceException;
import com.kaishengit.service.TopicService;
import com.kaishengit.web.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by liu on 2016/12/24.
 */
@WebServlet("/topicThank")
public class TopicThankServlet extends BaseServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        String topicId = req.getParameter("topicid");
        User user = (User) req.getSession().getAttribute("curr_user");
        TopicService topicService = new TopicService();
        JsonResult jsonResult=null;
        try{
            topicService.doThank(action,topicId,user);
            Topic topic = topicService.findTopicById(topicId);
            jsonResult = new JsonResult(topic);
        }catch (ServiceException e){
            jsonResult = new JsonResult(e.getMessage());
        }
        renderJSON(jsonResult,resp);
    }
}

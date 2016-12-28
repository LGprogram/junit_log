package com.kaishengit.web.topic;

import com.kaishengit.entity.*;
import com.kaishengit.exception.ServiceException;
import com.kaishengit.service.TopicService;
import com.kaishengit.web.BaseServlet;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by liu on 2016/12/20.
 */
@WebServlet("/topicDetail")
public class TopicDetailServlet extends BaseServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String topicid = req.getParameter("topicid");
        TopicService topicService = new TopicService();
        User user = (User) req.getSession().getAttribute("curr_user");

        try{
            Topic topic = topicService.findTopicById(topicid);
            List<Reply> replyList = topicService.findReplyListByTopicId(topicid);
            if(StringUtils.isNumeric(topicid)&&user!=null){
                Fav fav = topicService.findFavByTopicIdAndUser(topicid,user);
                req.setAttribute("fav",fav);
                Thank thank = topicService.findThankByTopicIdAndUser(topicid,user);
                req.setAttribute("thank",thank);
            }

            req.setAttribute("topic",topic);
            req.setAttribute("replyList",replyList);
            forward("/topic/topicDetail",req,resp);
        }catch(ServiceException e){
            resp.sendError(404,e.getMessage());
        }

    }
}

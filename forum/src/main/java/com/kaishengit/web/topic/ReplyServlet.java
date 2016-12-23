package com.kaishengit.web.topic;

import com.kaishengit.entity.User;
import com.kaishengit.exception.ServiceException;
import com.kaishengit.service.ReplyService;
import com.kaishengit.web.BaseServlet;
import org.apache.commons.lang3.StringUtils;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * Created by liu on 2016/12/21.
 */
@WebServlet("/reply")
public class ReplyServlet extends BaseServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String topicid =  req.getParameter("topicid");
        String content = req.getParameter("content");
        User user = (User) req.getSession().getAttribute("curr_user");
        Integer userid = user.getId();
        System.out.println(req.getRequestURI());//reply
        ReplyService replyService = new ReplyService();

        if (StringUtils.isNumeric(topicid)){
            try{
                replyService.addNewReply(content,topicid,userid);
                resp.sendRedirect("/topicDetail?topicid="+topicid);
            }catch (ServiceException e){
                resp.sendError(404,e.getMessage());
            }

        }else{
            resp.sendError(404);
        }

    }
}

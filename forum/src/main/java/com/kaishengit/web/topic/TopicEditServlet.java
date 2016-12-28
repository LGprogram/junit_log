package com.kaishengit.web.topic;

import com.kaishengit.dto.JsonResult;
import com.kaishengit.entity.Node;
import com.kaishengit.entity.Topic;
import com.kaishengit.entity.User;
import com.kaishengit.exception.ServiceException;
import com.kaishengit.service.NodeService;
import com.kaishengit.service.TopicService;
import com.kaishengit.util.Config;
import com.kaishengit.web.BaseServlet;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


/**
 * Created by liu on 2016/12/23.
 */
@WebServlet("/topicEdit")
public class TopicEditServlet extends BaseServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String topicId = req.getParameter("topicid");
        TopicService topicService = new TopicService();
        //获取七牛验证token
        Auth auth = Auth.create(Config.get("qiniu.ak"),Config.get("qiniu.sk"));
        StringMap stringMap = new StringMap();

        String returnBody = "{\"success\":true,\"file_path\":\""+Config.get("qiniu.domain")+"${key}\"}";
        stringMap.put("returnBody",returnBody);
        String token = auth.uploadToken(Config.get("qiniu.bucketName"),null,3600,stringMap);
        try{
            Topic topic =  topicService.editTopic(topicId);
            req.setAttribute("topic",topic);

            NodeService nodeService = new NodeService();
            List<Node> nodeList = nodeService.findAllNode();
            req.setAttribute("nodeList",nodeList);
            req.setAttribute("token",token);
            forward("topic/topicEdit",req,resp);
        }catch(ServiceException e){
            //TODO 错误页
            resp.sendError(404,e.getMessage());
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String topicid = req.getParameter("topicid");
        String title = req.getParameter("title");
        String content = req.getParameter("content");
        String nodeid = req.getParameter("nodeid");
        User user = (User) req.getSession().getAttribute("curr_user");
        TopicService topicService = new TopicService();
        JsonResult jsonResult=null;
        try{
            Topic topic =  topicService.updateTopic(user,topicid,title,content,nodeid);
            jsonResult = new JsonResult(topic);
        }catch(ServiceException e){
            jsonResult = new JsonResult(e.getMessage());
        }

        renderJSON(jsonResult,resp);

    }
}

package com.kaishengit.web.topic;

import com.kaishengit.dao.NodeDao;
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
import java.util.Arrays;
import java.util.List;

/**
 * Created by liu on 2016/12/20.
 */
@WebServlet("/newpost")
public class NewPostServlet extends BaseServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取七牛验证token
        Auth auth = Auth.create(Config.get("qiniu.ak"),Config.get("qiniu.sk"));
        StringMap stringMap = new StringMap();

        String returnBody = "{\"success\":true,\"file_path\":\""+Config.get("qiniu.domain")+"${key}\"}";
        stringMap.put("returnBody",returnBody);
        String token = auth.uploadToken(Config.get("qiniu.bucketName"),null,3600,stringMap);

        NodeService nodeService = new NodeService();

        List<Node> nodeList = nodeService.findAllNode();
        req.setAttribute("nodeList",nodeList);
        req.setAttribute("token",token);
        forward("topic/newpost",req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String title = req.getParameter("title");
        String content = req.getParameter("content");
        String nodeid = req.getParameter("nodeid");
        System.out.println(nodeid);
        User user = (User) req.getSession().getAttribute("curr_user");
        Integer userid = user.getId();
        TopicService topicService = new TopicService();
        JsonResult jsonResult=null;
        try{
            Topic topic =  topicService.addNewTopic(title,content,Integer.valueOf(nodeid),userid);
             jsonResult = new JsonResult(topic);
        }catch(ServiceException e){
            jsonResult = new JsonResult(e.getMessage());
        }

        renderJSON(jsonResult,resp);
    }
}

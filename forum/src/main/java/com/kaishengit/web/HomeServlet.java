package com.kaishengit.web;

import com.kaishengit.entity.Node;
import com.kaishengit.entity.Topic;
import com.kaishengit.exception.ServiceException;
import com.kaishengit.service.NodeService;
import com.kaishengit.service.TopicService;
import com.kaishengit.util.Page;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by liu on 2016/12/15.
 */
@WebServlet("/home")
public class HomeServlet extends BaseServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String nodeid = req.getParameter("nodeid");
        String p = req.getParameter("p");

        TopicService topicService = new TopicService();
        //將nodeList传到页面
        NodeService nodeService = new NodeService();
        List<Node> nodeList = nodeService.findAllNode();
        req.setAttribute("nodeList",nodeList);


        Page<Topic> page = topicService.findTopicListByNodeid(nodeid,p);
        req.setAttribute("page",page);
        forward("index",req,resp);






    }
}

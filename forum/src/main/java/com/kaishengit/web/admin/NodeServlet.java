package com.kaishengit.web.admin;

import com.kaishengit.entity.Node;
import com.kaishengit.service.NodeService;
import com.kaishengit.web.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by liu on 2016/12/28.
 */
@WebServlet("/admin/node")
public class NodeServlet extends BaseServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //將nodeList传到页面
        NodeService nodeService = new NodeService();
        List<Node> nodeList = nodeService.findAllNode();
        req.setAttribute("nodeList",nodeList);

        forward("admin/node",req,resp);
    }
}

package com.kaishengit.web.admin;

import com.kaishengit.dto.JsonResult;
import com.kaishengit.entity.Node;
import com.kaishengit.exception.ServiceException;
import com.kaishengit.service.NodeService;
import com.kaishengit.web.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by liu on 2016/12/29.
 */
@WebServlet("/admin/node/update")
public class NodeUpdateServlet extends BaseServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String nodeid = req.getParameter("nodeid");
        NodeService nodeService = new NodeService();
        try{
            Node node = nodeService.findNodeById(nodeid);
            req.setAttribute("node",node);
            forward("admin/nodeUpdate",req,resp);
        }catch (ServiceException e){
            resp.sendError(404,e.getMessage());
        }


    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String nodeid = req.getParameter("nodeid");
        String nodename = req.getParameter("nodename");

        NodeService nodeService = new NodeService();
        JsonResult jsonResult= null;
        try {
            nodeService.updateNode(nodeid, nodename);
            jsonResult = new JsonResult();
            jsonResult.setState(JsonResult.SUCCESS);
        }catch (ServiceException e){
            jsonResult = new JsonResult(e.getMessage());
        }
        renderJSON(jsonResult,resp);
    }
}


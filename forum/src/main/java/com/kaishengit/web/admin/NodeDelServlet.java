package com.kaishengit.web.admin;

import com.kaishengit.dto.JsonResult;
import com.kaishengit.exception.ServiceException;
import com.kaishengit.service.NodeService;
import com.kaishengit.web.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by liu on 2016/12/28.
 */
@WebServlet("/admin/nodeDel")
public class NodeDelServlet extends BaseServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String nodeid = req.getParameter("id");
        NodeService nodeService = new NodeService();
        JsonResult jsonResult = null;
        try{
            nodeService.delNodeById(nodeid);
            jsonResult  = new JsonResult();
            jsonResult.setState(JsonResult.SUCCESS);
        }catch (ServiceException e){
            jsonResult = new JsonResult();
            jsonResult.setState(JsonResult.ERROR);
            jsonResult.setMessage(e.getMessage());
        }
        renderJSON(jsonResult,resp);

    }
}

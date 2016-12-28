package com.kaishengit.web.user;

import com.kaishengit.service.NotifyService;
import com.kaishengit.web.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by liu on 2016/12/27.
 */
@WebServlet("/notifyRead")
public class NotifyReadServlet extends BaseServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String notifyidStr = req.getParameter("notifyidStr");
        String[] notifyIdArray = notifyidStr.split(",");
        for(int i=0;i<notifyIdArray.length;i++){
            Integer notifyId =Integer.valueOf(notifyIdArray[i]);
            NotifyService notifyService = new NotifyService();
            notifyService.updateByNotifyId(notifyId);

        }

        renderText("success",resp);
    }
}

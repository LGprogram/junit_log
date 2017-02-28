package com.kaishengit.controller;

import com.kaishengit.service.WeiXinService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;


/**
 * Created by liu on 2017/2/24.
 */
@Controller
@RequestMapping("/wx")
public class WeiXinController {

    private static Logger logger = LoggerFactory.getLogger(WeiXinController.class);
    @Autowired
    private WeiXinService weiXinService;
    @GetMapping("/init")
    @ResponseBody
    public String init(String msg_signature,String timestamp,String nonce,String echostr ){
        logger.info("{}-{}-{}-{}",msg_signature,timestamp,nonce,echostr);


        return weiXinService.init(msg_signature,timestamp,nonce,echostr);
    }

    @GetMapping("/metting")
    public String metting(String code,
                          @RequestHeader("User-Agent") String userAgent,
                          HttpServletResponse response){
        logger.debug("有人来了");
        logger.info("userAgent:{}",userAgent);
        String userId =  weiXinService.getUserId(code);
        logger.debug(userId);
        if(userId==null){
            logger.debug("未知用户访问");
            return "wx/403";
        }else{
            Cookie cookie = new Cookie("wxUser",userId);
            cookie.setMaxAge(60 * 60 * 24 * 30);
            cookie.setHttpOnly(true);
            cookie.setPath("/");
            response.addCookie(cookie);
            return "wx/metting";
        }

    }
    @GetMapping("/abc")
    public String abc(@CookieValue(value = "wxUser",required = false) String userId) {
        if(StringUtils.isEmpty(userId)) {
            logger.error("未知用户访问了abc");
            return "wx/403";
        } else {
            logger.info("成功到达Abc页面");
            return "wx/abc";
        }
    }
}

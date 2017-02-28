package com.kaishengit.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Created by liu on 2017/1/13.
 */
@Controller
public class HomeController {

    @RequestMapping(value = "/",method = RequestMethod.GET)
    public String login(){
        return "login";
    }
    @RequestMapping(value = "/",method = RequestMethod.POST)
    public String login(String username, String password, RedirectAttributes redirectAttribute){
        //shiro 方式的登录验证
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(new UsernamePasswordToken(username, password));
            return "redirect:/home";
        }catch (AuthenticationException e){
            e.printStackTrace();
            redirectAttribute.addFlashAttribute("message","账号或密码错误");
            return "redirect:/";
        }

    }
    @GetMapping("/logout")
    public String loginOut(RedirectAttributes redirectAttributes){
        redirectAttributes.addFlashAttribute("message","你已安全退出");
        SecurityUtils.getSubject().logout();
        return "redirect:/";
    }

    @RequestMapping(value = "/home",method = RequestMethod.GET)
    public String home() {
        return "home";
    }
    @RequestMapping("/403")
    public String error403() {
        return "403";
    }
}

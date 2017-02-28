package com.kaishengit.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by liu on 2017/1/10.
 */
@Controller
public class HomeController {
    @RequestMapping("/home")
    public String home(){
        System.out.println("hello,springMvc");
        return "home";
    }
}

package com.kaishengit.controller;

import com.kaishengit.dbutil.Page;
import com.kaishengit.exception.NotFoundException;
import com.kaishengit.pojo.Role;
import com.kaishengit.pojo.User;
import com.kaishengit.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * Created by liu on 2017/2/8.
 */
@Controller
@RequestMapping("/page/user")
public class Usercontroller {
    @Autowired
    private UserService userService;

    @RequestMapping(method = RequestMethod.GET)
    public String findAll(@RequestParam(required = false,defaultValue = "1") Integer p,
                          @RequestParam(required = false,defaultValue="") String q_name,
                          @RequestParam(required = false,defaultValue ="") String q_role,
                          Model model) throws UnsupportedEncodingException {
        List<Role> roleList = userService.findAllRole();
        model.addAttribute("roleList",roleList);
        if(q_name!= null && q_name!=""){
            q_name = new String(q_name.getBytes("ISO8859-1"),"UTF-8");
        }

        Page<User> page=  userService.findAll(p,q_name,q_role);
        model.addAttribute("page",page);
        model.addAttribute("q_name",q_name);
        model.addAttribute("q_role",q_role);
        return "/user/list";
    }

    @RequestMapping(value = "/new",method = RequestMethod.GET)
    public String save(Model model){

        List<Role> roleList = userService.findAllRole();
        model.addAttribute("roleList",roleList);
        return "/user/save";
    }
    @RequestMapping(value = "/new",method = RequestMethod.POST)
    public String save (User user,Integer[] roleIds, RedirectAttributes redirectAttributes){
        userService.save(user,roleIds);

        redirectAttributes.addFlashAttribute("message","操作成功");
        return "redirect:/page/user";
    }
    @RequestMapping(value = "/{id:\\d+}/del",method = RequestMethod.GET)
    public String delete(@PathVariable Integer id, RedirectAttributes redirectAttributes){
        userService.delete(id);
        redirectAttributes.addFlashAttribute("message","操作成功");
        return "redirect:/page/user";
    }
    @RequestMapping(value = "/{id:\\d+}/edit",method = RequestMethod.GET)
    public String edit(@PathVariable Integer id,Model model){
        User user =  userService.findById(id);
        if(user == null) {
            throw new NotFoundException();
        } else {
            List<Role> roleList = userService.findAllRole();
            model.addAttribute("roleList",roleList);

            model.addAttribute("user",user);
            return "/user/edit";
        }
    }
    @RequestMapping(value = "/{id:\\d+}/edit",method = RequestMethod.POST)
    public String edit(User user,Integer[] roleIds,RedirectAttributes redirectAttributes){
        userService.update(user,roleIds);
        redirectAttributes.addAttribute("message","操作成功");
        return "redirect:/page/user";
    }
}

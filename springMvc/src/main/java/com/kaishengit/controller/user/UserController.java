package com.kaishengit.controller.user;

import com.kaishengit.exception.NotfoundException;
import com.kaishengit.pojo.Account;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by liu on 2017/1/10.
 */
@Controller
@RequestMapping("/user")
public class UserController {
    @RequestMapping(value="/save",method = RequestMethod.GET)
    public String save(){

        return "user/save";
    }
    /*@RequestMapping(value="/save",method = RequestMethod.POST)
    public String save(String username){
        System.out.println("username:"+username);
        return "redirect:/home";//为什么这个地方要加一个:与前面的有区别为重定向到一个servlet/
    }*/
    /*@RequestMapping//省掉value和method的方法，根据条件匹配的方法只能有一个
    public String add(Account account,Integer id){
        System.out.println(account.getUsername()+":"+account.getAddress());
        return "redirect:/home";
    }*/


    @RequestMapping//user?id=1
    public String save(Integer id){
        System.out.println("id:" +id);
        return "redirect:/home";
    }
    /*@RequestMapping(value = "{id:\\d+}")// user/1
    public String save(@PathVariable Integer id){//得到URL路径中的变量
        System.out.println("id:" +id);
        return "redirect:/home";
    }*/
    @RequestMapping(value = "/{id:\\d{3,}}")// user/123且要求user/后变量位数超过3位
    public String add(@PathVariable Integer id){//得到URL路径中的变量
        System.out.println("id:" +id);
        return "redirect:/home";
    }

    /*@RequestMapping(value = "/{id:\\d+}/{type:\\d+}")
    public String show(@PathVariable Integer id, @PathVariable Integer type, Model model){
        System.out.println("id:"+id+"type:"+type);
        model.addAttribute("id",id);
        model.addAttribute("type",type);

        return "user/show";

    }*/
    @RequestMapping(value = "/{id:\\d+}/{type:\\d+}")//url:user/1/123
    public ModelAndView show(@PathVariable Integer id, @PathVariable Integer type){
        System.out.println("id:"+id+"...type:"+type);
        /*ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("user/show");
        modelAndView.addObject("id",id);
        modelAndView.addObject("type",type);
        return modelAndView;*/
        Map<String,Object> model = new HashMap();
        model.put("id",id);
        model.put("type",type);
        return new ModelAndView("user/show",model);
    }
    @RequestMapping(value="/id/{id:\\d+}/type/{type:\\d+}")//url:user/id/123/type/1234
    public ModelAndView showProduct(@PathVariable Integer id,@PathVariable Integer type){
        System.out.println("id:"+id+"...type:"+type);
        Map<String,Object> model = new HashMap();
        model.put("id",id);
        model.put("type",type);
        return new ModelAndView("user/show",model);
    }
    @RequestMapping(value = "/all", method=RequestMethod.GET)
    public String show(Model model){
        Account account = new Account("jack","usa");
        Account account1 = new Account("limei","china");
        List<Account> accountList = Arrays.asList(account,account1);
        model.addAttribute("accountList",accountList);
        return "user/showAll";

    }
    @RequestMapping(value ="/save",method = RequestMethod.POST)
    public String newAccount(Account account, Integer age, RedirectAttributes redirectAttributes){
        System.out.println(account.getUsername()+":"+account.getAddress());
        redirectAttributes.addFlashAttribute("message","添加成功");
        return "redirect:/user/all";

    }
    @RequestMapping(value = "/check/{userName}",method = RequestMethod.GET,
            produces = "text/plain;charset=UTF-8")//指定响应头类型content-type为UTF-8
    @ResponseBody
    public String checkUserName(@PathVariable String userName) {
        if(userName.equals("tom")) {
            return "不可用";
        } else {
            return "可以用";
        }
    }
    @RequestMapping("/get")
    @ResponseBody
    public Account get(){
        Account account = new Account("jack","usa");
        return account;
    }
    @RequestMapping("/http")
    public String method(HttpServletRequest request, HttpServletResponse response,
                         HttpSession session) {

        return "user/all";
    }
    @RequestMapping(value = "/upload",method = RequestMethod.GET)
    public String fileUpload() {
        return "user/upload";
    }
    @RequestMapping(value = "/upload",method = RequestMethod.POST)
// Springmvc提供MultipartFile接口的实现类来获取上传对象
    public String saveFile(String name, MultipartFile file) throws IOException {
        System.out.println("Name:" + name);
        if(!file.isEmpty()) {
            //上传表单控件的name属性值
            System.out.println("getName:" + file.getName());
            //上传文件的原始名称
            System.out.println("getOriginalFilename:" + file.getOriginalFilename());
            //文件大小（字节）
            System.out.println("size: " + file.getSize());
            //文件的MIME类型
            System.out.println("ContentType:" + file.getContentType());

            InputStream inputStream = file.getInputStream();
            FileOutputStream outputStream = new FileOutputStream(new File("d:/new.jpg"));
            IOUtils.copy(inputStream,outputStream);
            outputStream.flush();
            outputStream.close();
            inputStream.close();
        }
        return "redirect:/home";
    }

    @RequestMapping(value = "/check/{id:\\d+}",method = RequestMethod.GET)
    public String checkException(@PathVariable Integer id){
        if(id==100){
            throw new NotfoundException();
        }else{
            return "redirect:/home";
        }
    }
}

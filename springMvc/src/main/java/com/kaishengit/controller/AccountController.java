package com.kaishengit.controller;

        import com.kaishengit.pojo.Account;
        import org.springframework.web.bind.annotation.*;
        import org.springframework.web.client.RestTemplate;

/**
 * Created by liu on 2017/1/11.
 */
@RestController//此注解下不允许返回视图只能返回json相当于@Controller和@ResponseBody
@RequestMapping("/account")
public class AccountController {
    @GetMapping(value = "/check/{id:\\d+}")
    //@RequestHeader("user-Agent")读取请求头里面的键，将值赋给参数
    //@CookieValue(value = "name",required = false,defaultValue = "not set")读取cookie中的键的值
    public Account get(@PathVariable Integer id, @RequestHeader("user-Agent") String userAgent,@CookieValue(value = "name",required = false,defaultValue = "not set") String name){
        /*RestTemplate的getForObject完成get请求、postForObject完成post请求、put对应的完成put请求、delete完成delete请求；还有execute可以执行任何请求的方法，需要你设置RequestMethod来指定当前请求类型。
        RestTemplate.getForObject(String url, Class responseType, String... urlVariables)
        参数url是http请求的地址，参数Class是请求响应返回后的数据的类型，最后一个参数是请求中需要设置的参数。
        template.getForObject(url + "get/{id}.do", String.class, id);
        如上面的参数是{id}，返回的是一个string类型，设置的参数是id。最后执行该方法会返回一个String类型的结果。*/
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject("http://www.baidu.com",String.class);
        System.out.println(result);
        System.out.println("user-Agent:"+userAgent);
        System.out.println("CookieValue:name:"+name);
        Account account = new Account("jack","usa");
        return account;
    }
}

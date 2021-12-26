package com.chenchao.controller.consumers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 访问WEN-INF下的页面
 * 页面通过controller
 */
@Controller
public class PageJumpController {
    //权限不足页面
    @RequestMapping("/403")
    public String to403(){
        return "consumers/403";
    }

    @RequestMapping("/page/login")
    public String loginPage(){
        return "login";
    }

    @RequestMapping("/page/register")
    public String registerPage(){
        return "register";
    }

    @RequestMapping("/pay/success")
    public String paySuccess(){
        return "consumers/pay_success";
    }

    //后台页面

    @RequestMapping("/admin/home")
    public String adminHome(){
        return "admin/home";
    }

    @RequestMapping("/admin/center")
    public String adminCenter(){
        return "admin/center";
    }

    @RequestMapping("/admin/right")
    public String adminRight(){
        return "admin/right";
    }

    @RequestMapping("/admin/left")
    public String adminLeft(){
        return "admin/left";
    }

    //书籍访问量饼状图页面
    @RequestMapping("/admin/report/views")
    public String storeBookViews(){
        return "admin/report/views";
    }

    //书籍销量饼状图页面
    @RequestMapping("/admin/report/sales")
    public String storeBookSales(){
        return "admin/report/sales";
    }

    //后台页面

    @RequestMapping("/merchants/home")
    public String merchantsHome(){
        return "merchants/home";
    }

    @RequestMapping("/merchants/center")
    public String merchantsCenter(){
        return "merchants/center";
    }

    @RequestMapping("/merchants/right")
    public String merchantsRight(){
        return "merchants/right";
    }

    @RequestMapping("/merchants/left")
    public String merchantsLeft(){
        System.out.println("merchants1122");
        return "merchants/left";
    }

}

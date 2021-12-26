package com.chenchao.controller.consumers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

/**
 * Created by Administrator on 2021/04/25.
 */
@Controller
@RequestMapping("/user")
public class loginController {
    /**
     * 验证登录
     *
     * @param username
     * @param password
     * @param session
     * @return url
     */
    @RequestMapping(value = "/login")
    public String Login(String username, String password, HttpSession session, Model model) {
        return "consumers/index";
    }



    @RequestMapping("/nopermission")
    public String noPermission(){
        return "consumers/error";
    }
}

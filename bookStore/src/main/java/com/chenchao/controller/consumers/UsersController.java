package com.chenchao.controller.consumers;

import com.chenchao.utils.MD5Util;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.chenchao.model.Users;
import com.chenchao.model.Shop;
import com.chenchao.service.IShopService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.shiro.authc.UnknownAccountException;
import com.chenchao.service.ShiroService;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.authc.UsernamePasswordToken;
import com.chenchao.service.UserService;

@Controller
@RequestMapping("/users")
public class UsersController {
    private static String charset = "UTF-8";

    @Autowired
    private IShopService storeService;

    @Autowired
    private ShiroService shiroService;

    @Autowired
    private UserService userService;

    @RequestMapping("/information")
    public String personInfo(){
        return "consumers/users_information";
    }

    private final String USERNAME_PASSWORD_NOT_MATCH = "用户名或密码错误";

    @RequestMapping("/login")
    public String login(@RequestParam(value = "username", required = false) String username,
                        @RequestParam(value = "password", required = false) String password,
                        HttpServletRequest request, Model model) {
        System.out.println("11111");
        if(username==null){
            model.addAttribute("message", "账号不为空");
            return "login";
        }
        //主体,当前状态为没有认证的状态“未认证”
        Subject subject = SecurityUtils.getSubject();

        password = MD5Util.MD5Encode(password,charset);
//        password = "202cb962ac59075b964b07152d234b70";
        // 登录后存放进shiro token
        UsernamePasswordToken token=new UsernamePasswordToken(username,password);
        Users loginUser;

        //登录方法（认证是否通过）
        //使用subject调用securityManager,安全管理器调用Realm
        try {
            //利用异常操作
            //需要开始调用到Realm中
            System.out.println("========================================");
            System.out.println("1、进入认证方法");
            subject.login(token);
            loginUser = (Users) subject.getPrincipal();
            request.getSession().setAttribute("loginUser", loginUser);
            Shop store = storeService.findStoreByUserId(loginUser.getUserId());
            request.getSession().setAttribute("loginStore", store);            model.addAttribute("message", "登录完成");
            System.out.println("登录完成");
        } catch (UnknownAccountException e) {
            model.addAttribute("message", "账号密码不正确");
            return "login";
        }
        if(userService.findCharacterIdByUserId(username)==1){
            return "redirect:/admin_index";
        }
        else if(userService.findCharacterIdByUserId(username)==2){
            return "redirect:/use_index";
        }
        else if(userService.findCharacterIdByUserId(username)==3){
            return "redirect:/merchants_index";
        }
        return "login";

    }

    @RequestMapping("/logout")
    @CacheEvict(cacheNames="authorizationCache",allEntries = true)
    public String logout() {
//        SecurityUtils.getSubject().logout();
        return "login";
    }
}

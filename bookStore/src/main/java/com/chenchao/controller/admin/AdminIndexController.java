package com.chenchao.controller.admin;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/admin")
public class AdminIndexController {


    @RequestMapping({"", "/", "/index"})
    public String adminIndex() {
        return "admin/index";
    }
}

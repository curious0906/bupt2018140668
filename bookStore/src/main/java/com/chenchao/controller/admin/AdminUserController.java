package com.chenchao.controller.admin;

import com.chenchao.model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.chenchao.service.UserService;
import com.chenchao.dao.custom.CustomMapper;
import com.chenchao.pojo.BSResult;
import com.chenchao.model.CharacterAlter;
import com.chenchao.service.IRoleService;
import java.util.List;

@Controller
@RequestMapping("/admin/user")
public class AdminUserController {

    @Autowired
    private UserService userService;

    @Autowired
    private CustomMapper customMapper;

    @Autowired
    private IRoleService roleService;

    @RequestMapping("/echo/{userId}")
//    @RequiresPermissions("user-edit")
    public String echo(@PathVariable("userId") int userId, Model model){
        Users user = userService.findById(userId);
        List<CharacterAlter> userRoles = customMapper.findRolesByUserId(userId);
        List<CharacterAlter> allRoles = roleService.findAllRoles();
        model.addAttribute("user", user);
        model.addAttribute("userRoles", userRoles);
        model.addAttribute("allRoles", allRoles);
        return "admin/user/edit";
    }

    @RequestMapping("/update")
//    @RequiresPermissions("user-edit")
    public String updateUser(Users user, int[] roleIds, Model model){
        BSResult bsResult = userService.updateUser(user);
        if(bsResult.getCode() == 200){
            model.addAttribute("saveMsg","保存成功");
        }
        //更新用户角色的对应关系
        roleService.updateUserRoleRelationship(user.getUserId(),roleIds);
        return "forward:/admin/user/echo/"+user.getUserId();
    }

    @RequestMapping("/addition")
//    @RequiresPermissions("user-add")
    public String addUser(Users user, int[] roleIds){
        Users userFromDB = userService.addUser(user);
        //更新用户角色的对应关系
        roleService.updateUserRoleRelationship(userFromDB.getUserId(),roleIds);
        return "forward:list";
    }

    @RequestMapping("/deletion/{userId}")
//    @RequiresPermissions("user-delete")
    public String delUser(@PathVariable("userId") int userId){
        userService.delUser(userId);
        return "forward:../list";
    }

    @RequestMapping("/list")
//    @RequiresPermissions("user-list")
    public String userList(Model model){
        List<Users> users = userService.findAllUsers();
        model.addAttribute("users", users);
        return "admin/user/list";
    }

    @RequestMapping("/toAddition")
//    @RequiresPermissions("user-add")
    public String toAddition(Model model){
        model.addAttribute("allRoles", roleService.findAllRoles());
        return "admin/user/add";
    }

    @RequestMapping("/logout")
    @CacheEvict(cacheNames="authenticationCache",allEntries = true)
    public String logout() {
//        SecurityUtils.getSubject().logout();
        return "login";
    }
}


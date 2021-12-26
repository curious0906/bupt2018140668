package com.chenchao.controller.admin;

import com.chenchao.model.Permissions;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chenchao.model.ZTreeNode;
import com.chenchao.service.IPermissionsService;
import com.chenchao.pojo.BSResult;

import java.util.List;

@Controller
@RequestMapping("admin/privilege")
//@RequiresPermissions("privilege-manage")
public class PermissionsController {


    @Autowired
    private IPermissionsService permissionsService;

    @ResponseBody
    @RequestMapping("/treeNodes")
    public List<ZTreeNode> treeNodesJsonData(){
        return permissionsService.getZTreeNodes();
    }

    @ResponseBody
    @RequestMapping("/rolePrivileges/{roleId}")
    public List<ZTreeNode> treeRolePrivileges(@PathVariable("roleId") int roleId){
        return permissionsService.getRolePrivileges(roleId);
    }


    @ResponseBody
    @RequestMapping("/{privId}")
    public BSResult getPrivilege(@PathVariable("privId") int privId){
        return permissionsService.findById(privId);
    }


    @RequestMapping("/toEdit/{roleId}")
//    @RequiresPermissions("privilege-edit")
    public String toEditPrivilege(@PathVariable("roleId") int roleId, Model model){
        model.addAttribute("roleId", roleId);
        return "admin/permissions/edit";
    }


    @ResponseBody
    @RequestMapping("/edit")
//    @RequiresPermissions("privilege-edit")
    public BSResult editPrivilege(Permissions privilege){

        BSResult bsResult = permissionsService.updatePrivilege(privilege);

        return bsResult;
    }

    @RequestMapping("/list")
//    @RequiresPermissions("privilege-list")
    public String privilegeList(){
        return "admin/permissions/list";
    }

    @RequestMapping("/addition")
    @ResponseBody
//    @RequiresPermissions("privilege-add")
    public BSResult addPrivilege(Permissions privilege){
        //添加权限，仅有权限名字，之后再修改
        BSResult bsResult = permissionsService.addPrivilege(privilege);
        return bsResult;
    }

    @RequestMapping("/deletion/{privId}")
    @ResponseBody
//    @RequiresPermissions("privilege-delete")
    public BSResult deletePrivilege(@PathVariable("privId") int privId){
        BSResult bsResult = permissionsService.deleteById(privId);
        return bsResult;
    }

}

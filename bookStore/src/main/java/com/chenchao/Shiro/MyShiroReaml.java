package com.chenchao.Shiro;


import com.chenchao.model.Permissions;
import com.chenchao.model.Users;
import com.chenchao.service.ShiroService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import com.chenchao.utils.MD5Util;

import java.util.List;



public class MyShiroReaml extends AuthorizingRealm {


    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection pc) {

        /**
         *
         * 流程
         * 1.根据用户user->2.获取角色id->3.根据角色id获取权限permission
         */
        //方法一：获得user对象
        Users user=(Users)pc.getPrimaryPrincipal();
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        //获取permission
        if(user!=null) {
            List<Permissions> permissionsByUser = shiroService.getPermissionsByUser(user);
            if (permissionsByUser.size()!=0) {
                for (Permissions p: permissionsByUser) {
                    System.out.println("hello");
                    info.addStringPermission(p.getPermUrl());
                }
                System.out.println("hi");
                return info;
            }
        }

        //方法二： 从subject管理器里获取user
//      Subject subject = SecurityUtils.getSubject();
//      User _user = (User) subject.getPrincipal();
//      System.out.println("subject"+_user.getUsername());




        return null;
    }

    // 认证方法
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("进来验证了");
        //验证账号密码
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        System.out.println("1:"+token.getUsername());
        Users user = shiroService.getUserByUserName(token.getUsername());
        System.out.println("2");
        if(user==null){
            return null;
        }
        //最后的比对需要交给安全管理器
        //三个参数进行初步的简单认证信息对象的包装
        AuthenticationInfo info = new SimpleAuthenticationInfo(user, user.getUserPassword(), this.getName());

        return info;
    }


    private ShiroService shiroService;

    public ShiroService getShiroService() {
        return shiroService;
    }

    public void setShiroService(ShiroService shiroService) {
        this.shiroService = shiroService;
    }
}


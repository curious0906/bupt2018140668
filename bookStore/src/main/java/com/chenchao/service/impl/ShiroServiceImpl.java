package com.chenchao.service.impl;


import com.chenchao.dao.ShiroDao;
import com.chenchao.model.Permissions;
import com.chenchao.model.Users;
import com.chenchao.service.ShiroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2021/04/25.
 */
@Service("shiroService")
public class ShiroServiceImpl implements ShiroService {

    @Autowired
    private ShiroDao shiroDao;

    public Users getUserByUserName(String username) {
        //根据账号获取账号密码
        Users userByUserName = shiroDao.getUserByUserName(username);
        return userByUserName;
    }

    public List<Permissions> getPermissionsByUser(Users user) {
        //获取到用户角色userRole
        List<Integer> roleId = shiroDao.getUserRoleByUserId(user.getUserId());
        List<Permissions> perArrary = new ArrayList<>();

        if (roleId!=null&&roleId.size()!=0) {
            //根据roleid获取peimission
            for (Integer i : roleId) {
                perArrary.addAll(shiroDao.getPermissionsByRoleId(i));
            }
        }
        System.out.println(perArrary);
        return perArrary;
    }


}

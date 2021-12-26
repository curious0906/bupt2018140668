package com.chenchao.service;


import com.chenchao.model.Permissions;
import com.chenchao.model.Users;

import java.util.List;

/**
 * Created by Administrator on 2017/10/10.
 */
public interface ShiroService {

    /**
     * 根据账号获取账号密码
     * @param username
     * @return UserPojo
     */
    Users getUserByUserName(String username);

    /**
     * 根据账号获取该账号的权限
     *
     * @param user
     * @return List
     */
    List<Permissions> getPermissionsByUser(Users user);
}

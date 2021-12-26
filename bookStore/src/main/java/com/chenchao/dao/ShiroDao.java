package com.chenchao.dao;

import com.chenchao.model.Permissions;
import com.chenchao.model.Users;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by Administrator on 2021/04/24
 * */
@Component
public interface ShiroDao {

    /**
     * 根据账号获取账号密码
     * @param username
     * @return UserPojo
     */
    Users getUserByUserName(String username);

    /**
     * 根据角色id获取该账号的权限
     * @param roleId
     * @return List
     */
    List<Permissions> getPermissionsByRoleId(int roleId);

    /**
     * 根据userId获取角色id
     * @param id
     * @return LIST
     */
    List<Integer> getUserRoleByUserId(int id);

}

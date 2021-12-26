package com.chenchao.service;

import com.chenchao.model.Shop;
import com.chenchao.model.Users;
import com.chenchao.pojo.BSResult;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface UserService {
    /**
     * 分页查询用户信息数据
     * @param pageNum：当前页
     * @param size：每页显示的数据
     * @return
     */
    PageInfo<Users> findAll(int pageNum, int size);
    Integer findCharacterIdByUserId(String username);

    Users findById(Integer userId);

    BSResult updateUser(Users user);

    Users addUser(Users user);

    void delUser(int userId);

    List<Users> findAllUsers();

    List<Users> findBusinesses(int roleId);

}


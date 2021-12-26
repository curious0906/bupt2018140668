package com.chenchao.service.impl;

import com.chenchao.dao.UserMapper;
import com.chenchao.model.Shop;
import com.chenchao.model.Users;
import com.chenchao.pojo.BSResult;
import com.chenchao.service.UserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.chenchao.dao.custom.CustomMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private CustomMapper customMapper;

    /**
     * 分页查询用户信息数据
     * @param pageNum：当前页
     * @param size：每页显示的数据
     * @return
     */
    @Override
    public PageInfo<Users> findAll(int pageNum, int size) {
        PageHelper.startPage(pageNum,size);
        List<Users> userList = userMapper.findAll();
        return new PageInfo<Users>(userList);
    }

    @Override
    public Integer findCharacterIdByUserId(String username) {
        Integer characterId = userMapper.findCharacterIdByUserId(username);
        return characterId;
    }

    @Override
    public Users findById(Integer userId) {
        return null;
    }

    @Override
    public BSResult updateUser(Users user) {
        return null;
    }

    @Override
    public Users addUser(Users user) {
        return null;
    }

    @Override
    public void delUser(int userId) {

    }

    @Override
    public List<Users> findAllUsers() {
        return userMapper.selectAll();
    }

    @Override
    public List<Users> findBusinesses(int roleId) {
        return customMapper.findBusinesses(roleId);
    }

}

package com.chenchao.service.impl;

import com.chenchao.dao.RoleDao;
import com.chenchao.dao.custom.CustomMapper;
import com.chenchao.model.CharacterAlter;
import com.chenchao.pojo.BSResult;
import com.chenchao.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class RoleServiceImpl implements IRoleService {
    @Autowired
    private RoleDao roleDao;
    @Override
    public List<CharacterAlter> findAllRoles() {
        return roleDao.selectAll();
    }

    @Override
    public BSResult updateUserRoleRelationship(Integer userId, int[] roleIds) {
        return null;
    }

    @Override
    public CharacterAlter findById(int roleId) {
        return null;
    }

    @Override
    public BSResult deleteById(int roleId) {
        return null;
    }

    @Override
    public BSResult addRole(CharacterAlter role) {
        return null;
    }

    @Override
    public BSResult updateRole(CharacterAlter role) {
        return null;
    }

    @Override
    public BSResult updateRolesPrivilege(int[] ids, int roleId) {
        return null;
    }
}

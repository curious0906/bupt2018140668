package com.chenchao.service;

import com.chenchao.pojo.BSResult;
import com.chenchao.model.CharacterAlter;
import java.util.List;

public interface IRoleService {
    List<CharacterAlter> findAllRoles();

    BSResult updateUserRoleRelationship(Integer userId, int[] roleIds);

    CharacterAlter findById(int roleId);

    BSResult deleteById(int roleId);

    BSResult addRole(CharacterAlter role);

    BSResult updateRole(CharacterAlter role);

    BSResult updateRolesPrivilege(int[] ids, int roleId);
}


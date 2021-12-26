package com.chenchao.service;

import java.util.List;

import com.chenchao.model.Permissions;
import com.chenchao.pojo.BSResult;
import com.chenchao.model.ZTreeNode;

public interface IPermissionsService {
    List<ZTreeNode> getZTreeNodes();

    BSResult findById(int privId);

    BSResult updatePrivilege(Permissions privilege);

    BSResult addPrivilege(Permissions privilege);

    BSResult deleteById(int privId);

    List<ZTreeNode> getRolePrivileges(int roleId);
}

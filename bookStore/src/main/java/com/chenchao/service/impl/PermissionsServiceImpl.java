package com.chenchao.service.impl;

import com.chenchao.model.Permissions;
import com.chenchao.model.ZTreeNode;
import com.chenchao.pojo.BSResult;
import com.chenchao.service.IPermissionsService;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class PermissionsServiceImpl implements IPermissionsService{

    @Override
    public List<ZTreeNode> getZTreeNodes() {
        return null;
    }

    @Override
    public BSResult findById(int privId) {
        return null;
    }

    @Override
    public BSResult updatePrivilege(Permissions privilege) {
        return null;
    }

    @Override
    public BSResult addPrivilege(Permissions privilege) {
        return null;
    }

    @Override
    public BSResult deleteById(int privId) {
        return null;
    }

    @Override
    public List<ZTreeNode> getRolePrivileges(int roleId) {
        return null;
    }
}

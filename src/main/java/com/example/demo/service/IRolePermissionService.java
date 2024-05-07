package com.example.demo.service;

import com.example.demo.model.RolePermission;

import java.util.List;

public interface IRolePermissionService {
    List<RolePermission> findAllByRoleId(Long roleId);
}

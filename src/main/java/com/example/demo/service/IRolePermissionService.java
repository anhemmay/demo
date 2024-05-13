package com.example.demo.service;

import com.example.demo.dto.request.RolePermissionDTO;
import com.example.demo.exception.DataNotFoundException;
import com.example.demo.model.Role;
import com.example.demo.model.RolePermission;

import java.util.List;

public interface IRolePermissionService {
    List<RolePermission> findAllByRoleId(Long roleId);
    RolePermission addRolePermissionToRole(RolePermissionDTO rolePermissionDTO) throws DataNotFoundException;

}

package com.example.demo.service;

import com.example.demo.dto.request.PermissionDTO;
import com.example.demo.exception.DataNotFoundException;
import com.example.demo.model.Permission;

public interface IPermissionService {
    Permission createPermission(PermissionDTO permissionDTO);
    Permission updatePermission(Long permissionId, PermissionDTO permissionDTO) throws DataNotFoundException;
    void deletePermission(Long permissionId) throws DataNotFoundException;
}

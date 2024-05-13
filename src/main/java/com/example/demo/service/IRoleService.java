package com.example.demo.service;

import com.example.demo.dto.request.RoleDTO;
import com.example.demo.dto.request.RolePermissionDTO;
import com.example.demo.exception.DataNotFoundException;
import com.example.demo.model.Role;

import java.util.List;

public interface IRoleService {
    List<Role> getRoles();
    Role getRoleById(Long roleId) throws DataNotFoundException;
    Role createRole(RoleDTO roleDTO) throws Exception;
    Role updateRole(Long roleId, RoleDTO roleDTO) throws DataNotFoundException;
    void deleteRole(Long roleId) throws DataNotFoundException;

}

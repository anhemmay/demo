package com.example.demo.service.impl;

import com.example.demo.model.RolePermission;
import com.example.demo.repository.RolePermissionRepository;
import com.example.demo.service.IRolePermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RolePermissionServiceImpl implements IRolePermissionService {
    private final RolePermissionRepository rolePermissionRepository;

    @Override
    public List<RolePermission> findAllByRoleId(Long roleId) {
        return rolePermissionRepository.findAllByRoleId(roleId);
    }
}

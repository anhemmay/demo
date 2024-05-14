package com.example.demo.service.impl;

import com.example.demo.dto.request.RolePermissionDTO;
import com.example.demo.exception.DataNotFoundException;
import com.example.demo.model.Permission;
import com.example.demo.model.Role;
import com.example.demo.model.RolePermission;
import com.example.demo.repository.PermissionRepository;
import com.example.demo.repository.RolePermissionRepository;
import com.example.demo.repository.RoleRepository;
import com.example.demo.service.IRolePermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RolePermissionServiceImpl implements IRolePermissionService {
    private final RolePermissionRepository rolePermissionRepository;
    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;

    @Override
    public List<RolePermission> findAllByRoleId(Long roleId) {
        return rolePermissionRepository.findAllByRoleId(roleId);
    }
    @Transactional
    @Override
    public RolePermission addRolePermissionToRole(RolePermissionDTO rolePermissionDTO) throws DataNotFoundException {

        Role existRole = roleRepository
                .findById(rolePermissionDTO.getRoleId())
                .orElseThrow(() -> new DataNotFoundException("Cannot find role"));
        Permission existPermission = permissionRepository
                .findById(rolePermissionDTO.getPermissionId())
                .orElseThrow(() -> new DataNotFoundException("Cannot find permission"));
        Optional<RolePermission> optionalRolePermission = rolePermissionRepository.findByRoleAndPermission(existRole, existPermission);
        if (optionalRolePermission.isPresent()) {
            RolePermission existRolePermission = optionalRolePermission.get();
            return rolePermissionRepository.save(existRolePermission);
        } else {
            RolePermission newRolePermission = new RolePermission();
            newRolePermission.setRole(existRole);
            newRolePermission.setPermission(existPermission);
            return rolePermissionRepository.save(newRolePermission);
        }
    }
}

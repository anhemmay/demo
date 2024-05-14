package com.example.demo.service.impl;

import com.example.demo.dto.request.PermissionDTO;
import com.example.demo.dto.request.RoleDTO;
import com.example.demo.dto.request.RolePermissionDTO;
import com.example.demo.exception.DataNotFoundException;
import com.example.demo.model.Permission;
import com.example.demo.model.Role;
import com.example.demo.model.RolePermission;
import com.example.demo.repository.PermissionRepository;
import com.example.demo.repository.RolePermissionRepository;
import com.example.demo.repository.RoleRepository;
import com.example.demo.service.IRoleService;
import com.example.demo.util.ConvertUtil;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements IRoleService {
    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<Role> getRoles() {
        return roleRepository.findAll();
    }

    @Override
    public Role getRoleById(Long roleId) throws DataNotFoundException {
        return roleRepository.findById(roleId).orElseThrow(
                () -> new DataNotFoundException(String.format("Cannot find role with id: %d", roleId))
        );
    }

    @Transactional
    @Override
    public Role createRole(RoleDTO roleDTO) throws Exception {
        Role newRole = ConvertUtil.convertObject(roleDTO,
                object -> modelMapper.map(object, Role.class)
        );

        return roleRepository.save(newRole);
    }

    @Transactional
    @Override
    public Role updateRole(Long roleId, RoleDTO roleDTO) throws DataNotFoundException {
        Role existRole = roleRepository.findById(roleId)
                .orElseThrow(() -> new DataNotFoundException(String.format("Cannot find role with id: %d", roleId)));
        roleDTO.setName(roleDTO.getName().toUpperCase());
        ConvertUtil.convertObject(
                roleDTO, object -> {
                    modelMapper.map(object, existRole);
                    return existRole;
                }
        );
        return roleRepository.save(existRole);
    }




    @Transactional
    @Override
    public void deleteRole(Long roleId) throws DataNotFoundException {
        Role existRole = roleRepository.findById(roleId)
                .orElseThrow(() -> new DataNotFoundException(String.format("Cannot find role with id: %d", roleId)));
        existRole.setStatus(false);
        roleRepository.save(existRole);
    }

}

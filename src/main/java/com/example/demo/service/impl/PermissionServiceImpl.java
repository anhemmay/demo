package com.example.demo.service.impl;

import com.example.demo.dto.request.PermissionDTO;
import com.example.demo.exception.DataNotFoundException;
import com.example.demo.model.Permission;
import com.example.demo.repository.PermissionRepository;
import com.example.demo.service.IPermissionService;
import com.example.demo.util.ConvertUtil;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PermissionServiceImpl implements IPermissionService {

    private final PermissionRepository permissionRepository;

    private final ModelMapper modelMapper;

    @Override
    public Permission createPermission(PermissionDTO permissionDTO) {
        Permission newPermission = ConvertUtil.convertObject(permissionDTO, object -> modelMapper.map(object, Permission.class));
        return permissionRepository.save(newPermission);
    }

    @Override
    public Permission updatePermission(Long permissionId, PermissionDTO permissionDTO) throws DataNotFoundException {
        Permission existPermission = permissionRepository
                .findById(permissionId)
                .orElseThrow(() -> new DataNotFoundException("Cannot find permission"));
        ConvertUtil.convertObject(permissionDTO, object -> {
                    modelMapper.map(permissionDTO, existPermission);
                    return existPermission;
        });
        return permissionRepository.save(existPermission);
    }

    @Override
    public void deletePermission(Long permissionId) {

    }


}

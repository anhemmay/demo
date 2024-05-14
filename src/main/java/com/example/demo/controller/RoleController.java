package com.example.demo.controller;


import com.example.demo.common.constant.ResponseConstant;
import com.example.demo.dto.request.RoleDTO;
import com.example.demo.dto.request.RolePermissionDTO;
import com.example.demo.dto.response.Response;
import com.example.demo.model.Role;
import com.example.demo.model.RolePermission;
import com.example.demo.service.IRolePermissionService;
import com.example.demo.service.IRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.demo.common.constant.ResponseConstant.ERROR_CODE;
import static com.example.demo.common.constant.ResponseConstant.SUCCESS_CODE;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/role")
public class RoleController {
    private final IRoleService roleService;
    private final IRolePermissionService rolePermissionService;

    @GetMapping("get-roles")
    public ResponseEntity<Response<List<Role>>> getRoles(){
        try{
            List<Role> roles = roleService.getRoles();
            return ResponseEntity.ok().body(
                    new Response<>(SUCCESS_CODE, "", roles)
            );
        }catch (Exception e){
            return ResponseEntity.badRequest().body(
                    new Response<>(ERROR_CODE, e.getMessage())
            );
        }
    }

    @PostMapping("insert-role")
    public ResponseEntity<Response<Role>> createRole(@RequestBody RoleDTO roleDTO){
        try{
            Role role = roleService.createRole(roleDTO);
            return ResponseEntity.ok().body(
                    new Response<>(SUCCESS_CODE, "", role)
            );
        }catch (Exception e){
            return ResponseEntity.badRequest().body(
                    new Response<>(ERROR_CODE, e.getMessage())
            );
        }
    }

    @PutMapping("/update-role")
    public ResponseEntity<Response<Role>> updateRole(@RequestBody RoleDTO roleDTO, @RequestParam Long roleId){
        try{
            Role role = roleService.updateRole(roleId, roleDTO);
            return ResponseEntity.ok().body(
                    new Response<>(SUCCESS_CODE, "", role)
            );
        }catch (Exception e){
            return ResponseEntity.badRequest().body(
                    new Response<>(ERROR_CODE, e.getMessage())
            );
        }
    }

    @DeleteMapping("/delete-role")
    public ResponseEntity<Response<List<Role>>> deleteRole(@RequestParam("id") Long roleId){
        try{
            roleService.deleteRole(roleId);
            return ResponseEntity.ok().body(
                    new Response<>(SUCCESS_CODE, "Delete role successfully")
            );
        }catch (Exception e){
            return ResponseEntity.badRequest().body(
                    new Response<>(ERROR_CODE, e.getMessage())
            );
        }
    }

    @PostMapping("/add-role-permission-to-role")
    public ResponseEntity<Response<RolePermission>> addRolePermission(@RequestBody RolePermissionDTO rolePermissionDTO){
        try{
        RolePermission rolePermission = rolePermissionService.addRolePermissionToRole(rolePermissionDTO);
            return ResponseEntity.ok().body(new Response<>(SUCCESS_CODE, "success", rolePermission));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(new Response<>(ERROR_CODE, e.getMessage()));
        }
    }

}

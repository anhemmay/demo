package com.example.demo.controller;


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

@RestController
@RequiredArgsConstructor
@RequestMapping("api/role")
public class RoleController {
    private final IRoleService roleService;
    private final IRolePermissionService rolePermissionService;

    @GetMapping
    public ResponseEntity<Response<List<Role>>> getRoles(){
        try{
            List<Role> roles = roleService.getRoles();
            return ResponseEntity.ok().body(
                    new Response<>("200", "", roles)
            );
        }catch (Exception e){
            return ResponseEntity.badRequest().body(
                    new Response<>("403", e.getMessage())
            );
        }
    }

    @PostMapping
    public ResponseEntity<Response<Role>> createRole(@RequestBody RoleDTO roleDTO){
        try{
            Role role = roleService.createRole(roleDTO);
            return ResponseEntity.ok().body(
                    new Response<>("200", "", role)
            );
        }catch (Exception e){
            return ResponseEntity.badRequest().body(
                    new Response<>("403", e.getMessage())
            );
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response<Role>> updateRole(@RequestBody RoleDTO roleDTO, @PathVariable("id") Long roleId){
        try{
            Role role = roleService.updateRole(roleId, roleDTO);
            return ResponseEntity.ok().body(
                    new Response<>("200", "", role)
            );
        }catch (Exception e){
            return ResponseEntity.badRequest().body(
                    new Response<>("403", e.getMessage())
            );
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response<List<Role>>> deleteRole(@PathVariable("id") Long roleId){
        try{
            roleService.deleteRole(roleId);
            return ResponseEntity.ok().body(
                    new Response<>("200", "Delete role successfully")
            );
        }catch (Exception e){
            return ResponseEntity.badRequest().body(
                    new Response<>("403", e.getMessage())
            );
        }
    }

    @PostMapping("/add-role-permission-to-role")
    public ResponseEntity<Response<RolePermission>> addRolePermission(@RequestBody RolePermissionDTO rolePermissionDTO){
        try{
        RolePermission rolePermission = rolePermissionService.addRolePermissionToRole(rolePermissionDTO);
            return ResponseEntity.ok().body(new Response<>("200", "success", rolePermission));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(new Response<>("403", e.getMessage()));
        }
    }

}

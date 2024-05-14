package com.example.demo.controller;

import com.example.demo.common.constant.ResponseConstant;
import com.example.demo.dto.request.PermissionDTO;
import com.example.demo.dto.response.Response;
import com.example.demo.model.Permission;
import com.example.demo.service.IPermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.example.demo.common.constant.ResponseConstant.*;

@RestController
@RequiredArgsConstructor

@RequestMapping("/api/v1/permissions")
public class PermissionController {
    private final IPermissionService permissionService;

    @PostMapping("/insert-permission")
    public ResponseEntity<Response<Permission>> createPermission(@RequestBody PermissionDTO permissionDTO){
        try {
        return ResponseEntity.ok().body(new Response<>(CREATE_CODE, "successfully",permissionService.createPermission(permissionDTO)));

        }catch (Exception e){
            return ResponseEntity.badRequest().body(new Response<>(ERROR_CODE, e.getMessage()));
        }
    }

    @PutMapping("/update-permission")
    public ResponseEntity<Response<Permission>> updatePermission(@RequestParam Long id, @RequestBody PermissionDTO permissionDTO){
        try {
            return ResponseEntity.ok().body(new Response<>(SUCCESS_CODE, "successfully",permissionService.updatePermission(id, permissionDTO)));

        }catch (Exception e){
            return ResponseEntity.badRequest().body(new Response<>(ERROR_CODE, e.getMessage()));
        }
    }

    @DeleteMapping("/delete-permission")
    public ResponseEntity<Response<Permission>> deletePermission(@RequestParam Long id){
        try {
            permissionService.deletePermission(id);
            return ResponseEntity.ok().body(new Response<>(SUCCESS_CODE, "delete permission successfully"));

        }catch (Exception e){
            return ResponseEntity.badRequest().body(new Response<>(ERROR_CODE, e.getMessage()));
        }
    }
}

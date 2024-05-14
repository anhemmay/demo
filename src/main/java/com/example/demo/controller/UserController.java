package com.example.demo.controller;

import com.example.demo.common.constant.ResponseConstant;
import com.example.demo.dto.request.LoginDTO;
import com.example.demo.dto.request.RegisterDTO;
import com.example.demo.dto.request.UserDTO;
import com.example.demo.dto.response.Response;
import com.example.demo.model.User;
import com.example.demo.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.example.demo.common.constant.ResponseConstant.ERROR_CODE;
import static com.example.demo.common.constant.ResponseConstant.SUCCESS_CODE;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {
    private final IUserService userService;
    @PostMapping("/login")
    public ResponseEntity<Response<?>> login(@RequestBody LoginDTO loginDTO){
        try{
            String token = userService.login(loginDTO);
            return ResponseEntity.ok().body(
                    new Response<>(SUCCESS_CODE, "", token)
            );
        }catch (Exception e){
            return ResponseEntity.badRequest().body(
                    new Response<>(ERROR_CODE, e.getMessage())
            );
        }
    }
    @PostMapping("/register")
    public ResponseEntity<Response<User>> register(@RequestBody RegisterDTO registerDTO){
        try{
            User user = userService.register(registerDTO);
            return ResponseEntity.ok().body(
                    new Response<>(SUCCESS_CODE, "register successfully", user)
            );
        }catch (Exception e){
            return ResponseEntity.badRequest().body(
                    new Response<>(ERROR_CODE, e.getMessage())
            );
        }
    }
    @PutMapping("/update-user")
    public ResponseEntity<Response<User>> updateUser(@RequestParam Long userId, @RequestBody UserDTO userDTO){
        try{
            User user = userService.updateUser(userDTO, userId);
            return ResponseEntity.ok().body(
                    new Response<>(SUCCESS_CODE, "success",user)
            );
        }catch (Exception e){
            return ResponseEntity.badRequest().body(
              new Response<>(ERROR_CODE, e.getMessage())
            );
        }
    }
}

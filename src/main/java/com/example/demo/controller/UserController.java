package com.example.demo.controller;

import com.example.demo.dto.request.LoginDTO;
import com.example.demo.dto.request.RegisterDTO;
import com.example.demo.dto.request.UserDTO;
import com.example.demo.dto.response.Response;
import com.example.demo.model.User;
import com.example.demo.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    private final IUserService userService;
    @PostMapping("/login")
    public ResponseEntity<Response<?>> login(@RequestBody LoginDTO loginDTO){
        try{
            String token = userService.login(loginDTO);
            return ResponseEntity.ok().body(
                    new Response<>("200", "", token)
            );
        }catch (Exception e){
            return ResponseEntity.badRequest().body(
                    new Response<>("400", e.getMessage())
            );
        }
    }
    @PostMapping("/register")
    public ResponseEntity<Response<User>> register(@RequestBody RegisterDTO registerDTO){
        try{
            User user = userService.register(registerDTO);
            return ResponseEntity.ok().body(
                    new Response<>("200", "register successfully", user)
            );
        }catch (Exception e){
            return ResponseEntity.badRequest().body(
                    new Response<>("400", e.getMessage())
            );
        }
    }
    @PutMapping
    public ResponseEntity<Response<User>> updateUser(@RequestBody UserDTO userDTO, Long userId){
        try{
            User user = userService.updateUser(userDTO, userId);
            return ResponseEntity.ok().body(
                    new Response<>("200", "success",user)
            );
        }catch (Exception e){
            return ResponseEntity.badRequest().body(
              new Response<>("403", e.getMessage())
            );
        }
    }
}

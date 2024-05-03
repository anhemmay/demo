package com.example.demo.controller;

import com.example.demo.dto.request.LoginDTO;
import com.example.demo.dto.request.RegisterDTO;
import com.example.demo.dto.response.Response;
import com.example.demo.model.User;
import com.example.demo.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<Response<?>> register(@RequestBody RegisterDTO registerDTO){
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
}

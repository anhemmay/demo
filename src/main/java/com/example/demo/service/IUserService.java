package com.example.demo.service;

import com.example.demo.dto.request.LoginDTO;
import com.example.demo.dto.request.RegisterDTO;
import com.example.demo.dto.request.UserDTO;
import com.example.demo.model.User;
import jakarta.servlet.http.HttpServletRequest;

public interface IUserService {
    String login(LoginDTO loginDTO, HttpServletRequest request) throws Exception;

    User register(RegisterDTO registerDTO) throws Exception;

    User updateUser(UserDTO userDTO, Long userId) throws Exception;
}

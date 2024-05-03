package com.example.demo.service;

import com.example.demo.dto.request.LoginDTO;
import com.example.demo.dto.request.RegisterDTO;
import com.example.demo.model.User;

public interface IUserService {
    String login(LoginDTO loginDTO) throws Exception;

    User register(RegisterDTO registerDTO) throws Exception;
}

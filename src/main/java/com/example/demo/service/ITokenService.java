package com.example.demo.service;

import com.example.demo.model.Token;
import com.example.demo.model.User;
import jakarta.servlet.http.HttpServletRequest;

public interface ITokenService {
    Token addToken(User user, String token, HttpServletRequest request);

    Boolean isTokenExists(String jwtToken);
}

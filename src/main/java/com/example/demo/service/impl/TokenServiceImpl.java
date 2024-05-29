package com.example.demo.service.impl;

import com.example.demo.model.Token;
import com.example.demo.model.User;
import com.example.demo.repository.TokenRepository;
import com.example.demo.service.ITokenService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements ITokenService {
    private final TokenRepository tokenRepository;

    @Transactional
    @Override
    public Token addToken(User user, String jwtToken, HttpServletRequest request) {
        Token newToken = Token.builder()
                .token(jwtToken)
                .user(user)
                .expired(false)
                .revoked(false)
                .isMobile(false)
                .expirationDate(new Date(System.currentTimeMillis() + 24*60*60*1000L))
                .build();
        String userDevice = request.getHeader("User-Agent");
        if(userDevice.equalsIgnoreCase("Mobile")){
            newToken.setIsMobile(true);
        }
        List<Token> enableTokens = user.getTokens()
                .stream()
                .filter(Token::getRevoked)
                .collect(Collectors.toList());
        if(enableTokens.size() >= 3){
            Token oldestToken = enableTokens
                    .stream()
                    .filter(token -> !token.getIsMobile())
                    .findFirst()
                    .orElse(enableTokens.get(0));
            oldestToken.setRevoked(false);
            tokenRepository.save(oldestToken);
        }
        return tokenRepository.save(newToken);
    }

    @Override
    public Boolean isTokenExists(String jwtToken) {
        Token token =  tokenRepository.findByToken(jwtToken);
        return token != null && !token.getRevoked();
    }
}

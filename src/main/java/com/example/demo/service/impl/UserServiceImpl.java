package com.example.demo.service.impl;

import com.example.demo.common.constant.ResponseMessage;
import com.example.demo.dto.request.LoginDTO;
import com.example.demo.dto.request.RegisterDTO;
import com.example.demo.dto.request.UserDTO;
import com.example.demo.exception.DataNotFoundException;
import com.example.demo.model.Role;
import com.example.demo.model.Token;
import com.example.demo.model.User;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.ITokenService;
import com.example.demo.service.IUserService;
import com.example.demo.util.ConvertUtil;
import com.example.demo.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.common.errors.AuthorizationException;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.security.auth.login.AccountLockedException;
import java.util.Date;
import java.util.Optional;

import static com.example.demo.common.constant.ResponseMessage.*;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {

    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;
    private final ITokenService tokenService;

    @Override
    public String login(LoginDTO loginDTO, HttpServletRequest request) throws Exception {
        User user = userRepository.findByUsername(loginDTO.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException(WRONG_USERNAME_PASSWORD));
        if(!user.isActive()){
            throw new AccountLockedException(USER_IS_LOOKED);
        }
        if(!passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())){
            throw new UsernameNotFoundException(WRONG_USERNAME_PASSWORD);
        }
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword(), user.getAuthorities()));
        String jwtToken = jwtUtil.generateToken(user);
        Token token = tokenService.addToken(user, jwtToken, request);
        return jwtToken;
    }

    @Transactional
    @Override
    public User register(RegisterDTO registerDTO) throws Exception {
        if(!registerDTO.getPassword().equals(registerDTO.getRetypePassword())){
            throw new DataNotFoundException(PASSWORD_NOT_MATCH);
        }
        Optional<User> existingUser = userRepository.findByUsername(registerDTO.getUsername());
        if(existingUser.isPresent()){
            throw new DataNotFoundException(USERNAME_IS_EXISTS);
        }
        Role role = roleRepository.findByName(Role.USER);
        User user = User
                .builder()
                .firstName(registerDTO.getFirstName())
                .lastName(registerDTO.getLastName())
                .username(registerDTO.getUsername())
                .password(passwordEncoder.encode(registerDTO.getPassword()))
                .role(role)
                .isActive(true)
                .build();


        return userRepository.save(user);
    }

    @Transactional
    @Override
    public User updateUser(UserDTO userDTO, Long userId) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User loginUSer = (User) authentication.getPrincipal();
        if(!loginUSer.getId().equals(userId)){
            throw new AuthorizationException(NOT_ALLOWED);
        }
        User existUser = userRepository
                .findById(userId)
                .orElseThrow(() -> new DataNotFoundException("Cannot find user with id: " + userId));
        if(userDTO.getFirstName() != null){
            existUser.setFirstName(userDTO.getFirstName());
        }
        if(userDTO.getLastName() != null){
            existUser.setLastName(userDTO.getLastName());
        }
        return userRepository.save(existUser);
    }

}

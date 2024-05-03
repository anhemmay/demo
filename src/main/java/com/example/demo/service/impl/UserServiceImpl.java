package com.example.demo.service.impl;

import com.example.demo.dto.request.LoginDTO;
import com.example.demo.dto.request.RegisterDTO;
import com.example.demo.exception.DataNotFoundException;
import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.IUserService;
import com.example.demo.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.security.auth.login.AccountLockedException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {

    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Override
    public String login(LoginDTO loginDTO) throws Exception {
        User user = userRepository.findByUsername(loginDTO.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("Wrong username or password"));
        if(!user.isActive()){
            throw new AccountLockedException("account has been looked");
        }
        if(!passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())){
            throw new UsernameNotFoundException("Wrong username or password");
        }
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword(), user.getAuthorities()));
        return jwtUtil.generateToken(user);
    }

    @Transactional
    @Override
    public User register(RegisterDTO registerDTO) throws Exception {
        if(!registerDTO.getPassword().equals(registerDTO.getRetypePassword())){
            throw new DataNotFoundException("Password and retype password are not matches");
        }
        Optional<User> existingUser = userRepository.findByUsername(registerDTO.getUsername());
        if(existingUser.isPresent()){
            throw new DataNotFoundException("username is existing");
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

}

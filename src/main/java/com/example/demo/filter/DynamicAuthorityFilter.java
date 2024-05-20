package com.example.demo.filter;

import com.example.demo.model.Role;
import com.example.demo.model.RolePermission;
import com.example.demo.model.User;

import com.example.demo.service.IRolePermissionService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
@Order(value = 3)
@RequiredArgsConstructor
public class DynamicAuthorityFilter extends OncePerRequestFilter {
    private final IRolePermissionService rolePermissionService;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null){
            filterChain.doFilter(request, response);
            return;
        }
        User user = (User) authentication.getPrincipal();
        if(user.getRole().getName().equals(Role.ADMIN) && user.getRole().getStatus()){
            filterChain.doFilter(request, response);
            return;
        }
        String requestPath = request.getRequestURI();
        List<RolePermission> rolePermissionList = rolePermissionService.findAllByRoleId(user.getRole().getId());
        for(RolePermission rolePermission : rolePermissionList){
            if(requestPath.equals(rolePermission.getPermission().getUrl())){
                filterChain.doFilter(request, response);
                return;
            }
        }
        response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "Method Not Allow");
    }
}

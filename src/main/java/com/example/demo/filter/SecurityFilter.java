package com.example.demo.filter;

import com.example.demo.model.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
@EnableMethodSecurity

@Configuration
public class SecurityFilter {
    private final SimpleCorsFilter simpleCorsFilter;
    private final JwtTokenFilter jwtTokenFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(simpleCorsFilter, JwtTokenFilter.class)
                .authorizeHttpRequests(request -> {
                    request.requestMatchers(
                            "/api/user/login",
                                    "/api/user/register",
                                    "/api-docs/**",
                                    "/swagger-ui/**"
                            ).permitAll()

                            .requestMatchers(HttpMethod.GET, "/api/products/filter").permitAll()
                            .requestMatchers(HttpMethod.GET, "/api/products/**").permitAll()
                            .requestMatchers(HttpMethod.POST, "/api/products").hasRole(Role.ADMIN)
                            .requestMatchers(HttpMethod.PUT, "/api/products/**").hasRole(Role.ADMIN)
                            .requestMatchers(HttpMethod.DELETE, "/api/products/**").hasRole(Role.ADMIN)
                            .anyRequest().authenticated();
                })
                .build();
    }
}

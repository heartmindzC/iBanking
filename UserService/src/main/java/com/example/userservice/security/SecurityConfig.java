package com.example.userservice.security;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Tắt CSRF cho Postman test
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/**").permitAll() // Cho phép tất cả request
                        .anyRequest().authenticated()
                )
                .formLogin(login -> login.disable()) // Không cần form login
                .httpBasic(basic -> basic.disable()); // Không cần basic auth

        return http.build();
    }
}

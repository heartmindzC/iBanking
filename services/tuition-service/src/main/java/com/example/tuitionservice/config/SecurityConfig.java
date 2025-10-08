package com.example.tuitionservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Tắt CSRF protection cho API
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/iBanking/tuitions/**").permitAll() // Cho phép truy cập tất cả endpoints tuitions với context path
                        .requestMatchers("/tuitions/**").permitAll() // Cho phép truy cập tất cả endpoints tuitions (fallback)
                        .anyRequest().permitAll() // Cho phép tất cả các request khác (tạm thời để test)
                );

        return http.build();
    }

//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http
//            .csrf(csrf -> csrf.disable()) // Tắt CSRF protection cho API
//            .authorizeHttpRequests(authz -> authz
//                .requestMatchers("/tuitions/**").permitAll() // Cho phép truy cập tất cả endpoints tuitions
//                .anyRequest().authenticated() // Các request khác vẫn cần xác thực
//            );
//
//        return http.build();
//    }
}

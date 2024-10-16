package com.example.shop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests()
                .requestMatchers("/", "/index.html", "/women-collection.html", "/men-collection.html", "/teens-collection.html", "/static/**", "/images/**").permitAll() // Доступ ко всем HTML и статическим ресурсам
                .anyRequest().authenticated()
                .and()
            .formLogin()
                .loginPage("/login") // Страница для логина, можно изменить при необходимости
                .permitAll()
                .and()
            .logout()
                .permitAll();

        return http.build();
    }
}

package com.blogging.subtxt.security.config;

import com.blogging.subtxt.security.jwt.JwtUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public JwtUtil jwtUtil() {
        return new JwtUtil();
    }
}

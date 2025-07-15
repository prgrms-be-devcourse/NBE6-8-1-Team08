package com.gridsandcircles.config;

import com.gridsandcircles.domain.auth.JwtAuthenticationFilter;
import com.gridsandcircles.domain.auth.JwtUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer.FrameOptionsConfig;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SpringSecurity {

  @Bean
  SecurityFilterChain filterChain(HttpSecurity http, JwtUtil jwtUtil) throws Exception {
    http
        .csrf(AbstractHttpConfigurer::disable)
        .formLogin(AbstractHttpConfigurer::disable)
        .authorizeHttpRequests(auth -> auth
            .requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/h2-console/**", "/",
                "/admin/signup", "/auth/login").permitAll()
            .anyRequest().authenticated()
        )
        .headers(headers -> headers.frameOptions(FrameOptionsConfig::sameOrigin))
        .addFilterBefore(
            new JwtAuthenticationFilter(jwtUtil),
            UsernamePasswordAuthenticationFilter.class
        );

    return http.build();
  }

  @Bean
  PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}

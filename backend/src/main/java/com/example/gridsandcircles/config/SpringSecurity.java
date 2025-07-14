package com.example.gridsandcircles.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer.FrameOptionsConfig;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SpringSecurity {

  @Bean
  SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
        .csrf(AbstractHttpConfigurer::disable)
        .formLogin(AbstractHttpConfigurer::disable)
        .authorizeHttpRequests(auth -> auth
            .requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/h2-console/**", "/").permitAll()
            .anyRequest().authenticated()
        )
        .headers(headers -> headers.frameOptions(FrameOptionsConfig::sameOrigin));

    return http.build();
  }
}

package com.example.taskapi.config;

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
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api-docs/**", "/swagger-ui/**", "/swagger-ui.html", "/swagger-ui/index.html").permitAll()
                .requestMatchers("/actuator/health/**").permitAll()
                .requestMatchers("/h2-console/**").permitAll()
                .requestMatchers("/api/auth/**").permitAll()
                .anyRequest().authenticated())
            .oauth2Login(oauth2 -> oauth2
                .defaultSuccessUrl("/api/auth/success", true))
            .logout(logout -> logout
                .logoutUrl("/api/auth/logout")
                .logoutSuccessUrl("http://localhost:8180/realms/task-api/protocol/openid-connect/logout?redirect_uri=http://localhost:8080")
                .invalidateHttpSession(true));
        
        return http.build();
    }
}

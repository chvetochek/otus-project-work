package com.example.otus_project_work.config;

import com.example.otus_project_work.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig{

    private UserService userService;

    @Autowired
    public SecurityConfig(UserService userService) {
        this.userService = userService;
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring()
                .requestMatchers("/swagger-ui/**", "/v3/api-docs*/**", "/api/**");
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
                return http.authorizeHttpRequests(
                        request -> request
                                .requestMatchers("/", "/notes/**").hasRole("USER")
                                .anyRequest().authenticated())
                .formLogin((form) -> form
                                .successHandler(new CustomAuthenticationSuccessHandler())
                                .permitAll()
                        )
                        .logout(logout -> logout
                                .logoutUrl("/logout")
                                .logoutSuccessUrl("/"))
                        .build();
    }
}


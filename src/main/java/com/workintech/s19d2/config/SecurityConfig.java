package com.workintech.s19d2.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http) throws Exception {

        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(
                    HttpMethod.POST,
                    "/auth/register",
                    "/auth/register/",
                    "/workintech/auth/register",
                    "/workintech/auth/register/"
                ).permitAll()

                .requestMatchers(
                    HttpMethod.GET,
                    "/account",
                    "/account/",
                    "/account/**",
                    "/workintech/accounts",
                    "/workintech/accounts/",
                    "/workintech/accounts/**"
                ).hasAnyAuthority("USER", "ADMIN")

                .requestMatchers(
                    HttpMethod.POST,
                    "/account",
                    "/account/",
                    "/workintech/accounts",
                    "/workintech/accounts/"
                ).hasAuthority("ADMIN")

                .requestMatchers(
                    HttpMethod.PUT,
                    "/account/**",
                    "/workintech/accounts/**"
                ).hasAuthority("ADMIN")

                .requestMatchers(
                    HttpMethod.DELETE,
                    "/account/**",
                    "/workintech/accounts/**"
                ).hasAuthority("ADMIN")

                .anyRequest().authenticated()
            )
            .httpBasic(Customizer.withDefaults());

        return http.build();
    }
}
package com.example.NexusOS.config;

import com.example.NexusOS.security.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    // Custom filter responsible for validating JWT on every request.
    private final JwtAuthenticationFilter jwtAuthenticationFilter;


    // Constructor Injection
    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http

                // Disable CSRF because JWT is used instead of Session Authentication.
                .csrf(csrf -> csrf.disable())

                // Configure application to use Stateless Authentication.
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                // Configure endpoint authorization rules.
                .authorizeHttpRequests(auth -> auth

                        // Public endpoints (No Authentication Required)
                        .requestMatchers(
                                "/auth/**",
                                "/swagger-ui/**",
                                "/v3/api-docs/**"
                        ).permitAll()

                        // Every other endpoint requires authentication.
                        .anyRequest().authenticated()
                )

                /*
                 * Register the JWT filter before Spring's built-in
                 * UsernamePasswordAuthenticationFilter so that JWT
                 * authentication happens before request processing.
                 */
                .addFilterBefore(
                        jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class
                );

        return http.build();
    }

    // Password encoder used for hashing user passwords.
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Exposes AuthenticationManager as a Spring Bean.
    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration configuration) throws Exception {

        return configuration.getAuthenticationManager();
    }
}
package com.microservice.gateway.config;

import com.microservice.gateway.config.filter.JwtTokenValidator;
import com.microservice.gateway.util.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtUtils jwtUtils;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers(HttpMethod.POST, "/auth/**").permitAll();

                    // Product endpoints
                    auth.requestMatchers(HttpMethod.GET, "/products/**").hasAnyRole("ADMIN", "USER");
                    auth.requestMatchers(HttpMethod.POST, "/products").hasRole("ADMIN");
                    auth.requestMatchers(HttpMethod.PUT, "/products/**").hasRole("ADMIN");
                    auth.requestMatchers(HttpMethod.DELETE, "/products/**").hasRole("ADMIN");

                    // Cart endpoints
                    auth.requestMatchers(HttpMethod.POST, "/carts").hasAnyRole("ADMIN", "USER");
                    auth.requestMatchers(HttpMethod.GET, "/carts").hasAnyRole("ADMIN", "USER");
                    auth.requestMatchers(HttpMethod.GET, "/carts/**").hasAnyRole("ADMIN", "USER");
                    auth.requestMatchers(HttpMethod.PUT, "/carts/**").hasAnyRole("ADMIN", "USER");
                    auth.requestMatchers(HttpMethod.DELETE, "/carts/**").hasAnyRole("ADMIN", "USER");
                    auth.requestMatchers(HttpMethod.POST, "/carts/**/items").hasAnyRole("ADMIN", "USER");
                    auth.requestMatchers(HttpMethod.PUT, "/carts/**/items/**").hasAnyRole("ADMIN", "USER");
                    auth.requestMatchers(HttpMethod.DELETE, "/carts/**/items/**").hasAnyRole("ADMIN", "USER");
                    auth.requestMatchers(HttpMethod.GET, "/carts/users/**").hasRole("ADMIN");

                    // Order endpoints
                    auth.requestMatchers(HttpMethod.POST, "/orders/**").hasAnyRole("ADMIN", "USER");
                    auth.requestMatchers(HttpMethod.GET, "/orders").hasRole("ADMIN");
                    auth.requestMatchers(HttpMethod.GET, "/orders/**").hasAnyRole("ADMIN", "USER");
                    auth.requestMatchers(HttpMethod.PATCH, "/orders/**/complete").hasRole("ADMIN");
                    auth.requestMatchers(HttpMethod.PATCH, "/orders/**/cancel").hasRole("ADMIN");

                    auth.anyRequest().authenticated();
                })
                .addFilterBefore(new JwtTokenValidator(jwtUtils), UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}

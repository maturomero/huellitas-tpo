package com.uade.tpo.demo.controllers.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.uade.tpo.demo.enums.Role;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(req -> req
                .requestMatchers("/auth/**").permitAll()
                .requestMatchers("/error/**").permitAll()

                //PRODUCT
                .requestMatchers(HttpMethod.GET,"/products/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/products").hasAnyAuthority(Role.ADMIN.name())
                .requestMatchers(HttpMethod.DELETE, "/products/**").hasAnyAuthority(Role.ADMIN.name())
                .requestMatchers(HttpMethod.PATCH, "/products/**").hasAnyAuthority(Role.ADMIN.name())

                //CATEGORY
                .requestMatchers(HttpMethod.GET,"/categories/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/categories").hasAnyAuthority(Role.ADMIN.name())
                .requestMatchers(HttpMethod.DELETE, "/categories/**").hasAnyAuthority(Role.ADMIN.name())
                .requestMatchers(HttpMethod.PATCH, "/categories/**").hasAnyAuthority(Role.ADMIN.name())

                //ANIMAL
                .requestMatchers(HttpMethod.GET,"/animals/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/animals").hasAnyAuthority(Role.ADMIN.name())
                .requestMatchers(HttpMethod.DELETE, "/animals/**").hasAnyAuthority(Role.ADMIN.name())
                .requestMatchers(HttpMethod.PATCH, "/animals/**").hasAnyAuthority(Role.ADMIN.name())

                //PRODUCT IMAGE
                .requestMatchers(HttpMethod.GET,"/products/images/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/products/images").hasAnyAuthority(Role.ADMIN.name())
                .requestMatchers(HttpMethod.DELETE, "/products/images/**").hasAnyAuthority(Role.ADMIN.name())

                //ORDER
                .requestMatchers(HttpMethod.GET,"/orders/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/orders").permitAll()
                .requestMatchers(HttpMethod.DELETE, "/orders/**").permitAll()

                //ORDER PRODUCT
                .requestMatchers(HttpMethod.GET,"/order_products/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/order_products").permitAll()

                //AUTHENTICATION

                .anyRequest().authenticated()
            )
            .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
            .authenticationProvider(authenticationProvider)
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}

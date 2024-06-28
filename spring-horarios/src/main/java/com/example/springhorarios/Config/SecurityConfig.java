package com.example.springhorarios.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.springhorarios.Jwt.JwtAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf
                        .disable())
                .authorizeHttpRequests(authRequest -> authRequest
                // temporal
                // PERMITIMO ACCESO A TODOS LOS USUARIOS A LOS METODOS GET y OPTIONS
                .requestMatchers(HttpMethod.GET).permitAll()
                .requestMatchers(HttpMethod.OPTIONS).permitAll()
                
                //Permitir acceso para ausencias
                .requestMatchers("/ausencia/crear",  "/ausencia/delete/**").permitAll()
                
                // PERMITIMO ACCESO A TODOS LOS USUARIOS A LOS METODOS DE REGISTRO Y LOGIN
                .requestMatchers("/auth/forgot-password", "/auth/reset-password", "/auth/login", "/auth/register").permitAll()
                
                //permisos para modificar el propio profesor
                .requestMatchers("/profesor/update/**").permitAll()
                
                // ! Permitir acceso a todas las rutas para el rol "ADMIN"
                .requestMatchers("/**").hasAuthority("ADMIN")

                .anyRequest().authenticated()

                )
                .sessionManagement(sessionManagement -> sessionManagement
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}

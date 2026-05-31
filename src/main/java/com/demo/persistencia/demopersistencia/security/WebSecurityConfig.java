package com.demo.persistencia.demopersistencia.security;

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
public class WebSecurityConfig {

        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

                http
                                .csrf(csrf -> csrf.disable())
                                .cors(cors -> cors.configure(http))
                                .sessionManagement(session -> session.sessionCreationPolicy(
                                                SessionCreationPolicy.STATELESS))
                                .authorizeHttpRequests(auth -> auth

                                                // =========================
                                                // AUTH
                                                // =========================
                                                .requestMatchers(
                                                                "/auth/login",
                                                                "/auth/register", "/error")
                                                .permitAll()

                                                // =========================
                                                // USERS
                                                // =========================

                                                .requestMatchers(
                                                                HttpMethod.POST,
                                                                "/api/users/registrarUsuario")
                                                .permitAll()

                                                .requestMatchers(
                                                                HttpMethod.GET,
                                                                "/api/users/**")
                                                .hasRole("ADMIN")

                                                // proteger demás POST
                                                .requestMatchers(
                                                                HttpMethod.POST,
                                                                "/api/users/**")
                                                .hasRole("ADMIN")

                                                .requestMatchers(
                                                                HttpMethod.PUT,
                                                                "/api/users/**")
                                                .hasRole("ADMIN")

                                                .requestMatchers(
                                                                HttpMethod.DELETE,
                                                                "/api/users/**")
                                                .hasRole("ADMIN")

                                                // =========================
                                                // PACIENTES
                                                // =========================
                                                .requestMatchers(
                                                                HttpMethod.GET,
                                                                "/api/pacientes/**")
                                                .hasAnyRole("ADMIN", "DOCTOR", "PACIENTE")

                                                .requestMatchers(
                                                                HttpMethod.POST,
                                                                "/api/pacientes/**")
                                                .hasAnyRole("ADMIN")

                                                .requestMatchers(
                                                                HttpMethod.PUT,
                                                                "/api/pacientes/**")
                                                .hasAnyRole("ADMIN", "PACIENTE")

                                                .requestMatchers(
                                                                HttpMethod.DELETE,
                                                                "/api/pacientes/**")
                                                .hasAnyRole("ADMIN", "PACIENTE")

                                                // =========================
                                                // DOCTORES
                                                // =========================
                                                .requestMatchers(
                                                                HttpMethod.GET,
                                                                "/api/doctores/**")
                                                .hasAnyRole("ADMIN", "DOCTOR", "PACIENTE")

                                                .requestMatchers(
                                                                HttpMethod.POST,
                                                                "/api/doctores/**")
                                                .hasAnyRole("ADMIN", "DOCTOR", "PACIENTE")

                                                .requestMatchers(
                                                                HttpMethod.PUT,
                                                                "/api/doctores/**")
                                                .hasAnyRole("ADMIN", "DOCTOR", "PACIENTE")

                                                .requestMatchers(
                                                                HttpMethod.DELETE,
                                                                "/api/doctores/**")
                                                .hasAnyRole("ADMIN", "DOCTOR", "PACIENTE")

                                                // =========================
                                                // CITAS
                                                // =========================
                                                .requestMatchers(
                                                                HttpMethod.GET,
                                                                "/api/citas/**")
                                                .hasAnyRole("ADMIN", "DOCTOR", "PACIENTE")

                                                .requestMatchers(
                                                                HttpMethod.POST,
                                                                "/api/citas/**")
                                                .hasAnyRole("ADMIN", "DOCTOR", "PACIENTE")

                                                .requestMatchers(
                                                                HttpMethod.PUT,
                                                                "/api/citas/**")
                                                .hasAnyRole("ADMIN", "DOCTOR", "PACIENTE")

                                                .requestMatchers(
                                                                HttpMethod.DELETE,
                                                                "/api/citas/**")
                                                .hasRole("ADMIN")

                                                // =========================
                                                // HISTORIAL MÉDICO
                                                // =========================

                                                .requestMatchers(
                                                                HttpMethod.GET,
                                                                "/api/historial/**")
                                                .hasAnyRole("ADMIN", "DOCTOR", "PACIENTE")

                                                .requestMatchers(
                                                                HttpMethod.POST,
                                                                "/api/historial/**")
                                                .hasAnyRole("ADMIN", "DOCTOR", "PACIENTE")

                                                .requestMatchers(
                                                                HttpMethod.PUT,
                                                                "/api/historial/**")
                                                .hasAnyRole("ADMIN", "DOCTOR", "PACIENTE")

                                                .requestMatchers(
                                                                HttpMethod.DELETE,
                                                                "/api/historial/**")
                                                .hasRole("ADMIN")

                                                // =========================
                                                // REPORTES PDF
                                                // =========================
                                                .requestMatchers(
                                                                HttpMethod.GET,
                                                                "/api/reportes/**")
                                                .hasAnyRole("ADMIN", "DOCTOR", "PACIENTE")

                                                // TODO LO DEMÁS
                                                .anyRequest().authenticated())

                                .addFilterBefore(
                                                new JWTAuthorizationFilter(),
                                                UsernamePasswordAuthenticationFilter.class);

                return http.build();
        }
}
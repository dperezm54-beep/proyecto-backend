package com.demo.persistencia.demopersistencia.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

public class JWTAuthorizationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(

            HttpServletRequest request,

            HttpServletResponse response,

            FilterChain chain

    ) throws ServletException, IOException {

        String path = request.getServletPath();

        System.out.println("RUTA RECIBIDA: " + path);

        // =========================
        // RUTAS PUBLICAS
        // =========================
        if (

                        path.equals("/auth/login")

                        ||

                        path.equals("/auth/register")

                        ||

                        path.equals("/api/users/registrarUsuario")

                        ||
                        path.equals("/api/users/registrarUsuario")



        ) {

            System.out.println("RUTA PUBLICA");

            chain.doFilter(request, response);

            return;

        }

        try {

            // =========================
            // VALIDAR TOKEN
            // =========================
            if (existeJWTToken(request)) {

                Claims claims =
                        JWTUtil.validarToken(request);

                if (claims.getSubject() != null) {

                    String role =
                            claims.get(
                                    "role",
                                    String.class
                            );

                    setUpSpringAuthentication(

                            claims.getSubject(),

                            role

                    );

                } else {

                    SecurityContextHolder.clearContext();

                }

            } else {

                SecurityContextHolder.clearContext();

            }

            chain.doFilter(request, response);

        } catch (

                ExpiredJwtException
                |

                UnsupportedJwtException
                |

                MalformedJwtException e

        ) {

            response.setStatus(
                    HttpServletResponse.SC_FORBIDDEN
            );

            response.sendError(

                    HttpServletResponse.SC_FORBIDDEN,

                    e.getMessage()

            );

        }

    }

    // AUTH
    private void setUpSpringAuthentication(

            String username,

            String role

    ) {

        UsernamePasswordAuthenticationToken auth =

                new UsernamePasswordAuthenticationToken(

                        username,

                        null,

                        List.of(

                                new SimpleGrantedAuthority(

                                        "ROLE_" + role

                                )

                        )

                );

        SecurityContextHolder
                .getContext()
                .setAuthentication(auth);

    }

    // VALIDAR HEADER
    private boolean existeJWTToken(

            HttpServletRequest request

    ) {

        String authenticationHeader =

                request.getHeader("Authorization");

        return authenticationHeader != null

                &&

                authenticationHeader.startsWith("Bearer ");

    }

}
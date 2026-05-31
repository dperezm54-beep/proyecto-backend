package com.demo.persistencia.demopersistencia.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import jakarta.servlet.http.HttpServletRequest;

import java.nio.charset.StandardCharsets;

import java.util.Date;

public class JWTUtil {

        // CLAVE SECRETA
        private static final String SECRET =

                        "S3curity1025#$EstaClaveDebeTenerUnTamañoAdecuadoParaHS256";

        // 24 HORAS
        private static final long EXPIRATION_TIME = 86400000;

        // PREFIX
        private static final String PREFIX = "Bearer ";

        // HEADER
        private static final String HEADER = "Authorization";

        // GENERAR TOKEN
        public static String generarToken(

                        String username,
                        String role

        ) {

                return Jwts.builder()

                                .setSubject(username)

                                // GUARDAR ROLE
                                .claim("role", role)

                                .setIssuedAt(new Date())

                                .setExpiration(

                                                new Date(

                                                                System.currentTimeMillis()
                                                                                + EXPIRATION_TIME

                                                )

                                )

                                .signWith(

                                                SignatureAlgorithm.HS256,

                                                SECRET.getBytes(StandardCharsets.UTF_8)

                                )

                                .compact();

        }

        // VALIDAR TOKEN
        public static Claims validarToken(
                        HttpServletRequest request) {

                String token = request.getHeader(HEADER);

                if (

                token != null

                                &&

                                token.startsWith(PREFIX)

                ) {

                        token = token.replace(PREFIX, "");

                        return Jwts.parser()

                                        .setSigningKey(

                                                        SECRET.getBytes(
                                                                        StandardCharsets.UTF_8)

                                        )

                                        .parseClaimsJws(token)

                                        .getBody();

                }

                return null;

        }

}
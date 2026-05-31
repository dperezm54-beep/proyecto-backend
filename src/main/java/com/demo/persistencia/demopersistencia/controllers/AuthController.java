package com.demo.persistencia.demopersistencia.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.demo.persistencia.demopersistencia.entidades.User;
import com.demo.persistencia.demopersistencia.entidades.Paciente;
import com.demo.persistencia.demopersistencia.repositorio.UserRepositoy;
import com.demo.persistencia.demopersistencia.repositorio.PacienteRepositorio;
import com.demo.persistencia.demopersistencia.security.JWTUtil;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private UserRepositoy userRepository;

    @Autowired
    private PacienteRepositorio pacienteRepository;

    @PostMapping("/register")
    public ResponseEntity<?> register(
            @RequestBody User user
    ) {

        User existingUser =
                userRepository.findByEmail(
                        user.getEmail()
                );

        if (existingUser != null) {

            return ResponseEntity
                    .badRequest()
                    .body(
                            Map.of(
                                    "error",
                                    "El usuario ya existe"
                            )
                    );

        }

        user.setRole("PACIENTE");

        User nuevoUsuario =
                userRepository.save(user);

        Paciente paciente =
                new Paciente();

        paciente.setIdUsuario(
                nuevoUsuario.getId()
        );

        paciente.setNombre(
                nuevoUsuario.getNombre()
        );

        paciente.setEmail(
                nuevoUsuario.getEmail()
        );

        paciente.setFechaNacimiento(null);

        paciente.setDireccion("");

        paciente.setTelefono("");

        paciente.setSeguroMedico("");

        pacienteRepository.save(paciente);

        return ResponseEntity.ok(

                Map.of(
                        "message",
                        "Usuario registrado"
                )

        );

    }

    @PostMapping("/login")
    public ResponseEntity<?> login(
            @RequestBody User user
    ) {

        User existingUser =
                userRepository.findByEmail(
                        user.getEmail()
                );

        if (
                existingUser == null
                        ||
                !existingUser
                        .getPassword()
                        .equals(
                                user.getPassword()
                        )
        ) {

            return ResponseEntity
                    .status(401)
                    .body(

                            Map.of(
                                    "error",
                                    "Credenciales incorrectas"
                            )

                    );

        }

        String token =
                JWTUtil.generarToken(

                        existingUser.getEmail(),

                        existingUser.getRole()

                );

        return ResponseEntity.ok(

                Map.of(

                        "token",
                        token,

                        "role",
                        existingUser.getRole(),

                        "email",
                        existingUser.getEmail(),

                        "nombre",
                        existingUser.getNombre(),

                        "id",
                        existingUser.getId()

                )

        );

    }

}
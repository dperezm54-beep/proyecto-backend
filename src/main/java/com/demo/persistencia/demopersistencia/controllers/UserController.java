package com.demo.persistencia.demopersistencia.controllers;

import java.util.List;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import com.demo.persistencia.demopersistencia.entidades.User;
import com.demo.persistencia.demopersistencia.entidades.Paciente;
import com.demo.persistencia.demopersistencia.entidades.Doctor;

import com.demo.persistencia.demopersistencia.repositorio.UserRepositoy;
import com.demo.persistencia.demopersistencia.repositorio.PacienteRepositorio;
import com.demo.persistencia.demopersistencia.repositorio.DoctorRepositorio;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserRepositoy userRepository;

    @Autowired
    private PacienteRepositorio pacienteRepository;

    @Autowired
    private DoctorRepositorio doctorRepository;

    @GetMapping("/listarUsuarios")
    public List<User> listarUsuarios() {
        return userRepository.findAll();
    }

    @PostMapping("/registrarUsuario")
    @Transactional
    public User registrarUsuario(
            @RequestBody User user
    ) {

        User nuevoUsuario =
                userRepository.save(user);

        if (
                "PACIENTE"
                        .equalsIgnoreCase(
                                nuevoUsuario.getRole()
                        )
        ) {

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

            paciente.setDireccion("");
            paciente.setTelefono("");
            paciente.setSeguroMedico("");
            paciente.setFechaNacimiento(null);

            pacienteRepository.save(paciente);
        }

        if (
                "DOCTOR"
                        .equalsIgnoreCase(
                                nuevoUsuario.getRole()
                        )
        ) {

            Doctor doctor =
                    new Doctor();

            doctor.setIdUsuario(
                    nuevoUsuario.getId()
            );

            doctor.setNombreDoctor(
                    nuevoUsuario.getNombre()
            );

            doctor.setColegiado("");
            doctor.setEspecialidad("");
            doctor.setDireccion("");
            doctor.setCentroHospitalario("");
            doctor.setEdad("");
            doctor.setObservacion("");
            doctor.setFechaRegistro(LocalDate.now());

            doctorRepository.save(doctor);
        }

        return nuevoUsuario;
    }

    @PutMapping("/editarUsuario/{id}")
    @Transactional
    public User editarUsuario(
            @PathVariable Long id,
            @RequestBody User usuarioActualizado
    ) {

        User usuario =
                userRepository
                        .findById(id)
                        .orElseThrow();

        String emailAnterior =
                usuario.getEmail();

        usuario.setNombre(
                usuarioActualizado.getNombre()
        );

        usuario.setEmail(
                usuarioActualizado.getEmail()
        );

        usuario.setPassword(
                usuarioActualizado.getPassword()
        );

        usuario.setRole(
                usuarioActualizado.getRole()
        );

        User usuarioGuardado =
                userRepository.save(usuario);

        Paciente paciente =
                pacienteRepository
                        .findByEmail(emailAnterior);

        if (paciente != null) {

            paciente.setNombre(
                    usuarioGuardado.getNombre()
            );

            paciente.setEmail(
                    usuarioGuardado.getEmail()
            );

            paciente.setIdUsuario(
                    usuarioGuardado.getId()
            );

            pacienteRepository.save(paciente);
        }

        Doctor doctor =
                doctorRepository
                        .findByIdUsuario(
                                usuarioGuardado.getId()
                        );

        if (doctor != null) {

            doctor.setNombreDoctor(
                    usuarioGuardado.getNombre()
            );

            doctor.setIdUsuario(
                    usuarioGuardado.getId()
            );

            doctorRepository.save(doctor);
        }

        return usuarioGuardado;
    }

    @DeleteMapping("/eliminarUsuario/{id}")
    @Transactional
    public void eliminarUsuario(
            @PathVariable Long id
    ) {

        User usuario =
                userRepository
                        .findById(id)
                        .orElseThrow();

        Paciente paciente =
                pacienteRepository
                        .findByEmail(
                                usuario.getEmail()
                        );

        if (paciente != null) {
            pacienteRepository.delete(paciente);
        }

        Doctor doctor =
                doctorRepository
                        .findByIdUsuario(
                                usuario.getId()
                        );

        if (doctor != null) {
            doctorRepository.delete(doctor);
        }

        userRepository.deleteById(id);
    }
}
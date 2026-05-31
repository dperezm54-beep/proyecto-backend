package com.demo.persistencia.demopersistencia.controllers;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.demo.persistencia.demopersistencia.entidades.Doctor;
import com.demo.persistencia.demopersistencia.entidades.HistorialMedico;
import com.demo.persistencia.demopersistencia.entidades.Paciente;
import com.demo.persistencia.demopersistencia.entidades.User;

import com.demo.persistencia.demopersistencia.repositorio.UserRepositoy;

import com.demo.persistencia.demopersistencia.security.JWTUtil;

import com.demo.persistencia.demopersistencia.services.DoctorServicio;
import com.demo.persistencia.demopersistencia.services.HistorialMedicoServicio;
import com.demo.persistencia.demopersistencia.services.PacienteServicio;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/historial")
@CrossOrigin(origins = "*")
public class HistorialMedicoController {

    @Autowired
    private HistorialMedicoServicio historialServicio;

    @Autowired
    private PacienteServicio pacienteServicio;

    @Autowired
    private DoctorServicio doctorServicio;

    @Autowired
    private UserRepositoy userRepository;

    @GetMapping("/listarHistorial")
    public List<HistorialMedico> listarHistorial(
            HttpServletRequest request
    ) {

        Claims claims = JWTUtil.validarToken(request);

        String email = claims.getSubject();
        String role = claims.get("role", String.class);

        if ("ADMIN".equalsIgnoreCase(role)) {
            return historialServicio.listarHistoriales();
        }

        if ("PACIENTE".equalsIgnoreCase(role)) {

            User user = userRepository.findByEmail(email);

            if (user == null) {
                return List.of();
            }

            Paciente paciente =
                    pacienteServicio.buscarPorIdUsuario(user.getId());

            if (paciente == null) {
                return List.of();
            }

            return historialServicio.buscarPorPaciente(
                    paciente.getIdPaciente()
            );
        }

        if ("DOCTOR".equalsIgnoreCase(role)) {

            User user = userRepository.findByEmail(email);

            if (user == null) {
                return List.of();
            }

            Doctor doctor =
                    doctorServicio.buscarPorIdUsuario(user.getId());

            if (doctor == null) {
                return List.of();
            }

            return historialServicio.buscarPorDoctor(
                    doctor.getIdDoctor()
            );
        }

        return List.of();
    }

    @PostMapping("/registrarHistorial")
    public HistorialMedico registrarHistorial(
            @RequestBody HistorialMedico historial
    ) {

        if (historial.getFechaRegistro() == null) {
            historial.setFechaRegistro(LocalDate.now());
        }

        return historialServicio.guardarHistorial(historial);
    }

    @PutMapping("/editarHistorial/{id}")
    public HistorialMedico editarHistorial(
            @PathVariable Long id,
            @RequestBody HistorialMedico historialActualizado
    ) {

        historialActualizado.setIdHistorial(id);

        return historialServicio.guardarHistorial(historialActualizado);
    }

    @DeleteMapping("/eliminarHistorial/{id}")
    public void eliminarHistorial(
            @PathVariable Long id
    ) {

        historialServicio.eliminarHistorial(id);
    }
}
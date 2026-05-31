package com.demo.persistencia.demopersistencia.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.persistencia.demopersistencia.dto.PacienteDto;
import com.demo.persistencia.demopersistencia.entidades.Paciente;
import com.demo.persistencia.demopersistencia.services.PacienteServicio;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;

import io.jsonwebtoken.Claims;
import com.demo.persistencia.demopersistencia.security.JWTUtil;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/pacientes")

public class PacienteController {

    @Autowired
    private PacienteServicio servicioPaciente;

    @CrossOrigin(origins = "*")
    @GetMapping("/listarPacientes")
    public List<Paciente> consultarPacientes() {
        return servicioPaciente.consultarPacientes();
    }

    @PostMapping("/registrarPaciente")
    public Paciente registrarPaciente(@RequestBody PacienteDto pacienteJson) {

        Paciente paciente = new Paciente();

        paciente.setNombre(pacienteJson.getNombre());
        paciente.setFechaNacimiento(pacienteJson.getFechaNacimiento());
        paciente.setDireccion(pacienteJson.getDireccion());
        paciente.setTelefono(pacienteJson.getTelefono());
        paciente.setSeguroMedico(pacienteJson.getSeguroMedico());
        paciente.setEmail(pacienteJson.getEmail());

        System.out.println("valor a persistir " + paciente.toString());

        return servicioPaciente.registPacientes(paciente);

    }

    @PutMapping("/editarPaciente/{id}")
    public Paciente editarPaciente(@PathVariable Long id, @RequestBody PacienteDto pacienteJson) {

        Paciente paciente = servicioPaciente.buscarPorId(id);

        if (paciente != null) {
            paciente.setNombre(pacienteJson.getNombre());
            paciente.setFechaNacimiento(pacienteJson.getFechaNacimiento());
            paciente.setDireccion(pacienteJson.getDireccion());
            paciente.setTelefono(pacienteJson.getTelefono());
            paciente.setSeguroMedico(pacienteJson.getSeguroMedico());

            return servicioPaciente.registPacientes(paciente);
        }

        return null;
    }

    @DeleteMapping("/eliminarPaciente/{id}")
    public void eliminarPaciente(@PathVariable Long id) {

        servicioPaciente.eliminarPaciente(id);
    }

    @GetMapping("/buscarPaciente/{id}")
    public Paciente buscarPaciente(@PathVariable Long id) {

        return servicioPaciente.buscarPorId(id);

    }

    @GetMapping("/totalPacientes")
    public Long totalPacientes() {

        return servicioPaciente.contarPacientes();

    }

    @GetMapping("/miPaciente")
    public Paciente miPaciente(
            HttpServletRequest request) {

        Claims claims = JWTUtil.validarToken(request);

        String email = claims.getSubject();

        return servicioPaciente
                .buscarPorEmail(email);

    }

    @GetMapping("/buscarPorEmail/{email}")
    public Paciente buscarPorEmail(
            @PathVariable String email) {

        return servicioPaciente
                .buscarPorEmail(email);

    }

    @GetMapping("/buscarPorIdUsuario/{idUsuario}")
public Paciente buscarPorIdUsuario(
        @PathVariable Long idUsuario
) {

    return servicioPaciente.buscarPorIdUsuario(idUsuario);

}


}

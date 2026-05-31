package com.demo.persistencia.demopersistencia.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.demo.persistencia.demopersistencia.entidades.Cita;
import com.demo.persistencia.demopersistencia.entidades.Doctor;
import com.demo.persistencia.demopersistencia.entidades.Paciente;
import com.demo.persistencia.demopersistencia.entidades.User;

import com.demo.persistencia.demopersistencia.repositorio.UserRepositoy;

import com.demo.persistencia.demopersistencia.security.JWTUtil;

import com.demo.persistencia.demopersistencia.services.CitaServicio;
import com.demo.persistencia.demopersistencia.services.DoctorServicio;
import com.demo.persistencia.demopersistencia.services.PacienteServicio;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;

import com.demo.persistencia.demopersistencia.services.CorreoServicio;

import com.demo.persistencia.demopersistencia.repositorio.PacienteRepositorio;
import com.demo.persistencia.demopersistencia.repositorio.DoctorRepositorio;
import com.demo.persistencia.demopersistencia.repositorio.PacienteRepositorio;

@RestController
@RequestMapping("/api/citas")
@CrossOrigin(origins = "*")
public class CitaController {

    @Autowired
    private PacienteServicio pacienteServicio;

    @Autowired
    private DoctorServicio doctorServicio;

    @Autowired
    private CitaServicio citaServicio;

    @Autowired
    private UserRepositoy userRepository;

    @Autowired
private CorreoServicio correoServicio;

@Autowired
private PacienteRepositorio pacienteRepositorio;

@Autowired
private DoctorRepositorio doctorRepositorio;


    // LISTAR CITAS SEGUN ROL
 @GetMapping("/listarCitas")
public List<Cita> listarCitas(
        HttpServletRequest request
) {

    Claims claims = JWTUtil.validarToken(request);

    String email = claims.getSubject();

    String role = claims.get("role", String.class);


    if ("ADMIN".equalsIgnoreCase(role)) {
        return citaServicio.listarCitas();
    }

    if ("PACIENTE".equalsIgnoreCase(role)) {

        User user = userRepository.findByEmail(email);

        if (user == null) {
            return List.of();
        }

     Paciente paciente =
        pacienteServicio.buscarPorIdUsuario(
                user.getId()
        );

System.out.println("EMAIL TOKEN = " + email);
System.out.println("USER ID = " + user.getId());

if (paciente == null) {
    System.out.println("PACIENTE NULL");
    return List.of();
}

System.out.println("PACIENTE ID = " + paciente.getIdPaciente());

try {

    return citaServicio.buscarPorPaciente(
            paciente.getIdPaciente()
    );

} catch (Exception e) {

    e.printStackTrace();

    return List.of();
}

    }

    if ("DOCTOR".equalsIgnoreCase(role)) {

        User user = userRepository.findByEmail(email);

        if (user == null) {
            return List.of();
        }

        Doctor doctor =
                doctorServicio.buscarPorIdUsuario(
                        user.getId()
                );

        if (doctor == null) {
            return List.of();
        }

        return citaServicio.buscarPorDoctor(
                doctor.getIdDoctor()
        );
    }

    return List.of();
}


    // REGISTRAR CITA
@PostMapping("/registrarCita")
public Cita registrarCita(@RequestBody Cita cita) {

    if (cita.getEstado() == null || cita.getEstado().isEmpty()) {
        cita.setEstado("PENDIENTE");
    }

    Cita citaGuardada = citaServicio.guardarCita(cita);

    enviarCorreoPacienteYDoctor(
            citaGuardada,
            "Cita Registrada",
            "Su cita ha sido registrada.",
            "Se le ha asignado una nueva cita."
    );

    return citaGuardada;
}
    // REPROGRAMAR CITA
  @PutMapping("/reprogramarCita/{id}")
public Cita reprogramarCita(
        @PathVariable Long id,
        @RequestBody Cita citaNueva
) {

    Cita citaExistente =
            citaServicio.buscarPorId(id);

    if (citaExistente != null) {

        citaExistente.setFechaCita(
                citaNueva.getFechaCita()
        );

        citaExistente.setHoraCita(
                citaNueva.getHoraCita()
        );

        citaExistente.setEstado(
                "REPROGRAMADA"
        );

        if (citaExistente.getPaciente() != null &&
            citaExistente.getPaciente().getEmail() != null) {

            correoServicio.enviarCorreo(
                    citaExistente.getPaciente().getEmail(),
                    "Cita Reprogramada",
                    "Su cita fue reprogramada.\n\n" +
                    "Nueva fecha: " +
                    citaExistente.getFechaCita() +
                    "\nNueva hora: " +
                    citaExistente.getHoraCita()
            );
        }

        return citaServicio.guardarCita(
                citaExistente
        );
    }

enviarCorreoPacienteYDoctor(
        citaExistente,
        "Cita Reprogramada",
        "Su cita fue reprogramada.",
        "Una cita asignada a usted fue reprogramada."
);

    return null;
}

    // CANCELAR CITA
    @PutMapping("/cancelarCita/{id}")
    public Cita cancelarCita(
            @PathVariable Long id
    ) {

        Cita cita =
                citaServicio.buscarPorId(id);

        if (cita != null) {

            cita.setEstado("CANCELADA");

            enviarCorreoPacienteYDoctor(
        cita,
        "Cita Cancelada",
        "Su cita ha sido cancelada.",
        "Una cita asignada a usted fue cancelada."
);

            if (cita.getPaciente() != null &&
    cita.getPaciente().getEmail() != null) {

    correoServicio.enviarCorreo(
            cita.getPaciente().getEmail(),
            "Cita Cancelada",
            "Su cita ha sido cancelada."
    );
}

            return citaServicio.guardarCita(cita);
        }

        return null;
    }

    // ELIMINAR CITA
    @DeleteMapping("/eliminarCita/{id}")
    public void eliminarCita(
            @PathVariable Long id
    ) {

        citaServicio.eliminarCita(id);
    }

    // TOTAL CITAS
    @GetMapping("/totalCitas")
    public Long totalCitas() {

        return citaServicio.contarCitas();
    }

@PutMapping("/marcarAtendida/{id}")
public Cita marcarAtendida(
        @PathVariable Long id
) {

    Cita cita =
            citaServicio.buscarPorId(id);

    if (cita != null) {

        cita.setEstado("ATENDIDA");

        if (cita.getPaciente() != null &&
            cita.getPaciente().getEmail() != null) {

            correoServicio.enviarCorreo(
                    cita.getPaciente().getEmail(),
                    "Cita Atendida",
                    "Su cita ha sido marcada como atendida."
            );
        }

        return citaServicio.guardarCita(cita);
    }

    return null;
}

private void enviarCorreoPacienteYDoctor(
        Cita cita,
        String asunto,
        String mensajePaciente,
        String mensajeDoctor
) {

    try {

        Paciente paciente = null;
        Doctor doctor = null;

        if (
                cita.getPaciente() != null &&
                cita.getPaciente().getIdPaciente() != null
        ) {
            paciente = pacienteRepositorio
                    .findById(cita.getPaciente().getIdPaciente())
                    .orElse(null);
        }

        if (
                cita.getDoctor() != null &&
                cita.getDoctor().getIdDoctor() != null
        ) {
            doctor = doctorServicio.buscarDoctor(
                    cita.getDoctor().getIdDoctor()
            );
        }

        if (
                paciente != null &&
                paciente.getEmail() != null &&
                !paciente.getEmail().isEmpty()
        ) {
            correoServicio.enviarCorreo(
                    paciente.getEmail(),
                    asunto,
                    "Hospital La Bendicion\n\n" +
                    mensajePaciente + "\n\n" +
                    "Paciente: " + paciente.getNombre() + "\n" +
                    "Doctor: " + (doctor != null ? doctor.getNombreDoctor() : "Sin doctor") + "\n" +
                    "Fecha: " + cita.getFechaCita() + "\n" +
                    "Hora: " + cita.getHoraCita() + "\n" +
                    "Estado: " + cita.getEstado()
            );
        }

        if (
                doctor != null &&
                doctor.getIdUsuario() != null
        ) {
            User usuarioDoctor = userRepository
                    .findById(doctor.getIdUsuario())
                    .orElse(null);

            if (
                    usuarioDoctor != null &&
                    usuarioDoctor.getEmail() != null &&
                    !usuarioDoctor.getEmail().isEmpty()
            ) {
                correoServicio.enviarCorreo(
                        usuarioDoctor.getEmail(),
                        asunto,
                        "Hospital La Bendicion\n\n" +
                        mensajeDoctor + "\n\n" +
                        "Paciente: " + (paciente != null ? paciente.getNombre() : "Sin paciente") + "\n" +
                        "Doctor: " + doctor.getNombreDoctor() + "\n" +
                        "Fecha: " + cita.getFechaCita() + "\n" +
                        "Hora: " + cita.getHoraCita() + "\n" +
                        "Estado: " + cita.getEstado()
                );
            }
        }

    } catch (Exception e) {
        System.out.println("Error enviando correos: " + e.getMessage());
    }
}

}
package com.demo.persistencia.demopersistencia.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.demo.persistencia.demopersistencia.dto.DoctorDto;
import com.demo.persistencia.demopersistencia.entidades.Doctor;
import com.demo.persistencia.demopersistencia.entidades.User;
import com.demo.persistencia.demopersistencia.repositorio.UserRepositoy;
import com.demo.persistencia.demopersistencia.services.DoctorServicio;

@RestController
@RequestMapping("/api/doctores")
@CrossOrigin(origins = "*")
public class DoctorController {

    @Autowired
    private DoctorServicio servicioDoctor;

    @Autowired
    private UserRepositoy userRepository;

    @GetMapping("/listarDoctores")
    public List<Doctor> listarDoctores() {
        return servicioDoctor.listarDoctores();
    }

    @PostMapping("/registrarDoctor")
    public Doctor registrarDoctor(@RequestBody DoctorDto doctorJson) {

        Doctor doctor = new Doctor();

        doctor.setColegiado(doctorJson.getColegiado());
        doctor.setNombreDoctor(doctorJson.getNombreDoctor());
        doctor.setEspecialidad(doctorJson.getEspecialidad());
        doctor.setFechaRegistro(doctorJson.getFechaRegistro());
        doctor.setDireccion(doctorJson.getDireccion());
        doctor.setCentroHospitalario(doctorJson.getCentroHospitalario());
        doctor.setEdad(doctorJson.getEdad());
        doctor.setObservacion(doctorJson.getObservacion());
        doctor.setIdUsuario(doctorJson.getIdUsuario());

        Doctor doctorGuardado = servicioDoctor.guardarDoctor(doctor);

        actualizarNombreUsuario(doctorGuardado);

        return doctorGuardado;
    }

    @PutMapping("/editarDoctor/{id}")
    public Doctor editarDoctor(
            @PathVariable Long id,
            @RequestBody DoctorDto doctorJson
    ) {

        Doctor doctor = servicioDoctor.buscarDoctor(id);

        if (doctor != null) {

            doctor.setColegiado(doctorJson.getColegiado());
            doctor.setNombreDoctor(doctorJson.getNombreDoctor());
            doctor.setEspecialidad(doctorJson.getEspecialidad());
            doctor.setFechaRegistro(doctorJson.getFechaRegistro());
            doctor.setDireccion(doctorJson.getDireccion());
            doctor.setCentroHospitalario(doctorJson.getCentroHospitalario());
            doctor.setEdad(doctorJson.getEdad());
            doctor.setObservacion(doctorJson.getObservacion());
            doctor.setIdUsuario(doctorJson.getIdUsuario());

            Doctor doctorGuardado = servicioDoctor.guardarDoctor(doctor);

            actualizarNombreUsuario(doctorGuardado);

            return doctorGuardado;
        }

        return null;
    }

    private void actualizarNombreUsuario(Doctor doctor) {

        if (doctor.getIdUsuario() != null) {

            User user = userRepository
                    .findById(doctor.getIdUsuario())
                    .orElse(null);

            if (user != null) {

                user.setNombre(doctor.getNombreDoctor());

                userRepository.save(user);
            }
        }
    }

    @DeleteMapping("/eliminarDoctor/{id}")
    public void eliminarDoctor(@PathVariable Long id) {
        servicioDoctor.eliminarDoctor(id);
    }

    @GetMapping("/totalDoctores")
    public Long totalDoctores() {
        return servicioDoctor.contarDoctores();
    }

    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<Doctor> obtenerDoctorPorIdUsuario(
            @PathVariable Long idUsuario
    ) {

        Doctor doctor = servicioDoctor.buscarPorIdUsuario(idUsuario);

        if (doctor != null) {
            return ResponseEntity.ok(doctor);
        }

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/buscarPorIdUsuario/{idUsuario}")
    public Doctor buscarPorIdUsuario(
            @PathVariable Long idUsuario
    ) {

        return servicioDoctor.buscarPorIdUsuario(idUsuario);
    }
}

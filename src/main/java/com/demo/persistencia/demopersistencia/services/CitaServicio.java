package com.demo.persistencia.demopersistencia.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.persistencia.demopersistencia.entidades.Cita;
import com.demo.persistencia.demopersistencia.repositorio.CitaRepositorio;

@Service
public class CitaServicio {

    @Autowired
    private CitaRepositorio citaRepositorio;

    // LISTAR
    public List<Cita> listarCitas() {

        return (List<Cita>) citaRepositorio.findAll();

    }

    // GUARDAR
    public Cita guardarCita(
            Cita cita
    ) {

        return citaRepositorio.save(cita);

    }

    // BUSCAR POR ID
    public Cita buscarPorId(
            Long id
    ) {

        return citaRepositorio
                .findById(id)
                .orElse(null);

    }

    // BUSCAR CITA
    public Cita buscarCita(
            Long id
    ) {

        return citaRepositorio
                .findById(id)
                .orElse(null);

    }

    // ELIMINAR
    public void eliminarCita(
            Long id
    ) {

        citaRepositorio.deleteById(id);

    }

    // CONTAR
    public Long contarCitas() {

        return citaRepositorio.count();

    }

    // CITAS POR PACIENTE
    public List<Cita> buscarPorPaciente(
            Long idPaciente
    ) {

        return citaRepositorio
                .findByPaciente_IdPaciente(idPaciente);
        

    }

    // CITAS POR DOCTOR
    public List<Cita> buscarPorDoctor(
            Long idDoctor
    ) {

        return citaRepositorio
               .findByDoctor_IdDoctor(idDoctor);

    }

}
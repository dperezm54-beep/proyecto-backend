package com.demo.persistencia.demopersistencia.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.persistencia.demopersistencia.entidades.HistorialMedico;
import com.demo.persistencia.demopersistencia.repositorio.HistorialMedicoRepositorio;

@Service
public class HistorialMedicoServicio {

    @Autowired
    private HistorialMedicoRepositorio historialRepositorio;

    public List<HistorialMedico> listarHistoriales() {

        return (List<HistorialMedico>)
                historialRepositorio.findAll();
    }

    public HistorialMedico guardarHistorial(
            HistorialMedico historial
    ) {

        return historialRepositorio.save(historial);
    }

    public HistorialMedico buscarPorId(
            Long id
    ) {

        return historialRepositorio
                .findById(id)
                .orElse(null);
    }

    public List<HistorialMedico> buscarPorPaciente(
            Long idPaciente
    ) {

        return historialRepositorio
                .findByPacienteIdPaciente(idPaciente);
    }

    public List<HistorialMedico> buscarPorDoctor(
            Long idDoctor
    ) {

        return historialRepositorio
                .findByDoctorIdDoctor(idDoctor);
    }

    public void eliminarHistorial(
            Long id
    ) {

        historialRepositorio.deleteById(id);
    }
}
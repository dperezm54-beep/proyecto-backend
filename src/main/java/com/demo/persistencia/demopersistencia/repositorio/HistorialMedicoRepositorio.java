package com.demo.persistencia.demopersistencia.repositorio;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.demo.persistencia.demopersistencia.entidades.HistorialMedico;

@Repository
public interface HistorialMedicoRepositorio
        extends CrudRepository<HistorialMedico, Long> {

    List<HistorialMedico> findByPacienteIdPaciente(Long idPaciente);

    List<HistorialMedico> findByDoctorIdDoctor(Long idDoctor);
}
package com.demo.persistencia.demopersistencia.repositorio;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import com.demo.persistencia.demopersistencia.entidades.Cita;

public interface CitaRepositorio extends CrudRepository<Cita, Long> {

    List<Cita> findByPaciente_IdPaciente(Long idPaciente);

    List<Cita> findByDoctor_IdDoctor(Long idDoctor);

}
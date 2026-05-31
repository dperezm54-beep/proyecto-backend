package com.demo.persistencia.demopersistencia.repositorio;

import org.springframework.data.repository.CrudRepository;
import com.demo.persistencia.demopersistencia.entidades.Paciente;
import org.springframework.stereotype.Repository;

@Repository
public interface PacienteRepositorio extends CrudRepository<Paciente, Long> {
Paciente findByEmail(String email);
Paciente findByIdUsuario(Long idUsuario);

}

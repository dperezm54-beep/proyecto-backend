package com.demo.persistencia.demopersistencia.repositorio;

import org.springframework.data.repository.CrudRepository;
import com.demo.persistencia.demopersistencia.entidades.Doctor;
import org.springframework.stereotype.Repository;

@Repository
public interface DoctorRepositorio  extends CrudRepository<Doctor, Long> {

    Doctor findByIdUsuario(Long idUsuario);
}

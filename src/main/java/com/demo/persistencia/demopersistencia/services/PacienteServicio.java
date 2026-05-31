package com.demo.persistencia.demopersistencia.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.demo.persistencia.demopersistencia.entidades.Paciente;
import com.demo.persistencia.demopersistencia.repositorio.PacienteRepositorio;

@Service
public class PacienteServicio {

    @Autowired
    private PacienteRepositorio pacienteRepositorio;

    /**
     * consultar todos los pacientes.
     * 
     * @return
     */
    public List<Paciente> consultarPacientes() {
        return (List<Paciente>) pacienteRepositorio.findAll();
    }

    /**
     * @param paciente
     * @return el registro del paciente.
     */

    public Paciente registPacientes(Paciente paciente) {

        System.out.println("Servicio trae" + paciente.toString());
        return pacienteRepositorio.save(paciente);

    }

    public Paciente buscarPorId(Long id) {
      return pacienteRepositorio.findById(id).orElse(null);
   }

  
    public void eliminarPaciente(Long id) {
        pacienteRepositorio.deleteById(id);
    }

    public Long contarPacientes() {
    return pacienteRepositorio.count();
}

public Paciente buscarPorEmail(String email) {

    return pacienteRepositorio.findByEmail(email);

}

public Paciente buscarPorIdUsuario(Long idUsuario) {
        return pacienteRepositorio.findByIdUsuario(idUsuario);
    }

}

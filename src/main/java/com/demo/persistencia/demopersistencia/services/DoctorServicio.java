package com.demo.persistencia.demopersistencia.services;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import com.demo.persistencia.demopersistencia.entidades.Doctor;
import com.demo.persistencia.demopersistencia.repositorio.DoctorRepositorio;


@Service
public class DoctorServicio {

    @Autowired
    private DoctorRepositorio doctorRepositorio;
 /**
     * consultar el/las o doctores.
     * 
     * @return
     */
      // LISTAR
    public List<Doctor> listarDoctores() {
        return (List<Doctor>) doctorRepositorio.findAll();
    }

    // GUARDAR
    public Doctor guardarDoctor(Doctor doctor) {
        System.out.println("Servicio trae" + doctor.toString());
        return doctorRepositorio.save(doctor);
    }

    // BUSCAR
    public Doctor buscarDoctor(@NonNull Long id) {
        return doctorRepositorio.findById(id).orElse(null);
    }
    // ELIMINAR
    public void eliminarDoctor(@NonNull Long id) {
        doctorRepositorio.deleteById(id);
    }
    // CONTAR   
       public Long contarDoctores() {
    return doctorRepositorio.count();
    }

    public Doctor buscarPorIdUsuario(Long idUsuario) {
        return doctorRepositorio.findByIdUsuario(idUsuario);
    }   

}

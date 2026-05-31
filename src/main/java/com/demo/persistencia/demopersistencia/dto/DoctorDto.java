package com.demo.persistencia.demopersistencia.dto;


import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor

public class DoctorDto {

    private String colegiado;
    private String nombreDoctor;
    private String especialidad;
    private LocalDate fechaRegistro;
    private String direccion;
    private String centroHospitalario;
    private String edad;
    private String observacion;
    private Long idUsuario;

}


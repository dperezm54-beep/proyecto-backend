package com.demo.persistencia.demopersistencia.dto;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor

public class PacienteDto {

    private String nombre;
    private LocalDate fechaNacimiento;
    private String direccion;
    private String telefono;
    private String seguroMedico;
    private String email;
    

}

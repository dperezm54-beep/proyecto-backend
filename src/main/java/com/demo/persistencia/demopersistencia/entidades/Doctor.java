package com.demo.persistencia.demopersistencia.entidades;

import java.time.LocalDate;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "doctores")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_doctor")
    private Long idDoctor;

    @Column(name = "colegiado")
    private String colegiado;

    @Column(name = "nombre_doctor")
    private String nombreDoctor;

    @Column(name = "especialidad")
    private String especialidad;

    @Column(name = "fecha_registro")
    private LocalDate fechaRegistro;

    @Column(name = "direccion")
    private String direccion;

    @Column(name = "centro_hospitalario")
    private String centroHospitalario;

    @Column(name = "edad")
    private String edad;

    @Column(name = "observacion")
    private String observacion;

    @Column(name = "id_usuario", unique = true)
    private Long idUsuario;
}
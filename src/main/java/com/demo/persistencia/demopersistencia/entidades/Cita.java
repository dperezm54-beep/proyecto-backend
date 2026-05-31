package com.demo.persistencia.demopersistencia.entidades;

import java.time.LocalDate;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "citas")

@Data
@NoArgsConstructor
@AllArgsConstructor

public class Cita {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "id_cita")
    private Long idCita;

    // RELACION PACIENTE
    @ManyToOne
    @JoinColumn(name = "id_paciente")
    private Paciente paciente;

    // RELACION DOCTOR
    @ManyToOne
    @JoinColumn(name = "id_doctor")
    private Doctor doctor;

    @Column(name = "fecha_cita")
    private LocalDate fechaCita;

    @Column(name = "hora_cita")
    private String horaCita;

    @Column(name = "estado")
    private String estado;

    @Column(name = "observacion")
    private String observacion;

}

import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';

import { Paciente } from '../models/paciente';
import { PacienteService } from '../services/paciente.service';
import { CitaService } from '../services/cita.service';

@Component({
  selector: 'app-pacientes',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    RouterLink
  ],
  templateUrl: './pacientes.component.html',
  styleUrls: ['./pacientes.component.css']
})
export class PacientesComponent implements OnInit {

  pacientes: Paciente[] = [];

  paciente: any = {
    idPaciente: 0,
    idUsuario: 0,
    nombre: '',
    fechaNacimiento: '',
    direccion: '',
    telefono: '',
    seguroMedico: '',
    email: ''
  };

  modoEdicion = false;
  idUsuarioLogueado: number | null = null;
  rolUsuarioLogueado: string | null = null;

  constructor(
    private pacienteService: PacienteService,
    private citaService: CitaService
  ) {}

  ngOnInit(): void {

    const idLocal = localStorage.getItem('idUsuario');

    this.rolUsuarioLogueado = localStorage.getItem('role');

    if (idLocal) {
      this.idUsuarioLogueado = Number(idLocal);
    }

    if (this.rolUsuarioLogueado === 'ADMIN') {
      this.listarPacientes();
    }

    if (this.rolUsuarioLogueado === 'DOCTOR') {
      this.listarMisPacientesDoctor();
    }

    if (
      this.rolUsuarioLogueado === 'PACIENTE' &&
      this.idUsuarioLogueado
    ) {
      this.pacienteService
        .obtenerPacientePorIdUsuario(this.idUsuarioLogueado)
        .subscribe({
          next: (data) => {
            this.paciente = data;
            this.modoEdicion = true;
          },
          error: (err) => {
            console.error(err);
          }
        });
    }

  }

  listarPacientes() {

    this.pacienteService
      .listarPacientes()
      .subscribe({
        next: (data) => {
          this.pacientes = data;
        },
        error: (err) => {
          console.error(err);
        }
      });

  }

listarMisPacientesDoctor() {

  this.citaService
    .listarCitas()
    .subscribe({
      next: (citas) => {

        const pacientesDeCitas = citas
          .map(c => c.paciente)
          .filter((p, index, self) =>
            index === self.findIndex(
              x => x.idPaciente === p.idPaciente
            )
          );

        this.pacientes = [];

        pacientesDeCitas.forEach((p: any) => {

          if (p.idPaciente) {

            this.pacienteService
              .buscarPacientePorId(p.idPaciente)
              .subscribe({
                next: (pacienteCompleto) => {
                  this.pacientes.push(pacienteCompleto);
                },
                error: (err) => {
                  console.error(err);
                }
              });

          }

        });

      },
      error: (err) => {
        console.error(err);
      }
    });

}

  guardarPaciente() {

    if (!this.paciente.idPaciente) {
      alert('No se encontró el registro del paciente');
      return;
    }

    this.pacienteService
      .editarPaciente(this.paciente.idPaciente, this.paciente)
      .subscribe({
        next: () => {

          alert('Datos actualizados');

          if (this.rolUsuarioLogueado === 'ADMIN') {
            this.listarPacientes();
            this.limpiarFormulario();
          }

          if (this.rolUsuarioLogueado === 'PACIENTE') {
            this.modoEdicion = true;

            if (this.idUsuarioLogueado) {
              this.pacienteService
                .obtenerPacientePorIdUsuario(this.idUsuarioLogueado)
                .subscribe({
                  next: (data) => {
                    this.paciente = data;
                  }
                });
            }
          }

        },
        error: (err) => {
          console.error(err);
        }
      });

  }

  editarPaciente(paciente: any) {
    this.paciente = { ...paciente };
    this.modoEdicion = true;
  }

  limpiarFormulario() {

    this.paciente = {
      idPaciente: 0,
      idUsuario: 0,
      nombre: '',
      fechaNacimiento: '',
      direccion: '',
      telefono: '',
      seguroMedico: '',
      email: ''
    };

    this.modoEdicion = false;

  }

}
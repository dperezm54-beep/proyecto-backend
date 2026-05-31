import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';

import { Cita } from '../models/cita';
import { Paciente } from '../models/paciente';
import { Doctor } from '../models/doctor';

import { CitaService } from '../services/cita.service';
import { PacienteService } from '../services/paciente.service';
import { DoctorService } from '../services/doctor.service';

@Component({
  selector: 'app-citas',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    RouterLink
  ],
  providers: [CitaService],
  templateUrl: './citas.component.html',
  styleUrls: ['./citas.component.css']
})
export class CitasComponent implements OnInit {

  citas: Cita[] = [];
  pacientes: Paciente[] = [];
  doctores: Doctor[] = [];

  role: string | null = null;

  cita: Cita = {
    paciente: { idPaciente: 0 },
    doctor: { idDoctor: 0 },
    fechaCita: '',
    horaCita: '',
    estado: '',
    observacion: ''
  };

  modoEdicion = false;

  pacienteNuevo: Paciente = {
    idPaciente: 0,
    nombre: '',
    fechaNacimiento: '',
    direccion: '',
    telefono: '',
    seguroMedico: '',
    email: '',
    idUsuario: 0
  };

  mostrarFormularioPaciente = false;

  idUsuarioLogueado: number | null = null;

  constructor(
    private citaService: CitaService,
    private pacienteService: PacienteService,
    private doctorService: DoctorService
  ) {}

  ngOnInit(): void {

    const idLocal =
      localStorage.getItem('idUsuario');

    if (idLocal) {
      this.idUsuarioLogueado =
        Number(idLocal);
    }

    this.role =
      localStorage.getItem('role');

    this.listarCitas();
    this.listarPacientes();
    this.listarDoctores();
    this.verificarPaciente();
  }

  listarCitas() {

    this.citaService
      .listarCitas()
      .subscribe({

        next: (data) => {

          console.log(
            'CITAS RECIBIDAS:',
            data
          );

          this.citas = data;

        },

        error: (err) => {

          console.error(
            'ERROR AL LISTAR CITAS:',
            err
          );

        }

      });

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

  listarDoctores() {

    this.doctorService
      .listarDoctores()
      .subscribe({

        next: (data) => {

          this.doctores = data;

        },

        error: (err) => {

          console.error(err);

        }

      });

  }

  guardarCita() {

    if (
      this.modoEdicion &&
      this.cita.idCita
    ) {

      this.citaService
        .reprogramarCita(
          this.cita.idCita,
          this.cita
        )
        .subscribe({

          next: () => {

            alert(
              'Cita reprogramada'
            );

            this.listarCitas();

            this.limpiarFormulario();

          },

          error: (err) => {

            console.error(err);

          }

        });

    } else {

      this.citaService
        .registrarCita(this.cita)
        .subscribe({

          next: () => {

            alert(
              'Cita registrada'
            );

            this.listarCitas();

            this.limpiarFormulario();

          },

          error: (err) => {

            console.error(err);

          }

        });

    }

  }

  reprogramarCita(
    cita: Cita
  ) {

    this.cita = { ...cita };

    this.modoEdicion = true;

  }

  cancelarCita(
    id: number
  ) {

    if (
      confirm(
        '¿Seguro que deseas cancelar esta cita?'
      )
    ) {

      this.citaService
        .cancelarCita(id)
        .subscribe({

          next: () => {

            alert(
              'Cita cancelada'
            );

            this.listarCitas();

          },

          error: (err) => {

            console.error(err);

          }

        });

    }

  }

  editarCita(
    cita: Cita
  ) {

    this.cita = { ...cita };

    this.modoEdicion = true;

  }

  limpiarFormulario() {

    this.cita = {

      paciente: {
        idPaciente: 0
      },

      doctor: {
        idDoctor: 0
      },

      fechaCita: '',
      horaCita: '',
      estado: '',
      observacion: ''

    };

    this.modoEdicion = false;

  }

  verificarPaciente() {

    const role =
      localStorage.getItem(
        'role'
      );

    if (
      role === 'PACIENTE' &&
      this.idUsuarioLogueado
    ) {

      this.pacienteService
        .obtenerPacientePorIdUsuario(
          this.idUsuarioLogueado
        )
        .subscribe({

          next: (
            data: Paciente
          ) => {

            if (data) {

              this.mostrarFormularioPaciente =
                false;

              this.cita.paciente = {

                idPaciente:
                  data.idPaciente

              };

            } else {

              this.mostrarFormularioPaciente =
                true;

         this.pacienteNuevo.idUsuario =
  this.idUsuarioLogueado ?? 0;

            }

          },

          error: () => {

            this.mostrarFormularioPaciente =
              true;

            this.pacienteNuevo.idUsuario =
  this.idUsuarioLogueado ?? 0;

          }

        });

    }

  }

  guardarDatosPaciente() {

    if (
      this.idUsuarioLogueado
    ) {

      this.pacienteNuevo.idUsuario =
        this.idUsuarioLogueado;

    }

    this.pacienteService
      .registrarPaciente(
        this.pacienteNuevo
      )
      .subscribe({

        next: (
          pacienteCreado
        ) => {

          alert(
            'Datos completados'
          );

          this.mostrarFormularioPaciente =
            false;

          this.listarPacientes();

          this.cita.paciente = {

            idPaciente:
              pacienteCreado.idPaciente

          };

        },

        error: (err) => {

          console.error(err);

        }

      });

  }


  marcarAtendida(id: number) {

  this.citaService
    .marcarAtendida(id)
    .subscribe({

      next: () => {

        alert('Cita atendida');

        this.listarCitas();

      },

      error: (err) => {

        console.error(err);

      }

    });

}


eliminarCita(id: number) {

  if (confirm('¿Seguro que deseas eliminar esta cita definitivamente?')) {

    this.citaService
      .eliminarCita(id)
      .subscribe({
        next: () => {
          alert('Cita eliminada');
          this.listarCitas();
        },
        error: (err) => {
          console.error(err);
        }
      });

  }

}

descargarPdfCitas() {

  this.citaService.descargarPdfCitas().subscribe({

    next: (data) => {

      const blob = new Blob(
        [data],
        { type: 'application/pdf' }
      );

      const url = window.URL.createObjectURL(blob);

      const link = document.createElement('a');

      link.href = url;
      link.download = 'reporte_citas.pdf';

      document.body.appendChild(link);

      link.click();

      document.body.removeChild(link);

      window.URL.revokeObjectURL(url);

    },

    error: (err) => {

      console.error(err);

      alert('Error al descargar el PDF');

    }

  });

}

}
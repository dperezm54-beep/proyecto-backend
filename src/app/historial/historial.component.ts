import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';

import { Historial } from '../models/historial';
import { Paciente } from '../models/paciente';
import { Doctor } from '../models/doctor';
import { Cita } from '../models/cita';

import { HistorialService } from '../services/historial.service';
import { PacienteService } from '../services/paciente.service';
import { DoctorService } from '../services/doctor.service';
import { CitaService } from '../services/cita.service';

@Component({
  selector: 'app-historial',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    RouterLink
  ],
  templateUrl: './historial.component.html',
  styleUrls: ['./historial.component.css']
})
export class HistorialComponent implements OnInit {

  historialLista: Historial[] = [];
  pacientes: Paciente[] = [];
  doctores: Doctor[] = [];
  citas: Cita[] = [];

  role: string | null = null;

  historial: Historial = this.crearHistorialVacio();

  modoEdicion = false;

  constructor(
    private historialService: HistorialService,
    private pacienteService: PacienteService,
    private doctorService: DoctorService,
    private citaService: CitaService
  ) {}

 ngOnInit(): void {

  this.role = localStorage.getItem('role');

  this.listarHistorial();

  if (this.role === 'ADMIN') {

    this.listarPacientes();
    this.listarDoctores();

  }
  this.listarCitas();
}

  crearPacienteVacio(): Paciente {
    return {
      idPaciente: 0,
      idUsuario: 0,
      nombre: '',
      fechaNacimiento: '',
      direccion: '',
      telefono: '',
      seguroMedico: '',
      email: ''
    };
  }

  crearDoctorVacio(): Doctor {
    return {
      idDoctor: 0,
      idUsuario: 0,
      colegiado: '',
      nombreDoctor: '',
      especialidad: '',
      fechaRegistro: '',
      direccion: '',
      centroHospitalario: '',
      edad: '',
      observacion: ''
    };
  }

crearCitaVacia(): Cita {
  return {
    idCita: 0,

    paciente: {
      idPaciente: 0,
      nombre: ''
    },

    doctor: {
      idDoctor: 0,
      nombreDoctor: ''
    },

    fechaCita: '',
    horaCita: '',
    estado: '',
    observacion: ''
  };
}
  crearHistorialVacio(): Historial {
    return {
      paciente: this.crearPacienteVacio(),
      doctor: this.crearDoctorVacio(),
      cita: this.crearCitaVacia(),
      diagnostico: '',
      tratamiento: '',
      medicamentos: '',
      observaciones: '',
      fechaRegistro: ''
    };
  }

  listarHistorial() {
    this.historialService.listarHistorial().subscribe({
      next: (data) => {
        this.historialLista = data;
      },
      error: (err) => console.error(err)
    });
  }


  listarDoctores() {
    this.doctorService.listarDoctores().subscribe({
      next: (data) => this.doctores = data,
      error: (err) => console.error(err)
    });
  }

 guardarHistorial() {

  const citaSeleccionada = this.citas.find(
    c => c.idCita === this.historial.cita?.idCita
  );

  if (citaSeleccionada) {

    this.historial.paciente = {
      idPaciente: citaSeleccionada.paciente.idPaciente,
      idUsuario: 0,
      nombre: citaSeleccionada.paciente.nombre ?? '',
      fechaNacimiento: '',
      direccion: '',
      telefono: '',
      seguroMedico: '',
      email: ''
    };

    this.historial.doctor = {
      idDoctor: citaSeleccionada.doctor.idDoctor,
      idUsuario: 0,
      colegiado: '',
      nombreDoctor: citaSeleccionada.doctor.nombreDoctor ?? '',
      especialidad: '',
      fechaRegistro: '',
      direccion: '',
      centroHospitalario: '',
      edad: '',
      observacion: ''
    };

    this.historial.cita = {
      idCita: citaSeleccionada.idCita,
      paciente: {
        idPaciente: citaSeleccionada.paciente.idPaciente
      },
      doctor: {
        idDoctor: citaSeleccionada.doctor.idDoctor
      },
      fechaCita: '',
      horaCita: '',
      estado: '',
      observacion: ''
    };

  }

  if (this.modoEdicion && this.historial.idHistorial) {

    this.historialService
      .editarHistorial(this.historial.idHistorial, this.historial)
      .subscribe({
        next: () => {
          alert('Historial actualizado');
          this.listarHistorial();
          this.limpiarFormulario();
        },
        error: (err) => {
          console.error(err);
        }
      });

  } else {

    this.historialService
      .registrarHistorial(this.historial)
      .subscribe({
        next: () => {
          alert('Historial registrado');
          this.listarHistorial();
          this.limpiarFormulario();
        },
        error: (err) => {
          console.error(err);
        }
      });

  }

}
  editarHistorial(historial: Historial) {
    this.historial = { ...historial };
    this.modoEdicion = true;
  }

  eliminarHistorial(id: number) {
    if (confirm('¿Seguro que deseas eliminar este historial?')) {
      this.historialService.eliminarHistorial(id).subscribe({
        next: () => {
          alert('Historial eliminado');
          this.listarHistorial();
        },
        error: (err) => console.error(err)
      });
    }
  }

  limpiarFormulario() {
    this.historial = this.crearHistorialVacio();
    this.modoEdicion = false;
  }

  listarPacientes() {

  this.pacienteService.listarPacientes().subscribe({

    next: (data) => {

      console.log('PACIENTES', data);

      this.pacientes = data;

    },

    error: (err) => console.error(err)

  });

}


listarCitas() {

  this.citaService.listarCitas().subscribe({

    next: (data) => {

      console.log('CITAS', data);

      this.citas = data;

      if (this.role === 'DOCTOR') {

        this.pacientes = data
          .map(c => ({
            idPaciente: c.paciente.idPaciente,
            idUsuario: 0,
            nombre: c.paciente.nombre ?? '',
            fechaNacimiento: '',
            direccion: '',
            telefono: '',
            seguroMedico: '',
            email: ''
          }))
          .filter((paciente, index, self) =>
            index === self.findIndex(
              p => p.idPaciente === paciente.idPaciente
            )
          );

      }

    },

    error: (err) => console.error(err)

  });

}

descargarPdfHistorial() {

  this.historialService.descargarPdfHistorial().subscribe({

    next: (data) => {

      const blob = new Blob(
        [data],
        { type: 'application/pdf' }
      );

      const url = window.URL.createObjectURL(blob);

      const link = document.createElement('a');

      link.href = url;
      link.download = 'reporte_historial_medico.pdf';

      document.body.appendChild(link);

      link.click();

      document.body.removeChild(link);

      window.URL.revokeObjectURL(url);

    },

    error: (err) => {

      console.error(err);

      alert('Error al descargar PDF de historial');

    }

  });

}


}
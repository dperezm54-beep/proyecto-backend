import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';

import { Doctor } from '../models/doctor';
import { DoctorService } from '../services/doctor.service';

@Component({
  selector: 'app-doctores',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    RouterLink
  ],
  templateUrl: './doctores.component.html',
  styleUrls: ['./doctores.component.css']
})
export class DoctoresComponent implements OnInit {

  doctores: Doctor[] = [];

  doctor: Doctor = {
    idDoctor: undefined,
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

  modoEdicion = false;

  idUsuarioLogueado: number | null = null;
  rolUsuarioLogueado: string | null = null;

  constructor(
    private doctorService: DoctorService
  ) {}

  ngOnInit(): void {

    const idLocal = localStorage.getItem('idUsuario');

    this.rolUsuarioLogueado = localStorage.getItem('role');

    if (idLocal) {
      this.idUsuarioLogueado = Number(idLocal);
    }

    if (this.rolUsuarioLogueado === 'ADMIN') {
      this.listarDoctores();
    }

    if (
      this.rolUsuarioLogueado === 'DOCTOR' &&
      this.idUsuarioLogueado
    ) {
      this.cargarDoctorLogueado(this.idUsuarioLogueado);
    }

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

  cargarDoctorLogueado(idUsuario: number) {
    this.doctorService
      .obtenerDoctorPorIdUsuario(idUsuario)
      .subscribe({
        next: (data) => {
          this.doctor = data;
          this.modoEdicion = true;
        },
        error: (err) => {
          console.error(err);
        }
      });
  }

  guardarDoctor() {

    if (!this.modoEdicion || !this.doctor.idDoctor) {
      alert('Debe seleccionar un doctor existente');
      return;
    }

    this.doctorService
      .editarDoctor(this.doctor.idDoctor, this.doctor)
      .subscribe({
        next: () => {
          alert('Doctor actualizado');

          if (this.rolUsuarioLogueado === 'ADMIN') {
            this.listarDoctores();
            this.limpiarFormulario();
          }

          if (
            this.rolUsuarioLogueado === 'DOCTOR' &&
            this.idUsuarioLogueado
          ) {
            this.cargarDoctorLogueado(this.idUsuarioLogueado);
          }
        },
        error: (err) => {
          console.error(err);
        }
      });

  }

  editarDoctor(doctor: Doctor) {
    this.doctor = { ...doctor };
    this.modoEdicion = true;
  }

  limpiarFormulario() {
    this.doctor = {
      idDoctor: undefined,
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

    this.modoEdicion = false;
  }

}
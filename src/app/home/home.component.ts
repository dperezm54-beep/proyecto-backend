import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';

import { PacienteService } from '../services/paciente.service';
import { DoctorService } from '../services/doctor.service';
import { CitaService } from '../services/cita.service';
import { HistorialService } from '../services/historial.service';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [
    CommonModule,
    RouterLink
  ],
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  role: string | null = '';
  nombre: string | null = '';

  totalPacientes = 0;
  totalDoctores = 0;
  totalCitas = 0;
  totalHistoriales = 0;

  constructor(
    private router: Router,
    private pacienteService: PacienteService,
    private doctorService: DoctorService,
    private citaService: CitaService,
    private historialService: HistorialService
  ) {}

  ngOnInit(): void {

    this.role = localStorage.getItem('role');
    this.nombre = localStorage.getItem('nombre');

    this.obtenerTotales();

  }

  obtenerTotales() {

    if (this.role === 'ADMIN') {

      this.pacienteService.totalPacientes().subscribe({
        next: (data) => this.totalPacientes = data,
        error: (err) => console.error(err)
      });

      this.doctorService.totalDoctores().subscribe({
        next: (data) => this.totalDoctores = data,
        error: (err) => console.error(err)
      });

      this.citaService.totalCitas().subscribe({
        next: (data) => this.totalCitas = data,
        error: (err) => console.error(err)
      });

    }

    if (this.role === 'DOCTOR') {

      this.citaService.listarCitas().subscribe({
        next: (citas) => {

          this.totalCitas = citas.length;

          const pacientesUnicos = citas
            .map(c => c.paciente.idPaciente)
            .filter((id, index, self) => self.indexOf(id) === index);

          this.totalPacientes = pacientesUnicos.length;

        },
        error: (err) => console.error(err)
      });

      this.historialService.listarHistorial().subscribe({
        next: (historiales) => {
          this.totalHistoriales = historiales.length;
        },
        error: (err) => console.error(err)
      });

    }

    if (this.role === 'PACIENTE') {

      this.citaService.listarCitas().subscribe({
        next: (citas) => {
          this.totalCitas = citas.length;
        },
        error: (err) => console.error(err)
      });

      this.historialService.listarHistorial().subscribe({
        next: (historiales) => {
          this.totalHistoriales = historiales.length;
        },
        error: (err) => console.error(err)
      });

    }

  }

  logout() {

    localStorage.clear();
    this.router.navigate(['/login']);

  }

}
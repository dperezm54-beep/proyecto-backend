import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Paciente } from '../models/paciente';

@Injectable({
  providedIn: 'root'
})
export class PacienteService {

 // private apiUrl = 'http://localhost:8080/api/pacientes';
private apiUrl = 'proyectofinalbackend-production-f2d4.up.railway.app';
  constructor(
    private http: HttpClient
  ) { }

  // LISTAR TODOS LOS PACIENTES (Para el Admin)
  listarPacientes(): Observable<Paciente[]> {
    return this.http.get<Paciente[]>(`${this.apiUrl}/listarPacientes`);
  }

  // REGISTRAR / COMPLETAR DATOS DEL PACIENTE
  registrarPaciente(paciente: Paciente): Observable<Paciente> {
    return this.http.post<Paciente>(`${this.apiUrl}/registrarPaciente`, paciente);
  }

  // ELIMINAR PACIENTE
  eliminarPaciente(id: number): Observable<any> {
    return this.http.delete(`${this.apiUrl}/eliminarPaciente/${id}`);
  }

  // EDITAR PACIENTE
  editarPaciente(id: number, paciente: Paciente): Observable<any> {
    return this.http.put(`${this.apiUrl}/editarPaciente/${id}`, paciente);
  }

  // TOTAL DE PACIENTES (Para las métricas del Dashboard)
  totalPacientes(): Observable<number> {
    return this.http.get<number>(`${this.apiUrl}/totalPacientes`);
  }

  /**
   * NUEVO MÉTODO CLAVE:
   * Consume el endpoint que creamos en Spring Boot para buscar el perfil usando el ID del usuario logueado.
   */

obtenerPacientePorIdUsuario(idUsuario: number) {
  return this.http.get<Paciente>(
    `${this.apiUrl}/buscarPorIdUsuario/${idUsuario}`
  );
}


buscarPacientePorId(id: number) {
  return this.http.get<Paciente>(
    `${this.apiUrl}/buscarPaciente/${id}`
  );
}

}

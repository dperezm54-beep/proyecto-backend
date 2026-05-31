import { Injectable } from '@angular/core';
import { HttpClient} from '@angular/common/http';
import { Observable } from 'rxjs';
import { Doctor } from '../models/doctor';


@Injectable({
  providedIn: 'root'
})

export class DoctorService {

  //private apiUrl ='http://localhost:8080/api/doctores';
 private apiUrl ='proyectofinalbackend-production-f2d4.up.railway.app';
  constructor(
    private http: HttpClient
  ) {}

  // LISTAR
  listarDoctores():
  Observable<Doctor[]> {

    return this.http.get<Doctor[]>(
      `${this.apiUrl}/listarDoctores`
    );

  }

  // REGISTRAR
  registrarDoctor(
    doctor: Doctor
  ): Observable<Doctor> {

    return this.http.post<Doctor>(
      `${this.apiUrl}/registrarDoctor`,
      doctor
    );

  }

editarDoctor(id: number, doctor: Doctor) {

  return this.http.put(
    `${this.apiUrl}/editarDoctor/${id}`,
    doctor
  );

}

eliminarDoctor(id: number) {

  return this.http.delete(
    `${this.apiUrl}/eliminarDoctor/${id}`
  );

}

obtenerDoctorPorIdUsuario(idUsuario: number) {

  return this.http.get<Doctor>(
    `${this.apiUrl}/buscarPorIdUsuario/${idUsuario}`
  );

}

totalDoctores(): Observable<number> {

  return this.http.get<number>(
    `${this.apiUrl}/totalDoctores`
  );

}

}
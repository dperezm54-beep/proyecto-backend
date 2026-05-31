import { Injectable } from '@angular/core';

import {
  HttpClient
} from '@angular/common/http';

import { Observable } from 'rxjs';

import { Cita }
from '../models/cita';

@Injectable({
  providedIn: 'root'
})

export class CitaService {

 // private apiUrl =
  //'http://localhost:8080/api/citas';
private apiUrl =
'https://proyectofinalbackend-production-f2d4.up.railway.app/api/citas';
  constructor(
    private http: HttpClient
  ) {}

  // LISTAR
  listarCitas():
  Observable<Cita[]> {

    return this.http.get<Cita[]>(
      `${this.apiUrl}/listarCitas`
    );

  }

  // REGISTRAR
  registrarCita(
    cita: Cita
  ): Observable<Cita> {

    return this.http.post<Cita>(
      `${this.apiUrl}/registrarCita`,
      cita
    );

  }

  // ELIMINAR
  eliminarCita(id: number) {

    return this.http.delete(
      `${this.apiUrl}/eliminarCita/${id}`
    );

  }

  // EDITAR
  editarCita(
    id: number,
    cita: Cita
  ): Observable<Cita> {

    return this.http.put<Cita>(
      `${this.apiUrl}/editarCita/${id}`,
      cita
    );

  }


  totalCitas(): Observable<number> {

  return this.http.get<number>(
    `${this.apiUrl}/totalCitas`
  );

}


reprogramarCita(id: number, cita: Cita) {
  return this.http.put<Cita>(
    `${this.apiUrl}/reprogramarCita/${id}`,
    cita
  );
}

cancelarCita(id: number) {
  return this.http.put<Cita>(
    `${this.apiUrl}/cancelarCita/${id}`,
    {}
  );
}

marcarAtendida(id: number) {

  return this.http.put<Cita>(
    `${this.apiUrl}/marcarAtendida/${id}`,
    {}
  );

}
descargarPdfCitas() {
  return this.http.get(
    'https://proyectofinalbackend-production-f2d4.up.railway.app/api/reportes/citas/pdf',
    {
      responseType: 'blob'
    }
  );
}

// descargarPdfCitas() {
//   return this.http.get(
//     'http://localhost:8080/api/reportes/citas/pdf',
//     {
//       responseType: 'blob'
//     }
//   );
// }



}
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

import { Historial } from '../models/historial';

@Injectable({
  providedIn: 'root'
})
export class HistorialService {

  //private apiUrl ='http://localhost:8080/api/historial';
private apiUrl ='proyectofinalbackend-production-f2d4.up.railway.app';

  constructor(
    private http: HttpClient
  ) {}

  listarHistorial(): Observable<Historial[]> {
    return this.http.get<Historial[]>(
      `${this.apiUrl}/listarHistorial`
    );
  }

  registrarHistorial(
    historial: Historial
  ): Observable<Historial> {
    return this.http.post<Historial>(
      `${this.apiUrl}/registrarHistorial`,
      historial
    );
  }

  editarHistorial(
    id: number,
    historial: Historial
  ): Observable<Historial> {
    return this.http.put<Historial>(
      `${this.apiUrl}/editarHistorial/${id}`,
      historial
    );
  }

  eliminarHistorial(id: number) {
    return this.http.delete(
      `${this.apiUrl}/eliminarHistorial/${id}`
    );
  }

  descargarPdfHistorial() {
  return this.http.get(
    'http://localhost:8080/api/reportes/historial/pdf',
    {
      responseType: 'blob'
    }
  );
}

}
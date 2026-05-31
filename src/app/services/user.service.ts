import { Injectable } from '@angular/core';
import {
  HttpClient,
  HttpHeaders
} from '@angular/common/http';
import { Observable } from 'rxjs';
import { User } from '../models/user';

@Injectable({
  providedIn: 'root'
})
export class UserService {

 // private apiUrl = 'http://localhost:8080/api/users';
private apiUrl = 'proyectofinalbackend-production-f2d4.up.railway.app';

  constructor(
    private http: HttpClient
  ) {}

  private getHeaders() {
    const token = localStorage.getItem('token');

    return new HttpHeaders({
      Authorization: `Bearer ${token}`
    });
  }

  // LISTAR USUARIOS
  listarUsuarios(): Observable<User[]> {
    return this.http.get<User[]>(
      `${this.apiUrl}/listarUsuarios`,
      {
        headers: this.getHeaders()
      }
    );
  }

  // GUARDAR USUARIO
  guardarUsuario(
    usuario: any
  ): Observable<any> {
    return this.http.post(
      `${this.apiUrl}/registrarUsuario`,
      usuario,
      {
        headers: this.getHeaders()
      }
    );
  }

  // EDITAR USUARIO
  editarUsuario(
    id: number,
    usuario: any
  ): Observable<any> {
    return this.http.put(
      `${this.apiUrl}/editarUsuario/${id}`,
      usuario,
      {
        headers: this.getHeaders()
      }
    );
  }

  // ELIMINAR USUARIO
  eliminarUsuario(
    id: number
  ): Observable<any> {
    return this.http.delete(
      `${this.apiUrl}/eliminarUsuario/${id}`,
      {
        headers: this.getHeaders()
      }
    );
  }
}
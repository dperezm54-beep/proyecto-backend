import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { RouterModule, Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [FormsModule, RouterModule],
  templateUrl: './register.component.html',
  styleUrl: './register.component.css'
})
export class RegisterComponent {

  nombre = '';
  email = '';
  password = '';

  constructor(
    private http: HttpClient,
    private router: Router
  ) {}

  register() {

    const body = {

      nombre: this.nombre,
      email: this.email,
      password: this.password

    };

    console.log(body);

    this.http.post(
      'https://proyectofinalbackend-production-f2d4.up.railway.app',
      body
    ).subscribe({

      next: () => {

        alert('Usuario registrado');

        this.router.navigate(['/']);

      },

      error: (error) => {

        console.log(error);

        alert('Error al registrar');

      }

    });

  }

}
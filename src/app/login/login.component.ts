import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { RouterModule, Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [FormsModule, RouterModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})

export class LoginComponent {

  email = '';
  password = '';

  constructor(
    private http: HttpClient,
    private router: Router
  ) {}

  login() {

    const body = {

      email: this.email,
      password: this.password

    };

    console.log(body);

    this.http.post<any>(
      'https://proyectofinalbackend-production-f2d4.up.railway.app',
      body
    ).subscribe({

      next: (response) => {

        console.log(response);

        localStorage.setItem('token',response.token);
        localStorage.setItem('role',response.role);
        localStorage.setItem('email',response.email);
        localStorage.setItem('nombre',response.nombre);
        localStorage.setItem('token',response.token);
        localStorage.setItem('idUsuario',response.id);

        alert('Login correcto');

        this.router.navigate(['/home']);

      },

      error: (error) => {

        console.log(error);

         alert(JSON.stringify(error.error));

      }

    });

  }

}
import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';
import { User } from '../models/user';
import { UserService } from '../services/user.service';

const modoEdicion = false;

@Component({

  selector: 'app-usuarios',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    RouterLink
  ],

  templateUrl: './usuarios.component.html',
  styleUrls: ['./usuarios.component.css']

})

export class UsuariosComponent
implements OnInit {

  usuarios: User[] = [];
  usuario: User = {
    nombre: '',
    email: '',
    password: '',
    role: 'PACIENTE'

  };
  modoEdicion: boolean | undefined;

  constructor(
    private userService: UserService
  ) {}

  ngOnInit(): void {

    this.listarUsuarios();

  }

  listarUsuarios() {

    (this.userService
      .listarUsuarios() as any)
      .subscribe({

        next: (data: User[]) => {

          this.usuarios = data;

        }

      });

  }

  guardarUsuario() {

  // EDITAR
  if (

    this.modoEdicion &&

    this.usuario.id

  ) {

    (this.userService
      .editarUsuario(

        this.usuario.id,
        this.usuario

      ) as any)
      .subscribe({

        next: () => {

          alert('Usuario actualizado');

          this.listarUsuarios();

          this.limpiarFormulario();

        }

      });

  }

  // NUEVO
  else {

    (this.userService
      .guardarUsuario(this.usuario) as any)
      .subscribe({

        next: () => {

          alert('Usuario guardado');

          this.listarUsuarios();

          this.limpiarFormulario();

        }

      });

  }

}

  eliminarUsuario(id: number) {

    (this.userService
      .eliminarUsuario(id) as any)
      .subscribe({

        next: () => {
          alert('Usuario eliminado');
          this.listarUsuarios();

        }
      });
  }

  editarUsuario(usuario: any) {

  this.usuario = {

    ...usuario

  };

  this.modoEdicion = true;

}

limpiarFormulario() {

  this.usuario = {

    nombre: '',
    email: '',
    password: '',
    role: 'PACIENTE'

  };

  this.modoEdicion = false;

}


}
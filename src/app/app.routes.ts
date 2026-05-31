import { Routes } from '@angular/router';

import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { HomeComponent } from './home/home.component';

import { authGuard } from './auth.guard';
import { PacientesComponent } from './pacientes/pacientes.component';
import { DoctoresComponent } from './doctores/doctores.component';
import { CitasComponent } from './citas/citas.component';

export const routes: Routes = [

  {
    path: '',
    redirectTo: 'login',
    pathMatch: 'full'
  },

  {
    path: 'login',
    component: LoginComponent
  },

  {
    path: 'register',
    component: RegisterComponent
  },

  {
    path: 'home',
    component: HomeComponent,
    canActivate: [authGuard]
  },


  {
    path: 'pacientes',
    component: PacientesComponent,
    canActivate: [authGuard]
  },

  {
    path: 'doctores',
    component: DoctoresComponent,
    canActivate: [authGuard]
  },

  {
    path: 'citas',
    component: CitasComponent,
    canActivate: [authGuard]
  },

  {
    path: 'usuarios',
    loadComponent: () =>
      import('./usuarios/usuarios.component')
        .then(m => m.UsuariosComponent)
  },
  
{
  path: 'historial',
  loadComponent: () =>
    import('./historial/historial.component')
      .then(m => m.HistorialComponent)
},


  {
    path: '**',
    redirectTo: 'login'
  },

];
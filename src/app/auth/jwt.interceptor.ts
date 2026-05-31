import { HttpInterceptorFn } from '@angular/common/http';

export const jwtInterceptor: HttpInterceptorFn = (req, next) => {

  console.log('URL:', req.url);

  // RUTAS PUBLICAS
  const rutasPublicas = [

    '/auth/login',

    '/auth/register',

    '/api/users/registrarUsuario'

  ];

  // VALIDAR SI ES PUBLICA
  const esRutaPublica = rutasPublicas.some(

    ruta => req.url.includes(ruta)

  );

  // NO MANDAR TOKEN
  if (esRutaPublica) {

    console.log('RUTA PUBLICA - SIN TOKEN');

    return next(req);

  }

  // TOKEN
  const token = localStorage.getItem('token');

  // AGREGAR TOKEN
  if (token) {

    console.log('TOKEN ENVIADO');

    req = req.clone({

      setHeaders: {

        Authorization: `Bearer ${token}`

      }

    });

  }

  return next(req);

};
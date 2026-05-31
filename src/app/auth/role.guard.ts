import { CanActivateFn, Router } from '@angular/router';

export const roleGuard: CanActivateFn = (route, state) => {

  const role = localStorage.getItem('role');

  if (!role) {
    return false;
  }

  const expectedRoles = route.data['roles'];

  if (expectedRoles.includes(role)) {
    return true;
  }

  alert('No tienes permisos');
  return false;

};

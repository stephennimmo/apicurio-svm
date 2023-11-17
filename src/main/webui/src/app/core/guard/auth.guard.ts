import { CanActivateFn, Router } from '@angular/router';
import { inject } from '@angular/core';
import { KeycloakService } from 'keycloak-angular';

export const authGuard: CanActivateFn = async (route, state) => {
  const router = inject(Router);
  const keycloakService = inject(KeycloakService);
  const isLoggedIn = await keycloakService.isLoggedIn();
  if (!isLoggedIn) {
    await keycloakService.login({
      redirectUri: window.location.origin + state.url
    });
  }
  return true;
};

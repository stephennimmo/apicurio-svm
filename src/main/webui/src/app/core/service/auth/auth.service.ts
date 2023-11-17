import { Injectable } from '@angular/core';
import { KeycloakService } from 'keycloak-angular';
import { from, Observable } from 'rxjs';


@Injectable({
  providedIn: 'root'
})
export class AuthService {

  isLoggedIn?: Observable<boolean>;
  userProfile?: Observable<UserProfile>;

  constructor(private keycloakService: KeycloakService) {
    this.isLoggedIn = from(this.keycloakService.isLoggedIn().then(isLoggedIn => {
      if (isLoggedIn) {
        this.userProfile = from(this.keycloakService.loadUserProfile().then(keycloakProfile => {
          return { username: keycloakProfile.username };
        }));
      }
      return isLoggedIn;
    }));
  }

  public login(): Observable<void> {
    return from(this.keycloakService.login({
      redirectUri: window.location.origin
    }));
  }

  public logout(): Observable<void> {
    return from(this.keycloakService.logout(window.location.origin));
  }

}

export interface UserProfile {
  username: string | undefined;
}

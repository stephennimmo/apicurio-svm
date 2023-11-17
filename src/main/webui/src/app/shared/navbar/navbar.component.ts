import { Component } from '@angular/core';
import { AuthService, UserProfile } from '../../core/service/auth/auth.service';
import { Observable } from 'rxjs';
import { BaseComponent } from '../base-component';


@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss']
})
export class NavbarComponent extends BaseComponent {

  isCollapsed: boolean = false;

  isLoggedIn?: Observable<boolean>;
  userProfile?: Observable<UserProfile>;

  constructor(private authService: AuthService) {
    super();
  }

  init(): void {
    this.isLoggedIn = this.authService.isLoggedIn;
    this.addSubscription(this.isLoggedIn?.subscribe(s => {
      this.userProfile = this.authService.userProfile;
    }));
  }

  login(): void {
    this.authService.login();
  }

  logout(): void {
    this.authService.logout();
  }

  destroy(): void {
  }

}

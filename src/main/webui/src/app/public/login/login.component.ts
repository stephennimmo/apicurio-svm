import { Component } from '@angular/core';
import { AbstractControl, FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { BaseComponent } from '../../shared/base-component';
import { AuthService } from '../../core/service/auth/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent extends BaseComponent {

  loginForm = this.formBuilder.group({
    username: ['', [Validators.required]],
    password: ['', [Validators.required]]
  });

  constructor(private readonly router: Router, private readonly formBuilder: FormBuilder,
              private readonly authService: AuthService) {
    super();
  }

  init(): void {
  }

  destroy(): void {
  }

  onSubmit() {
    if (this.loginForm.invalid) {
      return;
    }
    this.authService.login(this.controls.username.value, this.controls.password.value);
    console.log(this.loginForm);
  }

  get controls(): { [p: string]: AbstractControl } {
    return this.loginForm.controls;
  }

}

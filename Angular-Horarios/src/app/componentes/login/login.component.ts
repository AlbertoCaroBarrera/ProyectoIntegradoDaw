import { Component } from '@angular/core';
import { PrimengModule } from './../../primeng.module';
import { ReactiveFormsModule, Validators, FormBuilder } from '@angular/forms';
import { Router } from '@angular/router';
import { LoginService } from '../../services/auth/login.service';
import { LoginRequest } from '../../services/auth/loginRequest';
import { MessageService } from 'primeng/api';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [PrimengModule, ReactiveFormsModule],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
  providers:[MessageService]
})
export class LoginComponent {
  loginError: string = "";

  loginForm = this.formBuilder.group({
    username: ['', [Validators.required, Validators.email]],
    password: ['', [Validators.required]]
  })

  get username() {
    return this.loginForm.controls['username'];
  }

  get password() { return this.loginForm.controls['password']; }

  constructor(
    private formBuilder: FormBuilder,
    private router: Router,
    private loginService: LoginService
  ) { }

  login() {
    if (this.loginForm.valid) {
      this.loginError = "";
      this.loginService.login(this.loginForm.value as LoginRequest).subscribe({
        next: (token) => {
          this.loginForm.reset();
          this.loginService.currentUserLoginOn.next(true);
        },
        error: (errorData) => {
          console.error(errorData);
          this.loginError = "Invalid username or password. Please try again.";
        },
        complete:() => {
          this.router.navigate(['/inicio']);
        }
      });
    } else {
      this.loginForm.markAllAsTouched();
      alert("Error al ingresar los datos.");
    }
  }
}
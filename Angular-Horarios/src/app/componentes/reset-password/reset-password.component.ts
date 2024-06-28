import { Component } from '@angular/core';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { PrimengModule } from '../../primeng.module';
import { CalendarModule } from 'primeng/calendar';
import { environment } from '../../../environments/environment';


@Component({
  selector: 'app-reset-password',
  standalone: true,
  imports: [PrimengModule, CalendarModule, FormsModule,ReactiveFormsModule],
  templateUrl: './reset-password.component.html',
  styleUrl: './reset-password.component.css'
})
export class ResetPasswordComponent {
  resetPasswordForm: FormGroup;
  successMessage: string = '';
  errorMessage: string = '';
  token: string = '';

  constructor(
    private fb: FormBuilder,
    private route: ActivatedRoute,
    private http: HttpClient,
    private router: Router
  ) {
    this.resetPasswordForm = this.fb.group({
      newPassword: ['', [Validators.required, Validators.minLength(6)]]
    });

    this.token = this.route.snapshot.queryParams['token'];
  }

  get newPassword() {
    return this.resetPasswordForm.get('newPassword');
  }

  onSubmit() {
    if (this.resetPasswordForm.valid && this.token) {
      const request = {
        token: this.token,
        newPassword: this.newPassword?.value
      };
      this.http.post( environment.urlHost + 'auth/reset-password', request)
        .subscribe(
          {
            next: () => {
              this.successMessage = 'Password has been reset successfully.';
              this.errorMessage = '';
              this.router.navigate(['/login']); // Redirect to login page after successful password reset
            },
            error: (error) => {
              console.error(error);
              this.errorMessage = 'Failed to reset password. Please try again later.';
              this.successMessage = '';
            }
          });
    }
  }
}
import { Component } from '@angular/core';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { ConfirmationService, MessageService } from 'primeng/api';
import { CalendarModule } from 'primeng/calendar';
import { PrimengModule } from '../../primeng.module';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../../environments/environment';

@Component({
  selector: 'app-forgot-password',
  templateUrl: './forgot-password.component.html',
  standalone:true,
  imports: [PrimengModule, CalendarModule, FormsModule,ReactiveFormsModule],
  styleUrls: ['./forgot-password.component.css'],
  providers: [ConfirmationService, MessageService]
})
export class ForgotPasswordComponent {
  forgotPasswordForm: FormGroup;
  successMessage: string = '';
  errorMessage: string = '';

  constructor(private fb: FormBuilder, private http: HttpClient) {
    this.forgotPasswordForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]]
    });
  }

  get email() {
    return this.forgotPasswordForm.get('email');
  }

  onSubmit() {
    if (this.forgotPasswordForm.valid && this.email) {
      this.http.post( environment.urlHost + 'auth/forgot-password', { username: this.email.value })
        .subscribe(
          {
            next: () => {
              this.successMessage = 'Password reset email sent. Please check your inbox.';
              this.errorMessage = '';
            },
            error: (error) => {
              console.error(error);
              this.errorMessage = 'Failed to send password reset email. Please try again later.';
              this.successMessage = '';
            }
          });

    }
  }
}
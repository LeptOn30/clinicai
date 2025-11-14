import { Component } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './login.component.html',
})
export class LoginComponent {
  loginForm = this.fb.group({
    username: ['', Validators.required],
    password: ['', Validators.required]
  });
  error: string | null = null;

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private router: Router
  ) {}

  onSubmit(): void {
    this.error = null;
    if (this.loginForm.valid) {
      this.authService.login(this.loginForm.value as any).subscribe({
        next: () => this.router.navigate(['/dashboard']),
        error: () => this.error = 'Login failed. Please check your credentials.'
      });
    }
  }
}
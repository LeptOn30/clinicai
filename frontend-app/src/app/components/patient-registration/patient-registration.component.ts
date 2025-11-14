import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-patient-registration',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterLink],
  templateUrl: './patient-registration.component.html',
})
export class PatientRegistrationComponent {
  patientForm = this.fb.group({
    firstName: ['', Validators.required],
    lastName: ['', Validators.required],
    dateOfBirth: ['', Validators.required],
    email: ['', [Validators.required, Validators.email]],
    phoneNumber: ['', Validators.required],
  });
  message: string | null = null;
  isError: boolean = false;

  constructor(private fb: FormBuilder, private http: HttpClient) {}

  onSubmit(): void {
    this.message = null;
    this.isError = false;

    if (this.patientForm.valid) {
      this.http.post('/api/v1/patients', this.patientForm.value, { responseType: 'text' }).subscribe({
        next: (response) => {
          this.message = response;
        },
        error: (err) => {
          this.isError = true;
          this.message = err.status === 403 ? 'Error: You do not have permission (Requires ADMIN role).' : 'An error occurred.';
        }
      });
    }
  }
}
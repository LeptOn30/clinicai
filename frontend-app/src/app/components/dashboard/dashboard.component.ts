import { Component } from '@angular/core';
import { AuthService } from '../../services/auth.service';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { User } from '../../models/user.model';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './dashboard.component.html',
})
export class DashboardComponent {
  user$: Observable<User | null>;

  constructor(public authService: AuthService) {
    this.user$ = this.authService.user$;
  }
}
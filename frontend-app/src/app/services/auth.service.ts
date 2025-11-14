import { Injectable, signal } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { Observable, tap, BehaviorSubject, of } from 'rxjs';
import { jwtDecode } from 'jwt-decode';
import { User, DecodedToken } from '../models/user.model';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private readonly TOKEN_KEY = 'token';
  private userSubject = new BehaviorSubject<User | null>(null);
  public user$ = this.userSubject.asObservable();

  constructor(private http: HttpClient, private router: Router) {
    this.loadInitialUser();
  }

  private loadInitialUser(): void {
    const token = this.getToken();
    if (token) {
      try {
        const decodedToken: DecodedToken = jwtDecode(token);
        if (decodedToken.exp * 1000 > Date.now()) {
          this.userSubject.next({
            username: decodedToken.sub,
            roles: decodedToken.roles.split(','),
          });
        } else {
          this.clearToken(); // Token is expired
        }
      } catch (error) {
        console.error("Failed to decode token", error);
        this.clearToken();
      }
    }
  }

  login(credentials: { username: string, password: string }): Observable<any> {
    return this.http.post<any>('/api/v1/auth/login', credentials).pipe(
      tap(response => {
        this.setToken(response.accessToken);
        this.loadInitialUser();
      })
    );
  }

  logout(): void {
    this.clearToken();
    this.userSubject.next(null);
    this.router.navigate(['/login']);
  }

  isAuthenticated(): boolean {
    return !!this.getToken();
  }

  getToken(): string | null {
    return localStorage.getItem(this.TOKEN_KEY);
  }

  private setToken(token: string): void {
    localStorage.setItem(this.TOKEN_KEY, token);
  }

  private clearToken(): void {
    localStorage.removeItem(this.TOKEN_KEY);
  }

  hasRole(role: string): boolean {
    const user = this.userSubject.getValue();
    return user?.roles.includes(role) ?? false;
  }
}
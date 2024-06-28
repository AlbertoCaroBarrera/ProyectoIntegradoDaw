import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, catchError, of, throwError } from 'rxjs';
import { User } from '../../clases/user';
import { environment } from '../../../environments/environment';
import { UserStateService } from './userState.service';


@Injectable({
  providedIn: 'root'
})
export class UserService {
  constructor(private http: HttpClient, private userStateService: UserStateService) { }

  getUser(): Observable<User | null> {
    const token = sessionStorage.getItem('token');

    if (token != null) {
      const headers = new HttpHeaders({
        'Authorization': `Bearer ${token}`
      });

      return this.http.get<User>(environment.urlHost + 'profesor/user', { headers: headers }).pipe(
        catchError(error => {
          console.error('Ocurrió un error:', error);
          return of(null); 
        })
      );
    } else {
      return of(null); 
    }
  }

  fetchAndUpdateUser(): void {
    this.getUser().subscribe({
      next: (userData: User | null) => {
        if (userData) {
          this.userStateService.updateUser(userData);
        } else {
          this.userStateService.clearUser();
        }
      },
      error: (error: any) => {
        console.error('Ocurrió un error al obtener el usuario:', error);
        this.userStateService.clearUser();
      }
    });
  }
}
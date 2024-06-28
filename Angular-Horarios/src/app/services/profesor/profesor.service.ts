import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../../environments/environment';
import { User } from '../../clases/user';
import { Observable, tap } from 'rxjs';
import { UserStateService } from '../user/userState.service';

@Injectable({
  providedIn: 'root'
})
export class ProfesorService {
  token = sessionStorage.getItem('token');

  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${this.token}`
    })
  };

  constructor(private http:HttpClient, private userStateService: UserStateService) { }

  getProfesores()
  {
    return this.http.get<User[]>(
       `${environment.urlHost}profesor/mostrar`
    )
  }

  addProfesores(profesor:User)
  {
    return this.http.post<User>(
      `${environment.urlHost}auth/register`,profesor,this.httpOptions 
    )
  }

  updateProfesores(profesor: Partial<User>): Observable<User> {
    return this.http.put<User>(`${environment.urlHost}profesor/update/${profesor.professor_cod}`, profesor, this.httpOptions).pipe(
      tap((updatedUser: User) => {
        this.userStateService.updateUser(updatedUser);
      })
    );
  }
  
  deleteProfesor(professor_cod : String){
    return  this.http.delete(`${environment.urlHost}profesor/delete/${professor_cod}`,this.httpOptions)
  }


}

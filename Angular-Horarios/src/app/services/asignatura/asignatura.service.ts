import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../../environments/environment';
import { HttpHeaders } from '@angular/common/http';
import { Asignatura } from '../../clases/asignatura';

@Injectable({
  providedIn: 'root'
})
export class AsignaturaService {
  token = sessionStorage.getItem('token');

  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${this.token}`
    })
  };

  constructor(private http:HttpClient) { }

  getAsignaturas()
  {
    return this.http.get<Asignatura[]>(
       `${environment.urlHost}asignatura/mostrar`
    )
  }

  addAsignaturas(asignatura:Asignatura)
  {
    return this.http.post<Asignatura>(
      `${environment.urlHost}asignatura/crear`,asignatura,this.httpOptions 
    )
  }

  updateAsignaturas(asignatura:Asignatura)
  {
    return this.http.put<Asignatura>(
      `${environment.urlHost}asignatura/update/${asignatura.asignaturaCod}`,asignatura,this.httpOptions
    )
  }

  deleteAsignatura(asignaturaCod : String){
    return  this.http.delete(`${environment.urlHost}asignatura/delete/${asignaturaCod}`,this.httpOptions)
  }


}

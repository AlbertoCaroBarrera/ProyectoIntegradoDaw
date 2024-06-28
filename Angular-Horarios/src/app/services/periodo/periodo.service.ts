import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../../environments/environment';
import { Periodo } from '../../clases/periodo';

@Injectable({
  providedIn: 'root'
})
export class PeriodoService {
  token = sessionStorage.getItem('token');

  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${this.token}`
    })
  };

  constructor(private http:HttpClient) { }

  getPeriodos()
  {
    return this.http.get<Periodo[]>(
       `${environment.urlHost}periodo/mostrar`
    )
  }

  addPeriodos(periodo:Periodo)
  {
    return this.http.post<Periodo>(
      `${environment.urlHost}periodo/crear`,periodo,this.httpOptions 
    )
  }

  updatePeriodos(periodo:Periodo)
  {
    return this.http.put<Periodo>(
      `${environment.urlHost}periodo/update/${periodo.id}`,periodo,this.httpOptions
    )
  }

  deletePeriodo(id : number){
    return  this.http.delete(`${environment.urlHost}periodo/delete/${id}`,this.httpOptions)
  }


}

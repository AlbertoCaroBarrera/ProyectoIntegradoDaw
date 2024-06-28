import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../../environments/environment';
import { Aula } from '../../clases/aula';

@Injectable({
  providedIn: 'root'
})
export class AulaService {
  token = sessionStorage.getItem('token');

  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${this.token}`
    })
  };

  constructor(private http:HttpClient) { }

  getAulas()
  {
    return this.http.get<Aula[]>(
       `${environment.urlHost}aula/mostrar`
    )
  }

  addAulas(aula:Aula)
  {
    return this.http.post<Aula>(
      `${environment.urlHost}aula/crear`,aula,this.httpOptions 
    )
  }

  updateAulas(aula:Aula)
  {
    return this.http.put<Aula>(
      `${environment.urlHost}aula/update/${aula.aulaCod}`,aula,this.httpOptions
    )
  }

  deleteAula(aulaCod : String){
    return  this.http.delete(`${environment.urlHost}aula/delete/${aulaCod}`,this.httpOptions)
  }


}

import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Grupo } from '../../clases/grupo';
import { environment } from '../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class GrupoService {
  token = sessionStorage.getItem('token');

  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${this.token}`
    })
  };

  constructor(private http:HttpClient) { }

  getGrupos()
  {
    return this.http.get<Grupo[]>(
       `${environment.urlHost}grupo/mostrar`
    )
  }

  addGrupos(grupo:Grupo)
  {
    return this.http.post<Grupo>(
      `${environment.urlHost}grupo/crear`,grupo,this.httpOptions 
    )
  }

  updateGrupos(grupo:Grupo)
  {
    return this.http.put<Grupo>(
      `${environment.urlHost}grupo/update/${grupo.grupoCod}`,grupo,this.httpOptions
    )
  }

  deleteGrupo(grupoCod : String){
    return  this.http.delete(`${environment.urlHost}grupo/delete/${grupoCod}`,this.httpOptions)
  }


}

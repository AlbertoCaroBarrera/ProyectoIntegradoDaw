import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Franja } from '../../clases/franja';
import { environment } from '../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class FranjaService {
  token = sessionStorage.getItem('token');

  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${this.token}`
    })
  };

  constructor(private http:HttpClient) { }

  getFranjas()
  {
    return this.http.get<Franja[]>(
       `${environment.urlHost}franja/mostrar`
    )
  }

  addFranjas(franja:Franja)
  {
    return this.http.post<Franja>(
      `${environment.urlHost}franja/crear`,franja,this.httpOptions 
    )
  }

  updateFranjas(franja:Franja)
  {
    return this.http.put<Franja>(
      `${environment.urlHost}franja/update/${franja.id}`,franja,this.httpOptions
    )
  }

  deleteFranja(id : number){
    return  this.http.delete(`${environment.urlHost}franja/delete/${id}`,this.httpOptions)
  }


}

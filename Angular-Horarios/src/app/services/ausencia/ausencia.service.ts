import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Ausencia } from '../../clases/ausencia';
import { environment } from '../../../environments/environment';
import { Observable } from 'rxjs';
import { AusenciaDetalle } from '../../clases/ausenciaDetalle';


@Injectable({
  providedIn: 'root'
})
export class AusenciaService {
  token = sessionStorage.getItem('token');

  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${this.token}`
    })
  };

  constructor(private http:HttpClient) { }

  getAusencias()
  {
    return this.http.get<Ausencia[]>(
       `${environment.urlHost}ausencia/mostrar`
    )
  }

  addAusencias(ausencia:Ausencia)
  {
    return this.http.post<Ausencia>(
      `${environment.urlHost}ausencia/crear`,ausencia,this.httpOptions 
    )
  }

  updateAusencias(ausencia:Ausencia)
  {
    return this.http.put<Ausencia>(
      `${environment.urlHost}ausencia/update/${ausencia.id}`,ausencia,this.httpOptions
    )
  }

  deleteAusencia(id : number){
    return  this.http.delete(`${environment.urlHost}ausencia/delete/${id}`,this.httpOptions)
  }


  getAusenciasDetalles(fecha: string): Observable<AusenciaDetalle[]> {
    return this.http.get<AusenciaDetalle[]>(`${environment.urlHost}ausencia/ausencias?fecha=${fecha}`, this.httpOptions);
  }
}

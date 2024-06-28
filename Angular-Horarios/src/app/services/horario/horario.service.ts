import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../../environments/environment';
import { Horario } from '../../clases/horarios';

@Injectable({
  providedIn: 'root'
})
export class HorarioService {
  token = sessionStorage.getItem('token');

  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${this.token}`
    })
  };

  constructor(private http:HttpClient) { }

  getHorarios()
  {
    return this.http.get<Horario[]>(
       `${environment.urlHost}horario/mostrar`
    )
  }

  addHorarios(horario:Horario)
  {
    const horarioFormateado = {
      
        "profesor" : {
            "professor_cod" : horario.profesor.professor_cod
        },
        "dia": horario.dia,
        "franja": {
            "id": horario.franja.id
        },
        "asignatura": {
            "asignaturaCod": horario.asignatura.asignaturaCod
        },
        "aula": {
            "aulaCod": horario.aula.aulaCod
        },
        "grupo": {
            "grupoCod": horario.grupo.grupoCod
        },
        "periodo": {
            "id": horario.periodo.id
        }
    
    }
    return this.http.post<Horario>(
      `${environment.urlHost}horario/crear`,horarioFormateado,this.httpOptions 
    )
  }

  updateHorarios(horario:Horario)
  {
    const horarioFormateado = {
      "profesor" : {
          "professor_cod" : horario.profesor.professor_cod
      },
      "dia": horario.dia,
      "franja": {
          "id": horario.franja.id
      },
      "asignatura": {
          "asignaturaCod": horario.asignatura.asignaturaCod
      },
      "aula": {
          "aulaCod": horario.aula.aulaCod
      },
      "grupo": {
          "grupoCod": horario.grupo.grupoCod
      },
      "periodo": {
          "id": horario.periodo.id
      }
  
  }
    
    return this.http.put<Horario>(
      `${environment.urlHost}horario/update/${horario.id}`,horarioFormateado,this.httpOptions
    )
  }

  deleteHorario(id : number | undefined){
    return  this.http.delete(`${environment.urlHost}horario/delete/${id}`,this.httpOptions)
  }


}

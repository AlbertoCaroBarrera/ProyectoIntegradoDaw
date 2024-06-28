import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class XmlService {
  
  private apiUrl = environment.urlHost + 'xml/upload';
  private apiUrlReset = environment.urlHost + 'reset/database';

  constructor(private http: HttpClient) { }

  uploadXml(xml: FormData) {
    const token = sessionStorage.getItem('token');
    const headers = new HttpHeaders({ 

      'Authorization': 'Bearer ' + token // Si tu servidor utiliza JWT para autenticación, incluye el token JWT aquí
    });
    return this.http.post(this.apiUrl, xml, { headers, responseType: 'text' });
  }
  
  resetDatabase() {
    const token = sessionStorage.getItem('token');
    const headers = new HttpHeaders({ 
      'Content-Type': 'application/json',
      'Authorization': 'Bearer ' + token // Si tu servidor utiliza JWT para autenticación, incluye el token JWT aquí
    });
    return this.http.delete(this.apiUrlReset, { headers, responseType: 'text' });
  }
  

}

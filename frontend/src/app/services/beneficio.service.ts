import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Beneficio, TransferenciaDTO } from '../models/beneficio.model';

@Injectable({
  providedIn: 'root'
})
export class BeneficioService {
  private apiUrl = 'http://localhost:8080/api/v1/beneficios';

  constructor(private http: HttpClient) {}

  listarTodos(): Observable<Beneficio[]> {
    return this.http.get<Beneficio[]>(this.apiUrl);
  }

  transferir(dto: TransferenciaDTO): Observable<string> {
    return this.http.post(
      `${this.apiUrl}/transferir`, 
      dto, 
      { responseType: 'text' }
    );
  }
}
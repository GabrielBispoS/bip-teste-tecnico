import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { retry, delay } from 'rxjs/operators';
import { Beneficio, TransferenciaDTO } from '../models/beneficio.model';

@Injectable({
  providedIn: 'root'
})
export class BeneficioService {
  private apiUrl = 'http://localhost:8080/api/beneficios';

  constructor(private http: HttpClient) {}

  listarTodos() {
  return this.http.get<Beneficio[]>(this.apiUrl).pipe(
    retry({ count: 5, delay: 2000 })
  );
}

  transferir(dto: TransferenciaDTO): Observable<string> {
    return this.http.post(
      `${this.apiUrl}/transferir`, 
      dto, 
      { responseType: 'text' }
    );
  }
}
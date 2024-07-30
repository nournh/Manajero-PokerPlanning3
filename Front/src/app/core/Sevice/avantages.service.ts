import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import { Observable } from 'rxjs';
import { Avantages } from '../Model/avantages';

@Injectable({
  providedIn: 'root'
})
export class AvantagesService {

  private baseUrl = 'http://localhost:8081/api'; // L'URL de base de votre API back-end

  constructor(private http: HttpClient) { }

  createAvantages(section: Avantages): Observable<Avantages> {
    return this.http.post<Avantages>(`${this.baseUrl}/Avantages`, section);
  }
  getAllAvantages(): Observable<Avantages[]> {
    return this.http.get<Avantages[]>(`${this.baseUrl}/Avantages`);
  }
 
  updateAvantages(id: string, section: Avantages): Observable<Avantages> {
    const url = `${this.baseUrl}/Avantages/${id}`;
    return this.http.put<Avantages>(url, section);
  }

 
  deleteAvantages(id: string): Observable<void> {
    const url = `${this.baseUrl}/Avantages/${id}`;
    return this.http.delete<void>(url);
  }
  
}

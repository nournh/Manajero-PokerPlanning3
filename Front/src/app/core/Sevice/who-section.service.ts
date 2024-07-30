import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { WhoSection } from '../Model/who-section';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class WhoSectionService {

  private baseUrl = 'http://localhost:8081/api'; // L'URL de base de votre API back-end

  constructor(private http: HttpClient) { }

  createWho(section: WhoSection): Observable<WhoSection> {
    return this.http.post<WhoSection>(`${this.baseUrl}/who-sections`, section);
  }
  getAllWho(): Observable<WhoSection[]> {
    return this.http.get<WhoSection[]>(`${this.baseUrl}/who-sections`);
  }
  updateWho(id: string, section: WhoSection): Observable<WhoSection> {
    const url = `${this.baseUrl}/who-sections/${id}`;
    return this.http.put<WhoSection>(url, section);
  }
  deleteWho(id: string): Observable<void> {
    const url = `${this.baseUrl}/who-sections/${id}`;
    return this.http.delete<void>(url);
  }
  
}

import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { WhySection } from '../Model/why-section';
import { HttpClient } from '@angular/common/http';
@Injectable({
  providedIn: 'root'
})
export class WhySectionServiceService {
  private baseUrl = 'http://localhost:8081/api'; // L'URL de base de votre API back-end

  constructor(private http: HttpClient) { }

  createWhy(section: WhySection): Observable<WhySection> {
    return this.http.post<WhySection>(`${this.baseUrl}/why-sections`, section);
  }
  getAllWhy(): Observable<WhySection[]> {
    return this.http.get<WhySection[]>(`${this.baseUrl}/why-sections`);
  }
  updateWhy(id: string, section: WhySection): Observable<WhySection> {
    const url = `${this.baseUrl}/why-sections/${id}`;
    return this.http.put<WhySection>(url, section);
  }
  deleteWhy(id: string): Observable<void> {
    const url = `${this.baseUrl}/why-sections/${id}`;
    return this.http.delete<void>(url);
  }
  
}

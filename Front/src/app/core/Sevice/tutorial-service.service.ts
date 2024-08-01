import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Tutorial } from '../Model/tutorial';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class TutorialServiceService {
  private baseUrl = 'http://localhost:8081/api'; // L'URL de base de votre API back-end

  constructor(private http: HttpClient) { }

  createTutorial(section: Tutorial): Observable<Tutorial> {
    return this.http.post<Tutorial>(`${this.baseUrl}/tutorials`, section);
  }
  getAllTutorial(): Observable<Tutorial[]> {
    return this.http.get<Tutorial[]>(`${this.baseUrl}/tutorials`);
  }
 
  updateTutorial(id: string, section: Tutorial): Observable<Tutorial> {
    const url = `${this.baseUrl}/tutorials/${id}`;
    return this.http.put<Tutorial>(url, section);
  }

 
  deleteTutorial(id: string): Observable<void> {
    const url = `${this.baseUrl}/tutorials/${id}`;
    return this.http.delete<void>(url);
  }
  
}

import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import { WhatSection } from '../Model/what-section';
import { Observable } from 'rxjs';


@Injectable({
  providedIn: 'root'
})
export class WhatSectionServiceService {

  private baseUrl = 'http://localhost:8081/api'; // L'URL de base de votre API back-end

  constructor(private http: HttpClient) { }
  createSection(section: WhatSection): Observable<WhatSection> {
    return this.http.post<WhatSection>(`${this.baseUrl}/what-sections`, section);
  }
  getAllSections(): Observable<WhatSection[]> {
    return this.http.get<WhatSection[]>(`${this.baseUrl}/what-sections`);
  }
  updateSection(id: string, section: WhatSection): Observable<WhatSection> {
    const url = `${this.baseUrl}/what-sections/${id}`;
    return this.http.put<WhatSection>(url, section);
}
deleteSection(id: string): Observable<void> {
  const url = `${this.baseUrl}/what-sections/${id}`;
  return this.http.delete<void>(url);
}


}

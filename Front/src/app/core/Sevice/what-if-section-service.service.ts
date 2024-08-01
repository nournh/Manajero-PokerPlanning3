import { Injectable } from '@angular/core';

import { Observable } from 'rxjs';
import { WhatIfSection } from '../Model/what-if-section';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class WhatIfSectionServiceService {

  private baseUrl = 'http://localhost:8081/api'; // L'URL de base de votre API back-end

  constructor(private http: HttpClient) { }

  createWhatif(section: WhatIfSection): Observable<WhatIfSection> {
    return this.http.post<WhatIfSection>(`${this.baseUrl}/what-if-sections`, section);
  }
  getAllWhatif(): Observable<WhatIfSection[]> {
    return this.http.get<WhatIfSection[]>(`${this.baseUrl}/what-if-sections`);
  }
  updateWhatif(id: string, section: WhatIfSection): Observable<WhatIfSection> {
    const url = `${this.baseUrl}/what-if-sections/${id}`;
    return this.http.put<WhatIfSection>(url, section);
  }
  deletewhatif(id: string): Observable<void> {
    const url = `${this.baseUrl}/what-if-sections/${id}`;
    return this.http.delete<void>(url);
  }
  
}

import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Project } from '../Model/project';
import { User } from '../Model/user';


import { id } from '@swimlane/ngx-charts';

@Injectable({
  providedIn: 'root'
})
export class ProjectServiceService {
  private baseUrl ='http://localhost:8081/api/projects'; // Remplacez par l'URL de votre backend Spring


  constructor(private http: HttpClient) { }
    //!!!!!!!importer le service d'authentification user au niveau du constructor
    
  getAllProjects(): Observable<Project[]> {
    return this.http.get<Project[]>(`${this.baseUrl}/getAllProjects`);
  }
  addProjectAndAssignProjectToUser(project: Project, id: string): Observable<Project> {
    return this.http.post<Project>(`${this.baseUrl}/addPAndAssign/${id}`, project);
  }
  
  
// Méthode pour supprimer un projet par son ID
deleteProjectById(id: number) {
  return this.http.delete(`${this.baseUrl}/${id}`);
}

// Méthode pour mettre à jour un projet
updateProject(project: Project): Observable<Project> {
  return this.http.put<Project>(`${this.baseUrl}/${id}`, project);
}
  // Méthode pour récupérer un projet par son ID
  getProjectById(id: string): Observable<Project> {
    return this.http.get<Project>(`${this.baseUrl}/${id}`);
  }

  //Assigner developpeurs au projet
  assignDevelopersToProject(idProject: number, idDevelopers: number[]): Observable<any> {
    return this.http.put<any>(`${this.baseUrl}/project/assignProjectToDeveloper/${idProject}/${idDevelopers.join(',')}`, {});
  }
    // Méthode pour récupérer tous les développeurs
    getAllDevelopers(projectId: String): Observable<User[]> {
      return this.http.get<User[]>(`${this.baseUrl}/project/getAllDevelopers/${projectId}`);
    }

    //methode pour recupere les developpeurs assigné a un projet specifique
    getDevelopersByProjectId(projectId: number): Observable<User[]> {
      return this.http.get<User[]>(`${this.baseUrl}/project/getDevelopersByProjectId/${projectId}`);
    }
    
   


    //statistiques
    getProjectStats(): Observable<any> {
      return this.http.get<any>(`${this.baseUrl}/project/project-stats`);
    }

    //recherche
    searchProjects(params: any): Observable<Project[]> {
      return this.http.get<Project[]>(`${this.baseUrl}/project/search`, { params });
    }

    //generation de doc pdf
    generatePDF(): Observable<Blob> {
      return this.http.get(`${this.baseUrl}/project/pdf`, {
        responseType: 'blob',
      });
    }


     // Méthode pour importer un projet à partir d'un fichier Excel
 /* importProjectFromExcel(file: FormData): Observable<any> {
    //const userId = this.authService.getUserId(); // Mettez à jour cette ligne en fonction de la façon dont vous récupérez l'ID du chef de projet
    return this.http.post<any>(`${this.baseUrl}/project/import`, file);
  }*/
  importProjectFromExcel(file: FormData, userId: number): Observable<any> {

    return this.http.post<any>(`${this.baseUrl}/project/import/${userId}`, file);
  }

  getImportedProjects(userId: number): Observable<Project[]> {
    return this.http.get<Project[]>(`${this.baseUrl}/project/importedProjects/${userId}`);
  }
  }











  
   /* generatePDFWithTable(projects: any[]): void {
      let doc: any = new jsPDF();
      const headers = [['Project ID', 'Project Name', 'End Date', 'Domain', 'Budget', 'Status']];
  
      const data = projects.map(project => [
        project.id,
        project.name,
        project.endDate,
        project.domain,
        project.badget,
        project.statusProject
      ]);
  
      doc.text('List of Projects', 10, 10);
      doc.autoTable({
        startY: 20,
        head: headers,
        body: data
      });
  
      doc.save('projects.pdf');
    }*/


   /* generatePDFWithTable(projects: any[]): void {
      let doc: any = new jsPDF();
      let tableData:any = [];
      const headers = ['Project ID', 'Project Name', 'End Date', 'Domain', 'Budget', 'Status'];
    
      projects.forEach(project => {
        const rowData = [
          project.id,
          project.name,
          project.endDate,
          project.domain,
          project.badget,
          project.statusProject
        ];
        tableData.push(rowData);
      });
    
      doc.autoTable({
        head: [headers],
        body: tableData,
        theme: 'grid' // Choisissez un thème pour le tableau, par exemple : 'striped', 'grid', 'plain', etc.
      });
    
      doc.save('projects.pdf');
    }*/
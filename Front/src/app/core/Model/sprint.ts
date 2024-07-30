import { Project } from "./project";

export class Sprint {
    id!: number;
    name!: string;
    startDate!: string; // Vous pouvez utiliser le type Date si nécessaire
    endDate!: string; // Vous pouvez utiliser le type Date si nécessaire
    duration!: number;
    objective!: string;
    project!: Project; // Assurez-vous d'avoir une interface ou une classe Project définie
   
}
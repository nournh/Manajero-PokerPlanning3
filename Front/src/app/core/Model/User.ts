import { Project } from "./project";

export class User {
    id!: number;
    firstname!: string;
    lastname!: string;
    email!: string;
    password!: string;
    picture!: string;
    role!: Role;
    projectSet!: Project[];
   // participationSet!: Participation[];
   // badge: !Badge;
  }
  
  export enum Role {
    ADMINISTRATOR = 'ADMINISTRATOR',
    PROJECTMANAGER = 'PROJECTMANAGER',
    DEVELOPER = 'DEVELOPER'
  }
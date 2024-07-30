import { Sprint } from "./sprint";
export enum StatusUs { INPROGRESS,
    PANDING,
    COMPLETED
}
export class UserStory {
    id!: number;
    description!: string;
    duration!: number;
    priority!: number;
    startDate!: string;
    endDate!: string;
    sprint!: Sprint;
    status!: StatusUs;
   ;

}
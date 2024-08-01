export enum Complexity {
    One = '1',
    Two = '2',
    Three = '3',
    Five ='5',
    Eight ='8',
    Thirteen='13'
  }

export class Card {
    id!:number;
    color!:string;
    complexity!:Complexity;
    idUser!:number;
    
}
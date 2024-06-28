import { User } from "./user";

export class Ausencia {
  id: number | null;
  fechaAusencia: Date;
  comentario: string;
  profesor: {
    professor_cod : String
  };

  constructor() {
    this.id = 0;
    this.fechaAusencia = new Date();
    this.comentario = '';
    this.profesor = new User();
  }
}

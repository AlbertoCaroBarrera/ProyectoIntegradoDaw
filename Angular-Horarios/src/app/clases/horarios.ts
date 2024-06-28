import { Observable } from "rxjs";
import { Asignatura } from "./asignatura";
import { Aula } from "./aula";
import { Franja } from "./franja";
import { Grupo } from "./grupo";
import { Periodo } from "./periodo";
import { User } from "./user";

export class Horario {
    id?:number;
    profesor!: User;
    dia!: string;
    franja!: Franja;
    asignatura!: Asignatura;
    aula!: Aula;
    grupo!: Grupo;
    periodo!: Periodo;

    constructor() {

    }
}

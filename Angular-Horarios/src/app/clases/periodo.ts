export class Periodo {
    id: number;
    descripcion: string;
    desdeFecha: Date;;
    hastaFecha: Date;

    constructor() {
        this.id = 0;
        this.descripcion = '';
        this.desdeFecha = new Date();
        this.hastaFecha = new Date();
    }
}

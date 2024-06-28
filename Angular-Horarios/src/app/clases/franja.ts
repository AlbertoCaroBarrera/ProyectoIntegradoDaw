export class Franja {
    id!: number | null;
    descripcion: string;
    horaDesde: String;
    horaHasta: String;

    constructor() {
        this.descripcion = '';
        this.horaDesde = "00:00:00";
        this.horaHasta = "00:00:00";
    }
}

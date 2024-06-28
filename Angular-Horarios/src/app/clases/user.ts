export class User {
    professor_cod: string;
    nombre: string;
    username: string;
    rol: Role;

    constructor() {
        this.professor_cod = '';
        this.nombre = '';
        this.username = '';
        this.rol = Role.PROFESOR;
    }


}
export enum Role {
    ADMIN = 'ADMIN',
    PROFESOR = 'PROFESOR'
}
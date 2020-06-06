import { User } from './user';

export class Comprador extends User {
    public sobrenome: string;
    public dividas: any[] = [];
    public creditos: any[] = [];

    constructor(nome: string, username: string, senha: string){
        super(nome, username, senha);
        this.tipo = "COMPRADOR"
    }
}

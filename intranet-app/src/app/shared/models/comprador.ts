import { UCType } from './../util/enuns-type.enum';
import { User } from './user';

export class Comprador extends User {
    public sobrenome: string;
    public devedor = false;
    public dividas: any[] = [];
    public creditos: any[] = [];

    constructor(nome: string, username: string, senha: string){
        super(nome, username, senha);
        this.tipo = UCType.COMPRADOR
    }
}

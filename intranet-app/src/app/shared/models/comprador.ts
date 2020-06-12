import { UCType } from './../util/enuns-type.enum';
import { User } from './user';

export class Comprador extends User {

    public sobrenome: string;
    public devedor = false;
    public dividaTotal: number;
    public creditoTotal: number;
    public dividas: any[] = [];
    public creditos: any[] = [];

    constructor(id: number, nome: string, username: string, senha: string){
        super(id, nome, username, senha);
        this.tipo = UCType.COMPRADOR
    }
}

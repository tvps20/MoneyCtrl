import { EntityType } from './../util/enuns-type.enum';
import { User } from './user';
import { Divida } from './divida';

export class Comprador extends User {

    public sobrenome: string;
    public devedor: boolean;
    public totalDividas: number;
    public totalCreditos: number;
    public totalPagamentos: number;
    public dividas: Divida[] = [];
    public creditos: any[] = [];

    constructor(id: number, nome: string, username: string, senha: string){
        super(id, nome, username, senha);
        this.tipo = EntityType.COMPRADOR
    }
}

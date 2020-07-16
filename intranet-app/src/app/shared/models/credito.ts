import { BaseEntity } from './baseEntity';
import { EntityType } from '../util/enuns-type.enum';

export class Credito extends BaseEntity {

    public valor: number;
    public data: string;
    public disponivel: boolean;
    public descricao: string;
    public compradorId: number;

    constructor(id: number, valor: number, descricao: string, compradorId: number){
        super(id);
        this.valor = valor;
        this.descricao = descricao;
        this.compradorId = compradorId;
        this.tipo = EntityType.CREDITO;
    }
}

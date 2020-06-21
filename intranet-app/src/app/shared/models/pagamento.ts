import { BaseEntity } from './baseEntity';
import { EntityType } from '../util/enuns-type.enum';

export class Pagamento extends BaseEntity {

    public valor: number;
    public data: string;
    public observacao: string;
    public dividaId: number;

    constructor(id: number, valor: number, dividaId: number){
        super(id);
        this.valor = valor;
        this.dividaId = dividaId;
        this.tipo = EntityType.PAGAMENTO;
    }
}

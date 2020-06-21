import { Pagamento } from './pagamento';
import { BaseEntity } from './baseEntity';
import { EntityType } from '../util/enuns-type.enum';

export class Divida extends BaseEntity {

    public faturaId: number
    public valorDivida: number;
    public descricao: string;
    public dataDivida: string;
    public paga = false;
    public compradorId: number;
    public pagamentos: Pagamento[] = [];
    public totalPagamentos: number;
    public compradorNome: string;

    constructor(id: number, valorDivida: number, descricao: string, dataDivida: string, compradorId: number){
        super(id);
        this.valorDivida = valorDivida;
        this.descricao = descricao;
        this.dataDivida = dataDivida;
        this.compradorId = compradorId;
        this.tipo = EntityType.DIVIDA;
    }
}

import { Comprador } from './comprador';
import { Fatura } from './fatura';
import { BaseEntity } from './baseEntity';
import { EntityType } from '../util/enuns-type.enum';

export class Divida extends BaseEntity {

    public faturaId: number
    public valorDivida: number;
    public descricao: string;
    public dataDivida: Date;
    public paga = false;
    public compradorId: number;
    public pagamentos: any[] = [];
    public totalPagamentos: number;

    constructor(id: number, valorDivida: number, descricao: string, dataDivida: Date, compradorId: number){
        super(id);
        this.valorDivida = valorDivida;
        this.descricao = descricao;
        this.dataDivida = dataDivida;
        this.compradorId = compradorId;
        this.tipo = EntityType.DIVIDA;
    }
}

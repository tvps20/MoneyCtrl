import { StatusType, EntityType } from './../util/enuns-type.enum';
import { BaseEntity } from './baseEntity';

export class Cota extends BaseEntity {

    public valor: number;
    public compradorId: number;
    public lancamentoId: number;
    public lancamento: StatusType;

    constructor(id: number, valor: number, compradorId: number, lancamentoId: number){
        super(id);
        this.valor = valor;
        this.compradorId = compradorId;
        this.lancamentoId = lancamentoId;
        this.tipo = EntityType.COTA;
    }
}

export class CotaFatura extends BaseEntity {
    public valorTotal: number;
    public compradorId: number;
    public compradorNome: string;
    public cotas: Cota[] = [];

    constructor(id: number, compradorId: number, compradorNome: string){
        super(id);
        this.compradorId = compradorId;
        this.compradorNome = compradorNome;
        this.tipo = EntityType.COTA;
    }
}

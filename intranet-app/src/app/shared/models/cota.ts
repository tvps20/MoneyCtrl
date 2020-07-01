import { StatusType, EntityType } from './../util/enuns-type.enum';
import { BaseEntity } from './baseEntity';

export class Cota extends BaseEntity {

    public valor: number;
    public compradorId: number;
    public lancamentoId: number;
    public lancamento: StatusType;

    constructor(id: number, valor: number, compradorId: number){
        super(id);
        this.valor = valor;
        this.compradorId = compradorId;
        this.tipo = EntityType.COTA;
    }
}

export class CotaFatura {
    public valorTotal: number;
    public compradorId: number;
    public compradorNome: string;
    public cotas: Cota[] = [];

    constructor(compradorId: number, compradorNome: string){
        this.compradorId = compradorId;
        this.compradorNome = compradorNome;
    }
}

export class CotaCartao {
    public valorTotal: number;
    public cartaoId: number;
    public cartaoNome: string;
    public faturaMes: string;
    public cotas: CotaFatura[] = [];

    constructor(cartaoId: number, cartaoNome: string){
        this.cartaoId = cartaoId;
        this.cartaoNome = cartaoNome;
    }
}

export class CotaComprador {
    public valorTotal: number;
    public cartaoId: number;
    public cartaoNome: string;
    public cotas: Cota[] = [];

    constructor(cartaoId: number, cartaoNome: string){
        this.cartaoId = cartaoId;
        this.cartaoNome = cartaoNome;
    }
}

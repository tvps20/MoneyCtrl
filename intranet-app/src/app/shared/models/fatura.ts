import { StatusType, EntityType } from './../util/enuns-type.enum';
import { BaseEntity } from './baseEntity';

export class Fatura extends BaseEntity {

    public vencimento: string;
    public observacao: string;
    public status: StatusType;
    public mesReferente: string;
    public cartaoId: number;
    public lancamentos: any[];

    constructor(id: number, vencimento: string, cartaoId: number, observacao: string){
        super(id);
        this.vencimento = vencimento;
        this.cartaoId = cartaoId;
        this.observacao = observacao;
        this.status = StatusType.ABERTA;
        this.tipo = EntityType.FARURA;
    }
}

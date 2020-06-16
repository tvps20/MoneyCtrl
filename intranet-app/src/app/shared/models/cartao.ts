import { Bandeira } from './bandeira';
import { BaseEntity } from './baseEntity';
import { EntityType } from '../util/enuns-type.enum';
import { Fatura } from './fatura';

export class Cartao extends BaseEntity {

    public nome: string;
    public bandeira: Bandeira;
    public faturas: Fatura[] = [];

    constructor(id: number, nome: string){
        super(id);
        this.nome = nome;
        this.tipo = EntityType.CARTAO;
    }
}
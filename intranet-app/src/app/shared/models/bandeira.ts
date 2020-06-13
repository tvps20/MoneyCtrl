import { BaseEntity } from './baseEntity';
import { Cartao } from './cartao';
import { EntityType } from '../util/enuns-type.enum';

export class Bandeira extends BaseEntity {

    public nome: string;
    public cartoes: Cartao[] = [];

    constructor(id: number, nome: string){
        super(id);
        this.nome = nome;
        this.tipo = EntityType.BANDEIRA;
    }
}

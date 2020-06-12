import { Bandeira } from './bandeira';
import { BaseEntity } from './baseEntity';

export class Cartao extends BaseEntity {

    public nome: string;
    public bandeira: Bandeira;
    public faturas: string[] = [];

    constructor(id: number, nome: string){
        super(id);
        this.nome = nome;
    }
}

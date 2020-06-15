import { EntityType, AcessoType, RolesType } from './../util/enuns-type.enum';
import { BaseEntity } from './baseEntity';

export class User extends BaseEntity{

    public nome: string;
    public username: string;
    public email: string;
    public verificaoEmail: Date;
    public password: string;
    public acesso: AcessoType[] = [];
    public roles: RolesType[] = [];

    constructor(id: number, nome: string, username: string, password: string){
        super(id);
        this.nome = nome;
        this.username = username;
        this.password = password;
        this.tipo = EntityType.USER;
        this.acesso.push(AcessoType.USERNAME);
        this.roles.push(RolesType.USER);
    }
}

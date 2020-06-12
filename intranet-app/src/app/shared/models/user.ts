import { UCType, AcessoType, RolesType } from './../util/enuns-type.enum';
import { BaseEntity } from './baseEntity';

export class User extends BaseEntity{

    public nome: string;
    public username: string;
    public email: string;
    public verificaoEmail: Date;
    public password: string
    public tipo: string;
    public acesso: string[] = [];
    public roles: string[] = [];

    constructor(id: number, nome: string, username: string, password: string){
        super(id);
        this.nome = nome;
        this.username = username;
        this.password = password;
        this.tipo = UCType.USER;
        this.acesso.push(AcessoType.USERNAME);
        this.roles.push(RolesType.USER);
    }
}

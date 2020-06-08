import { UCType, AcessoType, RolesType } from './../util/enuns-type.enum';
export class User {

    public id: number;
    public createdAt: Date;
    public updatedAt: Date;
    public ativo = true;

    public nome: string;
    public username: string;
    public email: string;
    public verificaoEmail: Date;
    public senha: string
    public tipo: string;
    public acesso: string[] = [];
    public roles: string[] = [];

    constructor(nome: string, username: string, senha: string){
        this.nome = nome;
        this.username = username;
        this.senha = senha;
        this.tipo = UCType.USER;
        this.acesso.push(AcessoType.USERNAME);
        this.roles.push(RolesType.USER);
    }
}

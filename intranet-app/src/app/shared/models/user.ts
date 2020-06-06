export class User {

    public id: number;
    public createAt: Date;
    public updateAt: Date;
    public ativo: boolean;

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
        this.tipo = "USER";
        this.acesso.push("USERNAME");
        this.roles.push("USER");
    }
}

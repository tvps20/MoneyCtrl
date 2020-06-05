export interface Comprador {
    id: number,
    email: string,
    username: string,
    acesso: string,
    createAt: Date,
    verificaro: Date,
    ativo: boolean,
    nome: string,
    sobrenome: string,
    perfil: string,
    dividas: [],
    creditos: [],
    roles: [ "USER" ]
}

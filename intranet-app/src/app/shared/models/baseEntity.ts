export abstract class BaseEntity {

    public id: number;
    public createdAt: Date;
    public updatedAt: Date;
    public ativo = true;

    constructor(id: number){
        this.id = id;
    }
}

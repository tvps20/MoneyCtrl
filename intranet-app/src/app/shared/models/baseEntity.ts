import { EntityType } from './../util/enuns-type.enum';
export abstract class BaseEntity {

    public id: number;
    public createdAt: Date;
    public updatedAt: Date;
    public ativo = true;
    public tipo: EntityType;

    constructor(id: number){
        this.id = id;
    }
}

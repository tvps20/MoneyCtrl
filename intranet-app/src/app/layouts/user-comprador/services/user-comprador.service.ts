import { EntityType } from './../../../shared/util/enuns-type.enum';
import { CompradorService } from './comprador.service';
import { UserService } from './user.service';
import { FormGroup } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

import { Comprador } from '../../../shared/models/comprador';
import { User } from 'src/app/shared/models/user';

@Injectable({
    providedIn: 'root'
})
export class UserCompradorService {

    constructor(protected http: HttpClient,
        private userService: UserService,
        private compradorService: CompradorService) { }


    public listAllUsers(){
        return this.userService.listAll();
    }

    public listAllCompradores(){
        return this.compradorService.listAll();
    }

    public listAllCompradoresPage(page: number, linesPerPage: number, direction: string, orderBy: string){
        return this.compradorService.listAllPage(page, linesPerPage, direction, orderBy)
    }

    public listAllUsersPage(page: number, linesPerPage: number, direction: string, orderBy: string){
        return this.userService.listAllPage(page, linesPerPage, direction, orderBy)
    }

    public findByIdUser(id: number){
        return this.userService.findById(id);
    }

    public findByIdComprador(id: number){
        return this.compradorService.findById(id);
    }

    public create(entity: User | Comprador){
        if(entity.tipo === EntityType.USER){
            return this.userService.create(entity);
        } else {
            return this.compradorService.create(<Comprador>entity);
        }
    }

    public update(entity: User | Comprador){
        if(entity.tipo === EntityType.USER){
            return this.userService.update(entity);
        } else {
            return this.compradorService.update(<Comprador>entity);
        }
    }

    public deleteUser(id: number){
        return this.userService.delete(id);
    }

    public deleteComprador(id: number){
        return this.compradorService.delete(id);
    }

    public verifacarEmailUnico(email: string){
        return this.userService.verificaEmail(email);
    }

    public verifacarUsernameUnico(username: string){
        return this.userService.verificaUsername(username);
    }

    public parseToUserComprador(form: FormGroup): User | Comprador  {
        if(form.get('tipo').value == EntityType.USER){
            return this.userService.parseToEntity(form);
        } else {
            return this.compradorService.parseToEntity(form);
        }
    }
}

import { RolesType, AcessoType } from './../../../shared/util/enuns-type.enum';
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { FormGroup } from '@angular/forms';
import { take, map, tap } from 'rxjs/operators';

import { AlertService } from './../../../shared/services/alert-service.service';
import { CrudService } from 'src/app/shared/services/crud-service';
import { User } from 'src/app/shared/models/user';

@Injectable({
    providedIn: 'root'
})
export class UserService extends CrudService<User> {

    constructor(protected http: HttpClient, protected alertService: AlertService) {
        super(http, '/api/usuarios', alertService);
    }

    public verificaEmail(email: string){
        return this.http.get(this.API_URL + `/valida/email-unico/${email}`)
        .pipe(
        //    tap(console.log),
            map((dados: { alreadySaved: boolean }) => dados.alreadySaved),
            take(1)
        );
    }

    public verificaUsername(username: string){
        return this.http.get(this.API_URL + `/valida/valor-unico/${username}`)
        .pipe(
            map((dados: { alreadySaved: boolean }) => dados.alreadySaved),
            take(1)
        );
    }

    public parseToEntity(form: FormGroup): User {
        let user: User = new User(null, form.get('nome').value, form.get('username').value, form.get('password').value);

        if(form.get('admin').value){
            user.roles.push(RolesType.ADMIN);
        }

        if(form.get('email') !== null && form.get('email').value !== ''){
            user.email = form.get('email').value;
            user.acesso.push(AcessoType.EMAIL);
        }

        return user;
    }
}

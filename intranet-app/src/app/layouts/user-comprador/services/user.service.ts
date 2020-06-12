import { RolesType, AcessoType } from './../../../shared/util/enuns-type.enum';
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { FormGroup } from '@angular/forms';
import { take, map, tap } from 'rxjs/operators';

import { CrudService } from 'src/app/shared/services/crud-service';
import { User } from 'src/app/shared/models/user';

@Injectable({
    providedIn: 'root'
})
export class UserService extends CrudService<User> {

    private api_url = '/api/usuarios';

    constructor(protected http: HttpClient) {
        super(http, '/api/usuarios');
    }

    public verificaEmail(email: string){
        return this.http.get(this.api_url + `/valida/email-unico/${email}`)
        .pipe(
        //    tap(console.log),
            map((dados: { alreadySaved: boolean }) => dados.alreadySaved),
            take(1)
        );
    }

    public verificaUsername(username: string){
        return this.http.get(this.api_url + `/valida/valor-unico/${username}`)
        .pipe(
            map((dados: { alreadySaved: boolean }) => dados.alreadySaved),
            take(1)
        );
    }

    public partoToEntity(form: FormGroup): User {
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

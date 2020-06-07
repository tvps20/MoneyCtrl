import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { FormGroup } from '@angular/forms';

import { CrudService } from 'src/app/shared/services/crud-service';
import { User } from 'src/app/shared/models/user';

@Injectable({
    providedIn: 'root'
})
export class UserService extends CrudService<User> {

    constructor(protected http: HttpClient) {
        super(http, `${environment.API}/compradores`);
    }

    public parseToUser(form: FormGroup): User {
        let user: User = new User(form.get('nome').value, form.get('username').value, form.get('senha').value);

        if(form.get('admin').value){
            user.roles.push('ADMIN');
        }

        if(form.get('email') !== null && form.get('email').value !== ''){
            user.email = form.get('email').value;
            user.acesso.push('EMAIL');
        }

        return user;
    }
}

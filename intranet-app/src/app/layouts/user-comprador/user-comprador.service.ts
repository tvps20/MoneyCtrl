import { CrudService } from './../../shared/services/crud-service';
import { FormGroup } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from './../../../environments/environment';

import { Comprador } from './../../shared/models/comprador';

@Injectable({
    providedIn: 'root'
})
export class UserCompradorService extends CrudService<Comprador> {

    private readonly API_USER = `${environment.API}/users`;
    private readonly API_COMPRADOR = `${environment.API}/compradores`;

    constructor(protected http: HttpClient) {
        super(http, `${environment.API}/compradores`);
     }

    public parseToComprador(form: FormGroup): Comprador {
        let comprador: Comprador = new Comprador(form.get('nome').value, form.get('username').value, form.get('senha').value);
        comprador.sobrenome = form.get('sobrenome').value;

        if(form.get('admin').value){
            comprador.roles.push('ADMIN');
        }

        if(form.get('email') !== null && form.get('email').value !== ''){
            comprador.email = form.get('email').value;
            comprador.acesso.push('EMAIL');
        }

        return comprador;
    }
}

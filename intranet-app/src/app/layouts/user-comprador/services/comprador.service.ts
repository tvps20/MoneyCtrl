import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { FormGroup } from '@angular/forms';

import { CrudService } from 'src/app/shared/services/crud-service';
import { Comprador } from 'src/app/shared/models/comprador';

@Injectable({
    providedIn: 'root'
})
export class CompradorService extends CrudService<Comprador> {

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

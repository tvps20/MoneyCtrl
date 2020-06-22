import { take } from 'rxjs/operators';
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { FormGroup } from '@angular/forms';

import { CrudService } from 'src/app/shared/services/crud-service';
import { Comprador } from 'src/app/shared/models/comprador';
import { Credito } from './../../../shared/models/credito';
import { AcessoType } from 'src/app/shared/util/enuns-type.enum';
import { RolesType } from './../../../shared/util/enuns-type.enum';

@Injectable({
    providedIn: 'root'
})
export class CompradorService extends CrudService<Comprador> {

    constructor(protected http: HttpClient) {
        super(http, '/api/compradores');
    }

    public createCredito(credito: Credito){
        return this.http.post(this.API_URL + `/${credito.compradorId}/creditos`, credito).pipe(take(1));
    }

    public deleteCredito(id: number){
        return this.http.delete(`${this.API_URL}/creditos/${id}`).pipe(take(1));
    }

    public parseToCredito(form: FormGroup): Credito {
        let credito = new Credito(null, form.get('valor').value, form.get('descricao').value, form.get('compradorId').value)

        return credito;
    }

    public parseToEntity(form: FormGroup): Comprador {
        let comprador: Comprador = new Comprador(null, form.get('nome').value, form.get('username').value, form.get('password').value);
        comprador.sobrenome = form.get('sobrenome').value;

        if(form.get('admin').value){
            comprador.roles.push(RolesType.ADMIN);
        }

        if(form.get('email') !== null && form.get('email').value !== ''){
            comprador.email = form.get('email').value;
            comprador.acesso.push(AcessoType.EMAIL);
        }

        return comprador;
    }
}

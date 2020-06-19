import { CrudService } from 'src/app/shared/services/crud-service';
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { FormGroup } from '@angular/forms';

import { Divida } from 'src/app/shared/models/divida';

@Injectable({
    providedIn: 'root'
})
export class DividaService extends CrudService<Divida> {

    constructor(protected http: HttpClient) {
        super(http, '/api/dividas');
    }

    public parseToEntity(form: FormGroup): Divida {
        let dataDivida = this.parseToDateFormat(form.get('dataDivida').value);
        let divida = new Divida(null, form.get('valor').value,
        form.get('descricao').value, dataDivida, form.get('compradorId').value);

        return divida;
    }

    private parseToDateFormat(date: Date){
        let aux = date.toISOString().slice(0,10) // yyyy-MM-dd
        aux += " " + date.toLocaleTimeString() // HH:mm:ss

        return aux;
    }
}

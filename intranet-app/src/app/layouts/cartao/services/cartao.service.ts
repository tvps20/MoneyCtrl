import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { FormGroup } from '@angular/forms';

import { CrudService } from 'src/app/shared/services/crud-service';
import { Cartao } from 'src/app/shared/models/cartao';

@Injectable({
    providedIn: 'root'
})
export class CartaoService extends CrudService<Cartao> {


    constructor(protected http: HttpClient) {
        super(http, '/api/cartoes');
    }

    public partoToEntity(form: FormGroup): Cartao {
        throw new Error("Method not implemented.");
    }
}

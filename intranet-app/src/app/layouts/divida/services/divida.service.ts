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
        throw new Error("Method not implemented.");
    }
}

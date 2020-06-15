import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { FormGroup } from '@angular/forms';
import { tap, take, map } from 'rxjs/operators';

import { Fatura } from 'src/app/shared/models/fatura';
import { CrudService } from 'src/app/shared/services/crud-service';

@Injectable({
    providedIn: 'root'
})
export class FaturaService extends CrudService<Fatura>{


    constructor(protected http: HttpClient) {
        super(http, '/api/faturas');
    }

    public listAllMonths() {
        return this.http.get('assets/dados/months.json').pipe(
            map((dados: any[]) => dados),
            take(1)
        );
    }

    public partoToEntity(form: FormGroup): Fatura {
        let fatura = new Fatura(null, new Date(), form.get('cartaoId').value, form.get('observacao').value);
        fatura.mesReferente = form.get('mesReferente').value;

        return fatura;
    }
}

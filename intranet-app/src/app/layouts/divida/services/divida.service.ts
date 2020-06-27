import { take } from 'rxjs/operators';
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { FormGroup } from '@angular/forms';

import { AlertService } from './../../../shared/services/alert-service.service';
import { Divida } from 'src/app/shared/models/divida';
import { Pagamento } from './../../../shared/models/pagamento';
import { CrudService } from 'src/app/shared/services/crud-service';

@Injectable({
    providedIn: 'root'
})
export class DividaService extends CrudService<Divida> {

    constructor(protected http: HttpClient, protected alertService: AlertService) {
        super(http, '/api/dividas', alertService);
    }

    public createPagamento(pagamento: Pagamento) {
        return this.http.post(this.API_URL + `/${pagamento.dividaId}/pagamentos`, pagamento).pipe(take(1));
    }

    public listAllPageStatus(paga: boolean, page: number, linesPerPage: number, direction: string, orderBy: string) {
        return this.http.get<Divida[]>(`${this.API_URL}/page/status?paga=${paga}&page=${page}&linesPerPage=${linesPerPage}&direction=${direction}&orderBy=${orderBy}`);
    }

    public parseToEntity(form: FormGroup): Divida {
        let dataDivida = this.parseToDateFormat(form.get('dataDivida').value);
        let divida = new Divida(null, form.get('valor').value,
            form.get('descricao').value, dataDivida, form.get('compradorId').value);

        return divida;
    }

    public parteToPagamento(form: FormGroup): Pagamento {
        let pagamento = new Pagamento(null, form.get('valor').value, form.get('dividaId').value);

        return pagamento;
    }
}

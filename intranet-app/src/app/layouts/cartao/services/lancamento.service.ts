import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

import { AlertService } from './../../../shared/services/alert-service.service';
import { CrudService } from 'src/app/shared/services/crud-service';
import { Lancamento } from './../../../shared/models/lancamento';
import { Cota } from './../../../shared/models/cota';
import { FormGroup } from '@angular/forms';

@Injectable({
    providedIn: 'root'
})
export class LancamentoService extends CrudService<Lancamento> {


    constructor(protected http: HttpClient, protected alertService: AlertService) {
        super(http, '/api/lancamentos', alertService);
    }

    public parseToEntity(form: FormGroup): Lancamento {
        let dataCompra = this.parseToDateFormat(form.get('dataCompra').value);
        let parcelaAtual: string = null;
        let qtdParcelas: string = null;
        if(form.get('parcelas').value != null){
            parcelaAtual = form.get('parcelas').value.substring(0,2);
            qtdParcelas = form.get('parcelas').value.substring(2, 4);
        }
        let cotasLancamento: any[] = form.get('compradores').value;
        let lancamento = new Lancamento(null, form.get('descricao').value, dataCompra,
        form.get('faturaId').value, form.get('tipoLancamento').value, parseInt(qtdParcelas), parseInt(parcelaAtual))

        lancamento.observacao = form.get('observacao').value;
        lancamento.cotas = cotasLancamento.map(x => new Cota(null, x.valor, x.compradorId))
        return lancamento;
    }
}

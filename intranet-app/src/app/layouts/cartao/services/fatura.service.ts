import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { FormGroup } from '@angular/forms';
import { tap, take, map, catchError } from 'rxjs/operators';
import { empty } from 'rxjs';

import { AlertService } from './../../../shared/services/alert-service.service';
import { CotaFatura } from './../../../shared/models/cota';
import { StatusType } from './../../../shared/util/enuns-type.enum';
import { Fatura } from 'src/app/shared/models/fatura';
import { CrudService } from 'src/app/shared/services/crud-service';

@Injectable({
    providedIn: 'root'
})
export class FaturaService extends CrudService<Fatura>{

    constructor(protected http: HttpClient, protected alertService: AlertService) {
        super(http, '/api/faturas', alertService);
        this.msgErro = 'Erro ao tentar carregar informações das faturas.'
    }

    public listAllCotas(faturaId: number) {
        return this.http.get<CotaFatura[]>(`${this.API_URL}/${faturaId}/cotas`).pipe(take(1)).pipe(
            catchError(error => {
                this.alertService.ShowAlertDanger('Erro ao tentar carregar informações das cotas.')
                return empty();
            })
        );
    }

    public listAllPageStatus(status: StatusType, page: number, linesPerPage: number, direction: string, orderBy: string) {
        return this.http.get<Fatura[]>(`${this.API_URL}/page/status?status=${status}&page=${page}&linesPerPage=${linesPerPage}&direction=${direction}&orderBy=${orderBy}`).pipe(
            catchError(error => {
                this.alertService.ShowAlertDanger(this.msgErro)
                return empty();
            })
        );
    }

    public listAllPageNoStatus(status: StatusType, page: number, linesPerPage: number, direction: string, orderBy: string) {
        return this.http.get<Fatura[]>(`${this.API_URL}/page/no-status?status=${status}&page=${page}&linesPerPage=${linesPerPage}&direction=${direction}&orderBy=${orderBy}`).pipe(
            catchError(error => {
                this.alertService.ShowAlertDanger(this.msgErro)
                return empty();
            })
        );
    }

    public listAllMonths() {
        return this.http.get('assets/dados/months.json').pipe(map((dados: any[]) => dados), take(1)).pipe(
            catchError(error => {
                this.alertService.ShowAlertDanger('Erro ao tentar carregar informações dos meses.')
                return empty();
            })
        );
    }

    public fecharFatura(id: number) {
        return this.http.get(this.API_URL + `/fechar-fatura/${id}`).pipe(take(1));
    }

    public pagarFatura(id: number) {
        return this.http.get(this.API_URL + `/pagar-fatura/${id}`).pipe(take(1));
    }

    public parseToEntity(form: FormGroup): Fatura {
        let vencimento = this.parseToDateFormat(form.get('vencimento').value);
        let fatura = new Fatura(null, vencimento, form.get('cartaoId').value, form.get('observacao').value);

        fatura.mesReferente = form.get('mesReferente').value;
        return fatura;
    }
}

import { UtilService } from './../../shared/util/util.service';
import { PageEvent } from '@angular/material/paginator';
import { Divida } from './../../shared/models/divida';
import { Component, OnInit } from '@angular/core';
import { catchError, tap, map } from 'rxjs/operators';
import { Observable, empty, Subject } from 'rxjs';

import { CotaCartao } from './../../shared/models/cota';

import { AlertService } from './../../shared/services/alert-service.service';
import { CartaoService } from './../cartao/services/cartao.service';
import { DividaService } from './../divida/services/divida.service';

@Component({
    selector: 'app-dashboard',
    templateUrl: './dashboard.component.html',
    styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {

    public dataAtual = new Date();
    public totalFaturas = 0;
    public totalDividas = 0;
    public mesReferente = 'Sem faturas ativas.';
    public cartaoCotas$: Observable<CotaCartao[]>;
    public dividasAtivas$: Observable<Divida[]>;
    public totalPagamentos = 0;
    public entitySelecionada: Divida;
    public faturaAtivasLength = 0;
    public DividasAtivasLength = 0;
    public errorDividasAtivas$ = new Subject<boolean>();

    // MatPaginator Dividas Ativas
    public lengthDividasAtivas = 0;
    public pageSizeDividasAtivas = 5;
    public pageIndexDividasAtivas = 1;

    constructor(private cartaoService: CartaoService,
        private utilService: UtilService,
        private alertServiceService: AlertService,
        private dividaService: DividaService) { }

    ngOnInit(): void {
        this.cartaoCotas$ = this.listAllCartaoCotas();
        this.dividasAtivas$ = this.listAllDividasAtivas();
    }

    public onSelectedEntity(entity: Divida) {
        this.entitySelecionada = entity;
    }

    public onRefreshList(event: boolean) {
        if (event) {
            this.dividasAtivas$ = this.listAllDividasAtivas();
        }
    }

    private listAllCartaoCotas(){
        let auxValorTotalCotas = 0;
        return this.cartaoService.listAllCotas().pipe(
            map((dados: CotaCartao[]) => dados.sort((a, b) => a.cotas.length < b.cotas.length ? 1 : -1)),
            tap((dados: CotaCartao[]) => this.faturaAtivasLength = dados.length),
            tap((dados: CotaCartao[]) => dados.map(cotaCartao => auxValorTotalCotas += cotaCartao.valorTotal )),
            tap((dados: CotaCartao[]) => dados.length > 0 ? this.mesReferente = dados[0].faturaMes : ''),
            tap(() => this.totalFaturas = auxValorTotalCotas),
            catchError(error => {
                this.alertServiceService.ShowAlertDanger('Ocorreu um erro ao buscar informações dos cartões no servidor.');
                return empty();
            })
        )
    }

    private listAllDividasAtivas() {
        let auxValorPagamentos = 0;
        let auxValorDivida = 0;
        return this.dividaService.listAllStatus(false).pipe(
            tap((content: Divida[]) => content.map( divida => {
                auxValorPagamentos += divida.totalPagamentos;
                auxValorDivida += divida.valorDivida;
            } )),
            tap((content: Divida[]) => {
                this.lengthDividasAtivas = content.length;
                this.DividasAtivasLength = content.length;
                this.totalPagamentos = auxValorPagamentos;
                this.totalDividas = auxValorDivida;
            }),
            map((content: Divida[]) => this.utilService.paginate(content, this.pageSizeDividasAtivas, this.pageIndexDividasAtivas)),
            catchError(error => {
                this.errorDividasAtivas$.next(true);
                return empty();
            })
        );
    }

    public changeListDividasAtivas(event: PageEvent) {
        this.pageSizeDividasAtivas = event.pageSize;
        this.pageIndexDividasAtivas = event.pageIndex + 1;
        this.dividasAtivas$ = this.listAllDividasAtivas();
    }
}

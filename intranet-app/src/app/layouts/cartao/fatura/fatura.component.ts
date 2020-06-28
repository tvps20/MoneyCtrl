import { Observable, Subject, empty } from 'rxjs';
import { catchError, tap, take, map } from 'rxjs/operators';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { PageEvent } from '@angular/material/paginator';

import { Fatura } from './../../../shared/models/fatura';
import { Cartao } from 'src/app/shared/models/cartao';
import { StatusType } from 'src/app/shared/util/enuns-type.enum';

import { AlertService } from './../../../shared/services/alert-service.service';
import { BaseFormComponent } from 'src/app/shared/components/base-form/base-form.component';
import { ValidFormsService } from 'src/app/shared/services/valid-forms.service';
import { CartaoService } from './../services/cartao.service';
import { FaturaService } from './../services/fatura.service';

@Component({
    selector: 'app-fatura',
    templateUrl: './fatura.component.html',
    styleUrls: ['./fatura.component.css']
})
export class FaturaComponent extends BaseFormComponent implements OnInit {

    public faturasAtivas$: Observable<Fatura[]>;
    public faturasOlds$: Observable<Fatura[]>;
    public cartoesSelect$:  Observable<Cartao[]>;
    public months$: Observable<any[]>;
    public submitte = false;
    public entitySelecionada: Fatura;

    // MatPaginator Faturas Ativas
    public lengthFaturasAtivas = 10;
    public pageSizeFaturasAtivas = 5;
    public pageIndexFaturasAtivas = 0;

    // MatPaginator Faturas Antigas
    public lengthFaturasOlds = 10;
    public pageSizeFaturasOlds = 5;
    public pageIndexFaturasOlds = 0;

    constructor(private formBuilder: FormBuilder,
        protected validFormsService: ValidFormsService,
        private faturaSevice: FaturaService,
        private cartaoService: CartaoService,
        private alertServiceService: AlertService) {
        super(validFormsService);
    }

    ngOnInit(): void {
        this.formulario = this.createForm();
        this.faturasAtivas$ = this.listAllFaturasAtivas();
        this.faturasOlds$ = this.listAllFaturasOlds();
        this.cartoesSelect$ = this.listAllCartoesSelect();
        this.months$ = this.listAllMonths();
    }

    public submit() {
        this.submitte = true;
        let newEntity = this.faturaSevice.parseToEntity(this.formulario);
        this.createEntity(newEntity, 'Fatura salva com sucesso.', 'Error ao tentar salvar fatura')
    }

    public createEntity(entity: Fatura, msgSuccess: string, msgError: string) {
        return this.faturaSevice.create(entity).subscribe(
            success => {
                this.reseteForm();
                this.submitte = false
                this.alertServiceService.ShowAlertSuccess(msgSuccess);
            },
            error => {
                this.submitte = false;
                this.alertServiceService.ShowAlertDanger(msgError);
            },
            () => {
                this.faturasAtivas$ = this.listAllFaturasAtivas();
                this.faturasOlds$ = this.listAllFaturasOlds();
            }
        );
    }

    public createForm() {
        return this.formBuilder.group({
            vencimento: [{value: new Date(), disabled: true}, Validators.required],
            mesReferente: [null, Validators.required],
            cartaoId: [null, Validators.required],
            observacao: [null]
        });
    }

    public reseteForm() {
        this.formulario.reset();
        this.formulario.get('vencimento').setValue(new Date());
    }

    public onSelectedEntity(entity: Fatura) {
        this.entitySelecionada = entity;
    }

    public onDelete(event: any) {
        if (event === 'sim') {
            this.faturaSevice.delete(this.entitySelecionada.id).subscribe(
                success => {
                    this.alertServiceService.ShowAlertSuccess("Fatura apagada com sucesso.");
                },
                error => {
                    this.alertServiceService.ShowAlertDanger(error.error.message);
                },
                () => {
                    this.faturasAtivas$ = this.listAllFaturasAtivas();
                    this.faturasOlds$ = this.listAllFaturasOlds();
                    this.entitySelecionada = null;
                }
            )
        }
    }

    public onFecharFatura(event: any){
        if( event === 'sim') {
            this.faturaSevice.fecharFatura(this.entitySelecionada.id).subscribe(
                success => {
                    this.alertServiceService.ShowAlertSuccess("Fatura fechada com sucesso.");
                },
                error => {
                    this.alertServiceService.ShowAlertDanger(error.error.message);
                },
                () => {
                    this.faturasAtivas$ = this.listAllFaturasAtivas();
                    this.faturasOlds$ = this.listAllFaturasOlds();
                    this.entitySelecionada = null;
                }
            )
        }

    }

    public onPagarFatura(event: any){
        if( event === 'sim') {
            this.faturaSevice.pagarFatura(this.entitySelecionada.id).subscribe(
                success => {
                    this.alertServiceService.ShowAlertSuccess("Fatura paga com sucesso.");
                },
                error => {
                    this.alertServiceService.ShowAlertDanger(error.error.message);
                },
                () => {
                    this.faturasAtivas$ = this.listAllFaturasAtivas();
                    this.faturasOlds$ = this.listAllFaturasOlds();
                    this.entitySelecionada = null;
                }
            )
        }
    }

    private listAllFaturasAtivas(direction = "DESC", orderBy = "createdAt") {
        return this.faturaSevice.listAllPageNoStatus(StatusType.PAGA, this.pageIndexFaturasAtivas, this.pageSizeFaturasAtivas, direction, orderBy).pipe(
            tap((page: any) => this.lengthFaturasAtivas = page.totalElements),
            map((page: any) => page.content)
        );
    }

    private listAllFaturasOlds(direction = "DESC", orderBy = "createdAt") {
        return this.faturaSevice.listAllPageStatus(StatusType.PAGA, this.pageIndexFaturasOlds, this.pageSizeFaturasOlds, direction, orderBy).pipe(
            tap((page: any) => this.lengthFaturasOlds = page.totalElements),
            map((page: any) => page.content)
        );
    }

    private listAllCartoesSelect(){
        return this.cartaoService.listAll();
    }

    private listAllMonths(): Observable<any[]>{
        return this.faturaSevice.listAllMonths();
    }

    public changeListFaturasAtivas(event: PageEvent){
        this.pageSizeFaturasAtivas = event.pageSize;
        this.pageIndexFaturasAtivas = event.pageIndex;
        this.faturasAtivas$ = this.listAllFaturasAtivas();
    }

    public changeListFaturasOlds(event: PageEvent){
        this.pageSizeFaturasOlds = event.pageSize;
        this.pageIndexFaturasOlds = event.pageIndex;
        this.faturasOlds$ = this.listAllFaturasOlds();
    }
}

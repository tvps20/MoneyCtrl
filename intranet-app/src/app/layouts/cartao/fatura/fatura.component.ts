import { AlertServiceService } from './../../../shared/services/alert-service.service';
import { Observable, Subject, empty } from 'rxjs';
import { catchError, tap, take, map } from 'rxjs/operators';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { PageEvent } from '@angular/material/paginator';

import { Fatura } from './../../../shared/models/fatura';
import { Cartao } from 'src/app/shared/models/cartao';

import { BaseFormComponent } from 'src/app/shared/components/base-form/base-form.component';
import { ValidFormsService } from 'src/app/shared/services/valid-forms.service';
import { CartaoService } from './../services/cartao.service';
import { FaturaService } from './../services/fatura.service';
import { StatusType } from 'src/app/shared/util/enuns-type.enum';

@Component({
    selector: 'app-fatura',
    templateUrl: './fatura.component.html',
    styleUrls: ['./fatura.component.css']
})
export class FaturaComponent extends BaseFormComponent implements OnInit {

    public faturasAtivas$: Observable<Fatura[]>;
    public faturasOlds$: Observable<Fatura[]>;
    public cartoes$:  Observable<Cartao[]>;
    public months$: Observable<any[]>;
    public error$ = new Subject<boolean>();
    public submitte = false;
    public entitySelecionada: Fatura;
    // MatPaginator Fatura Inputs
    public lengthFaturas;
    public pageSizeFaturas = 5;
    public pageIndexFaturas = 0;
    public PageFaturas: any;
    public directionFaturas = false;
    public orderByFaturas = "createdAt"
        // MatPaginator Fatura Inputs
    public lengthFaturasOlds;
    public pageSizeFaturasOlds = 5;
    public pageIndexFaturasOlds = 0;
    public PageFaturasOlds: any;
    public directionFaturasOlds = false;
    public orderByFaturasOlds = "createdAt"

    constructor(private formBuilder: FormBuilder,
        protected validFormsService: ValidFormsService,
        private faturaSerive: FaturaService,
        private cartaoService: CartaoService,
        private alertServiceService: AlertServiceService) {
        super(validFormsService);
    }

    ngOnInit(): void {
        this.formulario = this.createForm();
        this.faturasAtivas$ = this.listAllFaturasAtivas();
        this.faturasOlds$ = this.listAllFaturasOlds();
        this.cartoes$ = this.listAllCartoes();
        this.months$ = this.listAllMonths();
    }

    public submit() {
        this.submitte = true;
        let newEntity = this.faturaSerive.parseToEntity(this.formulario);
        this.create(newEntity, 'Fatura salva com sucesso.', 'Error ao tentar salvar fatura')
    }

    private create(entity: Fatura, msgSuccess: string, msgError: string) {
        return this.faturaSerive.create(entity).subscribe(
            success => {
                this.reseteForm();
                this.submitte = false
                this.alertServiceService.ShowAlertSuccess(msgSuccess);
                console.log(this.formulario)
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

    public reseteForm() {
        this.formulario = this.createForm();
    }

    public createForm() {
        return this.formBuilder.group({
            vencimento: [{value: new Date(), disabled: true}, Validators.required],
            mesReferente: [null, Validators.required],
            cartaoId: [null, Validators.required],
            observacao: [null]
        });
    }

    public onSelectedEntity(entity: Fatura) {
        this.entitySelecionada = entity;
    }

    public confirmModalExcluir(event: any) {
        if (event === 'sim') {
            let status = this.entitySelecionada.status;
            this.faturaSerive.delete(this.entitySelecionada.id).subscribe(
                success => {
                    this.alertServiceService.ShowAlertSuccess("Fatura apagada com sucesso.");
                },
                error => {
                    this.alertServiceService.ShowAlertDanger(error.error.message);
                },
                () => {
                    if (status !== StatusType.PAGA) {
                        this.pageIndexFaturas = this.lengthFaturas - 1 < this.pageSizeFaturas ? 0 :
                        this.PageFaturas.content.length - 1 <= 0 ? this.pageIndexFaturas - 1 : this.pageIndexFaturas;
                        this.faturasAtivas$ = this.listAllFaturasAtivas(this.pageIndexFaturas, this.pageSizeFaturas);
                        this.faturasOlds$ = this.listAllFaturasOlds();
                    } else {
                        this.pageIndexFaturasOlds = this.lengthFaturasOlds - 1 < this.pageSizeFaturasOlds ? 0 :
                        this.PageFaturasOlds.content.length - 1 <= 0 ? this.pageIndexFaturasOlds - 1 : this.pageIndexFaturasOlds;
                        this.faturasOlds$ = this.listAllFaturasOlds(this.pageIndexFaturasOlds, this.pageSizeFaturasOlds);
                        this.faturasAtivas$ = this.listAllFaturasAtivas();
                    }
                }
            )
        }

        this.entitySelecionada = null;
    }

    public confirmModalFecharFatura(event: any){
        if( event === 'sim') {
            this.faturaSerive.fecharFatura(this.entitySelecionada.id).subscribe(
                success => {
                    this.alertServiceService.ShowAlertSuccess("Fatura fechada com sucesso.");
                },
                error => {
                    this.alertServiceService.ShowAlertDanger(error.error.message);
                },
                () => {
                    this.pageIndexFaturas = this.lengthFaturas - 1 < this.pageSizeFaturas ? 0 :
                        this.PageFaturas.content.length - 1 <= 0 ? this.pageIndexFaturas - 1 : this.pageIndexFaturas;
                    this.faturasAtivas$ = this.listAllFaturasAtivas(this.pageIndexFaturas, this.pageSizeFaturas);
                    this.faturasOlds$ = this.listAllFaturasOlds();
                }
            )
        }

        this.entitySelecionada = null;
    }

    public confirmModalPagarFatura(event: any){
        if( event === 'sim') {
            this.faturaSerive.pagarFatura(this.entitySelecionada.id).subscribe(
                success => {
                    this.alertServiceService.ShowAlertSuccess("Fatura paga com sucesso.");
                },
                error => {
                    this.alertServiceService.ShowAlertDanger(error.error.message);
                },
                () => {
                    this.pageIndexFaturas = this.lengthFaturas - 1 < this.pageSizeFaturas ? 0 :
                        this.PageFaturas.content.length - 1 <= 0 ? this.pageIndexFaturas - 1 : this.pageIndexFaturas;
                    this.faturasAtivas$ = this.listAllFaturasAtivas(this.pageIndexFaturas, this.pageSizeFaturas);
                    this.faturasOlds$ = this.listAllFaturasOlds();
                }
            )
        }

        this.entitySelecionada = null;
    }

    public changeListFaturasAtivas(pageEvent: PageEvent){
        this.pageIndexFaturas = pageEvent.pageIndex;
        this.faturasAtivas$ = this.listAllFaturasAtivas(pageEvent.pageIndex, pageEvent.pageSize);
    }

    public changeListFaturasOlds(pageEvent: PageEvent){
        this.pageIndexFaturasOlds = pageEvent.pageIndex;
        this.faturasAtivas$ = this.listAllFaturasOlds(pageEvent.pageIndex, pageEvent.pageSize);
    }

    public verificarValidControl(control: string): boolean {
        let controlForm = this.formulario.get(control);
        return !controlForm.valid &&
            (controlForm.touched || controlForm.dirty);
    }

    public errorMessage(control: string, label: string) {
        return this.validFormsService.errorMessage(this.formulario.get(control), label);
    }

    private listaAllFaturas(page = 0, linesPerPage = 5, direction = "DESC", orderBy = "createdAt"){
        return this.faturaSerive.listAllPage(page, linesPerPage, direction, orderBy)
        .pipe(
        //    tap(console.log),
            catchError(error => {
                this.error$.next(true);
                this.alertServiceService.ShowAlertDanger('Error ao carregar faturas. Tente novamente mais tarde.')
                return empty();
            }
        ));
    }

    public listAllFaturasAtivas(page = 0, linesPerPage = 5, direction = "DESC", orderBy = "createdAt") {
        return this.listaAllFaturas(page, linesPerPage, direction, orderBy)
        .pipe(
        //    tap(console.log),
            tap((page: any) => this.PageFaturas = page),
            tap((page: any) => this.lengthFaturas = page.totalElements),
            map((page: any) => page.content),
            map((content: Fatura[]) => content.filter(fatura => fatura.status !== StatusType.PAGA))
        )
    }

    public listAllFaturasOlds(page = 0, linesPerPage = 5, direction = "DESC", orderBy = "createdAt") {
        return this.listaAllFaturas(page, linesPerPage, direction, orderBy)
        .pipe(
            tap((page: any) => this.PageFaturasOlds = page),
            tap((page: any) => this.lengthFaturasOlds = page.totalElements),
            map((page: any) => page.content),
            map((content: Fatura[]) => content.filter(fatura => fatura.status === StatusType.PAGA))
        )
    }

    public listAllCartoes(){
        return this.cartaoService.listAll()
        .pipe(
        //    tap(console.log),
            catchError(error => {
                this.alertServiceService.ShowAlertDanger('Error ao carregar cartões. Tente novamente mais tarde.')
                return empty();
            }
        ));
    }

    public listAllMonths(): Observable<any[]>{
        return this.faturaSerive.listAllMonths();
    }
}

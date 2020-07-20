import { PageEvent } from '@angular/material/paginator';
import { Component, OnInit } from '@angular/core';
import { catchError, tap, map } from 'rxjs/operators';
import { FormBuilder, Validators } from '@angular/forms';
import { Observable, Subject, empty } from 'rxjs';

import { Divida } from './../../shared/models/divida';
import { Comprador } from './../../shared/models/comprador';

import { AlertService } from './../../shared/services/alert-service.service';
import { DividaService } from './services/divida.service';
import { FormValidations } from './../../shared/util/form-validations';
import { CompradorService } from './../user-comprador/services/comprador.service';
import { ValidFormsService } from './../../shared/services/valid-forms.service';
import { BaseFormListComponent } from 'src/app/shared/components/base-form-list/base-form-list.component';

@Component({
    selector: 'app-divida',
    templateUrl: './divida.component.html',
    styleUrls: ['./divida.component.css']
})
export class DividaComponent extends BaseFormListComponent implements OnInit {

    public devedores: Comprador[];
    public devedores$: Observable<Comprador[]>;
    public totalDividas = 0;
    public dividasOlds$: Observable<Divida[]>;
    public dividasAtivas$: Observable<Divida[]>;
    public totalPagamentos = 0;
    public errorDevedores$ = new Subject<boolean>();
    public errorDividasOlds$ = new Subject<boolean>();
    public compradoresSelect: Comprador[];
    public entitySelecionada: Divida;
    public errorDividasAtivas$ = new Subject<boolean>();

    // MatPaginator Devedores
    public lengthDevedores = 0;
    public pageSizeDevedores = 5;
    public pageIndexDevedores = 1;

    // MatPaginator Dividas Ativas
    public lengthDividasAtivas = 0;
    public pageSizeDividasAtivas = 5;
    public pageIndexDividasAtivas = 0;

    // MatPaginator Dividas Antigas
    public lengthDividasOlds = 0;
    public pageSizeDividasOlds = 5;
    public pageIndexDividasOlds = 0;

    constructor(protected validFormsService: ValidFormsService,
        private alertServiceService: AlertService,
        private compradorService: CompradorService,
        private dividaService: DividaService,
        private formBuilder: FormBuilder) {
        super(validFormsService);
    }

    ngOnInit(): void {
        this.dividasAtivas$ = this.listAllDividasAtivas();
        this.dividasOlds$ = this.listAllDividasOlds();
        this.devedores$ = this.listAllDevedores();
        this.formulario = this.createForm();
    }

    public submit() {
        this.submitte = true;
        let newEntity = this.dividaService.parseToEntity(this.formulario);
        this.createEntity(newEntity, 'Divida salvo com sucesso.', 'Error ao tentar salvar divida.');
    }

    public createEntity(entity: Divida, msgSuccess: string, msgError: string) {
        return this.dividaService.create(entity).subscribe(
            success => {
                this.reseteForm();
                this.submitte = false;
                this.alertServiceService.ShowAlertSuccess(msgSuccess);
            },
            error => {
                this.submitte = false;
                this.alertServiceService.ShowAlertDanger(error);
            },
            () => {
                this.dividasAtivas$ = this.listAllDividasAtivas();
                this.devedores$ = this.listAllDevedores();
            }
        );
    }

    public createForm() {
        return this.formBuilder.group({
            valor: [null, Validators.required],
            dataDivida: [{ value: new Date(), disabled: true }, Validators.required],
            compradorId: [null, Validators.required],
            descricao: [null, [Validators.required, FormValidations.notStartNumber, Validators.minLength(5), Validators.maxLength(20)]]
        });
    }

    public defaultValuesForms() {
        this.formulario.get('dataDivida').setValue(new Date());
    }

    public onSelectedEntity(entity: Divida) {
        this.entitySelecionada = entity;
    }

    public onDelete(event: any) {
        if (event === 'sim') {
            this.dividaService.delete(this.entitySelecionada.id).subscribe(
                success => {
                    this.alertServiceService.ShowAlertSuccess("Divida apagada com sucesso.");
                },
                error => {
                    this.alertServiceService.ShowAlertDanger(error.error.message);
                },
                () => {
                    if (!this.entitySelecionada.paga) {
                        this.dividasAtivas$ = this.listAllDividasAtivas();
                        this.devedores$ = this.listAllDevedores();
                    } else {
                        this.dividasOlds$ = this.listAllDividasOlds();
                    }
                    this.entitySelecionada = null;
                }
            );
        }
    }

    public onRefreshList(event: boolean) {
        if (event) {
            this.dividasAtivas$ = this.listAllDividasAtivas();
            this.dividasOlds$ = this.listAllDividasOlds();
            this.devedores$ = this.listAllDevedores();
        }
    }

    private listAllDividasAtivas(direction = "DESC", orderBy = "createdAt") {
        return this.dividaService.listAllPageStatus(false, this.pageIndexDividasAtivas, this.pageSizeDividasAtivas, direction, orderBy).pipe(
            tap((page: any) => this.lengthDividasAtivas = page.totalElements),
            map((page: any) => page.content),
            catchError(error => {
                this.errorDividasAtivas$.next(true);
                return empty();
            })
        );
    }

    private listAllDividasOlds(page = 0, linesPerPage = 5, direction = "DESC", orderBy = "createdAt") {
        return this.dividaService.listAllPageStatus(true, this.pageIndexDividasOlds, this.pageSizeDividasOlds, direction, orderBy).pipe(
            tap((page: any) => this.lengthDividasOlds = page.totalElements),
            map((page: any) => page.content),
            catchError(error => {
                this.errorDividasOlds$.next(true);
                return empty();
            })
        );
    }

    private listAllDevedores() {
        let auxDividas = 0;
        let auxPagamentos = 0;
        return this.compradorService.listAll().pipe(
            tap((content: Comprador[]) => this.compradoresSelect = content),
            map((content: Comprador[]) => content.filter(comprador => comprador.devedor)),
            tap((content: Comprador[]) => content.map(comprador => {
                auxDividas += comprador.totalDividas;
                auxPagamentos += comprador.totalPagamentos;
            })),
            tap((content: Comprador[]) => {
                this.totalDividas = auxDividas;
                this.totalPagamentos = auxPagamentos;
                this.lengthDevedores = content.length;
            }),
            map((content: Comprador[]) => this.paginate(content, this.pageSizeDevedores, this.pageIndexDevedores)),
            catchError(error => {
                this.errorDevedores$.next(true);
                return empty();
            })
        );
    }

    public changeListDevedores(event: PageEvent) {
        this.pageSizeDevedores = event.pageSize;
        this.pageIndexDevedores = event.pageIndex + 1;
        this.devedores$ = this.listAllDevedores();
    }

    public changeListDividasAtivas(event: PageEvent) {
        this.pageSizeDividasAtivas = event.pageSize;
        this.pageIndexDividasAtivas = event.pageIndex;
        this.dividasAtivas$ = this.listAllDividasAtivas();
    }

    public changeListDividasOlds(event: PageEvent) {
        this.pageSizeDividasOlds = event.pageSize;
        this.pageIndexDividasOlds = event.pageIndex;
        this.dividasOlds$ = this.listAllDividasOlds();
    }
}

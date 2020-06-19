import { PageEvent } from '@angular/material/paginator';
import { Observable, Subject, empty } from 'rxjs';
import { catchError, tap, debounceTime, switchMap, take, map, count } from 'rxjs/operators';
import { FormBuilder, Validators } from '@angular/forms';
import { Component, OnInit } from '@angular/core';

import { Divida } from './../../shared/models/divida';
import { Comprador } from './../../shared/models/comprador';

import { DividaService } from './services/divida.service';
import { AlertServiceService } from './../../shared/services/alert-service.service';
import { UserCompradorService } from './../user-comprador/services/user-comprador.service';
import { ValidFormsService } from './../../shared/services/valid-forms.service';
import { BaseFormComponent } from 'src/app/shared/components/base-form/base-form.component';

@Component({
    selector: 'app-divida',
    templateUrl: './divida.component.html',
    styleUrls: ['./divida.component.css']
})
export class DividaComponent extends BaseFormComponent implements OnInit {

    public totalDividas = 0;
    public totalPagamentos = 0;
    public dividasAtivas$: Observable<Divida[]>;
    public dividasOlds$: Observable<Divida[]>;
    public devedores$: Observable<Comprador[]>;
    public devedores: Comprador[];
    public compradoresSelect: Comprador[];
    public error$ = new Subject<boolean>();
    public submitte = false;
    public entitySelecionada: Divida;

    // MatPaginator Devedores
    public lengthDevedores = 10;
    public pageSizeDevedores = 5;
    public pageIndexDevedores = 1;

    constructor(private formBuilder: FormBuilder,
        protected validFormsService: ValidFormsService,
        private dividaService: DividaService,
        private alertServiceService: AlertServiceService,
        private userCompradorService: UserCompradorService) {
        super(validFormsService);
    }

    ngOnInit(): void {
        this.formulario = this.createForm();
        this.dividasAtivas$ = this.listAllDividasAtivas();
        this.dividasOlds$ = this.listAllDividasOlds();
        this.devedores$ = this.listAllDevedores();
    }

    public submit() {
        this.submitte = true;
        let newEntity = this.dividaService.parseToEntity(this.formulario);
        this.create(newEntity, 'Divida salvo com sucesso.', 'Error ao tentar salvar divida');
    }

    private create(entity: Divida, msgSuccess: string, msgError: string) {
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
            descricao: [null, Validators.required]
        });
    }

    public reseteForm() {
        this.formulario.reset();
        this.formulario.get('dataDivida').setValue(new Date());
    }

    public verificarValidControl(control: string): boolean {
        let controlForm = this.formulario.get(control);
        return !controlForm.valid &&
            (controlForm.touched || controlForm.dirty);
    }

    public verificarValidControlSuccess(control: string): boolean {
        let controlForm = this.formulario.get(control);
        return controlForm.valid &&
            (controlForm.value !== null && controlForm.value !== '');
    }

    public errorMessage(control: string, label: string) {
        return this.validFormsService.errorMessage(this.formulario.get(control), label);
    }

    private listAllDividas(page = 0, linesPerPage = 5, direction = "DESC", orderBy = "createdAt") {
        return this.dividaService.listAllPage(page, linesPerPage, direction, orderBy)
            .pipe(
                catchError(error => {
                    this.error$.next(true);
                    this.alertServiceService.ShowAlertDanger('Error ao carregar dividas. Tente novamente mais tarde.')
                    return empty();
                }
                )
            );
    }

    private listAllDividasAtivas(page = 0, linesPerPage = 5, direction = "DESC", orderBy = "createdAt") {
        return this.listAllDividas(page, linesPerPage, direction, orderBy).pipe(
            map((page: any) => page.content),
            map((content: Divida[]) => content.filter(divida => !divida.paga))
        );
    }

    private listAllDividasOlds(page = 0, linesPerPage = 100, direction = "DESC", orderBy = "createdAt") {
        return this.listAllDividas(page, linesPerPage, direction, orderBy).pipe(
            map((page: any) => page.content),
            tap(console.log),
            map((content: Divida[]) => content.filter(divida => divida.paga))
        )
    }

    private listAllDevedores() {
        let auxDividas = 0;
        let auxPagamentos = 0;
        return this.userCompradorService.listAllCompradores().pipe(
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
                this.error$.next(true);
                this.alertServiceService.ShowAlertDanger('Error ao carregar devedores. Tente novamente mais tarde.')
                return empty();
            })
        );
    }

    public changeListDevedores(event: PageEvent){
        this.pageSizeDevedores = event.pageSize;
        this.pageIndexDevedores = event.pageIndex +1;
        this.devedores$ = this.listAllDevedores();
    }

    private paginate(array: any[], page_size, page_index) {
        // human-readable page numbers usually start with 1, so we reduce 1 in the first argument
        return array.slice((page_index - 1) * page_size, page_index * page_size);
    }
}

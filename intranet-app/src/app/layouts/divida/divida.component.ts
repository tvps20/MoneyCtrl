import { DividaService } from './services/divida.service';
import { Observable, Subject, empty } from 'rxjs';
import { catchError, tap, debounceTime, switchMap, take, map, count } from 'rxjs/operators';
import { ValidFormsService } from './../../shared/services/valid-forms.service';
import { FormBuilder } from '@angular/forms';
import { Component, OnInit } from '@angular/core';

import { Divida } from './../../shared/models/divida';

import { AlertServiceService } from './../../shared/services/alert-service.service';
import { BaseFormComponent } from 'src/app/shared/components/base-form/base-form.component';

@Component({
    selector: 'app-divida',
    templateUrl: './divida.component.html',
    styleUrls: ['./divida.component.css']
})
export class DividaComponent extends BaseFormComponent implements OnInit {

    public valormask = [/\d/, /\d/, ',', /\d/, /\d/, ' R$'];
    public totalDividas = 0;
    public totalPagamentos = 0;
    public dividasAtivas$: Observable<Divida[]>;
    public dividasOlds$: Observable<Divida[]>;
    public error$ = new Subject<boolean>();

    constructor(private formBuilder: FormBuilder,
        protected validFormsService: ValidFormsService,
        private dividaService: DividaService,
        private alertServiceService: AlertServiceService) {
        super(validFormsService);
    }

    ngOnInit(): void {
        this.formulario = this.createForm();
        this.dividasAtivas$ = this.listAllDividasAtivas();
        this.dividasOlds$ = this.listAllDividasOlds();
    }

    public submit() {
        throw new Error("Method not implemented.");
    }
    public createForm() {
        return this.formBuilder.group({});
    }
    public reseteForm() {
        throw new Error("Method not implemented.");
    }

    private listAllDividas(page = 0, linesPerPage = 5, direction = "DESC", orderBy = "createdAt"){
        return this.dividaService.listAllPage(page, linesPerPage, direction, orderBy)
        .pipe(
        //    tap(console.log),
            catchError(error => {
                this.error$.next(true);
                this.alertServiceService.ShowAlertDanger('Error ao carregar dividas. Tente novamente mais tarde.')
                return empty();
            }
        ));
    }

    public listAllDividasAtivas(page = 0, linesPerPage = 5, direction = "DESC", orderBy = "createdAt") {
        return this.listAllDividas(page, linesPerPage, direction, orderBy).pipe(
            // tap((page: any) => this.PageBandeiras = page),
            // tap((page: any) => this.lengthBandeiras = page.totalElements),
            map((page: any) => page.content),
            map((content: Divida[]) => content.filter(divida => !divida.paga)),
            tap((content: Divida[]) => content.map(divida => this.totalDividas += divida.valorDivida)),
            tap((content: Divida[]) => content.map(divida => this.totalPagamentos += divida.totalPagamentos))
        )
    }

    public listAllDividasOlds(page = 0, linesPerPage = 5, direction = "DESC", orderBy = "createdAt") {
        return this.listAllDividas(page, linesPerPage, direction, orderBy).pipe(
            // tap((page: any) => this.PageBandeiras = page),
            // tap((page: any) => this.lengthBandeiras = page.totalElements),
            map((page: any) => page.content),
            map((content: Divida[]) => content.filter(divida => divida.paga))
        )
    }
}

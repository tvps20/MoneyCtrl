import { Observable, Subject, empty, of } from 'rxjs';
import { catchError, tap, debounceTime, switchMap, take, map } from 'rxjs/operators';
import { Component, OnInit } from '@angular/core';
import { Validators, FormBuilder, FormControl } from '@angular/forms';
import { PageEvent } from '@angular/material/paginator';

import { Bandeira } from './../../shared/models/bandeira';
import { Cartao } from 'src/app/shared/models/cartao';
import { EntityType } from 'src/app/shared/util/enuns-type.enum';
import { FormValidations } from 'src/app/shared/util/form-validations';

import { BaseFormComponent } from 'src/app/shared/components/base-form/base-form.component';
import { CartaoService } from './services/cartao.service';
import { BandeiraService } from './services/bandeira.service';
import { AlertServiceService } from './../../shared/services/alert-service.service';
import { ValidFormsService } from 'src/app/shared/services/valid-forms.service';

@Component({
    selector: 'app-cartao',
    templateUrl: './cartao.component.html',
    styleUrls: ['./cartao.component.css']
})
export class CartaoComponent extends BaseFormComponent implements OnInit {

    public bandeiras$: Observable<Bandeira[]>;
    public cartoes$: Observable<Cartao[]>;
    public error$ = new Subject<boolean>();
    public submitte = false;
    public entitySelecionada: Cartao | Bandeira;
    // MatPaginator Cartao Inputs
    public lengthCartoes;
    public pageSizeCartoes = 5;
    public pageIndexCartoes = 0;
    public PageCartoes: any;
    public directionCartoes = false;
    public orderByCartoes = "nome"
    // MatPaginator Bandeira Inputs
    public lengthBandeiras;
    public pageSizeBandeiras = 5;
    public pageIndexBandeiras = 0;
    public PageBandeiras: any;
    public directionBandeiras = false;
    public orderByBandeiras = "createdAt"

    constructor(private formBuilder: FormBuilder,
        protected validFormsService: ValidFormsService,
        private bandeiraService: BandeiraService,
        private cartaoService: CartaoService,
        private alertServiceService: AlertServiceService) {
        super(validFormsService);
    }

    ngOnInit(): void {
        this.formulario = this.createForm();
        this.bandeiras$ = this.listAllBandeiras();
        this.cartoes$ = this.listAllCartoes();
    }

    public submit() {
        this.submitte = true;
        let newEntity = this.cartaoService.partoToEntity(this.formulario);
        this.create(newEntity, 'Cartão salvo com sucesso.', 'Error ao tentar salvar cartão')
    }

    private create(entity: Cartao, msgSuccess: string, msgError: string) {
        return this.cartaoService.create(entity).subscribe(
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
                this.cartoes$ = this.listAllCartoes();
                this.bandeiras$ = this.listAllBandeiras();
            }
        );
    }

    public reseteForm() {
        this.formulario.reset();
        this.formulario.get('novaBandeira').setValue(true);
    }

    public createForm() {
        return this.formBuilder.group({
            nome: [null, [FormValidations.onlyLetters, Validators.required, Validators.minLength(3), Validators.maxLength(12)], [this.validarNomeCartao.bind(this)]],
            bandeira: [null, [Validators.required, FormValidations.onlyLetters, Validators.minLength(3), Validators.maxLength(12)], [this.validarNomeBandeira.bind(this)]],
            bandeiraSelect: [{value: null, disabled: true}, Validators.required],
            novaBandeira: [true, Validators.required]
        });
    }

    public onDelete(entity: Cartao | Bandeira) {
        this.entitySelecionada = entity;
    }

    public confirmModal(event: any) {
        if (event === 'sim') {
            if (this.entitySelecionada.tipo === EntityType.CARTAO) {
                this.cartaoService.delete(this.entitySelecionada.id).subscribe(
                    success => {
                        this.alertServiceService.ShowAlertSuccess("Cartão apagado com sucesso.");
                    },
                    error => {
                        this.alertServiceService.ShowAlertDanger("Error ao tentar apagar cartão.");
                    },
                    () => {
                        this.pageIndexCartoes = this.lengthCartoes - 1 < this.pageSizeCartoes ? 0 :
                            this.PageCartoes.content.length - 1 <= 0 ? this.pageIndexCartoes - 1 : this.pageIndexCartoes;
                        this.cartoes$ = this.listAllCartoes(this.pageIndexCartoes, this.pageSizeCartoes);
                        this.bandeiras$ = this.listAllBandeiras();
                    }
                )
            } else {
                this.bandeiraService.delete(this.entitySelecionada.id).subscribe(
                    success => {

                        this.alertServiceService.ShowAlertSuccess("Bandeira apagado com sucesso.");
                    },
                    error => {
                        this.alertServiceService.ShowAlertDanger("Error ao tentar apagar bandeira.");
                    },
                    () => {
                        this.pageIndexBandeiras = this.lengthBandeiras - 1 < this.pageSizeBandeiras ? 0 :
                            this.PageBandeiras.content.length - 1 <= 0 ? this.pageIndexBandeiras - 1 : this.pageIndexBandeiras;
                        this.bandeiras$ = this.listAllBandeiras(this.pageIndexBandeiras, this.pageSizeBandeiras);
                        this.cartoes$ = this.listAllCartoes();
                    }
                )
            }
        }

        this.entitySelecionada = null;
    }

    public disableFields() {
        this.formulario.get('bandeira').markAsUntouched();
        if(!this.formulario.get('novaBandeira').value){
            this.formulario.get('bandeira').enable();
            this.formulario.get('bandeiraSelect').disable();
        } else {
            this.formulario.get('bandeira').disable();
            this.formulario.get('bandeiraSelect').enable();
        }
    }

    private validarNomeCartao(formControl: FormControl) {
        if (formControl.value !== '' && formControl.value !== null) {
            return formControl.valueChanges.pipe(
                debounceTime(300),
                switchMap(email => this.cartaoService.verificaNomeUnico(formControl.value)),
                take(1),
                map(nomeExist => nomeExist ? { valorUnique: true } : null)
            );
        }
        return of({});
    }

    private validarNomeBandeira(formControl: FormControl) {
        if (formControl.value !== '' && formControl.value !== null) {
            return formControl.valueChanges.pipe(
                debounceTime(300),
                switchMap(email => this.bandeiraService.verificaNomeUnico(formControl.value)),
                take(1),
                map(nomeExist => nomeExist ? { valorUnique: true } : null)
            );
        }
        return of({});
    }

    public changeListCartoes(pageEvent: PageEvent) {
        this.pageIndexCartoes = pageEvent.pageIndex;
        this.cartoes$ = this.listAllCartoes(pageEvent.pageIndex, pageEvent.pageSize);
    }

    public changeListBandeiras(pageEvent: PageEvent) {
        this.pageIndexBandeiras = pageEvent.pageIndex;
        this.bandeiras$ = this.listAllBandeiras(pageEvent.pageIndex, pageEvent.pageSize);
    }

    public listAllCartoes(page = 0, linesPerPage = 5, direction = "DESC", orderBy = "createdAt"){
        return this.cartaoService.listAllPage(page, linesPerPage, direction, orderBy)
            .pipe(
                tap(console.log),
                tap((page: any) => this.PageCartoes = page),
                tap((page: any) => this.lengthCartoes = page.totalElements),
                map((page: any) => page.content),
                catchError(error => {
                    this.error$.next(true);
                    this.alertServiceService.ShowAlertDanger('Error ao carregar cartoes. Tente novamente mais tarde.')
                    return empty();
                }
            ));
    }

    public listAllBandeiras(page = 0, linesPerPage = 5, direction = "DESC", orderBy = "createdAt"){
        return this.bandeiraService.listAllPage(page, linesPerPage, direction, orderBy)
            .pipe(
                tap(console.log),
                tap((page: any) => this.PageBandeiras = page),
                tap((page: any) => this.lengthBandeiras = page.totalElements),
                map((page: any) => page.content),
                catchError(error => {
                    this.error$.next(true);
                    this.alertServiceService.ShowAlertDanger('Error ao carregar bandeiras. Tente novamente mais tarde.')
                    return empty();
                }
            ));
    }
}

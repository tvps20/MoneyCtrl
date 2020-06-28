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
import { AlertService } from './../../shared/services/alert-service.service';
import { ValidFormsService } from 'src/app/shared/services/valid-forms.service';

@Component({
    selector: 'app-cartao',
    templateUrl: './cartao.component.html',
    styleUrls: ['./cartao.component.css']
})
export class CartaoComponent extends BaseFormComponent implements OnInit {

    public bandeiras$: Observable<Bandeira[]>;
    public bandeirasSelect$: Observable<Bandeira[]>;
    public cartoes$: Observable<Cartao[]>;
    public error$ = new Subject<boolean>();
    public submitte = false;
    public entitySelecionada: Cartao | Bandeira;

    // MatPaginator Cartões
    public lengthCartoes = 10;
    public pageSizeCartoes = 5;
    public pageIndexCartoes = 0;

    // MatPaginator Bandeiras
    public lengthBandeiras = 10;
    public pageSizeBandeiras = 5;
    public pageIndexBandeiras = 0;

    constructor(private formBuilder: FormBuilder,
        protected validFormsService: ValidFormsService,
        private bandeiraService: BandeiraService,
        private cartaoService: CartaoService,
        private alertServiceService: AlertService) {
        super(validFormsService);
    }

    ngOnInit(): void {
        this.formulario = this.createForm();
        this.cartoes$ = this.listAllCartoes();
        this.bandeiras$ = this.listAllBandeiras();
        this.bandeirasSelect$ = this.ListAllBandeirasSelect();
    }

    public submit() {
        this.submitte = true;
        let newEntity = this.cartaoService.parseToEntity(this.formulario);
        this.createEntity(newEntity, 'Cartão salvo com sucesso.', 'Error ao tentar salvar cartão')
    }

    public createEntity(entity: Cartao, msgSuccess: string, msgError: string) {
        return this.cartaoService.create(entity).subscribe(
            success => {
                this.reseteForm();
                this.submitte = false;
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

    public createForm() {
        return this.formBuilder.group({
            nome: [null, [FormValidations.onlyLetters, Validators.required, Validators.minLength(3), Validators.maxLength(12)], [this.validarNomeCartao.bind(this)]],
            bandeira: [{ value: null, disabled: true }, [Validators.required, FormValidations.onlyLetters, Validators.minLength(3), Validators.maxLength(12)], [this.validarNomeBandeira.bind(this)]],
            bandeiraId: [null, Validators.required],
            novaBandeira: [false, Validators.required]
        });
    }

    public reseteForm() {
        this.formulario.reset();
        this.formulario.get('novaBandeira').setValue(false);
    }

    public onDisableFields() {
        if (!this.formulario.get('novaBandeira').value) {
            this.formulario.get('bandeira').enable();
            this.formulario.get('bandeiraId').disable();
            this.formulario.get('bandeiraId').setValue(null);
        } else {
            this.formulario.get('bandeiraId').enable();
            this.formulario.get('bandeira').disable();
            this.formulario.get('bandeira').setValue(null);
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

    public onSelectedEntity(entity: Cartao | Bandeira) {
        this.entitySelecionada = entity;
    }

    public onDelete(event: any) {
        if (event === 'sim') {
            if (this.entitySelecionada.tipo === EntityType.CARTAO) {
                this.cartaoService.delete(this.entitySelecionada.id).subscribe(
                    success => {
                        this.alertServiceService.ShowAlertSuccess("Cartão apagado com sucesso.");
                    },
                    error => {
                        this.alertServiceService.ShowAlertDanger(error.error.message);
                    },
                    () => {
                        this.cartoes$ = this.listAllCartoes();
                        this.bandeiras$ = this.listAllBandeiras();
                        this.entitySelecionada = null;
                    }
                )
            } else {
                this.bandeiraService.delete(this.entitySelecionada.id).subscribe(
                    success => {

                        this.alertServiceService.ShowAlertSuccess("Bandeira apagado com sucesso.");
                    },
                    error => {
                        this.alertServiceService.ShowAlertDanger(error.error.message);
                    },
                    () => {
                        this.bandeiras$ = this.listAllBandeiras();
                        this.cartoes$ = this.listAllCartoes();
                        this.entitySelecionada = null;
                    }
                )
            }
        }
    }

    private listAllCartoes(direction = "DESC", orderBy = "createdAt") {
        return this.cartaoService.listAllPage(this.pageIndexCartoes, this.pageSizeCartoes, direction, orderBy).pipe(
            tap((page: any) => this.lengthCartoes = page.totalElements),
            map((page: any) => page.content),
            catchError(error => {
                this.error$.next(true);
                this.alertServiceService.ShowAlertDanger('Error ao carregar cartoes. Tente novamente mais tarde.')
                return empty();
            })
        );
    }

    private listAllBandeiras(direction = "DESC", orderBy = "createdAt") {
        return this.bandeiraService.listAllPage(this.pageIndexBandeiras, this.pageSizeBandeiras, direction, orderBy).pipe(
            tap((page: any) => this.lengthBandeiras = page.totalElements),
            map((page: any) => page.content),
            catchError(error => {
                this.error$.next(true);
                this.alertServiceService.ShowAlertDanger('Error ao carregar bandeiras. Tente novamente mais tarde.')
                return empty();
            })
        );
    }

    private ListAllBandeirasSelect(){
        return this.bandeiraService.listAll().pipe(
            catchError(error => {
                this.alertServiceService.ShowAlertDanger('Error ao carregar bandeiras. Tente novamente mais tarde.')
                return empty();
            }
        ));
    }

    public changeListCartoes(event: PageEvent) {
        this.pageSizeCartoes = event.pageSize;
        this.pageIndexCartoes = event.pageIndex;
        this.cartoes$ = this.listAllCartoes();
    }

    public changeListBandeiras(event: PageEvent) {
        this.pageSizeBandeiras = event.pageSize;
        this.pageIndexBandeiras = event.pageIndex;
        this.bandeiras$ = this.listAllBandeiras();
    }
}

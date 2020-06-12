import { CartaoService } from './services/cartao.service';
import { BandeiraService } from './services/bandeira.service';
import { Observable, Subject, empty, of } from 'rxjs';
import { catchError, tap, debounceTime, switchMap, take, map } from 'rxjs/operators';
import { Component, OnInit } from '@angular/core';
import { Validators, FormBuilder, FormControl } from '@angular/forms';

import { Bandeira } from './../../shared/models/bandeira';
import { FormValidations } from 'src/app/shared/util/form-validations';

import { BaseFormComponent } from 'src/app/shared/components/base-form/base-form.component';
import { AlertServiceService } from './../../shared/services/alert-service.service';
import { ValidFormsService } from 'src/app/shared/services/valid-forms.service';
import { Cartao } from 'src/app/shared/models/cartao';

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
        console.log('mandou')
    }

    public createForm() {
        return this.formBuilder.group({
            nome: [null, [FormValidations.onlyLetters, Validators.required, Validators.minLength(3), Validators.maxLength(12)]],
            bandeira: [null, [Validators.required, FormValidations.onlyLetters, Validators.minLength(3), Validators.maxLength(12)], [this.validarNome.bind(this)]],
            bandeiraSelect: [{value: null, disabled: true}, Validators.required],
            novaBandeira: [true, Validators.required]
        });
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

    private validarNome(formControl: FormControl) {
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

    public listAllBandeiras(){
        return this.bandeiraService.listAll()
            .pipe(
            //    tap(console.log),
                catchError(error => {
                    this.error$.next(true);
                    this.alertServiceService.ShowAlertDanger('Error ao carregar bandeiras. Tente novamente mais tarde.')
                    return empty();
                }
            ));
    }

    public listAllCartoes(){
        return this.cartaoService.listAll()
            .pipe(
            //    tap(console.log),
                catchError(error => {
                    this.error$.next(true);
                    this.alertServiceService.ShowAlertDanger('Error ao carregar cartoes. Tente novamente mais tarde.')
                    return empty();
                }
            ));
    }
}

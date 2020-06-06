import { Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, Validators, FormControl } from '@angular/forms';
import { map, catchError } from 'rxjs/operators';
import { Observable, empty, Subject, of } from 'rxjs';

import { User } from './../../shared/models/user';
import { Comprador } from './../../shared/models/comprador';
import { FormValidations } from 'src/app/shared/util/form-validations';

import { AlertServiceService } from './../../shared/services/alert-service.service';
import { UserCompradorService } from './user-comprador.service';
import { ValidFormsService } from 'src/app/shared/services/valid-forms.service';
import { BaseFormComponent } from 'src/app/shared/components/base-form/base-form.component';

@Component({
    selector: 'app-user-comprador',
    templateUrl: './user-comprador.component.html',
    styleUrls: ['./user-comprador.component.css']
})
export class UserCompradorComponent extends BaseFormComponent implements OnInit {

    public compradores$: Observable<Comprador[]>;
    public error$ = new Subject<boolean>();
    public submitte = false;
    public compradorSelecionado: Comprador;
    @ViewChild('myModal') myModal;


    constructor(private formBuilder: FormBuilder,
        public validFormsService: ValidFormsService,
        private userCompradorService: UserCompradorService,
        private alertServiceService: AlertServiceService) { super(validFormsService); }

    ngOnInit(): void {
        this.formulario = this.createForm();
        this.compradores$ = this.userCompradorService.listAllCompradores()
            .pipe(catchError(error => {
                this.error$.next(true);
                this.alertServiceService.ShowAlertDanger('Error ao carregar compradores. Tente novamente mais tarde.')
                return empty();
            }));
    }

    public submit() {
        let novoUser: User = this.userCompradorService.parseToComprador(this.formulario);
        console.log(novoUser);
        this.userCompradorService.create(novoUser).subscribe(
            success => {
                this.reseteForm();
                this.alertServiceService.ShowAlertSuccess("Comprador salvo com sucesso.")
            },
            error => {
                this.alertServiceService.ShowAlertDanger("Error ao tentar salvar comprador")
            }
        );
    }

    public onDelete(comprador: Comprador) {
        this.compradorSelecionado = comprador;
    }

    public confirmModal(event: any) {
        if (event === 'sim') {
            this.userCompradorService.delete(this.compradorSelecionado.id).subscribe(
                success => {
                    this.alertServiceService.ShowAlertSuccess("Comprador apagado com sucesso.")
                },
                error => {
                    this.alertServiceService.ShowAlertDanger("Error ao tentar apagar comprador")
                }
            )
        }
    }

    public createForm() {
        return this.formBuilder.group({
            nome: [null, [Validators.required, Validators.minLength(3), Validators.maxLength(20)]],
            sobrenome: [null, [Validators.minLength(3), Validators.maxLength(20)]],
            tipo: ["COMPRADOR", Validators.required],
            admin: [false, Validators.required],
            username: [null, [Validators.required, Validators.minLength(5), Validators.maxLength(10)], [this.validarUsername.bind(this)]],
            email: [null, Validators.email, [this.validarEmail.bind(this)]],
            senha: [null, [Validators.required]],
            confirmarSenha: [null, [FormValidations.equalsTo('senha'), Validators.required]],
            roles: [[]]
        });
    }

    public reseteForm() {
        this.formulario.reset();
        this.formulario.get('admin').setValue(false);
        this.formulario.get('tipo').setValue('COMPRADOR');
    }

    private validarEmail(formControl: FormControl) {
        if (formControl.value !== '' && formControl.value !== null) {
            console.log(formControl.value)
            return this.validFormsService.verificaEmail(formControl.value)
                .pipe(map(emailExist => emailExist ? { emailInvalido: true } : null));
        }
        return of({});
    }

    private validarUsername(formControl: FormControl) {
        if (formControl.value !== '' && formControl.value !== null) {
            return this.validFormsService.verificaUsername(formControl.value)
                .pipe(map(usernameExist => usernameExist ? { usernameInvalido: true } : null));
        }
        return of({});
    }
}

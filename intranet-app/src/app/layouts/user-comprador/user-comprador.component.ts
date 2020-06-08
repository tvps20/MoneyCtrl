import { UCType } from './../../shared/util/enuns-type.enum';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators, FormControl } from '@angular/forms';
import { map, catchError } from 'rxjs/operators';
import { Observable, empty, Subject, of } from 'rxjs';

import { User } from './../../shared/models/user';
import { Comprador } from './../../shared/models/comprador';
import { FormValidations } from 'src/app/shared/util/form-validations';

import { AlertServiceService } from './../../shared/services/alert-service.service';
import { UserCompradorService } from './services/user-comprador.service';
import { ValidFormsService } from 'src/app/shared/services/valid-forms.service';
import { BaseFormComponent } from 'src/app/shared/components/base-form/base-form.component';

@Component({
    selector: 'app-user-comprador',
    templateUrl: './user-comprador.component.html',
    styleUrls: ['./user-comprador.component.css']
})
export class UserCompradorComponent extends BaseFormComponent implements OnInit {

    public compradores$: Observable<Comprador[]>;
    public usuarios$: Observable<User[]>;
    public error$ = new Subject<boolean>();
    public submitte = false;
    public compradorSelecionado: Comprador;
    public usuarioSelecionado: User;


    constructor(private formBuilder: FormBuilder,
        protected validFormsService: ValidFormsService,
        private userCompradorService: UserCompradorService,
        private alertServiceService: AlertServiceService) { super(validFormsService); }

    ngOnInit(): void {
        this.formulario = this.createForm();
        this.compradores$ = this.listAllCompradores();
        this.usuarios$ = this.listAllUsers();
    }

    public submit() {
        this.submitte = true;
        let newEntity: Comprador | User = this.userCompradorService.parseToUserComprador(this.formulario);
        newEntity.tipo === UCType.COMPRADOR ? this.create(newEntity, 'Comprador salvo com sucesso.', 'Error ao tentar salvar comprador') :
            this.create(newEntity, 'Usuário salvo com sucesso.', 'Error ao tentar salvar usuário');
    }

    public onDelete(entity: Comprador | User) {
        if(entity.tipo === UCType.COMPRADOR){
            this.compradorSelecionado = <Comprador>entity;
        } else {
            this.usuarioSelecionado = entity;
        }
    }

    public confirmModal(event: any) {
        if (event === 'sim') {
            if(this.compradorSelecionado != null){
                this.userCompradorService.deleteComprador(this.compradorSelecionado.id).subscribe(
                    success => {
                        this.compradores$ = this.listAllCompradores();
                        this.alertServiceService.ShowAlertSuccess("Comprador apagado com sucesso.");
                    },
                    error => {
                        this.alertServiceService.ShowAlertDanger("Error ao tentar apagar comprador");
                    },
                    () => this.compradorSelecionado = null
                )
            } else {
                this.userCompradorService.deleteUser(this.usuarioSelecionado.id).subscribe(
                    success => {
                        this.usuarios$ = this.listAllUsers();
                        this.alertServiceService.ShowAlertSuccess("Usuário apagado com sucesso.");
                    },
                    error => {
                        this.alertServiceService.ShowAlertDanger("Error ao tentar apagar usuário");
                    },
                    () => this.usuarioSelecionado = null
                )
            }
        }
    }

    public createForm() {
        return this.formBuilder.group({
            nome: [null, [FormValidations.onlyLetters, Validators.required, Validators.minLength(3), Validators.maxLength(12)]],
            sobrenome: [null, [FormValidations.onlyLetters, Validators.minLength(3), Validators.maxLength(12)]],
            tipo: [UCType.COMPRADOR, Validators.required],
            admin: [false, Validators.required],
            username: [null, [FormValidations.notStartNumber, Validators.required, Validators.minLength(5), Validators.maxLength(10)], [this.validarUsername.bind(this)]],
            email: [null, Validators.email, [this.validarEmail.bind(this)]],
            senha: [null, [Validators.required, Validators.minLength(6)]],
            confirmarSenha: [null, [FormValidations.equalsTo('senha'), Validators.required]],
            roles: [[]]
        });
    }

    public reseteForm() {
        this.formulario.reset();
        this.formulario.get('admin').setValue(false);
        this.formulario.get('tipo').setValue(UCType.COMPRADOR);
    }

    private validarEmail(formControl: FormControl) {
        if (formControl.value !== '' && formControl.value !== null) {
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

    private listAllCompradores(){
        return this.userCompradorService.listAllCompradores()
        .pipe(catchError(error => {
            this.error$.next(true);
            this.alertServiceService.ShowAlertDanger('Error ao carregar compradores. Tente novamente mais tarde.')
            return empty();
        }));
    }

    private listAllUsers(){
        return this.userCompradorService.listAllUsers()
        .pipe(catchError(error => {
            this.error$.next(true);
            this.alertServiceService.ShowAlertDanger('Error ao carregar usuários. Tente novamente mais tarde.')
            return empty();
        }));
    }

    private create(entity: User | Comprador, msgSuccess: string, msgError: string){
        return this.userCompradorService.create(entity).subscribe(
            success => {
                this.reseteForm();
                entity instanceof Comprador ? this.compradores$ = this.listAllCompradores() :
                    this.usuarios$ = this.listAllUsers();
                this.alertServiceService.ShowAlertSuccess(msgSuccess);
            },
            error => {
                this.alertServiceService.ShowAlertDanger(msgError);
                this.submitte = false;
            },
            () => this.submitte = false
        );
    }
}

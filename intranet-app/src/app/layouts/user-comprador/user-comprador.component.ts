import { UCType } from './../../shared/util/enuns-type.enum';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators, FormControl } from '@angular/forms';
import { map, catchError, switchMap, debounceTime, take, tap } from 'rxjs/operators';
import { Observable, empty, Subject, of } from 'rxjs';
import { PageEvent } from '@angular/material/paginator';

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
    // MatPaginator Comprador Inputs
    public lengthCompradores;
    public pageSizeCompradores = 5;
    public pageIndexCompradores = 0;
    public PageCompradores: any;
    // MatPaginator Usuario Inputs
    public lengthUsuarios;
    public pageSizeUsuarios = 5;
    public pageIndexUsuarios = 0;
    public PageUsuarios: any;


    constructor(private formBuilder: FormBuilder,
        protected validFormsService: ValidFormsService,
        private userCompradorService: UserCompradorService,
        private alertServiceService: AlertServiceService) { super(validFormsService); }

    ngOnInit(): void {
        this.formulario = this.createForm();
        this.compradores$ = this.listAllCompradores();
        this.usuarios$ = this.listAllUsers();
    }

    public changeListComprador(pageEvent: PageEvent) {
        this.pageIndexCompradores = pageEvent.pageIndex;
        this.compradores$ = this.listAllCompradores(pageEvent.pageIndex, pageEvent.pageSize);
    }

    public changeListUsuario(pageEvent: PageEvent){
        this.pageIndexUsuarios = pageEvent.pageIndex;
        console.log(pageEvent)
        this.usuarios$ = this.listAllUsers(pageEvent.pageIndex, pageEvent.pageSize);
    }

    public submit() {
        this.submitte = true;
        let newEntity: Comprador | User = this.userCompradorService.parseToUserComprador(this.formulario);
        newEntity.tipo === UCType.COMPRADOR ? this.create(newEntity, 'Comprador salvo com sucesso.', 'Error ao tentar salvar comprador') :
            this.create(newEntity, 'Usuário salvo com sucesso.', 'Error ao tentar salvar usuário');
    }

    public onDelete(entity: Comprador | User) {
        if (entity.tipo === UCType.COMPRADOR) {
            this.compradorSelecionado = <Comprador>entity;
        } else {
            this.usuarioSelecionado = entity;
        }
    }

    public confirmModal(event: any) {
        if (event === 'sim') {
            if (this.compradorSelecionado != null) {
                this.userCompradorService.deleteComprador(this.compradorSelecionado.id).subscribe(
                    success => {
                        this.compradorSelecionado = null;
                        this.alertServiceService.ShowAlertSuccess("Comprador apagado com sucesso.");
                    },
                    error => {
                        this.alertServiceService.ShowAlertDanger("Error ao tentar apagar comprador");
                    },
                    () => {
                        this.pageIndexCompradores = this.lengthCompradores -1 < this.pageSizeCompradores ? 0 :
                            this.PageCompradores.content.length -1 <= 0 ? this.pageIndexCompradores -1 : this.pageIndexCompradores;
                        this.compradores$ = this.listAllCompradores(this.pageIndexCompradores, this.pageSizeCompradores);
                        this.usuarios$ = this.listAllUsers();
                    }
                )
            } else {
                this.userCompradorService.deleteUser(this.usuarioSelecionado.id).subscribe(
                    success => {
                        this.usuarioSelecionado = null
                        this.alertServiceService.ShowAlertSuccess("Usuário apagado com sucesso.");
                    },
                    error => {
                        this.alertServiceService.ShowAlertDanger("Error ao tentar apagar usuário");
                    },
                    () => {
                        this.pageIndexUsuarios = this.lengthUsuarios -1 < this.pageSizeUsuarios ? 0 :
                            this.PageUsuarios.content.length -1 <= 0 ? this.pageIndexUsuarios -1 : this.pageIndexUsuarios;
                        this.usuarios$ = this.listAllUsers(this.pageIndexUsuarios, this.pageSizeUsuarios);
                        this.compradores$ = this.listAllCompradores();
                    }
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
            password: [null, [Validators.required, Validators.minLength(6), Validators.maxLength(20)]],
            confirmarPassword: [null, [FormValidations.equalsTo('password'), Validators.required]]
        });
    }

    public reseteForm() {
        this.formulario.reset();
        this.formulario.get('admin').setValue(false);
        this.formulario.get('tipo').setValue(UCType.COMPRADOR);
    }

    private validarEmail(formControl: FormControl) {
        if (formControl.value !== '' && formControl.value !== null) {
            return formControl.valueChanges.pipe(
                debounceTime(300),
                switchMap(email => this.userCompradorService.verifacarEmailUnico(formControl.value)),
                take(1),
                map(usernameExist => usernameExist ? { usernameInvalido: true } : null)
            );
        }
        return of({});
    }

    private validarUsername(formControl: FormControl) {
        if (formControl.value !== '' && formControl.value !== null) {
            return formControl.valueChanges.pipe(
                debounceTime(300),
                switchMap(email => this.userCompradorService.verifacarUsernameUnico(formControl.value)),
                take(1),
                map(usernameExist => usernameExist ? { usernameInvalido: true } : null)
            );
        }
        return of({});
    }

    private listAllCompradores(page: number = 0, linesPerPage: number = 5) {
        return this.userCompradorService.listAllCompradoresPage(page, linesPerPage)
            .pipe(
                tap((page: any) => this.PageCompradores = page),
                tap((page: any) => this.lengthCompradores = page.totalElements),
                map((page: any) => page.content),
                catchError(error => {
                    this.error$.next(true);
                    this.alertServiceService.ShowAlertDanger('Error ao carregar compradores. Tente novamente mais tarde.')
                    return empty();
                    }
                ));
    }

    private listAllUsers(page: number = 0, linesPerPage: number = 5) {
        return this.userCompradorService.listAllUsersPage(page, linesPerPage)
            .pipe(
                tap((page: any) => this.PageUsuarios = page),
                tap((page: any) => this.lengthUsuarios= page.totalElements),
                map((page: any) => page.content),
                catchError(error => {
                    this.error$.next(true);
                    this.alertServiceService.ShowAlertDanger('Error ao carregar usuários. Tente novamente mais tarde.')
                    return empty();
                }
            ));
    }

    private create(entity: User | Comprador, msgSuccess: string, msgError: string) {
        return this.userCompradorService.create(entity).subscribe(
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
                this.compradores$ = this.listAllCompradores();
                this.usuarios$ = this.listAllUsers();
            }
        );
    }
}

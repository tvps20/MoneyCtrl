import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators, FormControl } from '@angular/forms';
import { map, catchError, switchMap, debounceTime, take, tap } from 'rxjs/operators';
import { Observable, empty, Subject, of } from 'rxjs';
import { PageEvent } from '@angular/material/paginator';

import { User } from './../../shared/models/user';
import { Comprador } from './../../shared/models/comprador';
import { EntityType } from './../../shared/util/enuns-type.enum';
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
    public entitySelecionada: Comprador | User;
    // MatPaginator Comprador Inputs
    public lengthCompradores;
    public pageSizeCompradores = 5;
    public pageIndexCompradores = 0;
    public PageCompradores: any;
    public directionCompradores = false;
    public orderByCompradores = "nome"
    // MatPaginator Usuario Inputs
    public lengthUsuarios;
    public pageSizeUsuarios = 5;
    public pageIndexUsuarios = 0;
    public PageUsuarios: any;
    public directionUsuarios = false;
    public orderByUsuarios = "createdAt"


    constructor(private formBuilder: FormBuilder,
        protected validFormsService: ValidFormsService,
        private userCompradorService: UserCompradorService,
        private alertServiceService: AlertServiceService) {
        super(validFormsService);
    }

    ngOnInit(): void {
        this.formulario = this.createForm();
        this.compradores$ = this.listAllCompradores();
        this.usuarios$ = this.listAllUsers();
    }

    public sortingTableUsuario(orderBy: string) {
        this.directionUsuarios = !this.directionUsuarios;
        this.orderByUsuarios = orderBy;
        let direction = this.directionUsuarios ? "ASC" : "DESC";
        this.usuarios$ = this.listAllUsers(this.pageIndexUsuarios, this.pageSizeUsuarios, direction, orderBy);
    }

    public sortingTableComprador(orderBy: string) {
        this.directionCompradores = !this.directionCompradores;
        this.orderByCompradores = orderBy;
        let direction = this.directionCompradores ? "ASC" : "DESC";
        if (orderBy === 'dividaTotal' || orderBy === 'creditoTotal') {
            this.compradores$ = this.listAllCompradores(this.pageIndexCompradores, this.pageSizeCompradores)
                .pipe(map(result => {
                    if (this.directionCompradores) {
                        return result.sort((a, b) => a[orderBy] < b[orderBy] ? -1 : 1)
                    } else {
                        return result.sort((a, b) => a[orderBy] > b[orderBy] ? -1 : 1)
                    }
                }));
        } else {
            this.compradores$ = this.listAllCompradores(this.pageIndexCompradores, this.pageSizeCompradores, direction, orderBy);
        }
    }

    public changeListComprador(pageEvent: PageEvent) {
        this.pageIndexCompradores = pageEvent.pageIndex;
        this.compradores$ = this.listAllCompradores(pageEvent.pageIndex, pageEvent.pageSize);
    }

    public changeListUsuario(pageEvent: PageEvent) {
        this.pageIndexUsuarios = pageEvent.pageIndex;
        this.usuarios$ = this.listAllUsers(pageEvent.pageIndex, pageEvent.pageSize);
    }

    public submit() {
        this.submitte = true;
        let newEntity: Comprador | User = this.userCompradorService.parseToUserComprador(this.formulario);
        newEntity.tipo === EntityType.COMPRADOR ? this.create(newEntity, 'Comprador salvo com sucesso.', 'Error ao tentar salvar comprador') :
            this.create(newEntity, 'Usuário salvo com sucesso.', 'Error ao tentar salvar usuário');
    }

    private create(entity: User | Comprador, msgSuccess: string, msgError: string) {
        return this.userCompradorService.create(entity).subscribe(
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
                this.compradores$ = this.listAllCompradores();
                this.usuarios$ = this.listAllUsers();
            }
        );
    }

    public onDelete(entity: Comprador | User) {
        this.entitySelecionada = entity;
    }

    public confirmModal(event: any) {
        if (event === 'sim') {
            if (this.entitySelecionada.tipo === EntityType.COMPRADOR) {
                this.userCompradorService.deleteComprador(this.entitySelecionada.id).subscribe(
                    success => {
                        this.alertServiceService.ShowAlertSuccess("Comprador apagado com sucesso.");
                    },
                    error => {
                        this.alertServiceService.ShowAlertDanger(error.error.message);
                    },
                    () => {
                        this.pageIndexCompradores = this.lengthCompradores - 1 < this.pageSizeCompradores ? 0 :
                            this.PageCompradores.content.length - 1 <= 0 ? this.pageIndexCompradores - 1 : this.pageIndexCompradores;
                        this.compradores$ = this.listAllCompradores(this.pageIndexCompradores, this.pageSizeCompradores);
                        this.usuarios$ = this.listAllUsers();
                    }
                )
            } else {
                this.userCompradorService.deleteUser(this.entitySelecionada.id).subscribe(
                    success => {
                        this.alertServiceService.ShowAlertSuccess("Usuário apagado com sucesso.");
                    },
                    error => {
                        this.alertServiceService.ShowAlertDanger(error.error.message);
                    },
                    () => {
                        this.pageIndexUsuarios = this.lengthUsuarios - 1 < this.pageSizeUsuarios ? 0 :
                            this.PageUsuarios.content.length - 1 <= 0 ? this.pageIndexUsuarios - 1 : this.pageIndexUsuarios;
                        this.usuarios$ = this.listAllUsers(this.pageIndexUsuarios, this.pageSizeUsuarios);
                        this.compradores$ = this.listAllCompradores();
                    }
                )
            }
        }

        this.entitySelecionada = null;
    }

    public createForm() {
        return this.formBuilder.group({
            nome: [null, [FormValidations.onlyLetters, Validators.required, Validators.minLength(3), Validators.maxLength(12)]],
            sobrenome: [null, [FormValidations.onlyLetters, Validators.minLength(3), Validators.maxLength(12)]],
            tipo: [EntityType.COMPRADOR, Validators.required],
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
        this.formulario.get('tipo').setValue(EntityType.COMPRADOR);
    }

    private validarEmail(formControl: FormControl) {
        if (formControl.value !== '' && formControl.value !== null) {
            return formControl.valueChanges.pipe(
                debounceTime(300),
                switchMap(email => this.userCompradorService.verifacarEmailUnico(formControl.value)),
                take(1),
                map(usernameExist => usernameExist ? { valorUnique: true } : null)
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
                map(usernameExist => usernameExist ? { valorUnique: true } : null)
            );
        }
        return of({});
    }

    private listAllCompradores(page = 0, linesPerPage = 5, direction = "DESC", orderBy = "createdAt"): Observable<Comprador[]> {
        return this.userCompradorService.listAllCompradoresPage(page, linesPerPage, direction, orderBy)
            .pipe(
                tap(console.log),
                tap((page: any) => this.PageCompradores = page),
                tap((page: any) => this.lengthCompradores = page.totalElements),
                map((page: any) => page.content),
                catchError(error => {
                    this.error$.next(true);
                    this.alertServiceService.ShowAlertDanger('Error ao carregar compradores. Tente novamente mais tarde.')
                    return empty();
                }
                )
            );
    }

    private listAllUsers(page = 0, linesPerPage = 5, direction = "DESC", orderBy = "createdAt"): Observable<User[]> {
        return this.userCompradorService.listAllUsersPage(page, linesPerPage, direction, orderBy)
            .pipe(
            //    tap(console.log),
                tap((page: any) => this.PageUsuarios = page),
                tap((page: any) => this.lengthUsuarios = page.totalElements),
                map((page: any) => page.content),
                catchError(error => {
                    this.error$.next(true);
                    this.alertServiceService.ShowAlertDanger('Error ao carregar usuários. Tente novamente mais tarde.')
                    return empty();
                }
            ));
    }
}

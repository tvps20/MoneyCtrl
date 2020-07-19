import { PageEvent } from '@angular/material/paginator';
import { Component, OnInit } from '@angular/core';
import { Observable, empty, Subject, of } from 'rxjs';
import { FormBuilder, Validators, FormControl } from '@angular/forms';
import { map, catchError, switchMap, debounceTime, take, tap } from 'rxjs/operators';

import { User } from './../../shared/models/user';
import { Comprador } from './../../shared/models/comprador';
import { EntityType } from './../../shared/util/enuns-type.enum';
import { FormValidations } from 'src/app/shared/util/form-validations';

import { AlertService } from './../../shared/services/alert-service.service';
import { ValidFormsService } from 'src/app/shared/services/valid-forms.service';
import { UserCompradorService } from './services/user-comprador.service';
import { BaseFormListComponent } from 'src/app/shared/components/base-form-list/base-form-list.component';

@Component({
    selector: 'app-user-comprador',
    templateUrl: './user-comprador.component.html',
    styleUrls: ['./user-comprador.component.css']
})
export class UserCompradorComponent extends BaseFormListComponent implements OnInit {

    public usuarios$: Observable<User[]>;
    public compradores$: Observable<Comprador[]>;
    public errorUsuarios$ = new Subject<boolean>();
    public entitySelecionada: Comprador | User;
    public errorCompradores$ = new Subject<boolean>();

    // MatPaginator Compradores
    public lengthCompradores = 0;
    public pageSizeCompradores = 5;
    public pageIndexCompradores = 0;
    public directionCompradores = false;
    public orderByCompradores = "nome"

    // MatPaginator Usuarios
    public lengthUsuarios = 0;
    public pageSizeUsuarios = 5;
    public pageIndexUsuarios = 0;
    public directionUsuarios = false;
    public orderByUsuarios = "createdAt"

    constructor(protected validFormsService: ValidFormsService,
        private userCompradorService: UserCompradorService,
        private alertServiceService: AlertService,
        private formBuilder: FormBuilder) {
        super(validFormsService);
    }

    ngOnInit(): void {
        this.usuarios$ = this.listAllUsers();
        this.formulario = this.createForm();
        this.compradores$ = this.listAllCompradores();
    }

    public submit() {
        this.submitte = true;
        let newEntity: Comprador | User = this.userCompradorService.parseToUserComprador(this.formulario);
        newEntity.tipo === EntityType.COMPRADOR ? this.createEntity(newEntity, 'Comprador salvo com sucesso.', 'Error ao tentar salvar comprador') :
            this.createEntity(newEntity, 'Usuário salvo com sucesso.', 'Error ao tentar salvar usuário');
    }

    public createEntity(entity: User | Comprador, msgSuccess: string, msgError: string) {
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

    public createForm() {
        return this.formBuilder.group({
            nome: [null, [FormValidations.onlyLetters, Validators.required, Validators.minLength(3), Validators.maxLength(12)]],
            sobrenome: [null, [FormValidations.onlyLetters, Validators.minLength(3), Validators.maxLength(12)]],
            tipo: [EntityType.COMPRADOR, Validators.required],
            admin: [false, Validators.required],
            username: [null, [FormValidations.notStartNumber, Validators.required, Validators.minLength(5), Validators.maxLength(10)], [this.validarUsername.bind(this)]],
            email: [null, Validators.email, [this.validarEmail.bind(this)]],
            password: [null, [Validators.required, Validators.minLength(6), Validators.maxLength(60)]],
            confirmarPassword: [null, [FormValidations.equalsTo('password'), Validators.required]]
        });
    }

    public defaultValuesForms() {
        this.formulario.get('admin').setValue(false);
        this.formulario.get('tipo').setValue(EntityType.COMPRADOR);
    }

    public onSelectedEntity(entity: User | Comprador) {
        this.entitySelecionada = entity;
    }

    public onDelete(event: any) {
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
                        this.compradores$ = this.listAllCompradores();
                        this.usuarios$ = this.listAllUsers();
                        this.entitySelecionada = null;
                    }
                )
            } else {
                console.log(this.entitySelecionada.username)
                console.log(this.entitySelecionada.username !== 'admin')
                if(this.entitySelecionada.username !== 'admin20'){
                    this.userCompradorService.deleteUser(this.entitySelecionada.id).subscribe(
                        success => {
                            this.alertServiceService.ShowAlertSuccess("Usuário apagado com sucesso.");
                        },
                        error => {
                            this.alertServiceService.ShowAlertDanger(error.error.message);
                        },
                        () => {
                            this.usuarios$ = this.listAllUsers();
                            this.compradores$ = this.listAllCompradores();
                            this.entitySelecionada = null;
                        }
                    )
                } else{
                    this.alertServiceService.ShowAlertWarning("O admin padrão do sistema não pode ser apagado.");
                    this.entitySelecionada = null;
                }
            }
        }
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

    private listAllCompradores(direction = "DESC", orderBy = "createdAt"): Observable<Comprador[]> {
        return this.userCompradorService.listAllCompradoresPage(this.pageIndexCompradores, this.pageSizeCompradores, direction, orderBy).pipe(
            tap((page: any) => this.lengthCompradores = page.totalElements),
            map((page: any) => page.content),
            catchError(error => {
                this.errorCompradores$.next(true);
                return empty();
            })
        );
    }

    private listAllUsers(direction = "DESC", orderBy = "createdAt"): Observable<User[]> {
        return this.userCompradorService.listAllUsersPage(this.pageIndexUsuarios, this.pageSizeUsuarios, direction, orderBy).pipe(
            tap((page: any) => this.lengthUsuarios = page.totalElements),
            map((page: any) => page.content),
            catchError(error => {
                this.errorUsuarios$.next(true);
                return empty();
            }
        ));
    }

    public sortingTableUsuario(orderBy: string) {
        this.directionUsuarios = !this.directionUsuarios;
        this.orderByUsuarios = orderBy;
        let direction = this.directionUsuarios ? "ASC" : "DESC";
        this.usuarios$ = this.listAllUsers(direction, orderBy);
    }

    public sortingTableComprador(orderBy: string) {
        this.directionCompradores = !this.directionCompradores;
        this.orderByCompradores = orderBy;
        let direction = this.directionCompradores ? "ASC" : "DESC";
        if (orderBy === 'dividaTotal' || orderBy === 'creditoTotal') {
            this.compradores$ = this.listAllCompradores().pipe(map(result => {
                if (this.directionCompradores) {
                    return result.sort((a, b) => a[orderBy] < b[orderBy] ? -1 : 1)
                } else {
                    return result.sort((a, b) => a[orderBy] > b[orderBy] ? -1 : 1)
                }
            }));
        } else {
            this.compradores$ = this.listAllCompradores(direction, orderBy);
        }
    }

    public changeListComprador(event: PageEvent) {
        this.pageSizeCompradores = event.pageSize;
        this.pageIndexCompradores = event.pageIndex;
        this.compradores$ = this.listAllCompradores();
    }

    public changeListUsuario(event: PageEvent) {
        this.pageSizeUsuarios = event.pageSize;
        this.pageIndexUsuarios = event.pageIndex;
        this.usuarios$ = this.listAllUsers();
    }
}

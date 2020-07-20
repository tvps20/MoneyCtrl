import { catchError, tap, map } from 'rxjs/operators';
import { PageEvent } from '@angular/material/paginator';
import { Observable, Subject, empty } from 'rxjs';
import { ActivatedRoute, Router } from '@angular/router';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';

import { Divida } from './../../../shared/models/divida';
import { Credito } from './../../../shared/models/credito';
import { Comprador } from './../../../shared/models/comprador';
import { EntityType } from 'src/app/shared/util/enuns-type.enum';
import { CotaComprador } from './../../../shared/models/cota';
import { FormValidations } from './../../../shared/util/form-validations';

import { AlertService } from './../../../shared/services/alert-service.service';
import { CompradorService } from './../services/comprador.service';
import { ValidFormsService } from './../../../shared/services/valid-forms.service';
import { BaseFormListComponent } from 'src/app/shared/components/base-form-list/base-form-list.component';

@Component({
    selector: 'app-comprador-detail',
    templateUrl: './comprador-detail.component.html',
    styleUrls: ['./comprador-detail.component.css']
})
export class CompradorDetailComponent extends BaseFormListComponent implements OnInit {

    public dividas: Divida[];
    public creditos: Credito[];
    public comprador: Comprador;
    public errorCotas$ = new Subject<boolean>();
    public compradorCotas$: Observable<CotaComprador[]>;
    public entitySelecionada: Credito | Divida;

    // MatPaginator Dividas
    public lengthDividas = 0;
    public pageSizeDividas = 5;
    public pageIndexDividas = 1;

    // MatPaginator Creditos
    public lengthCreditos = 0;
    public pageSizeCreditos = 5;
    public pageIndexCreditos = 1;

    constructor(protected validFormsService: ValidFormsService,
        private alertServiceService: AlertService,
        private compradroService: CompradorService,
        private formBuilder: FormBuilder,
        private route: ActivatedRoute,
        private router: Router) {
        super(validFormsService)
    }

    ngOnInit(): void {
        this.comprador = this.route.snapshot.data['comprador'];
        this.formulario = this.createForm();
        this.lengthDividas = this.comprador.dividas.length;
        this.lengthCreditos = this.comprador.creditos.length;
        this.compradorCotas$ = this.listAllCompradorCotas();
        this.dividas = this.paginate(this.comprador.dividas, this.pageSizeDividas, this.pageIndexDividas);
        this.creditos = this.paginate(this.comprador.creditos, this.pageSizeCreditos, this.pageIndexCreditos);
    }

    public submit() {
        this.submitte = true;
        let newEntity = this.compradroService.parseToCredito(this.formulario);
        this.createEntity(newEntity, "Crédito salvo com sucesso.", "Error ao tentar salvar crédito.")
    }

    public createEntity(entity: any, msgSuccess: string, msgError: string) {
        return this.compradroService.createCredito(entity).subscribe(
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
                this.buscarComprador();
            }
        );
    }

    public createForm() {
        return this.formBuilder.group({
            valor: [null, Validators.required],
            descricao: [null, [Validators.required, FormValidations.notStartNumber, Validators.minLength(5), Validators.maxLength(20)]],
            compradorId: [this.comprador.id]
        });
    }

    public defaultValuesForms() {
        this.formulario.get('compradorId').setValue(this.comprador.id);
    }

    public onSelectedEntity(entity: any) {
        this.entitySelecionada = entity;
    }

    public onDelete(event: any) {
        if (event === 'sim') {
            if(this.entitySelecionada.tipo === EntityType.CREDITO){
                this.compradroService.deleteCredito(this.entitySelecionada.id).subscribe(
                    success => {
                        this.alertServiceService.ShowAlertSuccess("Crédito apagado com sucesso.");
                    },
                    error => {
                        this.alertServiceService.ShowAlertDanger(error.error.message);
                    },
                    () => {
                        this.buscarComprador();
                        this.entitySelecionada = null;
                    }
                );
            }
        }
    }

    private buscarComprador() {
        this.compradroService.findById(this.comprador.id).subscribe(
            success => {
                this.comprador = success;
                this.lengthDividas = this.comprador.dividas.length;
                this.lengthCreditos = this.comprador.creditos.length;
                this.dividas = this.paginate(this.comprador.dividas, this.pageSizeDividas, this.pageIndexDividas);
                this.creditos = this.paginate(this.comprador.creditos, this.pageSizeCreditos, this.pageIndexCreditos);
            },
            error => {
                this.alertServiceService.ShowAlertDanger('Ocorreu um erro ao buscar informações do comprador.');
                this.router.navigate(['/user-comprador']);
            }
        );
    }

    private listAllCompradorCotas(){
        return this.compradroService.listAllCotas(this.comprador.id).pipe(
            map((dados: CotaComprador[]) => dados.sort((a, b) => a.cotas.length < b.cotas.length ? 1 : -1)),
            catchError(error => {
                this.errorCotas$.next(true);
                return empty();
            })
        )
    }

    public changeListDividas(event: PageEvent){
        this.pageSizeDividas = event.pageSize;
        this.pageIndexDividas = event.pageIndex +1;
        this.buscarComprador();
    }

    public changeListCreditos(event: PageEvent){
        this.pageSizeCreditos = event.pageSize;
        this.pageIndexCreditos = event.pageIndex +1;
        this.buscarComprador();
    }
}

import { FormBuilder, Validators } from '@angular/forms';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { Comprador } from './../../../shared/models/comprador';
import { Credito } from './../../../shared/models/credito';

import { BaseFormComponent } from 'src/app/shared/components/base-form/base-form.component';
import { CompradorService } from './../services/comprador.service';
import { ValidFormsService } from './../../../shared/services/valid-forms.service';
import { AlertService } from './../../../shared/services/alert-service.service';
import { FormValidations } from './../../../shared/util/form-validations';

@Component({
    selector: 'app-comprador-detail',
    templateUrl: './comprador-detail.component.html',
    styleUrls: ['./comprador-detail.component.css']
})
export class CompradorDetailComponent extends BaseFormComponent implements OnInit {

    public comprador: Comprador;
    public entitySelecionada: Credito;
    public submitte = false;

    constructor(private formBuilder: FormBuilder,
        protected validFormsService: ValidFormsService,
        private compradroService: CompradorService,
        private alertServiceService: AlertService,
        private route: ActivatedRoute) {
            super(validFormsService)
         }

    ngOnInit(): void {
        this.comprador = this.route.snapshot.data['comprador'];
        this.formulario = this.createForm();
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
                this.compradroService.findById(this.comprador.id).subscribe(
                    comprador => this.comprador = comprador
                );
            }
        );
    }

    public createForm() {
        return this.formBuilder.group({
            valor: [null, Validators.required],
            descricao: [null, [Validators.required, FormValidations.onlyLetters, Validators.minLength(5), Validators.maxLength(20)]],
            compradorId: [this.comprador.id]
        });
    }

    public reseteForm() {
        this.formulario.reset();
    }

    public onSelectedEntity(entity: any) {
        this.entitySelecionada = entity;
    }

    public onDelete(event: any) {
        if(event === 'sim'){
            this.compradroService.deleteCredito(this.entitySelecionada.id).subscribe(
                success => {
                    this.alertServiceService.ShowAlertSuccess("Crédito apagado com sucesso.");
                },
                error => {
                    this.alertServiceService.ShowAlertDanger(error.error.message);
                },
                () => {
                    this.compradroService.findById(this.comprador.id).subscribe(
                        comprador => this.comprador = comprador
                    );
                }
            );
        }
    }
}

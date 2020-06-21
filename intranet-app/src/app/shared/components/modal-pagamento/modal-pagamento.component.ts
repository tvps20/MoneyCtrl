import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';

import { Divida } from './../../models/divida';
import { Pagamento } from './../../models/pagamento';

import { ValidFormsService } from './../../services/valid-forms.service';
import { DividaService } from './../../../layouts/divida/services/divida.service';
import { AlertServiceService } from './../../services/alert-service.service';
declare var $: any;

@Component({
    selector: 'app-modal-pagamento',
    templateUrl: './modal-pagamento.component.html',
    styleUrls: ['./modal-pagamento.component.css']
})
export class ModalPagamentoComponent implements OnInit {

    @Input() identificador = "modalPagamento";
    @Input() divida: Divida;
    @Output() realizaPagamento = new EventEmitter();

    public formulario: FormGroup;
    public submitte = false;

    constructor(private formBuilder: FormBuilder,
        private dividaService: DividaService,
        private alertServiceService: AlertServiceService,
        private validFormsService: ValidFormsService) { }

    ngOnInit(): void {
        this.formulario = this.createForm();
    }

    public createForm() {
        return this.formBuilder.group({
            valor: [null, Validators.required],
            dividaId: [null]
        });
    }

    public reseteForm(){
        this.formulario.reset();
    }

    public onSubmit(){
        if(this.formulario.valid){
            this.submit();
        } else {
            this.validFormsService.verificarValidacoesForm(this.formulario);
        }
    }

    private submit(){
        this.submitte = true;
        this.formulario.get('dividaId').setValue(this.divida.id);
        let newEntity = this.dividaService.parteToPagamento(this.formulario);
        this.createEntity(newEntity, "Pagamento salvo com sucesso.", "Error ao tentar salvar pagamento.")
    }

    public createEntity(entity: Pagamento, msgSuccess: string, msgError: string){
        return this.dividaService.createPagamento(entity).subscribe(
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
                $(`#${this.identificador}`).modal('hide');
                this.realizaPagamento.emit(true);
            }
        );
    }

    public onClosedModal(){
        this.reseteForm();
        $(`#${this.identificador}`).modal('hide');
    }

    public verificarValidControl(control: string): boolean {
        let controlForm = this.formulario.get(control);
        return !controlForm.valid &&
            (controlForm.touched || controlForm.dirty);
    }

    public verificarValidControlSuccess(control: string): boolean {
        let controlForm = this.formulario.get(control);
        return controlForm.valid &&
            (controlForm.value !== null && controlForm.value !== '');
    }

    public errorMessage(control: string, label: string) {
        return this.validFormsService.errorMessage(this.formulario.get(control), label);
    }
}

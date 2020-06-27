import { FaturaService } from './../../services/fatura.service';
import { LancamentoService } from './../../services/lancamento.service';
import { LancamentoType } from './../../../../shared/util/enuns-type.enum';
import { catchError, tap, switchMap } from 'rxjs/operators';
import { Observable, empty, Subject } from 'rxjs';
import { CotaFatura } from './../../../../shared/models/cota';
import { ActivatedRoute } from '@angular/router';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators, FormArray, FormControl } from '@angular/forms';

import { Fatura } from './../../../../shared/models/fatura';
import { Comprador } from './../../../../shared/models/comprador';
import { Lancamento } from './../../../../shared/models/lancamento';

import { AlertService } from './../../../../shared/services/alert-service.service';
import { ValidFormsService } from './../../../../shared/services/valid-forms.service';
import { CompradorService } from './../../../user-comprador/services/comprador.service';
import { BaseFormComponent } from 'src/app/shared/components/base-form/base-form.component';

@Component({
    selector: 'app-fatura-detail',
    templateUrl: './fatura-detail.component.html',
    styleUrls: ['./fatura-detail.component.css'],
})
export class FaturaDetailComponent extends BaseFormComponent implements OnInit {

    public submitte = false;
    public step = 0;
    public panelOpenState = false;
    public fatura: Fatura;
    public cotas: CotaFatura[];
    public compradorSelect$: Observable<Comprador[]>;
    public lancamentoCotas: any[] = [];
    public entitySelecionada: Lancamento;
    public valorTotalCompradores = 0;

    constructor(private formBuilder: FormBuilder,
        protected validFormsService: ValidFormsService,
        private route: ActivatedRoute,
        private compradorService: CompradorService,
        private lancamentoService: LancamentoService,
        private faturaService: FaturaService,
        private alertServiceService: AlertService) {
        super(validFormsService);
    }

    ngOnInit(): void {
        this.fatura = this.route.snapshot.data['fatura'];
        this.cotas = this.route.snapshot.data['cotas'];
        this.compradorSelect$ = this.compradorService.listAll();
        this.formulario = this.createForm();
    }

    public submit() {
        this.formulario.setControl('compradores', this.formBuilder.array(this.lancamentoCotas));
        let newEntity = this.lancamentoService.parseToEntity(this.formulario);
        this.createEntity(newEntity, 'Lançamento salvo com sucesso.', 'Error ao tentar salvar lançamento')
    }

    public createEntity(entity: any, msgSuccess: string, msgError: string) {
        return this.lancamentoService.create(entity).subscribe(
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
                this.faturaService.findById(this.fatura.id).subscribe(
                    success => this.fatura = success
                );
                this.faturaService.listAllCotas(this.fatura.id).subscribe(
                    success => this.cotas = success
                );
            }
        );
    }

    public createForm() {
        return this.formBuilder.group({
            descricao: [null, [Validators.required, Validators.minLength(5), Validators.maxLength(20)]],
            tipoLancamento: [LancamentoType.AVISTA, Validators.required],
            parcelas: [{ value: null, disabled: true }, [Validators.required, Validators.minLength(4)]],
            dataCompra: [{ value: new Date(), disabled: true }, Validators.required],
            comprador: this.formBuilder.group({
                compradorId: [null],
                valor: [null]
            }),
            faturaId: [this.fatura.id],
            observacao: [null],
            compradores: this.formBuilder.array(this.lancamentoCotas)
        });
    }

    public onDisableFieldParcela(){
        this.formulario.get('parcelas').disable();
        this.formulario.get('parcelas').setValue(null);
    }

    public onEnableFieldParcela(){
        this.formulario.get('parcelas').enable();
    }

    get compradoresForm(): FormArray {
        return this.formulario.get("compradores") as FormArray;
    }

    public onAddCotaInLancamento() {
        if (this.formulario.get('comprador').valid) {
            let novoComprador = this.createCota();
            let comprador = this.searchCompradorInCotas(novoComprador.compradorId);
            if(comprador.length > 0){
                comprador[0].valor = parseFloat(comprador[0].valor) + parseFloat(novoComprador.valor);
            } else {
                this.lancamentoCotas.push(novoComprador)
            }

            this.valorTotalCompradores += parseFloat(novoComprador.valor);
            this.formulario.get('comprador').reset();
        }
    }

    private searchCompradorInCotas(compradorId){
        return this.lancamentoCotas.filter(x => x.compradorId === compradorId);
    }

    public onRevomeCotaInLancamento(compradorControl: any){
        let index = this.lancamentoCotas.indexOf(compradorControl);
        if(index > -1){
            let comprador: any = this.lancamentoCotas.splice(index, 1);
            this.valorTotalCompradores -= comprador[0].valor;
        }
    }

    private createCota() {
        return {
            compradorId: this.recuperarIdAndNameValueForm()[0],
            compradorNome: this.recuperarIdAndNameValueForm()[1],
            valor: this.formulario.get('comprador.valor').value
        }
    }

    private recuperarIdAndNameValueForm() {
        let aux: string = this.formulario.get('comprador.compradorId').value;
        return aux.split('/');
    }

    public reseteForm() {
        this.formulario.reset();
    }

    public setStep(index: number) {
        this.step = index;
    }

    public nextStep() {
        this.step++;
    }

    public prevStep() {
        this.step--;
    }

    public onSelectedEntity(entity: any) {
        this.entitySelecionada = entity;
    }

    public onDelete(event: any) {
        if (event === 'sim') {
            this.lancamentoService.delete(this.entitySelecionada.id).subscribe(
                success => {
                    this.alertServiceService.ShowAlertSuccess("Lançamento apagado com sucesso.");
                },
                error => {
                    this.alertServiceService.ShowAlertDanger(error.error.message);
                },
                () => {
                    this.faturaService.findById(this.fatura.id).subscribe(
                        success => this.fatura = success
                    );
                    this.faturaService.listAllCotas(this.fatura.id).subscribe(
                        success => this.cotas = success
                    );
                }
            )
        }
    }
}

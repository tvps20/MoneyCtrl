import { PageEvent } from '@angular/material/paginator';
import { catchError, tap, map } from 'rxjs/operators';
import { Component, OnInit } from '@angular/core';
import { Observable, empty, Subject } from 'rxjs';
import { ActivatedRoute, Router } from '@angular/router';
import { FormBuilder, Validators, FormArray } from '@angular/forms';

import { Fatura } from './../../../../shared/models/fatura';
import { Comprador } from './../../../../shared/models/comprador';
import { CotaFatura } from './../../../../shared/models/cota';
import { Lancamento } from './../../../../shared/models/lancamento';

import { AlertService } from './../../../../shared/services/alert-service.service';
import { FaturaService } from './../../services/fatura.service';
import { LancamentoType } from './../../../../shared/util/enuns-type.enum';
import { FormValidations } from 'src/app/shared/util/form-validations';
import { CompradorService } from './../../../user-comprador/services/comprador.service';
import { LancamentoService } from './../../services/lancamento.service';
import { ValidFormsService } from './../../../../shared/services/valid-forms.service';
import { BaseFormComponent } from 'src/app/shared/components/base-form/base-form.component';

@Component({
    selector: 'app-fatura-detail',
    templateUrl: './fatura-detail.component.html',
    styleUrls: ['./fatura-detail.component.css'],
})
export class FaturaDetailComponent extends BaseFormComponent implements OnInit {

    public step = 0;
    public fatura: Fatura;
    public submitte = false;
    public errorCotas$ = new Subject<boolean>();
    public lancamentos: Lancamento[] = [];
    public faturaCotas$: Observable<CotaFatura[]>;
    public panelOpenState = false;
    public lancamentoCotas: any[] = [];
    public compradorSelect$: Observable<Comprador[]>;
    public entitySelecionada: Lancamento;
    public valorTotalCompradores = 0;

    // MatPaginator Lançamentos
    public lengthLancamentos = 0;
    public pageSizeLancamentos = 5;
    public pageIndexLancamentos = 1;

    constructor( protected validFormsService: ValidFormsService,
        private alertServiceService: AlertService,
        private lancamentoService: LancamentoService,
        private compradorService: CompradorService,
        private faturaService: FaturaService,
        private formBuilder: FormBuilder,
        private route: ActivatedRoute,
        private router: Router) {
        super(validFormsService);
    }

    ngOnInit(): void {
        this.fatura = this.route.snapshot.data['fatura'];
        this.formulario = this.createForm();
        this.faturaCotas$ = this.listAllFaturaCotas();
        this.compradorSelect$ = this.listAllCompradores();
        this.lengthLancamentos = this.fatura.lancamentos.length;
        this.lancamentos = this.paginate(this.fatura.lancamentos, this.pageSizeLancamentos, this.pageIndexLancamentos);
        console.log(this.fatura)
    }

    public submit() {
        this.submitte = true;
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
                this.buscarFatura();
                this.faturaCotas$ = this.listAllFaturaCotas();
            }
        );
    }

    public createForm() {
        return this.formBuilder.group({
            descricao: [null, [Validators.required, Validators.minLength(5), Validators.maxLength(20), FormValidations.notStartNumber]],
            tipoLancamento: [LancamentoType.AVISTA, Validators.required],
            parcelas: [{ value: null, disabled: true }, [Validators.required, Validators.minLength(4)]],
            dataCompra: [{ value: new Date(), disabled: true }, Validators.required],
            comprador: this.formBuilder.group({
                compradorId: [null, FormValidations.validaArray('compradores')],
                valor: [null, FormValidations.validaArray('compradores')]
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
        if ((this.formulario.get('comprador.compradorId').value != null && this.formulario.get('comprador.compradorId').value != '')
            && (this.formulario.get('comprador.valor').value != null && this.formulario.get('comprador.valor').value != '')) {

            let novoComprador = this.createCota();
            let comprador = this.searchCompradorInCotas(novoComprador.compradorId);

            if(comprador.length > 0){
                comprador[0].valor = parseFloat(comprador[0].valor) + parseFloat(novoComprador.valor);
            } else {
                this.lancamentoCotas.push(novoComprador)
            }

            this.valorTotalCompradores += parseFloat(novoComprador.valor);
            this.formulario.setControl('compradores', this.formBuilder.array(this.lancamentoCotas));
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
            this.formulario.setControl('compradores', this.formBuilder.array(this.lancamentoCotas));
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
        this.formulario.get('tipoLancamento').setValue(LancamentoType.AVISTA);
        this.formulario.get('dataCompra').setValue(new Date());
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
                    this.buscarFatura();
                    this.faturaCotas$ = this.listAllFaturaCotas();
                    this.entitySelecionada = null;
                }
            )
        }
    }

    private buscarFatura(){
        this.faturaService.findById(this.fatura.id).subscribe(
            success => {
                this.fatura = success;
                this.lengthLancamentos = this.fatura.lancamentos.length;
                this.lancamentos = this.paginate(this.fatura.lancamentos, this.pageSizeLancamentos, this.pageIndexLancamentos);
            },
            error => {
                this.alertServiceService.ShowAlertDanger('Ocorreu um erro ao buscar informações da futura.');
                this.router.navigate(['/faturas']);
            }
        );
    }

    private listAllFaturaCotas(){
        return this.faturaService.listAllCotas(this.fatura.id).pipe(
            map((dados: CotaFatura[]) => dados.sort((a, b) => a.cotas.length < b.cotas.length ? 1 : -1)),
            catchError(error => {
                this.errorCotas$.next(true);
                return empty();
            })
        )
    }

    private listAllCompradores(){
        return this.compradorService.listAll().pipe(
            catchError(error => {
                this.alertServiceService.ShowAlertDanger('Ocorreu um erro ao buscar informações dos compradores no servidor.')
                return empty();
            })
        );
    }

    public changeListLancamentos(event: PageEvent) {
        this.pageSizeLancamentos = event.pageSize;
        this.pageIndexLancamentos = event.pageIndex +1;
        this.buscarFatura();
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
}

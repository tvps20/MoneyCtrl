import { PageEvent } from '@angular/material/paginator';
import { catchError } from 'rxjs/operators';
import { Component, OnInit } from '@angular/core';
import { Observable, empty } from 'rxjs';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';

import { Fatura } from './../../../shared/models/fatura';
import { Cartao } from './../../../shared/models/cartao';
import { StatusType } from 'src/app/shared/util/enuns-type.enum';

import { AlertService } from './../../../shared/services/alert-service.service';
import { FaturaService } from './../services/fatura.service';
import { CartaoService } from './../services/cartao.service';
import { ValidFormsService } from 'src/app/shared/services/valid-forms.service';
import { BaseFormComponent } from 'src/app/shared/components/base-form/base-form.component';

@Component({
    selector: 'app-cartao-detail',
    templateUrl: './cartao-detail.component.html',
    styleUrls: ['./cartao-detail.component.css']
})
export class CartaoDetailComponent extends BaseFormComponent implements OnInit {

    public cartao: Cartao;
    public submitte = false;
    public months$: Observable<any[]>;
    public faturas: Fatura[] = [];
    public entitySelecionada: Fatura;
    public totalFaturasAtivas = 0;

    // MatPaginator Faturas
    public lengthFaturas = 0;
    public pageSizeFaturas = 5;
    public pageIndexFaturas = 1;

    constructor(protected validFormsService: ValidFormsService,
        private alertServiceService: AlertService,
        private cartaoService: CartaoService,
        private faturaService: FaturaService,
        private formBuilder: FormBuilder,
        private route: ActivatedRoute,
        private router: Router) {
        super(validFormsService);
    }

    ngOnInit(): void {
        this.cartao = this.route.snapshot.data['cartao'];
        this.formulario = this.createForm();
        this.months$ = this.listAllMonths();
        this.lengthFaturas = this.cartao.faturas.length;
        this.faturas = this.paginate(this.cartao.faturas, this.pageSizeFaturas, this.pageIndexFaturas);
        this.calcularTotalFaturasAtivas();
    }

    public submit() {
        this.submitte = true;
        let newEntity = this.faturaService.parseToEntity(this.formulario);
        this.createEntity(newEntity, 'Fatura salva com sucesso.', 'Error ao tentar salvar fatura')
    }

    public createEntity(entity: any, msgSuccess: string, msgError: string) {
        return this.faturaService.create(entity).subscribe(
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
                this.buscarCartao();
            }
        );
    }

    public createForm() {
        return this.formBuilder.group({
            vencimento: [{ value: new Date(), disabled: true }, Validators.required],
            mesReferente: [null, Validators.required],
            cartaoId: [this.cartao.id, Validators.required],
            observacao: [null]
        });
    }

    public defaultValuesForms() {
        this.formulario.get('vencimento').setValue(new Date());
    }

    public onSelectedEntity(entity: Fatura) {
        this.entitySelecionada = entity;
    }

    public onDelete(event: any) {
        if (event === 'sim') {
            this.faturaService.delete(this.entitySelecionada.id).subscribe(
                success => {
                    this.alertServiceService.ShowAlertSuccess("Fatura apagada com sucesso.");
                },
                error => {
                    this.alertServiceService.ShowAlertDanger(error.error.message);
                },
                () => {
                    this.buscarCartao();
                    this.entitySelecionada = null;
                }
            )
        }
    }

    public onFecharFatura(event: any) {
        if (event === 'sim') {
            this.faturaService.fecharFatura(this.entitySelecionada.id).subscribe(
                success => {
                    this.alertServiceService.ShowAlertSuccess("Fatura fechada com sucesso.");
                },
                error => {
                    this.alertServiceService.ShowAlertDanger(error.error.message);
                },
                () => {
                    this.buscarCartao();
                    this.entitySelecionada = null;
                }
            )
        }
    }

    public onPagarFatura(event: any) {
        if (event === 'sim') {
            this.faturaService.pagarFatura(this.entitySelecionada.id).subscribe(
                success => {
                    this.alertServiceService.ShowAlertSuccess("Fatura paga com sucesso.");
                },
                error => {
                    this.alertServiceService.ShowAlertDanger(error.error.message);
                },
                () => {
                    this.buscarCartao();
                    this.entitySelecionada = null;
                }
            )
        }
    }

    private buscarCartao() {
        this.cartaoService.findById(this.cartao.id).subscribe(
            success => {
                this.cartao = success;
                this.lengthFaturas = this.cartao.faturas.length;
                this.faturas = this.paginate(this.cartao.faturas, this.pageSizeFaturas, this.pageIndexFaturas);
                this.calcularTotalFaturasAtivas();
            },
            error => {
                this.alertServiceService.ShowAlertDanger('Ocorreu um erro ao buscar informações da futura.');
                this.router.navigate(['/cartoes']);
            }
        );
    }

    private calcularTotalFaturasAtivas() {
        let auxTotalFaturas = 0;
        this.faturas.filter(x => x.status !== StatusType.PAGA).map(x => {
            auxTotalFaturas += x.valorTotal;
        })
        this.totalFaturasAtivas = auxTotalFaturas;
    }

    private listAllMonths(): Observable<any[]> {
        return this.faturaService.listAllMonths().pipe(
            catchError(error => {
                this.alertServiceService.ShowAlertDanger('Ocorreu um erro ao buscar informações dos meses no servidor.')
                return empty();
            })
        );
    }

    public changeListFaturas(event: PageEvent) {
        this.pageSizeFaturas = event.pageSize;
        this.pageIndexFaturas = event.pageIndex + 1;
        this.buscarCartao();
    }
}

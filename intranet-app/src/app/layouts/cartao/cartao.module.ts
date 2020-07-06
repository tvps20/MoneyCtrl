import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';

import { CartaoComponent } from './cartao.component';
import { FaturaComponent } from './fatura/fatura.component';
import { CartaoDetailComponent } from './cartao-detail/cartao-detail.component';
import { FaturaDetailComponent } from './fatura/fatura-detail/fatura-detail.component';
import { ModalLancamentoComponent } from './fatura/modal-lancamento/modal-lancamento.component';

import { ComponentsModule } from 'src/app/shared/components/components.module';
import { SharedImportModule } from 'src/app/shared/imports/shared-import.module';


@NgModule({
  declarations: [
    CartaoComponent,
    CartaoDetailComponent,
    FaturaComponent,
    FaturaDetailComponent,
    ModalLancamentoComponent
  ],
  imports: [
    CommonModule,
    RouterModule,
    SharedImportModule,
    ComponentsModule,
  ],
  exports: [
    CartaoComponent,
    CartaoDetailComponent,
    FaturaComponent,
    FaturaDetailComponent,
    ModalLancamentoComponent
  ]
})
export class CartaoModule { }

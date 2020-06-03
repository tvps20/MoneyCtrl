import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';

import { CartaoComponent } from './cartao.component';
import { CartaoDetailComponent } from './cartao-detail/cartao-detail.component';
import { FaturaComponent } from './fatura/fatura.component';
import { FaturaDetailComponent } from './fatura/fatura-detail/fatura-detail.component';

import { SharedImportModule } from 'src/app/shared/import/shared-import.module';
import { ComponentsModule } from 'src/app/components/components.module';


@NgModule({
  declarations: [
    CartaoComponent,
    CartaoDetailComponent,
    FaturaComponent,
    FaturaDetailComponent
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
    FaturaDetailComponent
  ]
})
export class CartaoModule { }

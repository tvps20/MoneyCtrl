import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';

import { CartaoDetailComponent } from './cartao-detail/cartao-detail.component';
import { FaturaComponent } from './fatura/fatura.component';
import { FaturaDetailComponent } from './fatura/fatura-detail/fatura-detail.component';

import { SharedImportModule } from '../shared-import/shared-import.module';


@NgModule({
  declarations: [
    CartaoDetailComponent,
    FaturaComponent,
    FaturaDetailComponent,
  ],
  imports: [
    CommonModule,
    RouterModule,
    SharedImportModule
  ]
})
export class CartaoModule { }

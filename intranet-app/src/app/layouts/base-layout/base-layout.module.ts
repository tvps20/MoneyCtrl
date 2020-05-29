import { FaturaDetailComponent } from './../cartao/fatura/fatura-detail/fatura-detail.component';
import { DashboardComponent } from './../dashboard/dashboard.component';
import { DividaComponent } from './../divida/divida.component';
import { FaturaComponent } from './../cartao/fatura/fatura.component';
import { CartaoDetailComponent } from './../cartao/cartao-detail/cartao-detail.component';
import { CartaoComponent } from './../cartao/cartao.component';
import { UserCompradorComponent } from './../user-comprador/user-comprador.component';
import { SharedImportModule } from 'src/app/shared-import/shared-import.module';
import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';

import { BaseLayoutRoutes } from './base-layout.routing';

import { CartaoModule } from '../cartao/cartao.module';
import { DashboardModule } from '../dashboard/dashboard.module';
import { DividaModule } from '../divida/divida.module';
import { UserCompradorModule } from '../user-comprador/user-comprador.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatRippleModule, MatNativeDateModule } from '@angular/material/core';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatTooltipModule } from '@angular/material/tooltip';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { TextMaskModule } from 'angular2-text-mask';
import { MatExpansionModule } from '@angular/material/expansion';
import { MatRadioModule } from '@angular/material/radio';


@NgModule({
  imports: [
    CommonModule,
    RouterModule.forChild(BaseLayoutRoutes),
    // SharedImportModule,

    DashboardModule,
    UserCompradorModule,
    CartaoModule,
    DividaModule,
  ],
  declarations: []
})

export class BaseLayoutModule { }

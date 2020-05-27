import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { AdminLayoutRoutes } from './admin-layout.routing';
import { DashboardComponent } from '../../dashboard/dashboard.component';
import { CartaoComponent } from '../../cartao/cartao.component';
import { UserCompradorComponent } from '../../user-comprador/user-comprador.component';
import { CartaoDetailComponent } from '../../cartao/cartao-detail/cartao-detail.component';
import { FaturaDetailComponent } from '../../cartao/cartao-detail/fatura-detail/fatura-detail.component';

import {MatButtonModule} from '@angular/material/button';
import {MatInputModule} from '@angular/material/input';
import {MatRippleModule} from '@angular/material/core';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatTooltipModule} from '@angular/material/tooltip';
import {MatSelectModule} from '@angular/material/select';
import {MatNativeDateModule} from '@angular/material/core';
import {MatDatepickerModule} from '@angular/material/datepicker';
import {MatCheckboxModule} from '@angular/material/checkbox';
import {TextMaskModule} from 'angular2-text-mask';
import {MatExpansionModule} from '@angular/material/expansion';

@NgModule({
  imports: [
    CommonModule,
    RouterModule.forChild(AdminLayoutRoutes),
    FormsModule,
    ReactiveFormsModule,
    MatButtonModule,
    MatRippleModule,
    MatFormFieldModule,
    MatInputModule,
    MatSelectModule,
    MatTooltipModule,
    MatNativeDateModule,
    MatDatepickerModule,
    MatCheckboxModule,
    TextMaskModule,
    MatExpansionModule
  ],
  declarations: [
    DashboardComponent,
    CartaoComponent,
    UserCompradorComponent,
    CartaoDetailComponent,
    FaturaDetailComponent
  ]
})

export class AdminLayoutModule {}

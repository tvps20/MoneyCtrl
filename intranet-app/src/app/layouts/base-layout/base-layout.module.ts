import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { BaseLayoutRoutes } from './base-layout.routing';
import { DashboardComponent } from '../../dashboard/dashboard.component';
import { CartaoComponent } from '../../cartao/cartao.component';
import { UserCompradorComponent } from '../../user-comprador/user-comprador.component';
import { DividaComponent } from '../../divida/divida.component';

import { CartaoModule } from 'src/app/cartao/cartao.module';

import { SharedImportModule } from 'src/app/shared-import/shared-import.module';

@NgModule({
  imports: [
    CommonModule,
    RouterModule.forChild(BaseLayoutRoutes),
    SharedImportModule,
    CartaoModule
  ],
  declarations: [
    DashboardComponent,
    CartaoComponent,
    UserCompradorComponent,
    DividaComponent
  ],
})

export class BaseLayoutModule {}

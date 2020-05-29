import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';

import { BaseLayoutRoutes } from './base-layout.routing';

import { SharedImportModule } from 'src/app/shared-import/shared-import.module';
import { CartaoModule } from '../cartao/cartao.module';
import { DashboardModule } from '../dashboard/dashboard.module';
import { DividaModule } from '../divida/divida.module';
import { UserCompradorModule } from '../user-comprador/user-comprador.module';


@NgModule({
  imports: [
    CommonModule,
    RouterModule.forChild(BaseLayoutRoutes),
    SharedImportModule,
    CartaoModule,
    UserCompradorModule,
    DashboardModule,
    DividaModule
  ],
  declarations: [],
})

export class BaseLayoutModule { }

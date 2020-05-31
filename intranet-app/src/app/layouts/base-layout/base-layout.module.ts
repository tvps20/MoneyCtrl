import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';

import { BaseLayoutRoutes } from './base-layout.routing';

import { CartaoModule } from '../cartao/cartao.module';
import { DashboardModule } from '../dashboard/dashboard.module';
import { DividaModule } from '../divida/divida.module';
import { UserCompradorModule } from '../user-comprador/user-comprador.module';


@NgModule({
  imports: [
    CommonModule,
    RouterModule.forChild(BaseLayoutRoutes),
    
    DashboardModule,
    UserCompradorModule,
    CartaoModule,
    DividaModule,
  ],
  declarations: []
})

export class BaseLayoutModule { }

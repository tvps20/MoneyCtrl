import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';

import { BaseLayoutRoutes } from './base-layout.routing';

import { CartaoModule } from './cartao/cartao.module';
import { DividaModule } from './divida/divida.module';
import { ContatoModule } from './contato/contato.module';
import { DashboardModule } from './dashboard/dashboard.module';
import { UserCompradorModule } from './user-comprador/user-comprador.module';


@NgModule({
    imports: [
        CommonModule,
        RouterModule.forChild(BaseLayoutRoutes),

        DashboardModule,
        UserCompradorModule,
        CartaoModule,
        DividaModule,
        ContatoModule
    ]
})

export class BaseLayoutModule { }

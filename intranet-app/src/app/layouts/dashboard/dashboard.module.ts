import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';

import { DashboardComponent } from './dashboard.component';

import { SharedImportModule } from 'src/app/shared/imports/shared-import.module';
import { ComponentsModule } from './../../shared/components/components.module';
import { ModalCotasComponent } from './../dashboard/modal-cotas/modal-cotas.component';


@NgModule({
    declarations: [
        DashboardComponent,
        ModalCotasComponent
    ],
    imports: [
        CommonModule,
        RouterModule,
        SharedImportModule,
        ComponentsModule,
    ],
    exports: [
        DashboardComponent
    ]
})
export class DashboardModule { }
